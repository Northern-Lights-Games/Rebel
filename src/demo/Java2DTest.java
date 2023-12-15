package demo;

import rebel.graphics.Renderer2D;
import rebel.graphics.Texture;
import rebel.graphics.Window;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Java2DTest {
    public static void main(String[] args) {
        Window window = new Window(640, 480, "Rebel");
        Renderer2D renderer2D = new Renderer2D(640, 480);

        Texture texture = new Texture();
        BufferedImage framebuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font("Arial", Font.ITALIC | Font.BOLD, 40);
        Graphics2D graphics2D = framebuffer.createGraphics();


        while(!window.shouldClose()){
            renderer2D.clear(0.0f, 0.0f, 0.0f, 0.0f);


            long start = System.currentTimeMillis();

            for (int i = 0; i < texture.getHeight(); i++) {
                for (int j = 0; j < texture.getWidth(); j++) {
                    graphics2D.setColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
                    graphics2D.fillRect(j * 20, i * 20, 20, 20);
                }


            }

            graphics2D.setFont(font);
            graphics2D.setColor(new Color(1f, 1f, 1f));
            graphics2D.drawString("This is a test", 0, graphics2D.getFontMetrics().getHeight());

            System.out.println(System.currentTimeMillis() - start);

            texture.setData(framebuffer);



            renderer2D.drawTexture(0, 0, texture.getWidth(), texture.getHeight(), texture);

            window.setTitle("PerPixelTest - " + window.getFPS());


            renderer2D.render();
            window.update();
        }

        window.close();


    }
}
