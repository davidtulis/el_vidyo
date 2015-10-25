import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class World {

    private Entity[][] grid;
    private int countw;
    private int counth;

    private boolean drawEdges;

    public static class Cell
    {
        private int row;
        private int col;
        private World world;

        public Cell(int row, int col, World w)
        {
            this.row = row;
            this.col = col;
            this.world = w;
        }

        public int getRow() { return row; }
        public int getCol() { return col; }
        
    }


    public World(int countw, int counth)
    {
        this.countw = countw;
        this.counth = counth;
        grid = new Entity[counth][countw];
        drawEdges = false;;

        Random rand = new Random();

        int box1X = rand.nextInt((10 - 1) + 1) ;
        int box1Y = rand.nextInt((5 - 1) + 1) ;

        int box2X = rand.nextInt((10 - 1) + 1) ;
        int box2Y = rand.nextInt((5 - 1) + 1) ;

        System.out.printf("1x:%d 1y:%d 2x:%d 2y:%d\n", box1X, box1Y, box2X, box2Y);

        World.Cell c1 = this.cellAtCoord(box1X, box1Y);
        World.Cell c2 = this.cellAtCoord(box2X, box2Y);
        System.out.printf("%d, %d\n", c1.getCol(), c1.getRow());
        System.out.printf("%d, %d\n", c2.getCol(), c2.getRow());

        this.set(c1.getRow(), c1.getCol(), new Box());
        this.set(c2.getRow(), c2.getCol(), new Box());



    }


    public void set(int r, int c, Entity e)
    {
        grid[r][c] = e;
    }

    public void setDrawEdges(boolean flag)
    {
        drawEdges = flag;
    }

    public Cell cellAtCoord(float x, float y)
    {
        float dx = Display.getWidth() / countw;
        float dy = Display.getHeight() / counth;

        int col = (int) (x);
        int row = (int) (y);

        return new Cell(row,col,this);

    }

    public void draw()
    {

        float dx = Display.getWidth() / countw;
        float dy = Display.getHeight() / counth;

        for (int c=0; c<countw; c++)
        {
            for (int r=0; r<counth; r++)
            {
                if (grid[r][c] != null)
                {
                    grid[r][c].drawAt(c*dx, r*dy, dx, dy);
                }

                if (drawEdges)
                {
                    float x = c*dx;
                    float y = r*dy;

                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                    GL11.glBegin(GL11.GL_QUADS);

                    GL11.glVertex2f(x,y);
                    GL11.glVertex2f(x+dx,y);
                    GL11.glVertex2f(x+dx,y+dy);
                    GL11.glVertex2f(x,y+dy);

                    GL11.glEnd();
                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);                    
                }

            }
        }

        
        
    }
    
    
    
}
