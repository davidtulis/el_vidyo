import org.lwjgl.opengl.GL11;

/**
 * Created by david on 8/24/15.
 */
public class Box extends Entity
{

    private float x;
    private float y;
    int width=10;

    public Box(float x, float y)
    {
        this.x=x;
        this.y=y;
    }

    public void init()
    {

    }

    public void destroy()
    {

    }

    public void update(float delta)
    {
        x=x+delta;
    }

    public void draw()
    {
        GL11.glColor3f(1, 1, 0);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x+width,y);
        GL11.glVertex2f(x+width,y+width);
        GL11.glVertex2f(x,y+width);


        GL11.glEnd();

    }
}


