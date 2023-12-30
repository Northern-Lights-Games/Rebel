package rebel.graphics;

import org.joml.Matrix4f;

public class Camera {
    private Matrix4f viewMatrix = new Matrix4f().identity();
    public Camera(){

    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
}
