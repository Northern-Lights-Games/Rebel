package rebel.graphics;

import java.util.List;

/***
 * Represents a CPU-based Bézier curve. This class cannot be instantiated, use CubicBezierCurve and QuadraticBezierCurve instead
 */
public interface BezierCurve2D {

    /***
     * Generates lines that follow the path of the Bézier curve
     * @param iterations
     * @return
     */
    List<Line2D> calculate(int iterations);

}
