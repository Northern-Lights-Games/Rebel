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

            renderer2D.drawFilledRect(0, 300, 150, 150, Color.GREEN);
            renderer2D.drawFilledRect(0, 450, 100, 150, Color.LIGHT_GRAY);





            renderer2D.drawTexture(0, 0, 300, 300, tex1);
            renderer2D.drawTexture(150, 0, 300, 300, tex2);


            renderer2D.drawFilledEllipse(window.getMouseX(), window.getMouseY(), 150, 150, Color.RED);
            renderer2D.drawFilledEllipse(window.getMouseX() + 150, window.getMouseY() + 150, 300, 300, Color.BLACK);
            renderer2D.drawTexture(window.getMouseX() - 150, window.getMouseY() + 150, 300, 300, tex2);






            renderer2D.render();
            window.update();
        }


        window.close();
    }


}