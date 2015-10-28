//David Tulis
import org.lwjgl.opengl.GL11;

public class MovementTest extends Scene {

    private World w;


    public MovementTest(String[] startCoords, String[] endCoords)
    {
        w = new World(10, 10, startCoords, endCoords);
    }

    public boolean drawFrame(float delta)
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        w.setDrawEdges(true);
        w.draw();
        return true;
    }
}
