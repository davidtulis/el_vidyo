import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.io.IOException;

public abstract class Scene
{
    public abstract boolean drawFrame(float delta);

    protected Scene nextScene() { return this; }

    private boolean paused=false;

    public Scene go()
    {
        long lastloop = (Sys.getTime()*1000 / Sys.getTimerResolution());


        boolean keepGoing = true;
        do
        {
            if (paused==false) {
                Display.sync(60);   // 60 FPS
                long now = (Sys.getTime() * 1000 / Sys.getTimerResolution());
                long delta = now - lastloop;
                lastloop = now;

                keepGoing = drawFrame(delta);

                // UPDATE DISPLAY
                Display.update();
            }

            /*while (Keyboard.next()) {
                if (Keyboard.getEventKey()==Keyboard.KEY_P)
                {
                    paused=!paused;
                }
            }*/

        } while (keepGoing && ! Display.isCloseRequested());


        return nextScene();
    }
}
