package rebel.physics;

import org.jbox2d.dynamics.World;
import org.joml.Vector2f;
import rebel.Time;

public class Scene {
    private World world;
    private float accumulator = 0f;

    public Scene(Vector2f gravity){
        world = new World(JBox2DVectorUtils.toVec2(gravity));
    }

    public void update(float step){
        //Avoid jumping skipping simulation times if the frame-rate is bad
        float frameTime = java.lang.Math.min(Time.deltaTime, 0.25f);
        accumulator += frameTime;

        while(accumulator >= step){
            world.step(step, 8, 2);
            accumulator -= step;
        }

    }

    public Vector2f getGravity(){
        return JBox2DVectorUtils.toVector2f(world.getGravity());
    }

    public World getWorld() {
        return world;
    }
}
