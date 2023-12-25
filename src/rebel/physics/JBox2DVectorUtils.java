package rebel.physics;

import org.jbox2d.common.Vec2;
import org.joml.Vector2f;

class JBox2DVectorUtils {
    public static Vec2 toVec2(Vector2f vector2f){
        return new Vec2(vector2f.x, vector2f.y);
    }

    public static Vector2f toVector2f(Vec2 vec2){
        return new Vector2f(vec2.x, vec2.y);
    }
}
