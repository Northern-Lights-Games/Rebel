import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL43.*;

public class Renderer2D {
    private int width;
    private int height;

    private Matrix4f proj, view, translation;

    private VertexBuffer vertexBuffer;

    private float[] vertices;
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
        vertexBuffer = new VertexBuffer(52);

        vertexArray.build();

        vertices = new float[vertexBuffer.getNumOfVertices() * vertexBuffer.getVertexDataSize()];
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

    private int drawCalls;
    private int textureDraws;

    public void drawTexture(float x, float y, float w, float h, Texture texture){
        drawTexture(x, y, w, h, texture, Color.WHITE);
    }


    public void drawTexture(float x, float y, float w, float h, Texture texture, Color color) {

        if(texture.getSlot() >= maxTextureSlots) texture.findSlot();


        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 0] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 1] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 2] = (0f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 3] = (0f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 4] = texture.getSlot();
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 5] = color.r;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 6] = color.g;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 7] = color.b;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 8] = color.a;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 9] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 10] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 11] = (w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 12] = (h);


        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 13] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 14] = (y + h);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 15] = (0f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 16] = (1f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 17] = texture.getSlot();
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 18] = color.r;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 19] = color.g;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 20] = color.b;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 21] = color.a;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 22] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 23] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 24] = (w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 25] = (h);


        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 26] = (x + w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 27] = (y + h);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 28] = (1f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 29] = (1f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 30] = texture.getSlot();
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 31] = color.r;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 32] = color.g;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 33] = color.b;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 34] = color.a;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 35] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 36] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 37] = (w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 38] = (h);



        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 39] = (x + w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 40] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 41] = (1f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 42] = (0f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 43] = texture.getSlot();
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 44] = color.r;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 45] = color.g;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 46] = color.b;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 47] = color.a;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 48] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 49] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 50] = (w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 51] = (h);

        drawCalls++;
        textureDraws++;

        if(textureDraws >= maxTextureSlots){
            Texture.availableSlot = 0;
            render();
        }



        if(drawCalls >= vertexBuffer.maxQuads()) {
            Texture.availableSlot = 0;
            render();
        }

    }

    public void drawFilledRect(float x, float y, float w, float h, Color color){
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 0] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 1] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 2] = (0f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 3] = (0f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 4] = -1;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 5] = color.r;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 6] = color.g;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 7] = color.b;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 8] = color.a;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 9] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 10] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 11] = (w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 12] = (h);


        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 13] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 14] = (y + h);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 15] = (0f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 16] = (1f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 17] = -1;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 18] = color.r;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 19] = color.g;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 20] = color.b;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 21] = color.a;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 22] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 23] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 24] = (w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 25] = (h);


        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 26] = (x + w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 27] = (y + h);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 28] = (1f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 29] = (1f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 30] = -1;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 31] = color.r;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 32] = color.g;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 33] = color.b;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 34] = color.a;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 35] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 36] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 37] = (w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 38] = (h);



        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 39] = (x + w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 40] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 41] = (1f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 42] = (0f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 43] = -1;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 44] = color.r;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 45] = color.g;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 46] = color.b;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 47] = color.a;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 48] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 49] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 50] = (w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 51] = (h);

        drawCalls++;

        if(drawCalls >= vertexBuffer.maxQuads()) render();
    }

    public void drawFilledEllipse(float x, float y, float w, float h, Color color) {
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 0] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 1] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 2] = (0f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 3] = (0f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 4] = -2;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 5] = color.r;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 6] = color.g;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 7] = color.b;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 8] = color.a;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 9] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 10] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 11] = (w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 12] = (h);


        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 13] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 14] = (y + h);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 15] = (0f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 16] = (1f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 17] = -2;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 18] = color.r;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 19] = color.g;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 20] = color.b;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 21] = color.a;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 22] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 23] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 24] = (w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 25] = (h);


        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 26] = (x + w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 27] = (y + h);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 28] = (1f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 29] = (1f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 30] = -2;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 31] = color.r;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 32] = color.g;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 33] = color.b;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 34] = color.a;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 35] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 36] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 37] = (w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 38] = (h);



        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 39] = (x + w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 40] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 41] = (1f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 42] = (0f);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 43] = -2;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 44] = color.r;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 45] = color.g;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 46] = color.b;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 47] = color.a;
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 48] = (x);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 49] = (y);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 50] = (w);
        vertices[(drawCalls * vertexBuffer.getVertexDataSize()) + 51] = (h);

        drawCalls++;

        if(drawCalls >= vertexBuffer.maxQuads()) render();

    }


    public void render() {

        glBindBuffer(GL_ARRAY_BUFFER, getVertexBuffer().myVbo);



        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);


        int numOfIndices = drawCalls * 6;
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


        vertices = new float[vertexBuffer.getNumOfVertices() * vertexBuffer.getVertexDataSize()];
        drawCalls = 0;
        textureDraws = 0;


    }

    private void checkError(){
        int error = glGetError();

        if(error != 0){
            System.err.println("OpenGL Error: " + error);
            System.exit(1);
        }
    }


    public void clear(float r, float g, float b, float a) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(r, g, b, a);
    }

    public String getHardwareInfo() {
        return glGetString(GL_RENDERER);
    }


}
