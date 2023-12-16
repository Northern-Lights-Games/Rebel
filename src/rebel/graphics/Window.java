package rebel.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import rebel.Time;

import java.nio.DoubleBuffer;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.opengl.GL46.*;

public class Window {
    private long window;

    private double mouseX, mouseY;
    private double start;
    private int width, height;

    public Window(int w, int h, String title){
        this.width = w;
        this.height = h;


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
        GLUtil.setupDebugMessageCallback();

        glfwSetWindowSizeCallback(window, (window1, width, height) -> glViewport(0, 0, width, height));

        start = glfwGetTime();
    }

    public long getGLFWHandle() {
        return window;
    }

    public void setSize(int w, int h){
        this.width = w;
        this.height = h;

        glfwSetWindowSize(window, w, h);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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

        double timeNow = glfwGetTime();
        double deltaTime = timeNow - start;
        start = timeNow;
        Time.deltaTime = (float) deltaTime;
    }

    public int getKey(int key){
        return glfwGetKey(window, key);
    }

    public boolean isKeyPressed(int key){
        return getKey(key) == GLFW_PRESS;
    }
    public boolean isKeyReleased(int key){
        return getKey(key) == GLFW_RELEASE;
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

    public boolean getMousePressed(int button){
        return glfwGetMouseButton(window, button) == GLFW_PRESS;
    }

    public int getFPS() {
        return (int) (1 / Time.deltaTime);
    }

    public void close() {
        Disposer.disposeAll();
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}