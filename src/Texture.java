import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.NativeType;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;


import static org.lwjgl.opengl.GL30.*;

public class Texture {
    private String path;
    private int texID;
    private static int availableSlot;
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


        slot = availableSlot++;
        texID = glGenTextures();
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, texID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, texture);
        glGenerateMipmap(GL_TEXTURE_2D);

        STBImage.stbi_image_free(texture);
    }

    public Texture() {
        slot = availableSlot++;
        texID = glGenTextures();
    }

    public void setData(int target, int level, int internalformat, int width, int height, int border,  int format, int type, ByteBuffer pixels){
        this.width = width;
        this.height = height;

        glActiveTexture(GL_TEXTURE0 + slot);
        bind();
        glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
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
    public int getSlot() {
        return slot;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
