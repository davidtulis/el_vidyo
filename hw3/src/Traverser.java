import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

/**
 * Created by david on 8/26/15.
 */
public class Traverser extends Entity {

    private Texture texture;

    private float imageWidth;
    private float imageHeight;
    AudioManager aman;


    char direction = 'd';

    public Traverser(int shrinkFactor, String imgpath) throws IOException
    {
        try
        {
            texture = TextureLoader.getTexture("jpg", ResourceLoader.getResourceAsStream(imgpath));
            //these are ratios
            imageWidth = (float)texture.getImageWidth() / texture.getTextureWidth() ;
            imageHeight = (float)texture.getImageHeight() / (texture.getTextureHeight() );

            hitbox = new Rectangle(0, 0, texture.getImageWidth()/shrinkFactor, texture.getImageHeight()/shrinkFactor);

            aman = AudioManager.getInstance();
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

        if (direction == 'r')
        {
            hitbox.setLocation((int)(xx+delta), (int)yy);
            if (xx>800-hitbox.getWidth())
            {
                direction = 'u';
            }
        }
        else if (direction == 'l')
        {
            hitbox.setLocation((int)(xx-delta), (int)yy);
            if (xx<0)
            {
                direction = 'd';
            }
        }
        else if (direction == 'd')
        {
            hitbox.setLocation((int)xx, (int)(yy+delta));
            if (yy>600-hitbox.getHeight())
            {
                direction = 'r';
            }
        }
        else if (direction=='u')
        {
            hitbox.setLocation((int)xx, (int)(yy-delta));
            if (yy<0)
            {
                direction = 'l';
            }
        }
    }

    public void onCollision(Entity other) {

        try
        {
            aman.loadSample("gunsound", "res/gunsound.ogg");
            aman.play("gunsound");
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

        public void draw()
    {
        float x =  hitbox.getX();
        float y =  hitbox.getY();
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