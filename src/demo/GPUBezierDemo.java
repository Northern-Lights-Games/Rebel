package demo;

import rebel.FileReader;
import rebel.Input;
import rebel.Math;
import rebel.Time;
import rebel.graphics.*;

public class GPUBezierDemo {
    public static void main(String[] args) {
        Window window = new Window(1920, 1080, "GPUBezierDemo");
        Renderer2D renderer2D = new Renderer2D(1920, 1080);


        Shader shader = new Shader(
                FileReader.readFile(Renderer2D.class.getClassLoader().getResourceAsStream("GPUBezierVertexShader.glsl")),
                FileReader.readFile(Renderer2D.class.getClassLoader().getResourceAsStream("GPUBezierFragmentShader.glsl"))
        );
        shader.compile();

        FontRes fontRes = new FontRes("Arial", FontRes.NORMAL, 20, true);



        float t = 0;

        while(!window.shouldClose()){
            renderer2D.clear(0f, 0f, 0f, 0f);
            renderer2D.setShader(shader);
            renderer2D.drawFilledRect(0, 0, 1920, 1080, Color.BLACK);

            shader.setFloat("myT", t);

            if(window.isKeyPressed(Input.REBEL_KEY_LEFT)) t = Math.clamp(t - (2 * Time.deltaTime), 0, 1);
            if(window.isKeyPressed(Input.REBEL_KEY_RIGHT)) t = Math.clamp(t + (2 * Time.deltaTime), 0, 1);

            //When you're done using a custom shader, you have to call render() before setting it back to the original one
            renderer2D.render();

            renderer2D.setShader(renderer2D.getDefaultShader());



            renderer2D.drawText(0, 0, "Horribly inefficient Cubic Bezier \non the GPU\nUse left and right arrows to control Bezier t-value\nFPS: " + window.getFPS(), Color.WHITE, fontRes);

            renderer2D.render();


            window.update();
        }

        window.close();


    }
}
