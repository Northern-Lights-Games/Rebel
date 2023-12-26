package rebel.physics;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.joml.Vector2f;
import rebel.Math;

public class CircleBody2D extends RigidBody2D {
    private float radius;

    public CircleBody2D(World world, float radius, Vector2f pos, Type bodyType) {
        this.radius = radius;
        this.bodyType = bodyType;



        BodyDef bodyDef = new BodyDef();
        bodyDef.type = toBox2DType(this.bodyType);
        bodyDef.position.set(pos.x, pos.y);
        body = world.createBody(bodyDef);
    }

    @Override
    public void setPhysics(float density, float friction, float res) {
        super.setPhysics(density, friction, res);
        body.createFixture(Fixtures.Circle(radius, density, friction, res));
    }

    @Override
    public Vector2f getOrigin() {
        return PhysicsUtil.toVector2f(body.getPosition());
    }

    public Vector2f getPosition(){
        return PhysicsUtil.toVector2f(body.getPosition()).sub(radius, radius);
    }

    public float getRadius() {
        return radius;
    }
}
