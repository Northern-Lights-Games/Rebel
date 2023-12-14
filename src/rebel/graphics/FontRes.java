package rebel.graphics;

import java.awt.*;
import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Hashtable;

public class FontRes {
    private Font awtFont;
    private boolean antialias;
    private HashMap<Integer, Texture> glyphs = new HashMap<>();
    private FontMetrics metrics;
    public static int NORMAL = Font.PLAIN;
    public static int ITALIC = Font.ITALIC;
    public static int BOLD = Font.BOLD;

    public FontRes(String name, int style, int size, boolean antialias){
        this.antialias = antialias;
        awtFont = new Font(name, style, size);
        metrics = createFontMetrics();

        for (int i = 32; i < 256; i++) {
            if (i == 127) continue;
            char c = (char) i;
            Texture glyph = new Texture();

            BufferedImage ch = toBufferedImage(c);
            glyph.setData(toByteBuffer(ch), ch.getWidth(), ch.getHeight());
            glyphs.put(i, glyph);
        }


    }

    public HashMap<Integer, Texture> getGlyphs() {
        return glyphs;
    }

    public Font getAWTFont() {
        return awtFont;
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

    private FontMetrics createFontMetrics(){
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antialias) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(awtFont);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

        return metrics;
    }

    private BufferedImage toBufferedImage(char c) {
        BufferedImage image = new BufferedImage(metrics.charWidth(c), metrics.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        if (antialias) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setPaint(java.awt.Color.WHITE);
        g.setFont(awtFont);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();

        return image;
    }

    public float getHeight(){
        return metrics.getHeight();
    }

    public float getWidthOf(String string){
        return metrics.stringWidth(string);
    }

}
