import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.NativeType;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;


import static org.lwjgl.opengl.GL30.*;

public class Texture {
    private String path;
    private int texID;
    public static int availableSlot;
    private int slot;
    private int width, height;

    public Texture(String path) {
        this.path = path;

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer channelsInFile = BufferUtils.createIntBuffer(1);
        ByteBuffer texture = STBImage.stbi_load(path, w, h, channelsInFile, 4);

        width = w.get();
        height = h.get();

        texID = glGenTextures();

        findSlot();
        glBindTexture(GL_TEXTURE_2D, texID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, texture);
        glGenerateMipmap(GL_TEXTURE_2D);

        STBImage.stbi_image_free(texture);
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
    public int getSlot() {
        return slot;
    }

    public void findSlot(){
        slot = availableSlot++;
        glActiveTexture(GL_TEXTURE0 + slot);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
