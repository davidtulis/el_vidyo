import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;

/**
 * Created by david on 8/26/15.
 */
public class PictureTest extends Entity {

    private Rectangle hitBox;
    private Texture texture;
    private float wr;
    private float hr;
    float x=100;
    float y=200;

    public PictureTest(int width, String imgpath)
    {
        try
        {
            texture = TextureLoader.getTexture("jpg", ResourceLoader.getResourceAsStream(imgpath));
            wr = (float)texture.getImageWidth() / texture.getTextureWidth();
            hr = (float)texture.getImageHeight() / texture.getTextureHeight();

            hitBox = new Rectangle(0, 0, width, (width * texture.getImageHeight() / texture.getImageWidth()));
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
            System.err.println("cannot open resource");
        }
    }

    public void draw()
    {
        x = (float) hitBox.getX();
        y = (float) hitBox.getY();
        float w = (float) hitBox.getWidth();
        float h = (float) hitBox.getHeight();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(x, y);

        GL11.glTexCoord2f(wr, 0);
        GL11.glVertex2f(x+w, y);

        GL11.glTexCoord2f(wr,hr);
        GL11.glVertex2f(x+w, y+h);

        GL11.glTexCoord2f(0,hr);
        GL11.glVertex2f(x, y+h);

        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
}
