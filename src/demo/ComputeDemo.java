package demo;

import rebel.gpu.ComputeShader;
import rebel.graphics.*;

import static org.lwjgl.opengl.GL46.*;

public class ComputeDemo {
    public static void main(String[] args) {
        Window window = new Window(640, 480, "Rebel");

        ComputeShader computeShader = new ComputeShader("#version 430 core\n" +
                "\n" +
                "layout (local_size_x = 1, local_size_y = 1, local_size_z = 1) in;\n" +
                "layout(rgba32f) uniform image2D imgOutput;\n" +
                "void main() {\n" +
                "    vec4 value = vec4(0.0, 0.0, 0.0, 1.0);\n" +
                "    ivec2 texelCoord = ivec2(gl_GlobalInvocationID.xy);\n" +
                "\t\n" +
                "    value.x = float(texelCoord.x)/(gl_NumWorkGroups.x);\n" +
                "    value.y = float(texelCoord.y)/(gl_NumWorkGroups.y);\n" +
                "\t\n" +
                "    imageStore(imgOutput, texelCoord, value);\n" +
                "}");
        computeShader.prepare();


        int textureID = glGenTextures();
        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA32F, 512, 512, 0, GL_RGBA,
                GL_FLOAT, 0);

        glBindImageTexture(1, textureID, 0, false, 0, GL_READ_ONLY, GL_RGBA32F);




        Renderer2D renderer2D = new Renderer2D(640, 480, true);



        while(!window.shouldClose()){

            computeShader.bind();
            glDispatchCompute(512, 512, 1);
            glMemoryBarrier(GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);



            int location = glGetUniformLocation(computeShader.getShaderProgram(), "imgOutput");
            System.out.println(location);
            glUniform1i(location, 1);



            //Set the current shader back to the Renderer2D
            //otherwise nothing will be drawn because we're still doing compute
            renderer2D.getCurrentShaderProgram().bind();
            renderer2D.clear(1f, 1f, 1f, 1f);
            glActiveTexture(GL_TEXTURE1);
            renderer2D.drawQuadGL(0, 0, 512, 512, 1, Color.WHITE, 0, 0, new Rect2D(0, 0, 1, 1), -1);
            renderer2D.render();



            window.update();
        }

        window.close();


    }
}
