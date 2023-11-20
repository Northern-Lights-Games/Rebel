import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLDebugMessageCallback;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL43.glDebugMessageCallback;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private long window;

    private double mouseX, mouseY;

    public Window(int w, int h, String title){


        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");


        window = glfwCreateWindow(w, h, title, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(window);

        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();
        glDebugMessageCallback((source, type, id, severity, length, message, userParam) -> {
            String messageStr = GLDebugMessageCallback.getMessage(length, message);

            System.out.println(messageStr);
        }, 0);

        glfwSetWindowSizeCallback(window, (window1, width, height) -> glViewport(0, 0, width, height));
    }

    public long getGLFWHandle() {
        return window;
    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(window);
    }

    public void update() {

        DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, posX, posY);

        mouseX = posX.get();
        mouseY = posY.get();



        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public void setTitle(String title){
        glfwSetWindowTitle(window, title);
    }

    public float getMouseX() {
        return (float) mouseX;
    }

    public float getMouseY() {
        return (float) mouseY;
    }

    public void close() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
