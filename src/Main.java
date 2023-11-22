import org.lwjgl.opengl.GL30;

public class Main {


    public static void main(String[] args) {


        Window window = new Window(1920, 1080, "Rebel");



        Renderer2D renderer2D = new Renderer2D(1920, 1080);

        window.setTitle("Rebel: " + renderer2D.getHardwareInfo());

        Texture tex1 = new Texture("texture.png");
        Texture tex2 = new Texture("amogus.png");




        while (!window.shouldClose()) {

            renderer2D.clear(1.0f, 1.0f, 1.0f, 1.0f);






            renderer2D.drawTexture(300f, 300f, 150f, 300f, tex1);
            renderer2D.drawTexture(window.getMouseX(), window.getMouseY(), 200, 200, tex2, Color.RED);
            renderer2D.drawFilledRect(300, 150, 300, 300, Color.GRAY);
            renderer2D.drawFilledRect(0, 0, 70, 70, Color.LIGHT_GRAY);






            renderer2D.render();
            window.update();
        }


        window.close();
    }


}