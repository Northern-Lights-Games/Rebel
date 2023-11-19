import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL45.*;

public class VertexBuffer {
    private float[] vertices;
    private int[] indices;

    public int myVbo;
    public int myEbo;
    private int maxVertices = 1000;

    private int numOfVertices;

    public VertexBuffer(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;


        build();
    }

    public int getNumOfVertices() {
        return numOfVertices;
    }

    private void build() {



        myVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, myVbo);



        FloatBuffer verticesAsFloatBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesAsFloatBuffer.put(vertices);
        verticesAsFloatBuffer.flip();

        if(vertices.length == 0){
            glBufferData(GL_ARRAY_BUFFER, maxVertices * 20L, GL_DYNAMIC_DRAW);
            numOfVertices = maxVertices;
        }
        else {
            glBufferData(GL_ARRAY_BUFFER, verticesAsFloatBuffer, GL_STATIC_DRAW);
            numOfVertices = vertices.length;
        }

        myEbo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, myEbo);

        IntBuffer indicesAsIntBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesAsIntBuffer.put(indices);
        indicesAsIntBuffer.flip();

        if(indices.length == 0){
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, maxVertices * 6L, GL_DYNAMIC_DRAW);
        }
        else
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesAsIntBuffer, GL_STATIC_DRAW);


    }

    public void vertex(int index, float x, float y, float w, float h, Texture texture) {
        glBufferSubData(GL_ARRAY_BUFFER, index, new float[]{x, y, w, h, texture.slot});
    }
}
