//David Tulis
import org.lwjgl.util.Rectangle;

public abstract class Entity {

    protected Rectangle hitbox;
    private boolean active;

    public Entity() {
        hitbox = new Rectangle(); // empty rectangle
        active = true;
    }

    public Entity(int x, int y, int w, int h) {
        hitbox = new Rectangle(x,y,w,h); // non-empty rectangle
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

    public void drawAt(float x, float y, float dx, float dy)
    {
        
    }
}
