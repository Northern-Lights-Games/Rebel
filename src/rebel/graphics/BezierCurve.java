package rebel.graphics;

import java.util.List;

public interface BezierCurve {
    List<Line> calculate(int iterations);

}
