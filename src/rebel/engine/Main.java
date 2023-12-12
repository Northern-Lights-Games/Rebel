package rebel.engine;

import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;

import java.lang.Math;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {


        Window window = new Window(1920, 1080, "Rebel");
        Renderer2D renderer2D = new Renderer2D(1920, 1080);
        window.setTitle("Rebel: " + renderer2D.getHardwareInfo());



        ArrayList<Texture> textures = new ArrayList<>();
        Texture logo = new Texture("project/logo.png");
        boolean sw = false;
        for (int i = 0; i < 32; i++) {
            textures.add(sw ? new Texture("project/logo.png") : new Texture("texture.png"));
            sw = !sw;
        }



        FontRes font = new FontRes("Times New Roman", FontRes.NORMAL, 40, true);
        FontRes font2 = new FontRes("Times New Roman", FontRes.BOLD | FontRes.ITALIC,  40, true);



        float rotation = 0f;


        while (!window.shouldClose()) {





            renderer2D.clear(1f, 1f, 1f, 1.0f);






            {

                int sx = 0, sy = 500;

                renderer2D.setTransform(new Matrix4f().rotate((float) Math.toRadians(30), 0, 0, 1).scale(2f, 1f, 0f));

                for (int i = 0; i < 32; i++) {
                    if ((sx / 100) % 8 == 0) {
                        sy += 100;
                        sx = 0;
                    }

                    if (Math.random() > 0.5)
                        renderer2D.drawFilledRect(sx, sy, 100, 100, Color.BLACK);
                    else
                        renderer2D.drawFilledEllipse(sx, sy, 100, 100, Color.BLACK);
                    sx += 100;
                }

                renderer2D.resetTransform();





                int tx = 0, ty = 0;



                for (int i = 0; i < textures.size(); i++) {
                    if ((tx / 100) % 8 == 0) {
                        ty += 100;
                        tx = 0;
                    }

                    renderer2D.drawTexture(tx, ty, 100, 100, textures.get(i));
                    tx += 100;
                }

                String text = renderer2D.getHardwareInfo();


                float width = font.getWidthOf(text);
                float height = font.getHeight();






                renderer2D.drawText((renderer2D.getWidth() / 2) - (width / 2), renderer2D.getHeight() - height, text, Color.RED, font);






                renderer2D.drawText(0, 0, "Rebel - The 2D Java Game Library\nRenderer2D OpenGL Demo. FPS: " + window.getFPS(), Color.BLUE, font2);



            }
















            //pos + origin
            renderer2D.setOrigin(1000 + 250, 300 + 250);
            renderer2D.rotate((float) Math.toRadians(rotation));
            renderer2D.drawTexture(1000, 300, 500, 500, logo);
            renderer2D.resetTransform();
            renderer2D.setOrigin(0, 0);

            rotation += 5 * Time.deltaTime;



            renderer2D.drawLine(30, 30, window.getMouseX(), window.getMouseY(), Color.RED, 5);


            renderer2D.render();

            Tools.logRenderCalls(renderer2D);




            renderer2D.finished();
            window.update();
        }


        window.close();
    }


}