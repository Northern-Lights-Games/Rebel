package demo;

import rebel.engine.*;

public class SpritesheetTest {


    public static void main(String[] args) {


        Window window = new Window(1920, 1080, "Rebel");
        Renderer2D renderer2D = new Renderer2D(1920, 1080);

        Texture texture = new Texture("project/img.png");

        while (!window.shouldClose()) {
            renderer2D.clear(1f, 1f, 1f, 1.0f);

            renderer2D.drawTexture(0, 0, 128, 128, texture, Color.WHITE, new Rect2D(0.5f, 0.5f, 0.5f, 0.5f));


            renderer2D.render();
            Tools.logRenderCalls(renderer2D);
            renderer2D.finished();
            window.update();
        }


        window.close();
    }


}