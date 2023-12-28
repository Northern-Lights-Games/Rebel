package rebel.physics;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.joml.Vector2f;
import rebel.Math;
import rebel.graphics.Rect2D;

public class RectBody2D extends RigidBody2D {

    private Rect2D rect2D;

    public RectBody2D(World world, Rect2D rect2D, Type bodyType, boolean canRotate) {
        this.rect2D = rect2D;
        this.bodyType = bodyType;

        BodyDef bodyDef = new BodyDef();
        bodyDef.setFixedRotation(!canRotate);
        bodyDef.type = toBox2DType(bodyType);
        bodyDef.position.set(rect2D.x, rect2D.y);
        body = world.createBody(bodyDef);
        body.createFixture(Fixtures.Box(rect2D.w / 2f, rect2D.h / 2f, 0.1f, 0.4f, 0.9f));
    }

    @Override
    public void setPhysics(float density, float friction, float res) {
        super.setPhysics(density, friction, res);
        body.createFixture(Fixtures.Box(rect2D.w / 2f, rect2D.h / 2f, density, friction, res));
    }

    @Override
    public Vector2f getOrigin() {
        return PhysicsUtil.toVector2f(body.getPosition());
    }

    public float getWidth(){
        return rect2D.w;
    }

    public float getHeight(){
        return rect2D.h;
    }

    public Vector2f getPosition(){
        return PhysicsUtil.toVector2f(body.getPosition());
    }
}
