//David Tulis
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class MovementTest extends Scene {

    private World w;


    public MovementTest()
    {
        w = new World(50, 50);
    }

    public boolean drawFrame(float delta)
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        w.setDrawEdges(true);

        while (Mouse.next())
        {
            if (Mouse.getEventButtonState())
            {
                if (Mouse.getEventButton() == 0)
                {
                    // left
                    w.moveToPosition(w.cellAtCoord(Mouse.getEventX(), Display.getHeight()-Mouse.getEventY()));

                }
                if (Mouse.getEventButton() == 1)
                {
                    // right
                }
            }
        }

        w.draw();
        return true;
    }
}
