package codes.wokstym.GameOfLife;

import codes.wokstym.GameOfLife.board.Board;
import codes.wokstym.GameOfLife.board.Utils.Position;
import codes.wokstym.GameOfLife.utils.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@AllArgsConstructor
@Getter
public class BoardOperator {

    int width;
    int height;
    int frequency;
    List<String> patterns;
    int currentPatternIndex;
    Board currentPatternBoard;

    public void genNewBoard(int patternIndex) throws Exception {
        currentPatternIndex = patternIndex;
        currentPatternBoard = JsonParser.getInstance().parsePositionList(patterns.get(currentPatternIndex), width, height);
    }

    public void switchPattern() throws Exception {
        genNewBoard((currentPatternIndex + 1) % patterns.size());
    }

    /* Every tick we count every alive cell neighbours,  */
    public void tick() {
        /* for every alive cell increase its neighbours occurrence in neighboursOccurrences */
        Map<Position, Integer> neighboursOccurrences = new HashMap<>();
        Set<Position> oldAliveCells = currentPatternBoard.getAliveCells();

        for (Position cellPos : oldAliveCells) {
            for (Position neighbourPos : cellPos.getNeighbours()) {
                if (neighbourPos.isInBoundary(currentPatternBoard.getUpperLeft(), currentPatternBoard.getLowerRight()) && !oldAliveCells.contains(neighbourPos))
                    /* compute hashMap, if it hasn't occurred yet put 1, otherwise increase by one */
                    neighboursOccurrences.compute(neighbourPos, (pos, count) -> count == null ? 1 : count + 1);
            }

        }

        currentPatternBoard.removeCells(3, 2);
        /* filter occurrences in neighboursOccurrences that are equal 3 and add that position to Board */
        neighboursOccurrences
                .keySet()
                .stream()
                .filter(pos -> neighboursOccurrences.get(pos) == 3)
                .forEach(position -> currentPatternBoard.addCell(position));

    }
}
