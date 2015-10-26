import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.Random;
import java.util.Scanner;

public class World {

    private Cell[][] grid;
    private int countw;

    public int getCounth() {
        return counth;
    }

    public int getCountw() {
        return countw;
    }

    private int counth;

    private boolean drawEdges;

    public World(int countw, int counth) {
        this.countw = countw;
        this.counth = counth;
        grid = new Cell[counth][countw];
        for (int i = 0; i<counth; i++)
        {
            for (int j=0; j<countw; j++)
            {
                grid[i][j] = new Cell(i, j, this, "empty");
            }
        }
        drawEdges = false;

        Scanner a = new Scanner(System.in);
        System.out.println("Enter x for 1st box");
        //int x1 = a.nextInt();
        System.out.println("Enter y for 1st box");
        //int y1 = a.nextInt();
        System.out.println("Enter x for 2nd box");
        //int x2 = a.nextInt();
        System.out.println("Enter y for 2nd box");
        //int y2 = a.nextInt();


        Random rand = new Random();
        //create two boxes to draw the path between
        int startX = rand.nextInt(countw);
        int startY = rand.nextInt(counth);
        int endX = rand.nextInt(countw);
        int endY = rand.nextInt(counth);

        grid[startY][startX].setAsStart();
        grid[endY][endX].setAsEnd();

        //create obstacles
        for (int i = 0; i < Math.sqrt(counth*countw)*2; i++)
        {
            int obstacle1X = rand.nextInt(countw);
            int obstacle1Y = rand.nextInt(counth);

            //cell is not occupied by the player so we set it as an obstacle
            if (grid[obstacle1Y][obstacle1X].isEmpty())
            {
                grid[obstacle1Y][obstacle1X].setAsObstacle();

            }
            else //try again and hope for the best
            {
                obstacle1X = rand.nextInt(countw);
                obstacle1Y = rand.nextInt(counth);
                grid[obstacle1Y][obstacle1X].setAsObstacle();
            }
        }

        Search searcher = new Search(grid, grid[startY][startX], grid[endY][endX]);
        searcher.aStarSearch();

    }

    public void setDrawEdges(boolean flag)
    {
        drawEdges = flag;
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
                    GL11.glColor3f(0, 1, 1);
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

    public Cell[][] getGrid() {
        return grid;
    }
}