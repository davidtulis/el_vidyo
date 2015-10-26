import java.util.ArrayList;
import java.util.List;

/**
 * Created by FATHER on 10/25/2015.
 */
public class Search {

    private Cell[][] grid;

    public Cell start;
    public Cell end;

    public Search(Cell[][] grid, Cell start, Cell end) {
        this.start = start;
        this.end = end;
        this.grid = grid;
    }

    public List<Cell> aStarSearch() {
        //set of tentative notes yet to be evaluated
        List<Cell> openSet = new ArrayList<>();

        //open set starts out with start node
        openSet.add(start);

        //list of nodes already evaluated
        List<Cell> closedSet = new ArrayList<>();



        while (openSet.size()!=0)
        {

        }

        //will return list of Cells to take
        //might want to consider returning a set of instructions to take from start to end, but this might be harder to draw
        return null;

    }
}
