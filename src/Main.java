import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {


        Window window = new Window((int) (640 * 1.5f), (int) (480 * 1.5f), "Rebel");



        Renderer2D renderer2D = new Renderer2D((int) (640 * 1.5f), (int) (480 * 1.5f));

        window.setTitle("Rebel: " + renderer2D.getHardwareInfo());

        ArrayList<Texture> textures = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            textures.add(new Texture("amogus.png"));
        }




        while (!window.shouldClose()) {

            renderer2D.clear(1.0f, 1.0f, 1.0f, 1.0f);



            int x = 0, y = 0;

            for (int i = 0; i < 64; i++) {
                if((x / 100) % 8 == 0){
                    y += 100;
                    x = 0;
                }
                x += 100;

                renderer2D.drawTexture(x, y, 100, 100, textures.get(i));


            }





            renderer2D.render();
            window.update();
        }


        window.close();
    }


}