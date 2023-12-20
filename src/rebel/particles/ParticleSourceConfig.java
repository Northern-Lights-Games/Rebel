package rebel.particles;


/***
 * Represents the configuration for a ParticleSource. This includes dimensions and velocity
 */
public class ParticleSourceConfig {
    public float w, h;
    public float particleLifetime;
    public float sizeReduction;
    public float velX, velY;

    public ParticleSourceConfig(float w, float h, float particleLifetime, float sizeReduction, float velX, float velY) {
        this.w = w;
        this.h = h;
        this.particleLifetime = particleLifetime;
        this.sizeReduction = sizeReduction;
        this.velX = velX;
        this.velY = velY;
    }
}
