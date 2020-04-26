package codes.wokstym.GameOfLife.board.operator;

import codes.wokstym.GameOfLife.board.operator.patternHolder.PatternHolder;
import codes.wokstym.GameOfLife.board.Board;
import codes.wokstym.GameOfLife.board.utils.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

/**
 * Class responsible for holding and performing transitions of a tick on board
 * and holding list of PatternHolders which are used to obtain new board
 *
 */
@ToString
@AllArgsConstructor
public class BoardOperator {

    final public int width;
    final public int height;
    final public int frequency;

    @Getter
    private List<String> patterns;
    @Getter
    private Board currentPatternBoard;

    private int currentPatternIndex;
    private List<PatternHolder> patternHolders;


    /**
     * Sets index of pattern which is used and gets a new board
     * from PatternHolder at that index from List of PatternHolders
     *
     * @param patternIndex new index in patternHolders list
     */
    public void setPattern(int patternIndex) {
        currentPatternIndex = patternIndex;
        currentPatternBoard = patternHolders.get(currentPatternIndex).generatePatternBoard();
    }

    /**
     * Shifts index of pattern which is used by one and sets current pattern board
     */
    public void switchPattern() {
        setPattern((currentPatternIndex + 1) % patterns.size());
    }

    /**
     * Adds a patternHolder
     *
     * @param patternHolder a PatternHolder which is added
     */
    public void addPatternHolder(PatternHolder patternHolder) {
        if (patternHolders == null)
            patternHolders = new ArrayList<>();
        patternHolders.add(patternHolder);
    }


    /**
     *  Function responsible for performing a tick, which consist of telling board to set as dead cells that have
     *  more than 3 or less than 2 alive neighbours and looks for dead cells that have exactly 3 alive neighbours
     *  and instructs board to set them as alive
     *
     */
    public void tick() {
        /* for every alive cell increase its neighbours occurrence in neighboursOccurrences */
        Map<Position, Integer> neighboursOccurrences = new HashMap<>();
        Set<Position> oldAliveCells = currentPatternBoard.getPositionsOfAlive();

        for (Position cellPos : oldAliveCells) {
            for (Position neighbourPos : cellPos.getNeighbours()) {
                if (neighbourPos.isInBoundary(currentPatternBoard.upperLeft, currentPatternBoard.lowerRight) && !oldAliveCells.contains(neighbourPos))
                    /* compute hashMap, if it hasn't occurred yet put 1, otherwise increase by one */
                    neighboursOccurrences.compute(neighbourPos, (pos, count) -> count == null ? 1 : count + 1);
            }

        }
        currentPatternBoard.setCellsAsDead(3, 2);

        /* filter occurrences in neighboursOccurrences that are equal 3 and add that position to Board */
        neighboursOccurrences
                .keySet()
                .stream()
                .filter(pos -> neighboursOccurrences.get(pos) == 3)
                .forEach(position -> currentPatternBoard.setCellAlive(position));

    }
}
