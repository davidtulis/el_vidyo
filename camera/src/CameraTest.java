import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import java.util.LinkedList;


public class CameraTest extends Scene {

    private Jumper jumper;
    private LinkedList<Platform> platforms;

    public CameraTest()
    {
        jumper = new Jumper();

        platforms = new LinkedList<>();

        platforms.add(new Platform(30, Display.getHeight()-50, 400, 10));
        platforms.add(new Platform(300, Display.getHeight()-175, 20, 10));
        platforms.add(new Platform(250, Display.getHeight()-250, 20, 10));
        platforms.add(new Platform(175, Display.getHeight()-400, 10, 10));

        
    }


    private int w=200;

    public boolean drawFrame(float delta)
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) 
        {
            w ++;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_B)) 
        {
            w --;
        }

        // draw the main screen
        GL11.glViewport(0,0,Display.getWidth(),Display.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(jumper.getX() - w, jumper.getX()+w,
                     jumper.getY()+w, jumper.getY()-w,
                     1,-1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);



        jumper.update(delta);

        for (Platform p : platforms)
        {
            p.update(delta);
            p.draw();
        }

        for (Platform p : platforms)
        {
            jumper.testCollision(p);
        }
        
        jumper.draw();


        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        // draw the minimap
        GL11.glViewport(Display.getWidth()-200, Display.getHeight()-200, 200, 200);
        
        GL11.glColor3f(1,1,1);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(0, 0);
        GL11.glVertex2f(Display.getWidth(), 0);
        GL11.glVertex2f(Display.getWidth(), Display.getHeight());
        GL11.glVertex2f(0, Display.getHeight());
        GL11.glEnd();
        


        for (Platform p : platforms)
        {
            p.draw();
        }
        jumper.draw();

        return true;
    }

    


}
