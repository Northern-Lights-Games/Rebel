import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

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

        //Vertex Texture Pos
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 52, 8);
        glEnableVertexAttribArray(1);

        //Vertex Texture ID
        glVertexAttribPointer(2, 1, GL_FLOAT, false, 52, 16);
        glEnableVertexAttribArray(2);

        //Vertex Color
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
