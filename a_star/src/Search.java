import java.util.*;

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
            Collections.sort(openSet, (c1, c2) -> c1.getFValue() - c2.getFValue());

            //cell with the lowest F value
            Cell current = openSet.get(0);

            if (current==end){
                LinkedList<Cell> path = new LinkedList<>();
                while (current!=start) {
                    path.add(current);
                    current = current.getCameFrom();
                }
                return path;
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
                int tentativeG = current.getGValue();
                if (!openSet.contains(c))
                {
                    openSet.add(c);
                }
                else if (tentativeG > c.getGValue())
                {
                    continue;
                }

                //at this point we have found the best path
                c.setCameFrom(current);
                c.setGValue(tentativeG);
                c.setFValue(c.getGValue()+c.getFValue());
            }
        }

        //if we reach this point, there is no path to the endpoint
        return null;
    }
}