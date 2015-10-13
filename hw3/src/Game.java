import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Game extends Scene {

    private Player player;
    private LinkedList<Wall> walls;
    private LinkedList<Projectile> projectiles;
    private Traverser target;
    private AudioManager aman;

    public Game() throws IOException
    {
        aman = AudioManager.getInstance();
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
        player = new Player(250, "res/breadedcat.png", projectiles);
        walls = new LinkedList<>();
        target = new Traverser(3, "res/mouse.png");

        walls.add(new Wall(30, Display.getHeight() - 175, 80, 80));
        walls.add(new Wall(300, Display.getHeight() - 175, 80, 80));
        walls.add(new Wall(500, Display.getHeight() - 250, 80, 80));
        walls.add(new Wall(700, Display.getHeight() - 200, 80, 80));
    }

    public Scene nextScene() { return null; }
    private int w=400;


    public boolean drawFrame(float delta)
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        aman.update();

        // draw the main screen
        GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(player.getX() - w, player.getX()+w,
                player.getY()+w, player.getY()-w,
                1,-1);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        player.update(delta);

        for (Wall w : walls)
        {
            w.update(delta);
            w.draw();
        }

        for (Wall w : walls)
        {
            player.testCollision(w);
        }

        player.draw();

        target.update(delta);
        target.draw();


        //update and move projectiles
        Projectile projectile;
        Iterator<Projectile> projectilesIt = projectiles.iterator();
        while (projectilesIt.hasNext())
        {
            projectile = projectilesIt.next();
            projectile.update(delta);

            if (!projectile.isActive())
            {
                projectilesIt.remove();
            }
            else
            {
                projectile.draw();
                target.testCollision(projectile);
                for (Wall w : walls)
                {
                    w.testCollision(projectile);
                }
            }
        }



        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);



        // draw the minimap
        GL11.glViewport(Display.getWidth() - 200, Display.getHeight() - 200, 200, 200);

        GL11.glColor3f(1, 1, 1);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(0, 0);
        GL11.glVertex2f(Display.getWidth(), 0);
        GL11.glVertex2f(Display.getWidth(), Display.getHeight());
        GL11.glVertex2f(0, Display.getHeight());
        GL11.glEnd();

        for (Wall w : walls)
        {
            w.draw();
        }
        player.draw();

        target.update(delta / 5);
        target.draw();

        return true;
    }
}
