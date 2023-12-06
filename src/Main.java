import org.lwjgl.opengl.GL30;

public class Main {


    public static void main(String[] args) {


        Window window = new Window((int) (640 * 1.5f), (int) (480 * 1.5f), "Rebel");



        Renderer2D renderer2D = new Renderer2D((int) (640 * 1.5f), (int) (480 * 1.5f));

        window.setTitle("Rebel: " + renderer2D.getHardwareInfo());

        Texture tex1 = new Texture("texture.png");
        Texture tex2 = new Texture("amogus.png");




        while (!window.shouldClose()) {

            renderer2D.clear(1.0f, 1.0f, 1.0f, 1.0f);








            renderer2D.drawFilledEllipse(0, 0, (int) (640 * 1.5f), (int) (480 * 1.5f), Color.RED);






            renderer2D.render();
            window.update();
        }


        window.close();
    }


}