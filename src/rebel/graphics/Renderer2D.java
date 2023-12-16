package rebel.graphics;

import org.joml.*;
import org.lwjgl.BufferUtils;
import rebel.FileReader;
import rebel.Rect2D;

import java.lang.Math;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class Renderer2D {
    private int width;
    private int height;
    private Matrix4f proj, view, translation;
    private VertexBuffer vertexBuffer;
    private float[] vertexData;
    private int maxTextureSlots;
    private Shader defaultShader, currentShader;
    private ArrayList<String> renderCallNames = new ArrayList<>(50);
    private static int RECT = -1;
    private static int CIRCLE = -2;
    private boolean debug = false;
    public Renderer2D(int width, int height) {
        this.width = width;
        this.height = height;

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        proj = new Matrix4f().ortho(0, width, height, 0, 0, 1);
        view = new Matrix4f().identity();
        translation = new Matrix4f().identity();


        IntBuffer d = BufferUtils.createIntBuffer(1);
        glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, d);
        maxTextureSlots  = d.get();



        defaultShader = new Shader(
                FileReader.readFile(Renderer2D.class.getClassLoader().getResourceAsStream("BatchVertexShader.glsl")),
                FileReader.readFile(Renderer2D.class.getClassLoader().getResourceAsStream("BatchFragmentShader.glsl"))
        );
        defaultShader.compile();


        currentShader = defaultShader;


        currentShader.bind();
        currentShader.setMatrix4f("v_model", getTranslation());
        currentShader.setMatrix4f("v_view", getView());
        currentShader.setMatrix4f("v_projection", getProj());
        currentShader.setIntArray("u_textures", createTextureSlots());



        VertexArray vertexArray = new VertexArray();
        vertexArray.bind();


        vertexArray.setVertexAttributes(
                new VertexAttribute(0, 2, false, "v_pos"),
                new VertexAttribute(1, 2, false, "v_uv"),
                new VertexAttribute(2, 1, false, "v_texindex"),
                new VertexAttribute(3, 4, false, "v_color"),
                new VertexAttribute(4, 2, false, "v_origin"),
                new VertexAttribute(5, 2, false, "v_size")
        );

        vertexBuffer = new VertexBuffer(1000, vertexArray.getStride());
        vertexArray.build();

        vertexData = new float[vertexBuffer.getNumOfVertices() * vertexBuffer.getVertexDataSize()];
    }

    public void setShader(Shader shader){

        if(currentShader != shader) {
            currentShader = shader;
            currentShader.bind();
            currentShader.setMatrix4f("v_model", getTranslation());
            currentShader.setMatrix4f("v_view", getView());
            currentShader.setMatrix4f("v_projection", getProj());
            currentShader.setIntArray("u_textures", createTextureSlots());
        }
    }

    public Shader getDefaultShader() {
        return defaultShader;
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
    public Matrix4f getView() {
        return view;
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
    public void rotate(float radians){
        setTransform(getTransform().mul(new Matrix4f().rotate(radians, 0, 0, 1)));
    }
    public void scale(float x, float y, float z){
        setTransform(getTransform().mul(new Matrix4f().scale(x, y, z)));
    }
    public void resetTransform(){
        setTransform(new Matrix4f().identity());
    }
    public void drawTexture(float x, float y, float w, float h, Texture texture){
        drawTexture(x, y, w, h, texture, Color.WHITE);
    }
    public void setOrigin(float x, float y){
        this.originX = x;
        this.originY = y;
    }
    public void drawRect(float x, float y, float w, float h, Color color, int thickness){

        //Left
        drawFilledRect(x - ((float) thickness / 2), y, thickness, h, color);
        //Top
        drawFilledRect(x, y - ((float) thickness / 2), w, thickness, color);
        //Bottom
        drawFilledRect(x, y - ((float) thickness / 2) + h, w, thickness, color);
        //Right
        drawFilledRect(x - ((float) thickness / 2) + w, y, thickness, h, color);

    }
    public void drawLine(float x1, float y1, float x2, float y2, Color color, int thickness){

        float dx = x2 - x1;
        float dy = y2 - y1;

        float angle = (float) Math.atan2(dy, dx);

        setOrigin(x1, y1);
        rotate(angle);

        float hypotenuse = (float) Math.sqrt((dx * dx) + (dy * dy));

        drawFilledRect(x1, y1 - (thickness / 2), hypotenuse, thickness, color);

        resetTransform();
        setOrigin(0, 0);




    }
    private Texture lastTexture;

    public void drawTexture(float x, float y, float w, float h, Texture texture, Color color){
        drawTexture(x, y, w, h, texture, color, new Rect2D(0, 0, 1, 1));
    }
    public void drawTexture(float x, float y, float w, float h, Texture texture, Color color, Rect2D rect2D) {

        int slot = nextTextureSlot;
        boolean isUniqueTexture = false;



        if(lastTexture == null) {
            glActiveTexture(GL_TEXTURE0 + slot);
            texture.bind();
            texture.setSlot(slot);
            lastTexture = texture;
            isUniqueTexture = true;
        }

        //Existing texture
        else if (lastTexture == texture) {
            slot = lastTexture.getSlot();
        }

        //Unique Texture
        else {
            glActiveTexture(GL_TEXTURE0 + slot);
            texture.bind();
            texture.setSlot(slot);
            lastTexture = texture;
            isUniqueTexture = true;
        }


        drawQuad(x, y, w, h, slot, color, originX, originY, rect2D);

        if(isUniqueTexture) nextTextureSlot++;

        if(nextTextureSlot == maxTextureSlots)
            render("Next Batch Render [No more rebel.engine.graphics.Texture slots out of " + maxTextureSlots + "]");

    }
    public void drawFilledRect(float x, float y, float w, float h, Color color){
        drawQuad(x, y, w, h, RECT, color, originX, originY, new Rect2D(0, 0, 1, 1));
    }
    public void drawFilledEllipse(float x, float y, float w, float h, Color color) {
        drawQuad(x, y, w, h, CIRCLE, color, originX, originY, new Rect2D(0, 0, 1, 1));
    }
    private void drawQuad(float x, float y, float w, float h, int slot, Color color, float originX, float originY, Rect2D region){
        //Translate back by origin (for rotation math)
        //This usually takes everything near (0, 0)
        Vector4f topLeft = new Vector4f(x - originX, y - originY, 0, 1);
        Vector4f topRight = new Vector4f(x + w - originX, y - originY, 0, 1);
        Vector4f bottomLeft = new Vector4f(x - originX, y + h - originY, 0, 1);
        Vector4f bottomRight = new Vector4f(x + w - originX, y + h - originY, 0, 1);

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

        float fx = topLeft.x;
        float fy = topLeft.y;
        float fw = topLeft.distance(topRight);
        float fh = topLeft.distance(bottomLeft);

        {



            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 0] = topLeft.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 1] = topLeft.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 2] = region.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 3] = region.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 4] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 5] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 6] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 7] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 8] = color.a;

            //These need to be changed to represent the new width/height if the quad is scaled
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 9]  = fx;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 10] = fy;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 11] = fw;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 12] = fh;


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 13] = bottomLeft.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 14] = bottomLeft.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 15] = region.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 16] = region.y + region.h;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 17] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 18] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 19] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 20] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 21] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 22] = fx;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 23] = fy;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 24] = fw;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 25] = fh;


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 26] = bottomRight.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 27] = bottomRight.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 28] = region.x + region.w;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 29] = region.y + region.h;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 30] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 31] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 32] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 33] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 34] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 35] = fx;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 36] = fy;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 37] = fw;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 38] = fh;


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 39] = topRight.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 40] = topRight.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 41] = region.x + region.w;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 42] = region.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 43] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 44] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 45] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 46] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 47] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 48] = fx;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 49] = fy;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 50] = fw;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 51] = fh;

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

    }
    public List<String> getRenderCalls(){
        return new ArrayList<>(renderCallNames);
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void clear(float r, float g, float b, float a) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(r, g, b, a);
    }
    public String getHardwareInfo() {
        return glGetString(GL_RENDERER);
    }

    public void drawText(float x, float y, String text, Color color, FontRes font) {

        for(char c : text.toCharArray()){

            if(c == '\n'){
                y += font.getHeight();
                x = 0;
                continue;
            }

            Texture texture = font.getGlyphs().get((int) c);
            drawTexture(x, y, texture.getWidth(), texture.getHeight(), texture, color);
            x += texture.getWidth();
        }

    }
}
