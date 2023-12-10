package rebel;

import static org.lwjgl.opengl.GL43.*;

public class VertexArray {
    private int myVao;




    public VertexArray() {
        myVao = glGenVertexArrays();
    }

    public void bind(){
        glBindVertexArray(myVao);
    }


    public void build() {
        //Vertex Pos
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 52,  0);
        glEnableVertexAttribArray(0);

        //Vertex rebel.Texture Pos
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 52, 8);
        glEnableVertexAttribArray(1);

        //Vertex rebel.Texture ID
        glVertexAttribPointer(2, 1, GL_FLOAT, false, 52, 16);
        glEnableVertexAttribArray(2);

        //Vertex rebel.Color
        glVertexAttribPointer(3, 4, GL_FLOAT, false, 52, 20);
        glEnableVertexAttribArray(3);

        //Quad Pos
        glVertexAttribPointer(4, 2, GL_FLOAT, false, 52, 36);
        glEnableVertexAttribArray(4);

        //Quad Dimensions
        glVertexAttribPointer(5, 2, GL_FLOAT, false, 52, 44);
        glEnableVertexAttribArray(5);
    }
}
