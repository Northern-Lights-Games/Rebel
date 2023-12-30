package rebel.graphics;

import org.joml.*;
import org.lwjgl.BufferUtils;
import rebel.FileReader;

import java.lang.Math;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;
/***
 * A Renderer2D is a Batch Renderer responsible for drawing 2D Graphics to the screen. This manages lots of OpenGL state internally including the current shader
 * VertexArray, VertexBuffer, and the Model, View and Projection Matrices. It also includes some debugging utilities to track draw calls using setDebug()
 */
public class Renderer2D {
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
    private int RECT = -1;
    private int CIRCLE = -2;
    private boolean debug = false;
    private FastTextureLookup textureLookup;

    public Renderer2D(int width, int height, boolean msaa) {
        this.width = width;
        this.height = height;

        if(msaa)
            glEnable(GL_MULTISAMPLE);
        else
            glDisable(GL_MULTISAMPLE);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        proj = new Matrix4f().ortho(0, width, height, 0, 0, 1);
        camera = new Camera();
        translation = new Matrix4f().identity();


        IntBuffer d = BufferUtils.createIntBuffer(1);
        glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, d);
        maxTextureSlots  = d.get();
        textureLookup = new FastTextureLookup(maxTextureSlots);



        defaultShaderProgram = new ShaderProgram(
                FileReader.readFile(Renderer2D.class.getClassLoader().getResourceAsStream("BatchVertexShader.glsl")),
                FileReader.readFile(Renderer2D.class.getClassLoader().getResourceAsStream("BatchFragmentShader.glsl"))
        );
        defaultShaderProgram.prepare();


        currentShaderProgram = defaultShaderProgram;


        currentShaderProgram.bind();
        updateCamera2D();

        VertexArray vertexArray = new VertexArray();
        vertexArray.bind();


        vertexArray.setVertexAttributes(
                new VertexAttribute(0, 2, false, "v_pos"),
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

    public ShaderProgram getDefaultShader() {
        return defaultShaderProgram;
    }

    public ShaderProgram getCurrentShaderProgram() {
        return currentShaderProgram;
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
    public Camera getCamera2D() {
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
    public void rotate(float radians){
        setTransform(getTransform().mul(new Matrix4f().rotate(radians, 0, 0, 1)));
    }
    public void scale(float x, float y, float z){
        setTransform(getTransform().mul(new Matrix4f().scale(x, y, z)));
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

    public void setOrigin(Vector2f vector2f){
        this.originX = vector2f.x;
        this.originY = vector2f.y;
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
    public void drawLine(float x1, float y1, float x2, float y2, Color color, int thickness, boolean round){

        float dx = x2 - x1;
        float dy = y2 - y1;

        float angle = (float) Math.atan2(dy, dx);

        setOrigin(x1, y1);
        rotate(angle);

        float hypotenuse = (float) Math.sqrt((dx * dx) + (dy * dy));

        drawFilledRect(x1, y1 - (thickness / 2), hypotenuse, thickness, color);

        resetTransform();
        setOrigin(0, 0);

        if(round){
            drawFilledEllipse(x1 - (thickness / 2f), y1 - (thickness / 2f), thickness, thickness, color);
            drawFilledEllipse(x2 - (thickness / 2f), y2 - (thickness / 2f), thickness, thickness, color);
        }




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
    public void drawFilledRect(float x, float y, float w, float h, Color color){
        drawQuadGL(x, y, w, h, RECT, color, originX, originY, new Rect2D(0, 0, 1, 1), -1, false, false);
    }
    public void drawFilledEllipse(float x, float y, float w, float h, Color color) {
        drawQuadGL(x, y, w, h, CIRCLE, color, originX, originY, new Rect2D(0, 0, 1, 1), 1, false, false);
    }
    public void drawEllipse(float x, float y, float w, float h, Color color, float thickness) {
        drawQuadGL(x, y, w, h, CIRCLE, color, originX, originY, new Rect2D(0, 0, 1, 1), thickness, false, false);
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


        {
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 0] = topLeft.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 1] = topLeft.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 2] = copy.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 3] = copy.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 4] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 5] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 6] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 7] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 8] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 9] = thickness;


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 10] = bottomLeft.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 11] = bottomLeft.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 12] = copy.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 13] = copy.h;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 14] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 15] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 16] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 17] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 18] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 19] = thickness;


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 20] = bottomRight.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 21] = bottomRight.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 22] = copy.w;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 23] = copy.h;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 24] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 25] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 26] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 27] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 28] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 29] = thickness;


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 30] = topRight.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 31] = topRight.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 32] = copy.w;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 33] = copy.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 34] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 35] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 36] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 37] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 38] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 39] = thickness;

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

    public boolean isDebug() {
        return debug;
    }

    /***
     * Enables OpenGL draw call tracking
     * @param debug
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void clear(Color color) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(color.r, color.g, color.b, color.a);
    }

    public float getOriginX() {
        return originX;
    }

    public float getOriginY() {
        return originY;
    }

    /***
     * Returns information about the current OpenGL Renderer
     * @return
     */
    public String getHardwareInfo() {
        return glGetString(GL_RENDERER);
    }

    public void drawText(float x, float y, String text, Color color, Font2D font) {

        float xc = x;

        for(char c : text.toCharArray()){

            if(c == '\n'){
                y += font.getLineHeight();
                xc = x;
                continue;
            }

            Texture2D texture = font.getGlyphs().get((int) c);




            drawTexture(xc, y, texture.getWidth(), texture.getHeight(), texture, color);
            xc += texture.getWidth();
        }

    }
}
