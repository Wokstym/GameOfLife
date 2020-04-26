package codes.wokstym.GameOfLife.board.operator.patternHolder;

import codes.wokstym.GameOfLife.board.Board;
import codes.wokstym.GameOfLife.board.utils.Position;

/**
 * Class which extends PatternHolder, instead of holding alivePositions it holds alive to all ratio
 * which is used for generating alivePositions
 */
public class RandomPatternHolder extends PatternHolder {

    private double aliveToAllRatio;

    public RandomPatternHolder(double aliveToAllRatio, Position upperLeft, Position lowerRight) {
        super(null, upperLeft, lowerRight);
        this.aliveToAllRatio = aliveToAllRatio;
    }

    /**
     * Generates based on alive to all ratio position of alive cells and calls superclass
     * to generate board
     *
     * @return newly generated board
     */
    @Override
    public Board generatePatternBoard() {

        int width = lowerRight.x - upperLeft.x + 1;
        int height = lowerRight.y - upperLeft.y + 1;

        this.alivePositions = Position.genRandomInBoundary((int) (width * height * aliveToAllRatio), upperLeft, lowerRight);
        return super.generatePatternBoard();
    }
}
