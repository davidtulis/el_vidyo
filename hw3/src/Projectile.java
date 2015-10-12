import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class Projectile extends Entity {

    private static final int WIDTH=20;
    private static final int HEIGHT=10;
    private static final float SPEED=1f;

    private int direction;

    private Vector2f velocity;

    // initial x,y.  direction should be 1 to go right, -1 to go left
    public Projectile(int x, int y, int direction) {
        super(x,y,WIDTH,HEIGHT);
        this.direction = direction;

        velocity = new Vector2f(direction, -.2f);
    }

    public void update(float delta) {

        float x  = hitbox.getX();
        float y = hitbox.getY();

        Vector2f.add(velocity, (Vector2f) new Vector2f(0, 0.001f).scale(delta), velocity);

        x+=velocity.getX()*delta;
        y+=velocity.getY()*delta;

        //int x = (int)(hitbox.getX() + (SPEED * delta * direction));
        //hitbox.setX(x);

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
        this.deactivate();
    }
}
