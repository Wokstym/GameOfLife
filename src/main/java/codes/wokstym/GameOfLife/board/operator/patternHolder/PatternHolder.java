package codes.wokstym.GameOfLife.board.operator.patternHolder;

import codes.wokstym.GameOfLife.board.Board;
import codes.wokstym.GameOfLife.board.utils.Cell;
import codes.wokstym.GameOfLife.board.utils.Position;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Class responsible for holding parsed alive positions and based
 * on that generating new Board
 */
@AllArgsConstructor
public class PatternHolder {

    List<Position> alivePositions;
    Position upperLeft;
    Position lowerRight;


    /**
     * Prepares data for board, based on alivePositions and generates new Board
     *
     * @return newly generated board
     */
    public Board generatePatternBoard() {
        Set<Position> aliveCells = new HashSet<>(alivePositions);

        Map<Position, Cell> allCells = new HashMap<>();

        for (int i = upperLeft.x; i <= lowerRight.x; i++) {
            for (int j = upperLeft.y; j <= lowerRight.y; j++) {
                Position cellPos = new Position(i, j);
                allCells.put(cellPos, new Cell(cellPos, aliveCells.contains(cellPos)));
            }
        }

        return new Board(aliveCells, allCells, upperLeft, lowerRight);
    }
}
