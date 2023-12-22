package rebel.graphics;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;
/***
 * Represents an OpenGL Shader Program. This is a Disposable OpenGL object and will be disposed by the Window.
 */
public class ShaderProgram implements Disposable {
    private String vertexShaderSource;
    private String fragmentShaderSource;

    private int shaderProgram;

    /***
     * Create a Shader from the specified Vertex and Fragment Shader sources
     * @param vertexShaderSource
     * @param fragmentShaderSource
     */
    public ShaderProgram(String vertexShaderSource, String fragmentShaderSource) {
        Disposer.add(this);
        this.vertexShaderSource = vertexShaderSource;
        this.fragmentShaderSource = fragmentShaderSource;
    }

    /***
     * Compiles and Links the shader
     */

    public void prepare(){

        int vertexShaderProg = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderProg, vertexShaderSource);
        glCompileShader(vertexShaderProg);
        System.err.println(glGetShaderInfoLog(vertexShaderProg));


        int fragmentShaderProg = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderProg, fragmentShaderSource);
        glCompileShader(fragmentShaderProg);
        System.err.println(glGetShaderInfoLog(fragmentShaderProg));


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

    @Override
    public void dispose() {
        glDeleteProgram(shaderProgram);
    }
}
