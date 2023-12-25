package rebel.gpu;

import rebel.graphics.Disposable;
import rebel.graphics.Disposer;

import static org.lwjgl.opengl.GL46.*;


public class ComputeShader implements Disposable {

    private final String computeShaderSource;
    private int computeShaderID;
    private int computeShaderProgramID;

    public ComputeShader(String shaderSource){
        Disposer.add(this);
        this.computeShaderSource = shaderSource;
    }

    public void prepare(){
        computeShaderID = glCreateShader(GL_COMPUTE_SHADER);
        glShaderSource(computeShaderID, computeShaderSource);
        glCompileShader(computeShaderID);
        System.err.println(glGetShaderInfoLog(computeShaderID));

        computeShaderProgramID = glCreateProgram();
        glAttachShader(computeShaderProgramID, computeShaderID);
        glLinkProgram(computeShaderProgramID);
    }

    public void bind(){
        glUseProgram(computeShaderProgramID);
    }

    public int getShaderProgram() {
        return computeShaderProgramID;
    }
    @Override
    public void dispose() {
        glDeleteProgram(computeShaderProgramID);
    }
}
