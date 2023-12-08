import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL43.*;

public class Renderer2D {
    private int width;
    private int height;

    private Matrix4f proj, view, translation;

    private VertexBuffer vertexBuffer;

    private float[] vertexData;
    private int maxTextureSlots;

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



        Shader shader = new Shader(
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

        System.out.println("Max Texture Slots: " + maxTextureSlots);
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

    public void drawTexture(float x, float y, float w, float h, Texture texture){
        drawTexture(x, y, w, h, texture, Color.WHITE);
    }


    public void drawTexture(float x, float y, float w, float h, Texture texture, Color color) {

        if(textureDraws >= maxTextureSlots){
            render("Next Batch Render [No more Texture slots out of " + maxTextureSlots + "]");
        }
        if(quadIndex >= vertexBuffer.maxQuads()) render("Next Batch Render");

        int slot = textureDraws;
        glActiveTexture(GL_TEXTURE0 + slot);
        texture.bind();

        {

            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 0] = (x);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 1] = (y);
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


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 13] = (x);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 14] = (y + h);
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


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 26] = (x + w);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 27] = (y + h);
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


            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 39] = (x + w);
            vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 40] = (y);
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
        textureDraws++;



    }

    public void drawFilledRect(float x, float y, float w, float h, Color color){
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 0] = (x);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 1] = (y);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 2] = (0f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 3] = (0f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 4] = -1;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 5] = color.r;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 6] = color.g;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 7] = color.b;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 8] = color.a;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 9] = (x);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 10] = (y);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 11] = (w);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 12] = (h);


        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 13] = (x);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 14] = (y + h);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 15] = (0f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 16] = (1f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 17] = -1;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 18] = color.r;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 19] = color.g;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 20] = color.b;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 21] = color.a;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 22] = (x);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 23] = (y);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 24] = (w);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 25] = (h);


        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 26] = (x + w);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 27] = (y + h);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 28] = (1f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 29] = (1f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 30] = -1;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 31] = color.r;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 32] = color.g;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 33] = color.b;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 34] = color.a;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 35] = (x);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 36] = (y);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 37] = (w);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 38] = (h);



        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 39] = (x + w);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 40] = (y);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 41] = (1f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 42] = (0f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 43] = -1;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 44] = color.r;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 45] = color.g;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 46] = color.b;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 47] = color.a;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 48] = (x);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 49] = (y);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 50] = (w);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 51] = (h);

        quadIndex++;

        if(quadIndex >= vertexBuffer.maxQuads()) render("Next Batch Render");
    }

    public void drawFilledEllipse(float x, float y, float w, float h, Color color) {
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 0] = (x);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 1] = (y);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 2] = (0f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 3] = (0f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 4] = -2;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 5] = color.r;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 6] = color.g;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 7] = color.b;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 8] = color.a;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 9] = (x);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 10] = (y);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 11] = (w);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 12] = (h);


        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 13] = (x);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 14] = (y + h);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 15] = (0f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 16] = (1f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 17] = -2;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 18] = color.r;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 19] = color.g;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 20] = color.b;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 21] = color.a;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 22] = (x);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 23] = (y);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 24] = (w);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 25] = (h);


        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 26] = (x + w);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 27] = (y + h);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 28] = (1f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 29] = (1f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 30] = -2;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 31] = color.r;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 32] = color.g;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 33] = color.b;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 34] = color.a;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 35] = (x);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 36] = (y);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 37] = (w);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 38] = (h);



        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 39] = (x + w);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 40] = (y);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 41] = (1f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 42] = (0f);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 43] = -2;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 44] = color.r;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 45] = color.g;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 46] = color.b;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 47] = color.a;
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 48] = (x);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 49] = (y);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 50] = (w);
        vertexData[(quadIndex * vertexBuffer.getVertexDataSize()) + 51] = (h);

        quadIndex++;

        if(quadIndex >= vertexBuffer.maxQuads()) render("Next Batch Render");

    }



    public void render(){
        render("Final Draw Call [Renderer2D.render()]");
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

    public void drawText(float x, float y, String text, Color color, RFont font) {

        for(char c : text.toCharArray()){
            int i = (int) c;
            Texture texture = font.getGlyphs().get(i);
            drawTexture(x, y, texture.getWidth(), texture.getHeight(), texture, color);
            x += texture.getWidth();

        }

    }
}
