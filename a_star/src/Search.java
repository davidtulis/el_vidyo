import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

        //g value is distance from current node to start node
        //g value of start node is 0
        start.setGValue(0);
        start.setHValue(end);
        start.setFValue(start.getGValue() + start.getHValue());

        while (openSet.size()!=0)
        {
            Collections.sort(openSet, new Comparator<Cell>() {
                public int compare(Cell c1, Cell c2) {
                    return c1.getFValue() - c2.getFValue();
                }
            });

            //cell with the lowest F value
            Cell current = openSet.get(0);

            if (current==end){
                //reconstruct path to start
                //end
            }

            openSet.remove(current);
            closedSet.add(current);

            List<Cell> neighboringCells = current.getNeighboringCells();

            //neighboring cells, sorted by lowest F value
            for (Cell c : neighboringCells)
            {
                if (closedSet.contains(c))
                {
                    continue;
                }
                int tentativeG = c.getGValue() +
            }
        }

        //will return list of Cells to take
        //might want to consider returning a set of instructions to take from start to end, but this might be harder to draw
        return null;

    }
}
;
