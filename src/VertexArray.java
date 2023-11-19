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
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 20,  0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 20, 8);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 1, GL_FLOAT, false, 20, 16);
        glEnableVertexAttribArray(2);
    }
}
