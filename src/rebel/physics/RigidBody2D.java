package rebel.physics;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.joml.Vector2f;

public abstract class RigidBody2D {
    protected Body body;
    protected float density;
    protected float friction;
    protected float res;

    public enum Type {
        DYNAMIC,
        STATIC,
        KINEMATIC
    }
    Type bodyType;
    public abstract Vector2f getOrigin();
    public abstract Vector2f getPosition();

    boolean canRotate;

    public float getRotation(){
        return body.getAngle();
    }

    protected BodyType toBox2DType(Type type){
        if(type == Type.DYNAMIC) return BodyType.DYNAMIC;
        if(type == Type.STATIC) return BodyType.STATIC;
        if(type == Type.KINEMATIC) return BodyType.KINEMATIC;

        return null;
    }

    public void applyForce(Vector2f where, Vector2f force){
        body.applyForce(PhysicsUtil.toVec2(where), PhysicsUtil.toVec2(force));
    }

    public void applyForceToCenter(Vector2f force){
        body.applyForceToCenter(PhysicsUtil.toVec2(force));
    }

    public void applyImpulseToCenter(Vector2f force){
        body.applyLinearImpulse(PhysicsUtil.toVec2(force), body.getWorldPoint(body.getLocalCenter()), true);
    }

    public void setPhysics(float density, float friction, float res){
        this.density = density;
        this.friction = friction;
        this.res = res;
    }

    public boolean isCanRotate() {
        return canRotate;
    }
}
