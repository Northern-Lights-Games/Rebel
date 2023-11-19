import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {
    private String path;
    private int texID;
    private static int availableSlot;

    public int slot;

    public Texture(String path) {
        this.path = path;

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channelsInFile = BufferUtils.createIntBuffer(1);


        ByteBuffer texture = STBImage.stbi_load(path, width, height, channelsInFile, 4);

        slot = availableSlot++;
        glActiveTexture(GL_TEXTURE0 + slot);
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, texture);


        glGenerateMipmap(GL_TEXTURE_2D);
        STBImage.stbi_image_free(texture);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texID);

    }

}
