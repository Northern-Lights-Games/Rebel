import static org.lwjgl.opengl.GL43.*;

public class VertexBuffer {
    public int myVbo;
    public int myEbo;

    //maxQuads * 4
    private int maxVertices;
    private int numOfVertices;
    private int vertexDataSize;

    public VertexBuffer(int maxQuads, int vertexDataSize) {
        this.vertexDataSize = vertexDataSize;
        maxVertices = maxQuads * 4;
        build();
    }

    public int getNumOfVertices() {
        return numOfVertices;
    }

    public int getVertexDataSize() {
        return vertexDataSize;
    }

    private void build() {



        myVbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, myVbo);
        glBufferData(GL_ARRAY_BUFFER, maxVertices * vertexDataSize * Float.BYTES, GL_DYNAMIC_DRAW);
        numOfVertices = maxVertices;

        myEbo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, myEbo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, (maxVertices / 4) * 6L * Integer.BYTES, GL_DYNAMIC_DRAW);

    }

    public int maxQuads(){
        return maxVertices / 4;
    }

}
