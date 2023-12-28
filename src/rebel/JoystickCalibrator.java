package rebel;
import static java.lang.Math.*;
public class JoystickCalibrator {
    public double maxX;
    public double minX;
    public double maxY;
    public double minY;

    public void calibrate(double x, double y){
        maxX = max(maxX, x);
        minX = min(minX, x);

        maxY = max(maxY, y);
        minY = min(minY, y);
    }



}
