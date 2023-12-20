package rebel.particles;

import org.joml.Vector2f;
import rebel.graphics.Rect2D;
import rebel.Time;

import java.util.ArrayList;

/***
 * A ParticleSource is responsible for emitting Particles at a specified location
 */
public class ParticleSource {

    private ArrayList<Particle> particles = new ArrayList<>(500);
    private ParticleSourceConfig particleSourceConfig;
    private Vector2f pos;

    /**
     * Creates a ParticleSource with the specified ParticleSourceConfig and location on the screen
     * @param particleSourceConfig
     * @param pos
     */
    public ParticleSource(ParticleSourceConfig particleSourceConfig, Vector2f pos) {
        this.particleSourceConfig = particleSourceConfig;
        this.pos = pos;
    }


    /**
     * Updates the ParticleSystem. This updates the state of every Particle in the pool, computing velocity and more.
     */
    public void update(){
        for (Particle particle : particles) {
            particle.lifetimeMs -= Time.deltaTime * 1000;

            if (particle.lifetimeMs <= 0) {
                particle.rect2D.x = pos.x - (particle.rect2D.w / 2);
                particle.rect2D.y = pos.y - (particle.rect2D.w / 2);
                particle.rect2D.w = particleSourceConfig.w;
                particle.rect2D.h = particleSourceConfig.h;
                particle.lifetimeMs = (float) (Math.random() * particleSourceConfig.particleLifetime);

            } else {

                particle.rect2D.x += particle.velX;
                particle.rect2D.y += particle.velY;
                particle.rect2D.w -= particleSourceConfig.sizeReduction * Time.deltaTime;
                particle.rect2D.h -= particleSourceConfig.sizeReduction * Time.deltaTime;
            }
        }
    }


    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public void addParticles(int n){
        for (int i = 0; i < n; i++) {
            Particle particle = new Particle();

            particle.rect2D = new Rect2D(pos.x - (particleSourceConfig.w / 2), pos.y - (particleSourceConfig.h / 2), particleSourceConfig.w, particleSourceConfig.h);
            particle.velX = (float) ((float) (Math.random() - 0.5f) * (particleSourceConfig.velX * Math.random()));
            particle.velY = (float) ((float) (Math.random() - 0.5f) * (particleSourceConfig.velY * Math.random()));
            particle.lifetimeMs = (float) (Math.random() * particleSourceConfig.particleLifetime);;

            particles.add(particle);
        }
    }

}
