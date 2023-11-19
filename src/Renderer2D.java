import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;

public class Renderer2D {
    private int width;
    private int height;

    private Matrix4f proj, view, translation;

    private VertexBuffer vertexBuffer;

    private float[] vertices;

    public Renderer2D(int width, int height) {
        this.width = width;
        this.height = height;

        proj = new Matrix4f().ortho(0, 1920, 1080, 0, 0, 1);
        view = new Matrix4f().identity();
        translation = new Matrix4f().translation(0, 0, 0);



        Shader shader = new Shader(
                FileReader.readFile("shaders/BatchVertexShader.glsl"),
                FileReader.readFile("shaders/BatchFragmentShader.glsl")
        );





        shader.compile();
        shader.bind();

        shader.setMatrix4f("v_model", getTranslation());
        shader.setMatrix4f("v_view", getView());
        shader.setMatrix4f("v_projection", getProj());


        shader.setIntArray("u_textures", new int[]{0, 1});



        VertexArray vertexArray = new VertexArray();
        vertexArray.bind();

        vertexBuffer = new VertexBuffer(
                new float[]{

                },

                new int[]{  // note that we start from 0!

                }
        );

        vertexArray.build();

        vertices = new float[vertexBuffer.getNumOfVertices()];
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
    public void drawTexture(float x, float y, float w, float h, Texture texture) {


        vertices[(numOfDraws * 20) + 0] = (x);
        vertices[(numOfDraws * 20) + 1] = (y);
        vertices[(numOfDraws * 20) + 2] = (0f);
        vertices[(numOfDraws * 20) + 3] = (0f);
        vertices[(numOfDraws * 20) + 4] = ((float) texture.slot);

        vertices[(numOfDraws * 20) + 5] = (x);
        vertices[(numOfDraws * 20) + 6] = (y + h);
        vertices[(numOfDraws * 20) + 7] = (0f);
        vertices[(numOfDraws * 20) + 8] = (1f);
        vertices[(numOfDraws * 20) + 9] = ((float) texture.slot);

        vertices[(numOfDraws * 20) + 10] = (x + w);
        vertices[(numOfDraws * 20) + 11] = (y + h);
        vertices[(numOfDraws * 20) + 12] = (1f);
        vertices[(numOfDraws * 20) + 13] = (1f);
        vertices[(numOfDraws * 20) + 14] = ((float) texture.slot);

        vertices[(numOfDraws * 20) + 15] = (x + w);
        vertices[(numOfDraws * 20) + 16] = (y);
        vertices[(numOfDraws * 20) + 17] = (1f);
        vertices[(numOfDraws * 20) + 18] = (0f);
        vertices[(numOfDraws * 20) + 19] = ((float) texture.slot);

        numOfDraws++;
    }

    public void render() {

        glBindBuffer(GL_ARRAY_BUFFER, getVertexBuffer().myVbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);

        int numOfQuads = vertices.length / 20;
        int numOfIndices = numOfQuads * 6;


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

        vertices = new float[vertexBuffer.getNumOfVertices()];
        numOfDraws = 0;
    }


    public void clear(float r, float g, float b, float a) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(r, g, b, a);
    }
}
