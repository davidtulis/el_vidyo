import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import java.io.IOException;

public abstract class Scene
{
    public abstract boolean drawFrame(float delta);

    protected Scene nextScene() { return this; }

    public Scene go()
    {
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

        long lastloop = (Sys.getTime()*1000 / Sys.getTimerResolution());


        boolean keepGoing = true;
        do
        {
            aman.update();


            Display.sync(60);   // 60 FPS
            long now = (Sys.getTime()*1000 / Sys.getTimerResolution());
            long delta = now - lastloop;
            lastloop = now;

            keepGoing = drawFrame(delta);
            
            // UPDATE DISPLAY
            Display.update();
        } while (keepGoing && ! Display.isCloseRequested());


        return nextScene();
    }
}
