import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.io.IOException;

public abstract class Scene
{
    private boolean doExit = false;

    public abstract boolean drawFrame(float delta);
    protected void exit()
    {
        doExit=true;
    };


    protected Scene nextScene() {
        return null;
    }

    public boolean go()
    {
        long lastloop = (Sys.getTime()*1000 / Sys.getTimerResolution());

        boolean keepGoing = true;
        do
        {
            Display.sync(60);   // 60 FPS
            long now = (Sys.getTime() * 1000 / Sys.getTimerResolution());
            long delta = now - lastloop;
            lastloop = now;

            keepGoing = drawFrame(delta);

            // UPDATE DISPLAY
            Display.update();

            if (Display.isCloseRequested())
            {
                return false;
            }

        } while (keepGoing);

        return true;
    }
}
