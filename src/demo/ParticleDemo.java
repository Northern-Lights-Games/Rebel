package demo;

import org.joml.Vector2f;
import rebel.engine.*;
import rebel.engine.particles.Particle;
import rebel.engine.particles.ParticleSource;
import rebel.engine.particles.ParticleSourceConfig;

public class ParticleDemo {


    public static void main(String[] args) {


        Window window = new Window(1920, 1080, "Rebel");
        Renderer2D renderer2D = new Renderer2D(1920, 1080);

        FontRes fontRes = new FontRes("Times New Roman", FontRes.NORMAL, 40, true);

        ParticleSourceConfig p = new ParticleSourceConfig();
        p.w = 20;
        p.h = 20;
        p.vx = 80;
        p.vy = 80;
        p.particleLifetime = 3000;
        p.scale = 7;

        ParticleSource particleSource = new ParticleSource(p, new Vector2f((float) 1920 / 2, (float) 1080 / 2));




        for (int i = 0; i < 1000; i++) {
            particleSource.emit();
        }

        Texture texture = new Texture("project/logo.png");

        while (!window.shouldClose()) {

            renderer2D.clear(1f, 1f, 1f, 1.0f);



            particleSource.setSource(new Vector2f(window.getMouseX(), window.getMouseY()));
            particleSource.update();

            for(Particle particle : particleSource.getParticles()){
                renderer2D.drawTexture(particle.rect2D.x, particle.rect2D.y, particle.rect2D.w, particle.rect2D.h, texture);
            }


            renderer2D.drawText(0, 0, "Rebel - ParticleSource Test", Color.BLUE, fontRes);

            if(window.isKeyPressed(Input.REBEL_KEY_ESCAPE)) System.exit(1);


            renderer2D.render();
            Tools.logRenderCalls(renderer2D);
            renderer2D.finished();
            window.update();
        }


        window.close();
    }


}