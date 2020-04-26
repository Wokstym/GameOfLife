package codes.wokstym.GameOfLife.board;

import codes.wokstym.GameOfLife.board.utils.Cell;
import codes.wokstym.GameOfLife.board.utils.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Representation of a plane which holds dead and alive cells. It is responsible for
 * storing Cells and changing their state
 *
 */
@AllArgsConstructor
public class Board {

    @Getter
    private Set<Position> positionsOfAlive;
    @Getter
    private Map<Position, Cell> allCells;

    final public Position upperLeft;
    final public Position lowerRight;

    /**
     * Marks Cells as dead that meets the conditions - if it has number of neighbours that is more than
     * first parameter or less than second parameter it will be marked as dead
     *
     * @param moreThan biggest number of neighbours that is enough to survive
     * @param lessThan smallest number of neighbours that is enough to survive
     */
    public void setCellsAsDead(int moreThan, int lessThan) {

        Set<Position> deadCellPositions = positionsOfAlive
                .stream()
                .filter(cellPosition -> {
                    int neighboursNr = countAliveNeighbours(cellPosition);
                    return neighboursNr < lessThan || neighboursNr > moreThan;
                })
                .collect(Collectors.toSet());

        for (Position cellPosition : deadCellPositions) {
            positionsOfAlive.remove(cellPosition);
            allCells.get(cellPosition).setAlive(false);
        }
    }

    /**
     * Mark Cell as alive
     *
     * @param cellPosition Position on map of the cell
     */
    public void setCellAlive(Position cellPosition) {
        Cell cell = allCells.get(cellPosition);
        cell.setAlive(true);
        positionsOfAlive.add(cellPosition);
    }

    private int countAliveNeighbours(Position position) {
        return (int) position
                .getNeighbours()
                .stream()
                .filter(neighbourPos -> neighbourPos.isInBoundary(upperLeft, lowerRight) && positionsOfAlive.contains(neighbourPos))
                .count();
    }


    /**
     * Convert board into string for testing purposes.
     *
     * @return String representation of board
     */
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("\n========================================================\n");
        for (int i = upperLeft.x; i < lowerRight.x; i++) {
            for (int j = upperLeft.y; j < lowerRight.y; j++) {
                if (positionsOfAlive.contains(new Position(j, i)))
                    resultBuilder.append("X");
                else
                    resultBuilder.append(" ");
            }
            resultBuilder.append("\n");
        }
        resultBuilder.append("========================================================\n");
        return resultBuilder.toString();
    }
}
