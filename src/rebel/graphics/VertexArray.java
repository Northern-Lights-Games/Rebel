package rebel.graphics;

import static org.lwjgl.opengl.GL46.*;

/***
 * Represents an OpenGL Vertex Array. This is a Disposable OpenGL object and will be disposed by the Window.
 */
public class VertexArray implements Disposable {
    private int myVao;

    private VertexAttribute[] vertexAttributes;
    private int stride = 0;




    public VertexArray() {
        Disposer.add(this);
        myVao = glGenVertexArrays();
    }

    public void bind(){
        glBindVertexArray(myVao);
    }


    public void build() {

        int pointer = 0;

        for(VertexAttribute vertexAttribute : vertexAttributes){

            glVertexAttribPointer(
                    vertexAttribute.index,
                    vertexAttribute.size,
                    GL_FLOAT,
                    vertexAttribute.normalized,
                    stride,
                    pointer
            );

            glEnableVertexAttribArray(vertexAttribute.index);

            pointer += vertexAttribute.size * Float.BYTES;
        }

    }

    public void setVertexAttributes(VertexAttribute... vertexAttributes) {
        this.vertexAttributes = vertexAttributes;
        for(VertexAttribute vertexAttribute : vertexAttributes){
            stride += vertexAttribute.size * Float.BYTES;
        }
    }

    public int getStride() {
        return stride;
    }

    @Override
    public void dispose() {
        glDeleteVertexArrays(myVao);
    }
}
