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
    private Vector2f source;

    /**
     * Creates a ParticleSource with the specified ParticleSourceConfig and location on the screen
     * @param particleSourceConfig
     * @param source
     */
    public ParticleSource(ParticleSourceConfig particleSourceConfig, Vector2f source) {
        this.particleSourceConfig = particleSourceConfig;
        this.source = source;
    }


    /**
     * Updates the ParticleSystem. This updates the state of every Particle in the pool, computing velocity and more.
     */
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

    public void addParticles(int n){
        for (int i = 0; i < n; i++) {
            emit();
        }
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
