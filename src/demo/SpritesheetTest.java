package demo;

import rebel.Animation;
import rebel.Rect2D;
import rebel.Time;
import rebel.Tools;
import rebel.graphics.*;

public class SpritesheetTest {


    public static void main(String[] args) {


        Window window = new Window(1920, 1080, "Rebel");
        Renderer2D renderer2D = new Renderer2D(1920, 1080);

        Animation animation = new Animation(new Texture("project/img.png", Texture.FILTER_NEAREST));
        animation.create(2, 2, 4);
        animation.setDelay(150);
        animation.setPlaymode(Animation.Playmode.PLAY_REPEAT);


        while (!window.shouldClose()) {
            renderer2D.clear(1f, 1f, 1f, 1.0f);

            animation.update(Time.deltaTime);

            renderer2D.drawTexture(0, 0, 128, 128, animation.getTexture(), Color.WHITE, animation.getCurrentFrame());


            renderer2D.render();
            Tools.logRenderCalls(renderer2D);
            renderer2D.finished();
            window.update();
        }


        window.close();
    }


}