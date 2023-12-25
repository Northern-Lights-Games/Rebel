package demo;

import rebel.gpu.ComputeShader;
import rebel.gpu.ComputeTask;
import rebel.gpu.ComputeTexture2D;
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

        ComputeTask task = new ComputeTask(computeShader);
        ComputeTexture2D computeTexture2D = new ComputeTexture2D(512, 512);


        Texture2D logo = new Texture2D("project/logo.png");
        Renderer2D renderer2D = new Renderer2D(640, 480, true);

        while(!window.shouldClose()){


            //Render
            renderer2D.clear(1f, 1f, 1f, 1f);
            renderer2D.drawTexture(0, 0, 512, 512, computeTexture2D);

            renderer2D.drawTexture(512, 0, 100, 100, logo, Color.WHITE);
            renderer2D.render();


            //Compute
            task.dispatchComputeTask(computeTexture2D, 512, 512, 1);
            task.barrier(GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);


            renderer2D.getCurrentShaderProgram().bind();



            window.update();
        }

        window.close();


    }
}
