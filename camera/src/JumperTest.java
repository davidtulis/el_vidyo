import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import java.util.LinkedList;

public class JumperTest extends Scene {

    private Jumper jumper;
    private LinkedList<Platform> platforms;

    public JumperTest()
    {
        jumper = new Jumper();

        platforms = new LinkedList<>();

        platforms.add(new Platform(30, Display.getHeight()-50, 400, 10));
        platforms.add(new Platform(300, Display.getHeight()-175, 20, 10));
        platforms.add(new Platform(250, Display.getHeight()-250, 20, 10));
        platforms.add(new Platform(175, Display.getHeight()-400, 10, 10));

        
    }


    public boolean drawFrame(float delta)
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

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

        return true;
    }

    


}
