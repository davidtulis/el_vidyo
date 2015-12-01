import org.lwjgl.input.Keyboard;
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
    private int ammo=30;

    float x=100;
    float y=200;
    private float imageWidth;
    private float imageHeight;
    private boolean paused=false;
    private boolean restarted=false;
    private boolean supershotEnabled=false;

    public int checkAmmo()
    {
        return ammo;
    }

    public Player(int shrinkFactor, String imgpath, LinkedList<Projectile> projectiles)
    {
        super(0,0,100,100);
        this.projectiles = projectiles;
        this.projectileDirection = 1;
        restarted=false;
        paused=false;

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
       if (!paused) {
            double xx = hitbox.getX();
            double yy = hitbox.getY();

            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                x += delta / 2;
                hitbox.setLocation((int) (xx + (delta / 2)), (int) yy);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                x -= delta / 2;
                hitbox.setLocation((int) (xx - (delta / 2)), (int) yy);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                y -= delta / 2;
                hitbox.setLocation((int) xx, (int) (yy - (delta / 2)));
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                y += delta / 2;
                hitbox.setLocation((int) xx, (int) (yy + (delta / 2)));
            }
        }

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (!paused) {
                    if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                        if (ammo > 0) {
                            projectiles.add(new Projectile((int) x, (int) y, false, -1));
                            fire(1);

                        }
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                        if (ammo > 0) {
                            projectiles.add(new Projectile((int) x, (int) y, false, 1));
                            fire(1);
                        }

                    }

                    if (Keyboard.getEventKey() == Keyboard.KEY_W) {
                        if (ammo > 0) {
                            projectiles.add(new Projectile((int) x, (int) y, true, -1));
                            fire(1);
                        }

                    }

                    if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                        if (ammo > 0) {
                            projectiles.add(new Projectile((int) x, (int) y, true, 1));
                            fire(1);
                        }

                    }
                    if (supershotEnabled==true) {
                        if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                            if (ammo >= 4) {
                                projectiles.add(new Projectile((int) x, (int) y, false, -1));
                                projectiles.add(new Projectile((int) x, (int) y, false, 1));
                                projectiles.add(new Projectile((int) x, (int) y, true, -1));
                                projectiles.add(new Projectile((int) x, (int) y, true, 1));
                                fire(4);
                            }

                        }
                    }
                }
                if (Keyboard.getEventKey()==Keyboard.KEY_P)
                {
                    paused = !paused;
                }
                if (Keyboard.getEventKey()==Keyboard.KEY_N)
                {
                    restarted=true;
                }
            }
        }

        if (x>Display.getWidth()-100) x=Display.getWidth()-100;
        if (x<0) x=0;
        if (y>Display.getHeight()-100) y=Display.getHeight()-100;
        if (y<0) y=0;

        hitbox.setLocation((int) x, (int) y);
    }

    private void fire(int i) {
        ammo-=i;
        System.out.printf("Ammo: %d\n", ammo);
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

            if (overlapHeight>0 && overlapHeight<overlapWidth)
            {
                if (y<other.hitbox.getY())
                {
                    y-=(float)overlapHeight;
                }
                else
                {
                    y+=(float)overlapHeight;
                }
            }
            if (overlapWidth>0 && overlapWidth<overlapHeight)
            {
                if (x<other.hitbox.getX())
                {
                    x-=(float)overlapWidth;
                }
                else
                {
                    x+=(float)overlapWidth;
                }
            }

            hitbox.setLocation((int) x, (int) y);
        }
        if (other instanceof Powerup)
        {
            ammo+=30;
            supershotEnabled=true;
            System.out.println("You picked up more ammo and enabled the supershot! Press the spacebar to use the supershot");
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

    public boolean getPaused() {
        return paused;
    }

    public boolean getRestarted() {
        return restarted;
    }

    public void setRestarted(boolean restarted) {
        this.restarted = restarted;
    }
}
