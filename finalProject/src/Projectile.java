import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import java.time.LocalDateTime;
import java.util.Calendar;

public class Projectile extends Entity {

    private static final int WIDTH=10;
    private static final int HEIGHT=10;
    private static final float SPEED=1f;

    private Vector2f velocity;

    // initial x,y.  direction should be 1 to go right, -1 to go left
    public Projectile(int x, int y, boolean updown, int direction) {
        super(x, y, WIDTH, HEIGHT);
        hitbox = new Rectangle(x, y, 10, 10);

        if (updown==false)
            {
                velocity = new Vector2f(direction, 0);
            }
            else
            {
                velocity = new Vector2f(0, direction);
            }
    }

    public void update(float delta) {

        float x  = hitbox.getX();
        float y = hitbox.getY();

        x+=velocity.getX()*delta;
        y+=velocity.getY()*delta;

        hitbox.setLocation((int)(x+delta), (int)y);

        if (x < 0 - hitbox.getWidth() || x > Display.getWidth())
        {
            this.deactivate();
        }

        hitbox.setLocation((int)x, (int)y);
    }
    
    public void draw() {

        int x = hitbox.getX();
        int y = hitbox.getY();
        int w = hitbox.getWidth();
        int h = hitbox.getHeight();

        GL11.glColor3f(1,1,0);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+w,y);
        GL11.glVertex2f(x+w,y+h);
        GL11.glVertex2f(x,y+h);

        GL11.glEnd();

    }

    public void onCollision(Entity other) {
        System.out.println("collision");
        this.deactivate();
    }
}
