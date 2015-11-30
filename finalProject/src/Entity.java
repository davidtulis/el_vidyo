import org.lwjgl.util.Rectangle;

import java.io.IOException;

/**
 * Created by david on 8/24/15.
 */

public abstract class Entity {

    protected Rectangle hitbox;
    private boolean active;

    public Entity() throws IOException {
        hitbox = new Rectangle();
        active = true;
    }

    public Entity(int x, int y, int w, int h)  {
        hitbox = new Rectangle(x, y, w, h) ;
        active = true;
    }

    public void init()
    {
    }

    public void destroy()
    {
    }

    public void update(float delta)
    {
    }

    public void draw()
    {
    }

    public boolean testCollision(Entity other)
    {
        if (hitbox.intersects(other.hitbox))
        {
            onCollision(other);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void onCollision(Entity other)
    {

    }

    public Rectangle intersection(Entity other)
    {
        Rectangle rval = new Rectangle();
        return hitbox.intersection(other.hitbox, rval);
    }

    public boolean isActive()
    {
        return active;
    }

    protected void deactivate()
    {
        active = false;
    }


}
