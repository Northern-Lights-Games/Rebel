package demo;

import org.joml.*;
import rebel.Animation;
import rebel.Time;
import rebel.graphics.*;
import rebel.particles.Particle;
import rebel.particles.ParticleSource;
import rebel.particles.ParticleSourceConfig;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class ShowcaseDemo {


    public static void main(String[] args) {
        Window window = new Window(1920, 1080, "Rebel");
        Renderer2D renderer2D = new Renderer2D(1920, 1080);
        renderer2D.setDebug(true);

        renderer2D.getCamera2D().getViewMatrix().translate(100f, 0f, 0f).rotate((float) Math.toRadians(45), 0, 0, 1);
        renderer2D.updateCamera2D();




        window.setTitle("Rebel: " + renderer2D.getHardwareInfo());


        ArrayList<Texture2D> textures = new ArrayList<>();
        Texture2D logo = new Texture2D("project/logo.png");
        Texture2D opengl = new Texture2D("project/texture.png");


        for (int i = 0; i < 32; i++) {
            textures.add(i % 2 == 0 ? logo : opengl);
        }

        Font2D font = new Font2D("Arial", Font2D.NORMAL, 40, true);
        Font2D font2 = new Font2D("Cascadia Mono", Font2D.NORMAL | Font2D.BOLD,  40, true);
        CubicBezierCurve2D cubicBezierCurve = new CubicBezierCurve2D(
                new Vector2f(200, 800),
                new Vector2f(600, 400),
                new Vector2f(900, 1400),
                new Vector2f(1500, 800)
        );
        List<Line2D> line2DS = cubicBezierCurve.calculate(15);
        float rotation = 0f;


        ParticleSourceConfig particleSourceConfig = new ParticleSourceConfig(10, 10, 3500, 2, 10, 10);
        ParticleSource particleSource = new ParticleSource(particleSourceConfig, new Vector2f(700f, 700f));
        particleSource.addParticles(200);

        Animation animation = new Animation(new Texture2D("project/img.png", Texture2D.FILTER_NEAREST));
        animation.create(2, 2, 4);
        animation.setDelay(150);
        animation.setPlaymode(Animation.Playmode.PLAY_REPEAT);


        while (!window.shouldClose()) {
            renderer2D.clear(0.5f, 0.5f, 0.5f, 1.0f);
            renderer2D.drawText(0, 0, "Rebel - The 2D Java Game Library\nRenderer2D OpenGL Demo. FPS: " + window.getFPS(), Color.BLUE, font2);



            {
                renderer2D.drawRect(1000, 700, 300, 300, Color.GREEN, 3);
                renderer2D.drawFilledRect(1300, 700, 100, 100, Color.RED);

                for(Line2D line2D : line2DS){
                    renderer2D.drawLine(line2D.start.x, line2D.start.y, line2D.end.x, line2D.end.y, Color.WHITE, 40, true);
                }

                renderer2D.drawFilledEllipse(1400, 700, 200, 200, Color.GREEN);
                renderer2D.drawEllipse(1600, 700, 200, 200, Color.GREEN, 0.5f);
                particleSource.update();

                for(Particle particle : particleSource.getParticles()){
                    renderer2D.drawTexture(particle.rect2D.x, particle.rect2D.y, particle.rect2D.w, particle.rect2D.h, logo);
                }


            }

            {
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
                float height = font.getLineHeight();

                renderer2D.drawText((renderer2D.getWidth() / 2) - (width / 2), renderer2D.getHeight() - height, text, Color.RED, font);
                renderer2D.drawText(0, 0, "Rebel - The 2D Java Game Library\nRenderer2D OpenGL Demo. FPS: " + window.getFPS(), Color.WHITE, font2);




                animation.update(Time.deltaTime);

                renderer2D.drawTexture(0, renderer2D.getHeight() - 512, 512, 512, animation.getTexture(), Color.WHITE, animation.getCurrentFrame());


            }


            {
                renderer2D.setOrigin(1000 + 250 / 2f, 300 + 250 / 2f);
                renderer2D.scale(2f, 2f, 1f);
                renderer2D.rotate((float) Math.toRadians(rotation));
                renderer2D.drawTexture(1000, 300, 250, 250, logo);
                renderer2D.resetTransform();
                renderer2D.setOrigin(0, 0);

                rotation += 5 * Time.deltaTime;
            }






            renderer2D.render();
            window.update();
        }


        window.close();
    }


}