import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;

public class Jumper extends Entity {

    private static int height=50;
    private static int width=20;


    private Vector2f velocity;
    private float mass;


    public Jumper()
    {
        super(0, Display.getHeight()-height, width, height);
        velocity = new Vector2f(0,0);
        mass = 2;
    }

    public void draw() {

        int x = hitbox.getX();
        int y = hitbox.getY();
        int w = hitbox.getWidth();
        int h = hitbox.getHeight();
        
        GL11.glColor3f(0,1,0);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+w,y);
        GL11.glVertex2f(x+w,y+h);
        GL11.glVertex2f(x,y+h);

        GL11.glEnd();            
    }

    public void update(float delta) {

        float x = hitbox.getX();
        float y = hitbox.getY();

        // fix on boundaries...
        if (x < 0)
        {
            x = 0;
            velocity.setX(0);
        }

        if (x > Display.getWidth() - width)
        {
            x = Display.getWidth() - width;
            velocity.setX(0);
        }

        if (y < 0)
        {
            y = 0;
            velocity.setY(0);
        }

        if (y > Display.getHeight() - height)
        {
            y = Display.getHeight() - height;
            velocity.setY(0);
        }




        Vector2f extraForce = new Vector2f(0,0);

        
        // apply gravity
        Vector2f.add(extraForce,
                     (Vector2f) new Vector2f(0, .001f).scale(delta/mass),
                     extraForce);



        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
        {
            Vector2f.add(extraForce, new Vector2f(0f, -.01f), extraForce); // force going up
        }

        // add some horizontal forces in response to key presses
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {
            Vector2f.add(extraForce, new Vector2f(-.001f, 0), extraForce);
        }
      
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
            Vector2f.add(extraForce, new Vector2f(.001f, 0), extraForce);
        }


        // apply force to velocity vector
        extraForce.scale(delta/mass);
        Vector2f.add(velocity, extraForce, velocity);



        x += velocity.getX()*delta;
        y += velocity.getY()*delta;


        

        hitbox.setLocation((int)x,(int)y);
    }

    public void onCollision(Entity other)
    {
        if (other instanceof Platform)
        {

            Rectangle overlap = intersection(other);
            float x =hitbox.getX();
            float y =hitbox.getY();
            double width = overlap.getWidth();
            double height = overlap.getHeight();
            
            if (height > width)
            {
                // horizontal
                x-=width;
                velocity.setX(0);
            }
            else
            {
                // vertical collision
                y -= height;
                velocity.setY(0);
            }

            hitbox.setLocation((int)x,(int)y);
        }
    }


}
