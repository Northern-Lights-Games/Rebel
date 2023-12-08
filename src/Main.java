import static org.lwjgl.glfw.GLFW.*;

import java.awt.*;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {


        Window window = new Window(1920, 1080, "Rebel");



        Renderer2D renderer2D = new Renderer2D(1920, 1080);

        window.setTitle("Rebel: " + renderer2D.getHardwareInfo());

        ArrayList<Texture> textures = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            textures.add(Math.random() > 0.5 ? new Texture("amogus.png") : new Texture("texture.png"));
        }

        RFont font = new RFont("Arial", 50, true);

        //Looks like every 32 textures, the first one goes missing



        double start = glfwGetTime();


        while (!window.shouldClose()) {

            double timeNow = glfwGetTime();
            double deltaTime = timeNow - start;
            start = timeNow;





            renderer2D.clear(1f, 1f, 1f, 1.0f);

            int sx = 0, sy = 0;

            for (int i = 0; i < 64; i++) {
                if((sx / 100) % 8 == 0){
                    sy += 100;
                    sx = 0;
                }

                if(Math.random() > 0.5)
                    renderer2D.drawFilledRect(sx, sy, 100, 100, Color.BLACK);
                else
                    renderer2D.drawFilledEllipse(sx, sy, 100, 100, Color.BLACK);
                sx += 100;
            }

            int tx = 0, ty = 0;

            for (int i = 0; i < textures.size(); i++) {
                if((tx / 100) % 8 == 0){
                    ty += 100;
                    tx = 0;
                }

                renderer2D.drawTexture(tx, ty, 100, 100, textures.get(i));
                tx += 100;
            }

            renderer2D.drawText(window.getMouseX(), window.getMouseY(), renderer2D.getHardwareInfo(), Color.RED, font);
            renderer2D.drawText(0, 0, "Rebel - The 2D Java Game Library", Color.GRAY, font);







            renderer2D.render();

            //Tools.logRenderCalls(renderer2D);




            renderer2D.finished();
            window.update();
        }


        window.close();
    }


}