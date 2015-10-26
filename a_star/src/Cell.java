import org.lwjgl.opengl.GL11;

public class Cell extends Entity
{
    private int row;
    private int col;
    private World world;
    private String status;

    //heuristic value
    private int h;

    //movement cost
    private int g;

    public Cell(int row, int col, World w, String status)
    {
        this.row = row;
        this.col = col;
        this.world = w;
        this.status = status;
    }

    public void drawAt(float x, float y, float dx, float dy) {

        if (status.equals("obstacle"))
        {
            GL11.glColor3f(1, 0, 0);
        }
        else if (status.equals("start"))
        {
            GL11.glColor3f(0,0.3f,0.3f);
        }
        else if (status.equals("end"))
        {
            GL11.glColor3f(0,1,1);
        }
        else
        {
            GL11.glColor3f(0,0,0);
        }

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+dx,y);
        GL11.glVertex2f(x+dx,y+dy);
        GL11.glVertex2f(x,y+dy);

        GL11.glEnd();

    }

    public void setAsStart() {
        status = "start";
    }
    public void setAsEnd() {
        status = "end";
    }

    public void setAsObstacle() {
        status = "obstacle";
    }

    public void setAsEmpty()
    {
        status = "empty";
    }

    public boolean isStart()
    {
        return status.equals("start");
    }

    public boolean isEnd()
    {
        return status.equals("end");
    }

    public boolean isObstacle()
    {
        return status.equals("obstacle");
    }

    public boolean isEmpty()
    {
        return status.equals("empty");
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}