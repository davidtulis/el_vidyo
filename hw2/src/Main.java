import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Main
{
    public static final int TARGET_FPS=100;
    public static final int SCR_WIDTH=800;
    public static final int SCR_HEIGHT=600;

    private static LinkedList<Projectile> projectiles;
    private static MouseFollower player;
    private static Traverser target;


    public static void main(String[] args) throws LWJGLException, IOException
    {
        initGL(SCR_WIDTH, SCR_HEIGHT);

        AudioManager aman = AudioManager.getInstance();
        try
        {
            aman.loadLoop("song", "res/doom1.ogg");
            aman.play("song");
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

        projectiles = new LinkedList<>();
        player = new MouseFollower(250, "res/breadedcat.png", projectiles);
        target = new Traverser(3, "res/mouse.png");

        long lastLoop = (Sys.getTime()*1000 / Sys.getTimerResolution());

        while (! Display.isCloseRequested())
        {
            Display.sync(TARGET_FPS);

            long now = (Sys.getTime()*1000 / Sys.getTimerResolution());
            long delta = now - lastLoop;
            lastLoop=now;

            GL11.glClear((GL11.GL_COLOR_BUFFER_BIT));
            Projectile projectile;

            // UPDATE GAME OBJECTS
            player.update(delta);
            target.update(delta/3);

            Iterator<Projectile> iterator = projectiles.iterator();
            while (iterator.hasNext())
            {
                projectile = iterator.next();
                projectile.update(delta);

                if (!projectile.isActive())
                {
                    iterator.remove();
                }
                else
                {
                    projectile.draw();
                    target.testCollision(projectile);
                }
            }

            // DRAW OBJECTS
            player.draw();
            target.draw();

            // UPDATE DISPLAY
            Display.update();
        }

        Display.destroy();
    }

    public static void initGL(int width, int height) throws LWJGLException
    {
        // open window of appropriate size
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.create();
        Display.setVSyncEnabled(true);

        // enable 2D textures
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // set "clear" color to black
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // set viewport to entire window
        GL11.glViewport(0,0,width,height);

        // set up orthographic projectionr
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        // GLU.gluPerspective(90f, 1.333f, 2f, -2f);
        // GL11.glTranslated(0, 0, -500);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
}