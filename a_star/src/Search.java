//David Tulis
import java.util.*;

public class Search {

    public Cell start;
    public Cell end;
    private Cell[][] grid;

    public Search(Cell[][] grid, Cell start, Cell end) {
        this.start = start;
        this.end = end;
        this.grid = grid;
    }

    public List<Cell> findPath() {
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

        while (openSet.size() != 0) {
            Collections.sort(openSet, new Comparator<Cell>() {
                public int compare(Cell c1, Cell c2) {
                    return c1.getFValue() - c2.getFValue();
                }
            });

            //cell with the lowest F value will be the first cell
            Cell current = openSet.get(0);

            if (current == end) {
                LinkedList<Cell> path = new LinkedList<>();
                while (current != start) {
                    path.add(current);
                    current = current.getCameFrom();
                }
                return path;
            }

            openSet.remove(current);
            closedSet.add(current);

            List<Cell> neighboringCells = current.getNeighboringCells();

            //neighboring cells, sorted by lowest F value
            for (Cell neighbor : neighboringCells) {
                if (closedSet.contains(neighbor)) {//already evaluated this one
                    continue;
                }

                int tentativeG = current.getGValue() + 1;
                if (!openSet.contains(neighbor)) { //discover new node
                    openSet.add(neighbor);
                } else if (tentativeG >= neighbor.getGValue()) { //this is not the better path
                    continue;
                }

                //at this point we have found the best path
                neighbor.setCameFrom(current);
                neighbor.setHValue(end);
                neighbor.setGValue(tentativeG);
                neighbor.setFValue(neighbor.getGValue() + neighbor.getHValue());
            }
        }

        //if we reach this point, there is no path to the endpoint
        return null;
    }
}