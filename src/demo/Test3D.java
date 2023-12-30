package demo;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL46;
import rebel.graphics.*;

public class Test3D {
    public static void main(String[] args) {
        Window window = new Window(1000, 600, "");
        Renderer3D renderer3D = new Renderer3D(window.getWidth(), window.getHeight(), true);


        Texture2D a = new Texture2D("project/logo.png");
        Texture2D b = new Texture2D("project/texture.png");


        float rotX = 0f, rotY = 0f, rotZ = 0f;

        while(!window.shouldClose()){
            renderer3D.clear(Color.WHITE);


            renderer3D.setOrigin(0, 0);
            renderer3D.drawTexture(0.5f / -2f, 0.5f / -2f, 0.5f, 0.5f, a, Color.RED);
            renderer3D.drawTexture((0.5f / -2f) + 0.5f, 0.5f / -2f, 1f, 1f, b, Color.BLUE);
            renderer3D.resetTransform();



            renderer3D.getCamera().getViewMatrix().rotate((float) Math.toRadians(rotX), 1, 0, 0);
            renderer3D.getCamera().getViewMatrix().rotate((float) Math.toRadians(rotY), 0, 1, 0);
            renderer3D.getCamera().getViewMatrix().translate(0, 0, 1f);


            rotX = 360 * -(window.getMouseY() / window.getHeight());
            rotY = 360 * (window.getMouseX() / window.getWidth());




            renderer3D.updateCamera2D();
            renderer3D.render();
            renderer3D.getCamera().getViewMatrix().set(new Matrix4f().identity());

            System.out.println(GL46.glGetError());


            window.update();
        }

        window.close();


    }
}
