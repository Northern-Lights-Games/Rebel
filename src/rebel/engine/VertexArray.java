package rebel.engine;

import static org.lwjgl.opengl.GL46.*;

public class VertexArray {
    private int myVao;

    private VertexAttribute[] vertexAttributes;
    private int stride = 0;




    public VertexArray() {
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
}
