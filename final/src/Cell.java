//David Tulis
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Cell extends Entity {
    private int row;
    private int col;
    private World world;
    private String status;
    private int FValue;

    //heuristic distance from cell to end
    private int HValue;

    //shortest distance from start to cell
    private int GValue;

    public Cell getCameFrom() {
        return cameFrom;
    }

    private Cell cameFrom;

    public Cell(int row, int col, World w, String status) {
        this.row = row;
        this.col = col;
        this.world = w;
        this.status = status;
    }

    public List<Cell> getNeighboringCells() {
        List<Cell> neighboringCells = new LinkedList<>();
        Cell[][] grid = world.getGrid();
        //down
        if (row + 1 < world.getCounth() && !grid[row + 1][col].isObstacle()) {
            neighboringCells.add(grid[row + 1][col]);
        }

        //up
        if (row - 1 >= 0 && !grid[row - 1][col].isObstacle()) {
            neighboringCells.add(grid[row - 1][col]);
        }

        //right
        if (col + 1 < world.getCountw() && !grid[row][col + 1].isObstacle()) {
            neighboringCells.add(grid[row][col + 1]);
        }

        //left
        if (col - 1 >= 0 && !grid[row][col - 1].isObstacle()) {
            neighboringCells.add(grid[row][col - 1]);
        }

        Collections.sort(neighboringCells, new Comparator<Cell>() {
            public int compare(Cell c1, Cell c2) {
                return c1.getFValue() - c2.getFValue();
            }
        });

        return neighboringCells;
    }


    public void drawAt(float x, float y, float dx, float dy) {

        if (status.equals("obstacle")) {
            GL11.glColor3f(1, 0, 0); //red
        } else if (status.equals("start")) {
            GL11.glColor3f(0, 1, 1); //cyan
        } else if (status.equals("end")) {
            GL11.glColor3f(0, 1, 0); //green
        } else if (status.equals("path")) {
            GL11.glColor3f(1, 1, 0); //yellow
        } else {
            GL11.glColor3f(0, 0, 0);
        }

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + dx, y);
        GL11.glVertex2f(x + dx, y + dy);
        GL11.glVertex2f(x, y + dy);

        GL11.glEnd();

    }

    public void setHValue(Cell end) {
        //find the x and y distances from the cell to the endpoint
        if (this.isObstacle()) {
            HValue = Integer.MAX_VALUE; //big number
        }
        int hxDistance = Math.abs(this.col - end.getCol());
        int hyDistance = Math.abs(this.row - end.getRow());
        HValue = ((int) Math.floor(Math.sqrt(hxDistance * hxDistance + hyDistance * hyDistance)));
    }

    public int getHValue() {
        return HValue;
    }

    public int getGValue() {
        return GValue;
    }

    public Cell setAsStart() {
        status = "start";
        return this;
    }

    public Cell setAsEnd() {
        status = "end";
        return this;
    }

    public void setAsObstacle() {
        status = "obstacle";
    }

    public void setAsEmpty() {
        status = "empty";
    }

    public void setAsPath() {
        status = "path";
    }

    public boolean isStart() {
        return status.equals("start");
    }

    public boolean isEnd() {
        return status.equals("end");
    }

    public boolean isObstacle() {
        return status.equals("obstacle");
    }

    public boolean isEmpty() {
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

    public void setGValue(int GValue) {
        this.GValue = GValue;
    }

    public void setFValue(int FValue) {
        this.FValue = FValue;
    }

    public int getFValue() {
        return FValue;
    }

    public void setCameFrom(Cell cameFrom) {
        this.cameFrom = cameFrom;
    }
}