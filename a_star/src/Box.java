import org.lwjgl.opengl.GL11;

public class Box extends Entity {

    private boolean isObstacle=false;

    public Box(boolean isObstacle) {
        this.isObstacle=isObstacle;
    }


}
