package rebel.physics;

import org.jbox2d.dynamics.World;
import org.joml.Vector2f;
import rebel.Time;
import rebel.graphics.Rect2D;

public class Scene2D {
    private World world;
    private float accumulator = 0f;
    private float physicsToScreenScale;

    private int velocityIterations = 8;
    private int positionIterations = 2;


    public Scene2D(float physicsToScreenScale, Vector2f gravity){
        setPhysicsToScreenScale(physicsToScreenScale);
        world = new World(PhysicsUtil.toVec2(gravity));
    }

    public void update(float step){
        //Prevent skipping the Box2D simulation too far into the future if the
        //Frame-rate gets slowed because of the Rendering side
        float frameTime = java.lang.Math.min(Time.deltaTime, 0.25f);
        accumulator += frameTime;

        while(accumulator >= step){
            world.step(step, velocityIterations, positionIterations);
            accumulator -= step;
        }

    }

    public int getVelocityIterations() {
        return velocityIterations;
    }

    public void setVelocityIterations(int velocityIterations) {
        this.velocityIterations = velocityIterations;
    }

    public int getPositionIterations() {
        return positionIterations;
    }

    public void setPositionIterations(int positionIterations) {
        this.positionIterations = positionIterations;
    }

    public RectBody2D newRectBody2D(Rect2D rect2D, RigidBody2D.Type bodyType){
        return new RectBody2D(world, rect2D.mul(getPhysicsToScreenScale()), bodyType);
    }

    public CircleBody2D newCircleBody2D(Vector2f pos, float radius, RigidBody2D.Type bodyType) {
        return new CircleBody2D(world, radius * getPhysicsToScreenScale(), pos.mul(getPhysicsToScreenScale()), bodyType);
    }

    public float getPhysicsToScreenScale() {
        return physicsToScreenScale;
    }

    public void setPhysicsToScreenScale(float physicsToScreenScale) {
        this.physicsToScreenScale = physicsToScreenScale;
    }

    public Vector2f getGravity(){
        return PhysicsUtil.toVector2f(world.getGravity());
    }

    public World getWorld() {
        return world;
    }
}
