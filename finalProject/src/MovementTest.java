import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class MovementTest extends Scene {

    private Player player;
    private LinkedList<Wall> walls;
    private LinkedList<Projectile> projectiles;
    private Traverser target;
    private LinkedList<Powerup> powerups;
    private int score=0;
    private int ammo=30;
    private int state=0;
    private boolean powerupsAvailable=true;

    public static MovementTest current;

    //0=OK
    //1=paused
    //2=needs to be restarted
    //3=win
    //4=loss

    public MovementTest() throws IOException
    {
        state=0;
        projectiles = new LinkedList<>();
        player = new Player(250, "res/breadedcat.png", projectiles);

        player.setRestarted(false);
        powerups = new LinkedList<>();

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
        current=this;

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // draw the main screen
        GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(player.getX() - w, player.getX()+w,
                player.getY()+w, player.getY()-w,
                1,-1);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        if (player.checkAmmo()<10 && powerupsAvailable==true)
        {
            powerups.add(new Powerup("res/ammo.png", 220, 200, 60, 60));
            powerupsAvailable=false;
        }

        for (Powerup powerup : powerups) {
            if (state==0) {
                powerup.update(delta);
            }
            powerup.draw();
        }

        for (int i=0; i<powerups.size(); i++)
        {
            if (player.testCollision(powerups.get(i)))
            {
                powerups.get(i).deactivate();
                powerups.remove(i);
            }
        }

        player.update(delta);

        for (Wall w : walls) {
            if (state==0) {
                w.update(delta);
            }
            w.draw();
        }

        for (Wall w : walls) {
            player.testCollision(w);
        }

        player.draw();

        if (state==0) {
            target.update(delta);
        }
        target.draw();



        //update and move projectiles
        Projectile projectile;
        Iterator<Projectile> projectilesIt = projectiles.iterator();
        while (projectilesIt.hasNext()) {
            projectile = projectilesIt.next();
            if (state==0) {
                projectile.update(delta);
            }

            if (!projectile.isActive()) {
                projectilesIt.remove();
            } else {
                projectile.draw();
                target.testCollision(projectile);
                for (Wall w : walls) {
                    w.testCollision(projectile);
                }
            }
        }

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        //0=OK
        //1=paused
        //2=needs to be restarted
        //3=win
        //4=loss

        if (player.checkAmmo()<0)
        {
            state=4;
        }
        if (target.getScore()>10)
        {
            state=3;
        }

        if (player.getPaused()==true)
        {
             state=1;//game is paused
        }
        else
        {
            state=0;
        }

        if (player.getRestarted()==true)
        {
            state=2;
            player.setRestarted(false);
            return false;
        }

        return true;
    }

    public int getState() {
        return state;
    }
}
