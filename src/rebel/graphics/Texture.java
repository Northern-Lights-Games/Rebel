package rebel.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;
import org.lwjgl.stb.STBImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL46.*;

public class Texture {
    private String path;
    private int texID;
    private int width, height;

    private int slot;

    public static int FILTER_NEAREST = GL46.GL_NEAREST;
    public static int FILTER_LINEAR = GL46.GL_LINEAR;

    public Texture(String path){
        this(path, FILTER_LINEAR);
    }

    public Texture(String path, int filter) {
        if(!new File(path).exists()) throw new RuntimeException("The file " + path + " does not exist!");


        this.path = path;

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer channelsInFile = BufferUtils.createIntBuffer(1);
        ByteBuffer texture = STBImage.stbi_load(path, w, h, channelsInFile, 4);

        width = w.get();
        height = h.get();

        texID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, texID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, texture);
        glGenerateMipmap(GL_TEXTURE_2D);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);

        STBImage.stbi_image_free(texture);
    }

    public Texture() {
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public void setData(ByteBuffer data, int width, int height) {
        this.width = width;
        this.height = height;

        glBindTexture(GL_TEXTURE_2D, texID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);
    }



    public void bind(){
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public String getPath() {
        return path;
    }

    public int getTexID() {
        return texID;
    }



    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
