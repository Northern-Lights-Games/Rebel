package rebel.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;
import org.lwjgl.stb.STBImage;

import java.awt.*;
import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Hashtable;

import static org.lwjgl.opengl.GL46.*;

/***
 * Represents an OpenGL 2D Texture. This is a Disposable OpenGL object and will be disposed by the Window.
 */
public class Texture2D implements Disposable {
    private String path;
    private int texID;
    private int width, height;

    private int slot;

    public static int FILTER_NEAREST = GL46.GL_NEAREST;
    public static int FILTER_LINEAR = GL46.GL_LINEAR;

    /***
     * Create a Texture from a path. This defaults to a default Linear filter
     * @param path
     */
    public Texture2D(String path){
        this(path, FILTER_LINEAR);
    }

    /***
     * Creates a Texture from a path using the specified Filter
     * @param path
     * @param filter
     */
    public Texture2D(String path, int filter) {
        if(!new File(path).exists()) throw new RuntimeException("The file " + path + " does not exist!");
        Disposer.add(this);


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

    /***
     * Creates a Texture with no uploaded data
     */
    public Texture2D() {
        Disposer.add(this);
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    /***
     * Uploads a ByteBuffer to the Texture
     * @param data
     * @param width
     * @param height
     */
    public void setData(ByteBuffer data, int width, int height) {
        this.width = width;
        this.height = height;

        glBindTexture(GL_TEXTURE_2D, texID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    /***
     * Uploads a BufferedImage to the Texture. For compatibility with Java2D
     * @param bufferedImage
     */
    public void setData(BufferedImage bufferedImage){
        setData(toByteBuffer(bufferedImage), bufferedImage.getWidth(), bufferedImage.getHeight());
    }


    //COPIED! From https://stackoverflow.com/questions/5194325/how-do-i-load-an-image-for-use-as-an-opengl-texture-with-lwjgl
    //TODO: Replace this!
    /**
     * Convert the buffered image to a texture
     */
    private ByteBuffer toByteBuffer(BufferedImage bufferedImage) {
        ByteBuffer imageBuffer;
        WritableRaster raster;
        BufferedImage texImage;

        ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace
                .getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 },
                true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);

        raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                bufferedImage.getWidth(), bufferedImage.getHeight(), 4, null);
        texImage = new BufferedImage(glAlphaColorModel, raster, true,
                new Hashtable());

        // copy the source image into the produced image
        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0f, 0f, 0f, 0f));
        g.fillRect(0, 0, 256, 256);
        g.drawImage(bufferedImage, 0, 0, null);

        // build a byte buffer from the temporary image
        // that be used by OpenGL to produce a texture.
        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer())
                .getData();

        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        return imageBuffer;
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

    @Override
    public void dispose() {
        glDeleteTextures(texID);
    }
}
