package rebel.graphics;

import org.joml.*;
import org.lwjgl.BufferUtils;
import rebel.FileReader;

import java.lang.Math;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class Renderer3D {
    private int width;
    private int height;
    private Matrix4f proj;
    private Camera camera;
    private Matrix4f translation;
    private VertexBuffer vertexBuffer;
    private float[] vertexData;
    private int maxTextureSlots;
    private ShaderProgram defaultShaderProgram, currentShaderProgram;
    private ArrayList<String> renderCallNames = new ArrayList<>(50);
    private boolean debug = false;
    private FastTextureLookup textureLookup;

    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    public Renderer3D(int width, int height, boolean msaa) {
        this.width = width;
        this.height = height;

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_NOTEQUAL);


        float aspectRatio = (float) getWidth() / getHeight();
        proj = new Matrix4f().perspective(FOV, aspectRatio,
                Z_NEAR, Z_FAR);
        camera = new Camera();
        translation = new Matrix4f().identity();


        IntBuffer d = BufferUtils.createIntBuffer(1);
        glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, d);
        maxTextureSlots  = d.get();
        textureLookup = new FastTextureLookup(maxTextureSlots);



        defaultShaderProgram = new ShaderProgram(
                FileReader.readFile(Renderer2D.class.getClassLoader().getResourceAsStream("3DBatchVertexShader.glsl")),
                FileReader.readFile(Renderer2D.class.getClassLoader().getResourceAsStream("3DBatchFragmentShader.glsl"))
        );

        defaultShaderProgram.prepare();
        currentShaderProgram = defaultShaderProgram;
        currentShaderProgram.bind();


        updateCamera2D();

        VertexArray vertexArray = new VertexArray();
        vertexArray.bind();


        vertexArray.setVertexAttributes(
                new VertexAttribute(0, 3, false, "v_pos"),
                new VertexAttribute(1, 2, false, "v_uv"),
                new VertexAttribute(2, 1, false, "v_texindex"),
                new VertexAttribute(3, 4, false, "v_color"),
                new VertexAttribute(4, 1, false, "v_thickness")
        );

        vertexBuffer = new VertexBuffer(1000, vertexArray.getStride());
        vertexArray.build();

        vertexData = new float[vertexBuffer.getNumOfVertices() * vertexBuffer.getVertexDataSize()];
    }

    /***
     * Sets the current shader. This shader must be compiled before calling this method!
     * @param shaderProgram
     */
    public void setShader(ShaderProgram shaderProgram){

        if(currentShaderProgram != shaderProgram) {
            currentShaderProgram = shaderProgram;
            currentShaderProgram.bind();
            updateCamera2D();
        }
    }

    public void updateCamera2D(){
        currentShaderProgram.setMatrix4f("v_model", getTranslation());
        currentShaderProgram.setMatrix4f("v_view", getView());
        currentShaderProgram.setMatrix4f("v_projection", getProj());
        currentShaderProgram.setIntArray("u_textures", createTextureSlots());
    }


    private int[] createTextureSlots() {
        int[] slots = new int[maxTextureSlots];
        for (int i = 0; i < maxTextureSlots; i++) {
            slots[i] = i;
        }
        return slots;
    }
    public VertexBuffer getVertexBuffer() {
        return vertexBuffer;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Matrix4f getProj() {
        return proj;
    }
    public Camera getCamera() {
        return camera;
    }
    public Matrix4f getView() {
        return camera.getViewMatrix();
    }
    public Matrix4f getTranslation() {
        return translation;
    }
    private int quadIndex;
    private int nextTextureSlot;
    private Matrix4f transform = new Matrix4f().identity();
    private float originX, originY;
    public Matrix4f getTransform() {
        return transform;
    }
    public void setTransform(Matrix4f transform) {
        this.transform = transform;
    }
    public void resetTransform(){
        setTransform(new Matrix4f().identity());
    }
    public void drawTexture(float x, float y, float w, float h, Texture2D texture){
        drawTexture(x, y, w, h, texture, Color.WHITE);
    }
    public void setOrigin(float x, float y){
        this.originX = x;
        this.originY = y;
    }




    public void drawTexture(float x, float y, float w, float h, Texture2D texture, Color color){
        drawTexture(x, y, w, h, texture, color, new Rect2D(0, 0, 1, 1), false, false);
    }
    public void drawTexture(float x, float y, float w, float h, Texture2D texture, Color color, Rect2D rect2D, boolean xFlip, boolean yFlip) {

        int slot = nextTextureSlot;
        boolean isUniqueTexture = false;



        //Existing texture
        if (textureLookup.hasTexture(texture)) {
            slot = textureLookup.getTexture(texture);
        }

        //Unique Texture
        else {
            glActiveTexture(GL_TEXTURE0 + slot);
            texture.bind();
            texture.setSlot(slot);
            textureLookup.registerTexture(texture, slot);
            isUniqueTexture = true;
        }


        drawQuadGL(x, y, w, h, slot, color, originX, originY, rect2D, -1, xFlip, yFlip);

        if(isUniqueTexture) nextTextureSlot++;

        if(nextTextureSlot == maxTextureSlots)
            render("Next Batch Render [No more rebel.engine.graphics.Texture slots out of " + maxTextureSlots + "]");

    }


    public void drawQuadGL(float x, float y, float w, float h, int slot, Color color, float originX, float originY, Rect2D region, float thickness, boolean xFlip, boolean yFlip){
        //Translate back by origin (for rotation math)
        //This usually takes everything near (0, 0)

        Rect2D copy = new Rect2D(region.x, region.y, region.w, region.h);

        if(xFlip){
            float temp = copy.x;
            copy.x = copy.w;
            copy.w = temp;
        }

        if(yFlip){
            float temp = copy.y;
            copy.y = copy.h;
            copy.h = temp;
        }

        float z = -0.25f;

        Vector4f topLeft = new Vector4f(x - originX, y - originY, z, 1);
        Vector4f topRight = new Vector4f(x + w - originX, y - originY, z, 1);
        Vector4f bottomLeft = new Vector4f(x - originX, y + h - originY, z, 1);
        Vector4f bottomRight = new Vector4f(x + w - originX, y + h - originY, z, 1);

        topLeft.mul(transform);
        topRight.mul(transform);
        bottomLeft.mul(transform);
        bottomRight.mul(transform);



        //Translate forward by origin back to the current position
        topLeft.x += originX;
        topRight.x += originX;
        bottomLeft.x += originX;
        bottomRight.x += originX;

        topLeft.y += originY;
        topRight.y += originY;
        bottomLeft.y += originY;
        bottomRight.y += originY;



        {
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 0] = topLeft.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 1] = topLeft.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 2] = topLeft.z;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 3] = copy.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 4] = copy.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 5] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 6] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 7] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 8] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 9] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 10] = thickness;


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 11] = bottomLeft.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 12] = bottomLeft.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 13] = bottomLeft.z;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 14] = copy.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 15] = copy.h;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 16] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 17] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 18] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 19] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 20] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 21] = thickness;


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 22] = bottomRight.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 23] = bottomRight.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 24] = bottomRight.z;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 25] = copy.w;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 26] = copy.h;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 27] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 28] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 29] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 30] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 31] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 32] = thickness;


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 33] = topRight.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 34] = topRight.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 35] = topRight.z;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 36] = copy.w;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 37] = copy.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 38] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 39] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 40] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 41] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 42] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 43] = thickness;


        }
        quadIndex++;


        if(quadIndex == vertexBuffer.maxQuads()) render("Next Batch Render");
    }
    public void render(){
        render("Final Draw Call [rebel.engine.graphics.Renderer2D.render()]");

        if(debug){
            System.out.println("Renderer2D (" + this + ") - Debug");

            for(String call : getRenderCalls()){
                System.out.print("\t" + call + "\n");
            }
            System.out.println("\n");
        }


        renderCallNames.clear();
    }

    public void render(String renderName) {
        renderCallNames.add(renderName);

        glBindBuffer(GL_ARRAY_BUFFER, getVertexBuffer().myVbo);



        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexData);


        int numOfIndices = quadIndex * 6;
        int[] indices = new int[numOfIndices];
        int offset = 0;

        for (int j = 0; j < numOfIndices; j += 6) {

            indices[j] =         offset;
            indices[j + 1] = 1 + offset;
            indices[j + 2] = 2 + offset;
            indices[j + 3] = 2 + offset;
            indices[j + 4] = 3 + offset;
            indices[j + 5] =     offset;

            offset += 4;
        }

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, getVertexBuffer().myEbo);
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, indices);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);


        vertexData = new float[vertexBuffer.getNumOfVertices() * vertexBuffer.getVertexDataSize()];
        quadIndex = 0;
        nextTextureSlot = 0;
        textureLookup.clear();

    }
    public List<String> getRenderCalls(){
        return new ArrayList<>(renderCallNames);
    }


    public void clear(Color color) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(color.r, color.g, color.b, color.a);
    }


}
