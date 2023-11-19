
public class Main {


    public static void main(String[] args) {

        Window window = new Window(1920, 1080, "Rebel");
        Renderer2D renderer2D = new Renderer2D(1920, 1080);



        Texture tex1 = new Texture("texture.png");
        Texture tex2 = new Texture("amogus.png");





        while (!window.shouldClose()) {

            renderer2D.clear(0.0f, 0.0f, 0.0f, 0.0f);


            renderer2D.drawTexture(0f, 300f, 300f, 300f, tex2);
            renderer2D.drawTexture(300f, 300f, 300f, 300f, tex1, new Color(0f, 0f, 1f, 1f));




            renderer2D.drawFilledRect(600f, 300f, 300f, 300f, Color.RED);
            renderer2D.drawTexture(window.getMouseX() - 150f, window.getMouseY() - 150, 300f, 300f, tex2);
            renderer2D.drawFilledRect(600f, 600f, 300f, 300f, Color.BLUE);
            renderer2D.drawTexture(450f, 450f, 300f, 300f, tex1);






            renderer2D.render();
            window.update();
        }


        window.close();
    }


}