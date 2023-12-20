package rebel.graphics;

import static org.lwjgl.opengl.GL46.*;

/***
 * Represents an OpenGL Vertex Buffer. This is a Disposable OpenGL object and will be disposed by the Window.
 * This class also manages the Index Buffer automatically
 */
public class VertexBuffer implements Disposable {
    public int myVbo;
    public int myEbo;

    //maxQuads * 4
    private int maxVertices;
    private int numOfVertices;
    private int vertexDataSize;

    public VertexBuffer(int maxQuads, int vertexDataSize) {
        Disposer.add(this);
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


    /***
     * Actually allocates space for the Vertex Buffer and Index Buffer
     */
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

    @Override
    public void dispose() {
        glDeleteBuffers(myVbo);
        glDeleteBuffers(myEbo);
    }
}
