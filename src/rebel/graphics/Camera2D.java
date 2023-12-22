package rebel.graphics;

import org.joml.Matrix4f;

public class Camera2D {
    private Matrix4f viewMatrix = new Matrix4f().identity();
    public Camera2D(){

    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
}
