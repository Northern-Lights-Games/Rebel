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
    private int vertexDataLength;

    public VertexBuffer(float[] vertices, int[] indices, int vertexDataLength) {
        this.vertices = vertices;
        this.indices = indices;
        this.vertexDataLength = vertexDataLength;


        build();
    }

    public int getNumOfVertices() {
        return numOfVertices;
    }

    public int getVertexDataLength() {
        return vertexDataLength;
    }

    private void build() {



        myVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, myVbo);



        FloatBuffer verticesAsFloatBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesAsFloatBuffer.put(vertices);
        verticesAsFloatBuffer.flip();

        if(vertices.length == 0){
            glBufferData(GL_ARRAY_BUFFER, maxVertices * vertexDataLength, GL_DYNAMIC_DRAW);
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

}
