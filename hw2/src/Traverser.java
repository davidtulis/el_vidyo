import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;

/**
 * Created by david on 8/26/15.
 */
public class Traverser extends Entity {

    private Texture texture;

    private float imageWidth;
    private float imageHeight;

    float x=100;
    float y=200;
    float imageWidthPX;
    float imageHeightPX;
    char direction = 'd';

    public Traverser(int width, String imgpath)
    {
        try
        {
            texture = TextureLoader.getTexture("jpg", ResourceLoader.getResourceAsStream(imgpath));
            imageWidth = (float)texture.getImageWidth() / texture.getTextureWidth();
            imageHeight = (float)texture.getImageHeight() / texture.getTextureHeight();

            imageWidthPX = width;
            imageHeightPX = width * texture.getImageHeight() / texture.getImageWidth();

            //hitbox = new Rectangle(0, 0, width, (width * texture.getImageHeight() / texture.getImageWidth()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("cannot open resource");
        }
    }

    public void init()
    {

    }

    public void destroy()
    {

    }

    public void update(float delta)
    {
        double xx = hitbox.getX();
        double yy = hitbox.getY();
        //System.out.println(xx);
        System.out.println(yy);

        if (direction == 'r')
        {
            hitbox.setLocation((int)(xx+delta), (int)yy);
            if (x>800-imageWidthPX)
            {
                direction = 'u';
            }
        }
        else if (direction == 'l')
        {
            hitbox.setLocation((int)(xx-delta), (int)yy);
            if (x<0)
            {
                direction = 'd';
            }
        }
        else if (direction == 'd')
        {
            hitbox.setLocation((int)xx, (int)(yy+delta));
            if (y>600-imageHeightPX)
            {
                direction = 'r';
            }
        }
        else if (direction=='u')
        {
            hitbox.setLocation((int)xx, (int)(yy-delta));
            if (y<0)
            {
                direction = 'l';
            }
        }
    }

    public void onCollision(Entity other) {
        System.out.println("The mouse has been hit");
    }

        public void draw()
    {
        x = (float) hitbox.getX();
        y = (float) hitbox.getY();
        float w = (float) hitbox.getWidth();
        float h = (float) hitbox.getHeight();

        GL11.glColor3f(1,1,1);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(x, y);

        GL11.glTexCoord2f(imageWidth, 0);
        GL11.glVertex2f(x+w, y);

        GL11.glTexCoord2f(imageWidth, imageHeight);
        GL11.glVertex2f(x+w, y+h);

        GL11.glTexCoord2f(0, imageHeight);
        GL11.glVertex2f(x, y+h);

        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        GL11.glEnd();
    }
}
