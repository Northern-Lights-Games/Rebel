import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL30.*;

public class Renderer2D {
    private int width;
    private int height;

    private Matrix4f proj, view, translation;

    private VertexBuffer vertexBuffer;

    private float[] vertices;

    public Renderer2D(int width, int height) {
        this.width = width;
        this.height = height;

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        proj = new Matrix4f().ortho(0, width, height, 0, 0, 1);
        view = new Matrix4f().identity();
        translation = new Matrix4f().identity();





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
        vertexBuffer = new VertexBuffer(
                52
        );

        vertexArray.build();

        vertices = new float[vertexBuffer.getNumOfVertices()];
    }

    private int[] createTextureSlots() {

        IntBuffer maxTextureSlots = BufferUtils.createIntBuffer(1);
        glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, maxTextureSlots);
        int size = maxTextureSlots.get();

        int[] slots = new int[size];

        for (int i = 0; i < size; i++) {
            slots[i] = i;
        }

        System.out.println("Max Texture Slots: " + size);
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

    private int numOfDraws;

    public void drawTexture(float x, float y, float w, float h, Texture texture){
        drawTexture(x, y, w, h, texture, Color.WHITE);
    }


    public void drawTexture(float x, float y, float w, float h, Texture texture, Color color) {



        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 0] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 1] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 2] = (0f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 3] = (0f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 4] = texture.getSlot();
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 5] = color.r;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 6] = color.g;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 7] = color.b;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 8] = color.a;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 9] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 10] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 11] = (w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 12] = (h);


        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 13] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 14] = (y + h);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 15] = (0f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 16] = (1f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 17] = texture.getSlot();
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 18] = color.r;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 19] = color.g;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 20] = color.b;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 21] = color.a;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 22] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 23] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 24] = (w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 25] = (h);


        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 26] = (x + w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 27] = (y + h);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 28] = (1f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 29] = (1f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 30] = texture.getSlot();
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 31] = color.r;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 32] = color.g;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 33] = color.b;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 34] = color.a;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 35] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 36] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 37] = (w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 38] = (h);



        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 39] = (x + w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 40] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 41] = (1f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 42] = (0f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 43] = texture.getSlot();
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 44] = color.r;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 45] = color.g;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 46] = color.b;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 47] = color.a;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 48] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 49] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 50] = (w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 51] = (h);

        numOfDraws++;
    }

    public void drawFilledRect(float x, float y, float w, float h, Color color){
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 0] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 1] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 2] = (0f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 3] = (0f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 4] = -1;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 5] = color.r;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 6] = color.g;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 7] = color.b;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 8] = color.a;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 9] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 10] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 11] = (w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 12] = (h);


        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 13] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 14] = (y + h);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 15] = (0f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 16] = (1f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 17] = -1;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 18] = color.r;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 19] = color.g;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 20] = color.b;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 21] = color.a;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 22] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 23] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 24] = (w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 25] = (h);


        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 26] = (x + w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 27] = (y + h);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 28] = (1f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 29] = (1f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 30] = -1;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 31] = color.r;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 32] = color.g;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 33] = color.b;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 34] = color.a;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 35] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 36] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 37] = (w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 38] = (h);



        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 39] = (x + w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 40] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 41] = (1f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 42] = (0f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 43] = -1;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 44] = color.r;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 45] = color.g;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 46] = color.b;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 47] = color.a;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 48] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 49] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 50] = (w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 51] = (h);

        numOfDraws++;
    }

    public void drawFilledEllipse(float x, float y, float w, float h, Color color) {
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 0] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 1] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 2] = (0f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 3] = (0f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 4] = -2;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 5] = color.r;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 6] = color.g;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 7] = color.b;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 8] = color.a;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 9] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 10] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 11] = (w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 12] = (h);


        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 13] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 14] = (y + h);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 15] = (0f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 16] = (1f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 17] = -2;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 18] = color.r;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 19] = color.g;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 20] = color.b;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 21] = color.a;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 22] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 23] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 24] = (w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 25] = (h);


        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 26] = (x + w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 27] = (y + h);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 28] = (1f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 29] = (1f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 30] = -2;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 31] = color.r;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 32] = color.g;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 33] = color.b;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 34] = color.a;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 35] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 36] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 37] = (w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 38] = (h);



        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 39] = (x + w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 40] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 41] = (1f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 42] = (0f);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 43] = -2;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 44] = color.r;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 45] = color.g;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 46] = color.b;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 47] = color.a;
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 48] = (x);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 49] = (y);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 50] = (w);
        vertices[(numOfDraws * vertexBuffer.getVertexDataLength()) + 51] = (h);

        numOfDraws++;
    }


    public void render() {

        glBindBuffer(GL_ARRAY_BUFFER, getVertexBuffer().myVbo);

        {

            IntBuffer size = BufferUtils.createIntBuffer(1);
            glGetBufferParameteriv(GL_ARRAY_BUFFER, GL_BUFFER_SIZE, size);
            System.out.println("Size: " + size.get() + " " + vertices.length);
        }











        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);


        int numOfIndices = 6 * 6;


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

        vertices = new float[vertexBuffer.getNumOfVertices() * vertexBuffer.getVertexDataLength()];
        numOfDraws = 0;
    }


    public void clear(float r, float g, float b, float a) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(r, g, b, a);
    }

    public String getHardwareInfo() {
        return glGetString(GL_RENDERER);
    }


}
