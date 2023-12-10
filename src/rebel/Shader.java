package rebel;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL43.*;

public class Shader {
    private String vertexShaderSource;
    private String fragmentShaderSource;

    private int shaderProgram;

    public Shader(String vertexShaderSource, String fragmentShaderSource) {
        this.vertexShaderSource = vertexShaderSource;
        this.fragmentShaderSource = fragmentShaderSource;
    }

    public void compile(){
        int vertexShaderProg = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderProg, vertexShaderSource);
        glCompileShader(vertexShaderProg);

        System.out.println(glGetShaderInfoLog(vertexShaderProg));


        int fragmentShaderProg = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderProg, fragmentShaderSource);
        glCompileShader(fragmentShaderProg);

        System.out.println(glGetShaderInfoLog(fragmentShaderProg));


        shaderProgram = glCreateProgram();



        glAttachShader(shaderProgram, vertexShaderProg);
        glAttachShader(shaderProgram, fragmentShaderProg);


        glDeleteShader(vertexShaderProg);
        glDeleteShader(fragmentShaderProg);

        glLinkProgram(shaderProgram);

    }

    public void bind(){
        glUseProgram(shaderProgram);
    }

    public int getShaderProgram() {
        return shaderProgram;
    }

    public void setFloat(String name, float value){
        int location = glGetUniformLocation(shaderProgram, name);
        glUniform1f(location, value);
    }

    public void setMatrix4f(String name, Matrix4f proj) {
        int location = glGetUniformLocation(shaderProgram, name);

        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        proj.get(floatBuffer);

        glUniformMatrix4fv(location, false, floatBuffer);
    }

    public void setIntArray(String name, int[] array) {
        int location = glGetUniformLocation(shaderProgram, name);
        glUniform1iv(location, array);
    }

}
