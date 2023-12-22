package rebel.graphics;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/***
 * Represents a Font that can be rendered to the screen. This class can also interoperate with AWT Fonts, as well as load TrueType (.ttf) fonts from disk.
 */
public class Font2D {
    private final Font awtFont;
    private final boolean antialias;
    private final HashMap<Integer, Texture2D> glyphs = new HashMap<>();
    private FontMetrics metrics;
    public static int NORMAL = Font.PLAIN;
    public static int ITALIC = Font.ITALIC;
    public static int BOLD = Font.BOLD;

    /***
     * Loads a TrueType (.ttf) font from a file
     * @param ttfPath
     * @param style
     * @param size
     * @param antialias
     */
    public Font2D(File ttfPath, int style, int size, boolean antialias){
        this(loadTTF(ttfPath).deriveFont(style, size), antialias);
    }

    /***
     * Loads a system-installed font
     * @param name
     * @param style
     * @param size
     * @param antialias
     */

    public Font2D(String name, int style, int size, boolean antialias){
        this(new Font(name, style, size), antialias);
    }


    /***
     * Loads an AWT font
     * @param awtFont
     * @param antialias
     */
    public Font2D(Font awtFont, boolean antialias){
        this.antialias = antialias;
        this.awtFont = awtFont;
        createGlyphs();
    }

    private static Font loadTTF(File ttfPath){
        try {
            return Font.createFont(Font.TRUETYPE_FONT, ttfPath);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createGlyphs(){
        metrics = createFontMetrics();

        for (int i = 32; i < 256; i++) {
            if (i == 127) continue;
            char c = (char) i;
            Texture2D glyph = new Texture2D();

            BufferedImage ch = toBufferedImage(c);
            glyph.setData(ch);
            glyphs.put(i, glyph);
        }
    }

    public HashMap<Integer, Texture2D> getGlyphs() {
        return glyphs;
    }

    public Font getAWTFont() {
        return awtFont;
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

    public float getLineHeight(){
        return metrics.getHeight();
    }


    //Use this in Zara2
    public float getHeightOf(String string){
        String[] lines = string.split("\n");
        float height = 0f;
        for (String ignored : lines) {
            height += metrics.getHeight();
        }

        return height;
    }



    public float getWidthOf(String string){

        String[] lines = string.split("\n");
        float width = 0f;

        for(String line : lines){
            float lineWidth = metrics.stringWidth(line);
            if(lineWidth > width){
                width = lineWidth;
            }
        }

        return width;
    }

}
