package rebel.gpu;

import rebel.graphics.Texture2D;
import static org.lwjgl.opengl.GL46.*;


public class GPUCompute {
    private final ComputeShader computeShader;

    public GPUCompute(ComputeShader computeShader) {
        this.computeShader = computeShader;
    }

    public void dispatchComputeTask(Texture2D texture2D, int workGroupsX, int workGroupsY, int workGroupsZ) {
        glBindImageTexture(texture2D.getSlot(), texture2D.getTexID(), 0, false, 0, GL_READ_ONLY, GL_RGBA32F);
        computeShader.bind();
        int location = glGetUniformLocation(computeShader.getShaderProgram(), "imgOutput");
        glUniform1i(location, texture2D.getSlot());
        glDispatchCompute(workGroupsX, workGroupsY, workGroupsZ);
    }

    public void barrier(int type) {
        glMemoryBarrier(type);
    }
}
