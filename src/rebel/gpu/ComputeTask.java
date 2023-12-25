package rebel.gpu;

import rebel.graphics.Texture2D;
import static org.lwjgl.opengl.GL46.*;


public class ComputeTask {
    private final ComputeShader ComputeShader;

    public ComputeTask(ComputeShader ComputeShader) {
        this.ComputeShader = ComputeShader;
    }

    public void dispatchComputeTask(Texture2D texture2D, int workGroupsX, int workGroupsY, int workGroupsZ) {
        glBindImageTexture(texture2D.getSlot(), texture2D.getTexID(), 0, false, 0, GL_READ_ONLY, GL_RGBA32F);
        ComputeShader.bind();
        int location = glGetUniformLocation(ComputeShader.getShaderProgram(), "imgOutput");
        glUniform1i(location, texture2D.getSlot());
        glDispatchCompute(workGroupsX, workGroupsY, workGroupsZ);
    }

    public void barrier(int type) {
        glMemoryBarrier(type);
    }
}
