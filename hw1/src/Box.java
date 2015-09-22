import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * Created by david on 8/24/15.
 */
public class Box extends Entity
{

    private float x;
    private float y;
    int width=10;
    char direction = 'd';

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
        if (direction == 'r')
        {
            x=x+delta;

            if (x>800-width)
            {
                direction = 'u';
            }
        }
        else if (direction == 'l')
        {
            x=x-delta;
            if (x<0)
            {
                direction = 'd';
            }
        }
        else if (direction == 'd')
        {
            y=y+delta;
            if (y>600-width)
            {
                direction = 'r';
            }
        }
        else if (direction=='u')
        {
            y=y-delta;
            if (y<0)
            {
                direction = 'l';
            }
        }
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


