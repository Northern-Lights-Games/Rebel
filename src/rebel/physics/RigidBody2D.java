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

    public float getRotation(){
        return body.getAngle();
    }

    protected BodyType toBox2DType(Type type){
        if(type == Type.DYNAMIC) return BodyType.DYNAMIC;
        if(type == Type.STATIC) return BodyType.STATIC;
        if(type == Type.KINEMATIC) return BodyType.KINEMATIC;

        return null;
    }

    public void setPhysics(float density, float friction, float res){
        this.density = density;
        this.friction = friction;
        this.res = res;
    }

}
