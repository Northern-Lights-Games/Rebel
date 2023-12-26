package rebel.physics;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

class Fixtures {
    public static FixtureDef Circle(float radius, float density, float friction, float res){
        FixtureDef fixtureDef = new FixtureDef();

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);


        fixtureDef.shape = circleShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = res;

        return fixtureDef;
    }



    public static FixtureDef Box(float halfX, float halfY, float density, float friction, float res){
        FixtureDef fixtureDef = new FixtureDef();

        PolygonShape polygonShape = new PolygonShape();


        Vec2 origin = new Vec2(halfX, halfY);

        polygonShape.setAsBox(halfX, halfY, origin, 0);

        fixtureDef.shape = polygonShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = res;

        return fixtureDef;
    }
}
