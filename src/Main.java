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



        FontRes font = new FontRes("Comic Sans MS", FontRes.NORMAL, 40, true);
        FontRes font2 = new FontRes("Ink Free", FontRes.BOLD,  40, true);








        double start = glfwGetTime();

        float rotation = 0f;


        while (!window.shouldClose()) {

            double timeNow = glfwGetTime();
            double deltaTime = timeNow - start;
            start = timeNow;





            renderer2D.clear(1f, 1f, 1f, 1.0f);

            Matrix4f matrix4f = new Matrix4f();
            matrix4f.rotate(rotation, 0, 0, 1);




            {

                int sx = 0, sy = 500;

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





                renderer2D.setOrigin(((renderer2D.getWidth() / 2) - (width / 2)) + (width / 2), (renderer2D.getHeight() - height) + (height / 2));
                renderer2D.setTransform(matrix4f);
                renderer2D.drawText((renderer2D.getWidth() / 2) - (width / 2), renderer2D.getHeight() - height, text, Color.RED, font);
                renderer2D.setTransform(new Matrix4f().identity());
                renderer2D.setOrigin(0, 0);






                renderer2D.drawText(0, 0, "Rebel - The 2D Java Game Library\nRenderer2D OpenGL Demo", Color.BLUE, font2);

            }















            //pos + origin
            renderer2D.setOrigin(1000 + 250, 300 + 250);
            renderer2D.setTransform(matrix4f);
            renderer2D.drawTexture(1000, 300, 500, 500, logo);
            renderer2D.setTransform(new Matrix4f().identity());
            renderer2D.setOrigin(0, 0);

            rotation += 5 * deltaTime;



            renderer2D.render();

            Tools.logRenderCalls(renderer2D);




            renderer2D.finished();
            window.update();
        }


        window.close();
    }


}