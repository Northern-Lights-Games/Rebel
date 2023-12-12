package rebel.engine.particles;

import org.joml.Vector2f;
import rebel.engine.Rect2D;
import rebel.engine.Time;

import java.util.ArrayList;

public class ParticleSource {

    private ArrayList<Particle> particles = new ArrayList<>(500);
    private ParticleSourceConfig particleSourceConfig;
    private Vector2f source;

    public ParticleSource(ParticleSourceConfig particleSourceConfig, Vector2f source) {
        this.particleSourceConfig = particleSourceConfig;
        this.source = source;
    }



    public void update(){
        for (Particle particle : particles) {
            particle.lifetime -= Time.deltaTime * 1000;

            if (particle.lifetime <= 0) {
                particle.rect2D.x = source.x - (particle.rect2D.w / 2);
                particle.rect2D.y = source.y - (particle.rect2D.w / 2);
                particle.rect2D.w = particleSourceConfig.w;
                particle.rect2D.h = particleSourceConfig.h;
                particle.lifetime = (float) (Math.random() * particleSourceConfig.particleLifetime);

            } else {

                particle.rect2D.x += particle.vx;
                particle.rect2D.y += particle.vy;
                particle.rect2D.w -= particleSourceConfig.scale * Time.deltaTime;
                particle.rect2D.h -= particleSourceConfig.scale * Time.deltaTime;
            }
        }
    }


    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public Vector2f getSource() {
        return source;
    }

    public void setSource(Vector2f source) {
        this.source = source;
    }

    public void emit(){
        Particle particle = new Particle();




        particle.rect2D = new Rect2D(source.x - (particleSourceConfig.w / 2), source.y - (particleSourceConfig.h / 2), particleSourceConfig.w, particleSourceConfig.h);
        particle.vx = (float) ((float) (Math.random() - 0.5f) * (particleSourceConfig.vx * Math.random()));
        particle.vy = (float) ((float) (Math.random() - 0.5f) * (particleSourceConfig.vy * Math.random()));
        particle.lifetime = (float) (Math.random() * particleSourceConfig.particleLifetime);;



        particles.add(particle);

    }
}
