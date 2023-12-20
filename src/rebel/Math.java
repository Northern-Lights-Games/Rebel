package rebel;

/***
 * Math utilities for Rebel
 */
public class Math {
    public static float clamp(float value, float min, float max){
        if(value >= max) return max;
        if(value <= min) return min;

        return value;
    }
}
