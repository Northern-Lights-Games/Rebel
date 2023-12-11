package rebel.engine;

import org.joml.*;
import org.lwjgl.BufferUtils;

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
    private Shader shader;

    private static int RECT = -1;
    private static int CIRCLE = -2;

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



        shader = new Shader(
                FileReader.readFile("shaders/BatchVertexShader.glsl"),
                FileReader.readFile("shaders/BatchFragmentShader.glsl")
        );





        shader.compile();
        shader.bind();

        shader.setMatrix4f("v_model", getTranslation());
        shader.setMatrix4f("v_view", getView());
        shader.setMatrix4f("v_projection", getProj());
        shader.setIntArray("u_textures", createTextureSlots());



        VertexArray vertexArray = new VertexArray();
        vertexArray.bind();

        //vertexDataLength should be determined by VAO and passed to VBO, changing 52 everywhere is annoying
        vertexBuffer = new VertexBuffer(1000, 52);

        vertexArray.build();

        vertexData = new float[vertexBuffer.getNumOfVertices() * vertexBuffer.getVertexDataSize()];
    }



    private int[] createTextureSlots() {



        int[] slots = new int[maxTextureSlots];

        for (int i = 0; i < maxTextureSlots; i++) {
            slots[i] = i;
        }

        System.out.println("Max rebel.engine.Texture Slots: " + maxTextureSlots);
        System.out.println(Arrays.toString(slots));


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
    private int textureDraws;

    private Matrix4f transform = new Matrix4f().identity();

    private float originX, originY;

    private static final Matrix4f identity = new Matrix4f().identity();

    public Matrix4f getTransform() {
        return transform;
    }

    public void setTransform(Matrix4f transform) {
        this.transform = transform;
    }

    public void rotate(float radians){
        setTransform(new Matrix4f().rotate(radians, 0, 0, 1));
    }

    public void resetTransform(){
        setTransform(identity);
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

        setOrigin(x1, y1 + (thickness / 2));
        rotate(angle);

        float hypotenuse = (float) Math.sqrt((dx * dx) + (dy * dy));

        drawFilledRect(x1, y1 - (thickness / 2), hypotenuse, thickness, color);

        resetTransform();
        setOrigin(0, 0);




    }

    public void drawTexture(float x, float y, float w, float h, Texture texture, Color color) {

        int slot = textureDraws;
        glActiveTexture(GL_TEXTURE0 + slot);
        texture.bind();

        drawQuad(x, y, w, h, slot, color, originX, originY);

        textureDraws++;

        if(textureDraws == maxTextureSlots)
            render("Next Batch Render [No more rebel.engine.Texture slots out of " + maxTextureSlots + "]");

    }

    public void drawFilledRect(float x, float y, float w, float h, Color color){
        drawQuad(x, y, w, h, RECT, color, originX, originY);
    }

    public void drawFilledEllipse(float x, float y, float w, float h, Color color) {
        drawQuad(x, y, w, h, CIRCLE, color, originX, originY);
    }


    private void drawQuad(float x, float y, float w, float h, int slot, Color color, float originX, float originY){
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


        {

            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 0] = topLeft.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 1] = topLeft.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 2] = (0f);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 3] = (0f);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 4] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 5] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 6] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 7] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 8] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 9] = (x);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 10] = (y);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 11] = (w);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 12] = (h);


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 13] = bottomLeft.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 14] = bottomLeft.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 15] = (0f);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 16] = (1f);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 17] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 18] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 19] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 20] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 21] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 22] = (x);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 23] = (y);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 24] = (w);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 25] = (h);


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 26] = bottomRight.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 27] = bottomRight.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 28] = (1f);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 29] = (1f);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 30] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 31] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 32] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 33] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 34] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 35] = (x);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 36] = (y);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 37] = (w);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 38] = (h);


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 39] = topRight.x;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 40] = topRight.y;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 41] = (1f);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 42] = (0f);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 43] = slot;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 44] = color.r;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 45] = color.g;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 46] = color.b;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 47] = color.a;
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 48] = (x);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 49] = (y);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 50] = (w);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 51] = (h);

        }
        quadIndex++;


        if(quadIndex == vertexBuffer.maxQuads()) render("Next Batch Render");
    }

    public void render(){
        render("Final Draw Call [rebel.engine.Renderer2D.render()]");
    }

    public ArrayList<String> renderCallNames = new ArrayList<>(50);

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
        textureDraws = 0;

    }

    public List<String> getRenderCalls(){
        return new ArrayList<>(renderCallNames);
    }




    public void clear(float r, float g, float b, float a) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(r, g, b, a);
    }

    public String getHardwareInfo() {
        return glGetString(GL_RENDERER);
    }


    public void finished() {
        renderCallNames.clear();
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
