import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by david on 8/26/15.
 */
public class Player extends Entity {

    private Texture texture;
    private LinkedList<Projectile> projectiles;
    private int projectileDirection;


    float x=100;
    float y=200;
    private float imageWidth;
    private float imageHeight;



    public Player(int shrinkFactor, String imgpath, LinkedList<Projectile> projectiles)
    {
        super(0,0,100,100);
        this.projectiles = projectiles;
        this.projectileDirection = 1;

        try
        {
            texture = TextureLoader.getTexture("jpg", ResourceLoader.getResourceAsStream(imgpath));
            imageWidth = (float)texture.getImageWidth() / texture.getTextureWidth()  ;
            imageHeight = (float)texture.getImageHeight() / texture.getTextureHeight() ;

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
        double xx = hitbox.getX();
        double yy = hitbox.getY();

        if (Mouse.isButtonDown(0)){
            int mx = Mouse.getX();
            int my = Display.getHeight() - Mouse.getY();

            x+=(mx-x)*0.1*delta;
            y+=(my-y)*0.1*delta;

            hitbox.setLocation((int)x, (int)y);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
            x+=delta/2;
            hitbox.setLocation((int) (xx + (delta / 2)), (int) yy);
            projectileDirection=1;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {
            x-=delta/2;
            hitbox.setLocation((int) (xx - (delta / 2)), (int) yy);
            projectileDirection=-1;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
        {
            y-=delta/2;
            hitbox.setLocation((int) xx, (int) (yy - (delta / 2)));
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        {
            y+=delta/2;
            hitbox.setLocation((int)xx, (int)(yy+(delta/2)));
        }

        if (x>700) x=700;
        if (x<0) x=0;
        if (y>500) y=500;
        if (y<0) y=0;

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
        {
            projectiles.add(new Projectile((int)x, (int)y,projectileDirection));
        }

        hitbox.setLocation((int) x, (int) y);
    }

    public void draw()
    {
        x = (float) hitbox.getX();
        y = (float) hitbox.getY();
        float w = (float) hitbox.getWidth();
        float h = (float) hitbox.getHeight();

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

    public void onCollision(Entity other)
    {
        if (other instanceof Wall)
        {
            org.lwjgl.util.Rectangle overlap = intersection(other);

            //player coordinates
            float x =hitbox.getX();
            float y =hitbox.getY();

            double overlapWidth = overlap.getWidth();
            double overlapHeight = overlap.getHeight();

            if (overlapHeight > 0)
            {
                System.out.println("Overlap height: " + overlapHeight);
            }

            if (overlapWidth > 0)
            {
                System.out.println("Overlap width: " + overlapWidth);
            }

            hitbox.setLocation((int)x,(int)y);
        }
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

}
