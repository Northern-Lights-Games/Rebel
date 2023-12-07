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




        while (!window.shouldClose()) {

            renderer2D.clear(1.0f, 1.0f, 1.0f, 1.0f);

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

            for (int i = 0; i < 64; i++) {
                if((tx / 100) % 8 == 0){
                    ty += 100;
                    tx = 0;
                }

                renderer2D.drawTexture(tx + window.getMouseX(), ty + window.getMouseY(), 100, 100, textures.get(i));
                tx += 100;
            }


            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            renderer2D.render();

            System.out.println(renderer2D.getRenderCalls());

            window.update();
        }


        window.close();
    }


}