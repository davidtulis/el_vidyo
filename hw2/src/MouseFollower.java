import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;

/**
 * Created by david on 8/26/15.
 */
public class MouseFollower extends Entity {

    private Rectangle hitBox;
    private Texture texture;

    float x=100;
    float y=200;
    private float imageWidth;
    private float imageHeight;

    public MouseFollower(int width, String imgpath)
    {
        try
        {
            texture = TextureLoader.getTexture("jpg", ResourceLoader.getResourceAsStream(imgpath));
            imageWidth = (float)texture.getImageWidth() / texture.getTextureWidth();
            imageHeight = (float)texture.getImageHeight() / texture.getTextureHeight();


            hitBox = new Rectangle(0, 0, width, (width * texture.getImageHeight() / texture.getImageWidth()));
        }
        catch (java.io.IOException e)
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
        double xx = hitBox.getX();
        double yy = hitBox.getY();

        if (Mouse.isButtonDown(0)){
            int mx = Mouse.getX();
            int my = Display.getHeight() - Mouse.getY();

            x+=(mx-x)*0.1*delta;
            y+=(my-y)*0.1*delta;

            hitBox.setLocation((int)x, (int)y);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
            hitBox.setLocation((int) (xx + (delta / 2)), (int) yy);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {
            hitBox.setLocation((int) (xx - (delta / 2)), (int) yy);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
        {
            hitBox.setLocation((int) xx, (int) (yy - (delta / 2)));
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        {
            hitBox.setLocation((int)xx, (int)(yy+(delta/2)));
        }

        if (x>800 || x<0) x=800;
        if (y>600 || y<0) y=600;

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
        {
            System.out.println("new box!");
            Main.fireProjectile(x, y);
        }

    }

    public void draw()
    {
        x = (float) hitBox.getX();
        y = (float) hitBox.getY();
        float w = (float) hitBox.getWidth();
        float h = (float) hitBox.getHeight();

        GL11.glColor3f(1, 1, 1);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(x, y);

        GL11.glTexCoord2f(imageWidth, 0);
        GL11.glVertex2f(x + w, y);

        GL11.glTexCoord2f(imageWidth, imageHeight);
        GL11.glVertex2f(x + w, y + h);

        GL11.glTexCoord2f(0, imageHeight);
        GL11.glVertex2f(x, y + h);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        GL11.glEnd();

    }
}
