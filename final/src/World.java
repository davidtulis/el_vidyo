//David Tulis
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Random;

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

    public Cell cellAtCoord(float x, float y)
    {
        float dx = Display.getWidth() / countw;
        float dy = Display.getHeight() / counth;

        int col = (int) (x/dx);
        int row = (int) (y/dy);

        return grid[row][col];
    }

    public World(int countw, int counth) {
        this.countw = countw;
        this.counth = counth;
        grid = new Cell[counth][countw];
        for (int i = 0; i < counth; i++) {
            for (int j = 0; j < countw; j++) {
                grid[i][j] = new Cell(i, j, this, "empty");
                grid[i][j].setGValue(Integer.MAX_VALUE);
                grid[i][j].setFValue(Integer.MAX_VALUE);
            }
        }
        drawEdges = false;

        Cell start = null;
        Cell end = null;
        try {
            start = grid[Integer.parseInt(startCoords[1].trim()) - 1][Integer.parseInt(startCoords[0].trim()) - 1].setAsStart();
            end = grid[Integer.parseInt(endCoords[1].trim()) - 1][Integer.parseInt(endCoords[0].trim()) - 1].setAsEnd();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You put in some bad coordinates. please try again");
            System.exit(0);
        }

        //create obstacles
        Random rand = new Random();
        for (int i = 0; i < (counth * countw) / 5; i++) {
            int obstacle1X = rand.nextInt(countw);
            int obstacle1Y = rand.nextInt(counth);

            if (!grid[obstacle1Y][obstacle1X].isEmpty()) {
                obstacle1X = rand.nextInt(countw);
                obstacle1Y = rand.nextInt(counth);
            }

            grid[obstacle1Y][obstacle1X].setAsObstacle();
        }

        Search searcher = new Search(grid, start, end);

        List<Cell> path = searcher.findPath();

        if (path == null) {
            System.out.println("no path found");
        } else {
            System.out.println("Blue is where you started. Green is where you ended. Yellow highlights the path");
            System.out.printf("distance=%d\n", path.size());
            path.remove(0); //remove the end block, because technically it isnt part of the path
            for (Cell c : path) {
                c.setAsPath();
            }
        }
    }

    public void setDrawEdges(boolean flag) {
        drawEdges = flag;
    }

    public void draw() {

        float dx = Display.getWidth() / countw;
        float dy = Display.getHeight() / counth;

        for (int c = 0; c < countw; c++) {
            for (int r = 0; r < counth; r++) {
                if (grid[r][c] != null) {
                    grid[r][c].drawAt(c * dx, r * dy, dx, dy);
                }

                if (drawEdges) {
                    GL11.glColor3f(0, 1, 1);
                    float x = c * dx;
                    float y = r * dy;

                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                    GL11.glBegin(GL11.GL_QUADS);

                    GL11.glVertex2f(x, y);
                    GL11.glVertex2f(x + dx, y);
                    GL11.glVertex2f(x + dx, y + dy);
                    GL11.glVertex2f(x, y + dy);

                    GL11.glEnd();
                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                }
            }
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void moveToPosition(Cell destination) {
        System.out.printf("moving to cell at %d, %d\n", destination.getCol(), destination.getRow());
    }
}