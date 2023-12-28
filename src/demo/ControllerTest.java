package demo;

import rebel.GameController;
import rebel.Input;
import rebel.graphics.Color;
import rebel.graphics.Font2D;
import rebel.graphics.Renderer2D;
import rebel.graphics.Window;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;

public class ControllerTest {
    public static void main(String[] args) {
        Window window = new Window(1920, 1080, "Rebel");
        Renderer2D renderer2D = new Renderer2D(1920, 1080, true);


        Font2D font = new Font2D(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[0].getFontName(), Font2D.NORMAL, 20);


        GameController gameController = new GameController(GLFW_JOYSTICK_1);







        while(!window.shouldClose()){
            renderer2D.clear(new Color(1f, 1f, 1f, 1f));




            String output = gameController.getName() + "\n";

            gameController.update();

            output += "Controller: \n";


            //Move this range code to GameController.update() somehow decimal accuracy breaks agghghgh
            output += gameController.getLeftJoystickX() + "\n";
            output += gameController.getLeftJoystickY() + "\n";
            output += gameController.getRightJoystickX() + "\n";
            output += gameController.getRightJoystickY() + "\n";

            output += "Raw Values\n";

            output += gameController.getRawLeftJoystickX() + "\n";
            output += gameController.getRawLeftJoystickY() + "\n";
            output += gameController.getRawRightJoystickX() + "\n";
            output += gameController.getRawRightJoystickY() + "\n";



            output += "Left Calibration: \n";
            output += gameController.getLeftJoystickCalibrator().minX + "\n";
            output += gameController.getLeftJoystickCalibrator().maxX + "\n";
            output += gameController.getLeftJoystickCalibrator().minY + "\n";
            output += gameController.getLeftJoystickCalibrator().maxY + "\n";

            output += "Right Calibration: \n";
            output += gameController.getRightJoystickCalibrator().minX + "\n";
            output += gameController.getRightJoystickCalibrator().maxX + "\n";
            output += gameController.getRightJoystickCalibrator().minY + "\n";
            output += gameController.getRightJoystickCalibrator().maxY + "\n";

            output += "Buttons\n";

            for (int i = 0; i < gameController.getNumOfButtons(); i++) {
                output += gameController.getButton(i) + "\n";
            }




            renderer2D.drawText(0, 0, output, Color.BLUE, font);





            if(window.isKeyPressed(Input.REBEL_KEY_SPACE)){
                gameController.sampleCalibration();
            }






            renderer2D.render();
            window.update();
        }

        window.close();
    }
}
