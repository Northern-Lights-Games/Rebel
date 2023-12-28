package rebel;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class GameController {
    private JoystickCalibrator left, right;
    private int id;
    private String name;
    private float rawLeftJoystickX, rawLeftJoystickY, rawRightJoystickX, rawRightJoystickY;
    private float leftJoystickX, leftJoystickY, rightJoystickX, rightJoystickY;
    private byte[] buttons;

    public GameController(int id) {
        this.id = id;
        this.name = glfwGetJoystickName(id);

        left = new JoystickCalibrator();
        right = new JoystickCalibrator();
    }

    public void update(){
        FloatBuffer axes = glfwGetJoystickAxes(id);


        for (int i = 0; i < axes.remaining(); i++) {
            float value = axes.get(i);

            if(i == 0) rawLeftJoystickX = value;
            if(i == 1) rawLeftJoystickY = value;

            if(i == 2) rawRightJoystickX = value;
            if(i == 3) rawRightJoystickY = value;
        }

        leftJoystickX = (float) (rawLeftJoystickX / ((left.maxX - left.minX) / 2));
        leftJoystickY = (float) (rawLeftJoystickY / ((left.maxY - left.minY) / 2));

        rightJoystickX = (float) (rawRightJoystickX / ((right.maxX - right.minX) / 2));
        rightJoystickY = (float) (rawRightJoystickY / ((right.maxY - right.minY) / 2));


        ByteBuffer buttons = glfwGetJoystickButtons(id);

        if(this.buttons == null){
            this.buttons = new byte[buttons.remaining()];
        }

        for (int i = 0; i < this.buttons.length; i++) {
            this.buttons[i] = buttons.get(i);
        }



    }

    public boolean getButton(int button){
        return buttons[button] == GLFW_PRESS;
    }

    public int getNumOfButtons(){
        return buttons.length;
    }



    public void sampleCalibration(){
        left.calibrate(rawLeftJoystickX, rawLeftJoystickY);
        right.calibrate(rawRightJoystickX, rawRightJoystickY);
    }

    public float getLeftJoystickX() {
        return leftJoystickX;
    }

    public float getLeftJoystickY() {
        return leftJoystickY;
    }

    public float getRightJoystickX() {
        return rightJoystickX;
    }

    public float getRightJoystickY() {
        return rightJoystickY;
    }

    public float getRawLeftJoystickX() {
        return rawLeftJoystickX;
    }

    public float getRawLeftJoystickY() {
        return rawLeftJoystickY;
    }

    public float getRawRightJoystickX() {
        return rawRightJoystickX;
    }

    public float getRawRightJoystickY() {
        return rawRightJoystickY;
    }

    public JoystickCalibrator getLeftJoystickCalibrator() {
        return left;
    }

    public JoystickCalibrator getRightJoystickCalibrator() {
        return right;
    }

    public String getName() {
        return name;
    }
}
