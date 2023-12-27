package demo;

import rebel.FileReader;
import rebel.gpu.ComputeShader;
import rebel.gpu.ComputeTask;
import rebel.gpu.ComputeTexture2D;
import rebel.graphics.*;

import static org.lwjgl.opengl.GL46.*;

public class ComputeDemo {
    public static void main(String[] args) {
        Window window = new Window(640, 480, "Rebel");

        ComputeShader computeShader = new ComputeShader(
                FileReader.readFile(Renderer2D.class.getClassLoader().getResourceAsStream("ComputeParticlesShader.glsl"))
        );
        computeShader.prepare();

        ComputeTask task = new ComputeTask(computeShader);
        ComputeTexture2D computeTexture2D = new ComputeTexture2D(512, 512);


        Texture2D logo = new Texture2D("project/logo.png");
        Renderer2D renderer2D = new Renderer2D(640, 480, true);

        while(!window.shouldClose()){


            //Render
            renderer2D.clear(new Color(1f, 1f, 1f, 1f));
            task.dispatchComputeTask(computeTexture2D, 512, 512, 1);
            task.barrier(GL_SHADER_IMAGE_ACCESS_BARRIER_BIT);
            renderer2D.getCurrentShaderProgram().bind();




            renderer2D.drawTexture(0, 0, 512, 512, computeTexture2D);
            renderer2D.drawTexture(512, 0, 100, 100, logo, Color.WHITE);
            renderer2D.render();


            //Compute





            window.update();
        }

        window.close();


    }
}
