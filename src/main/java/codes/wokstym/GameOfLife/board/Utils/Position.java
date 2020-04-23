package codes.wokstym.GameOfLife.board.Utils;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class Position {

    final public int x;
    final public int y;

    /* checks if this Position is in square defined by Upper Left Position and Lower Right Position */
    public Boolean isInBoundary(Position upperLeft, Position lowerRight) {
        return this.follows(upperLeft) && this.precedes(lowerRight);
    }

    /* generate List of Positions that are in a Boundary of upperLeft and lowerRight without repetitions*/
    public static List<Position> genRandomInBoundary(int n, Position upperLeft, Position lowerRight) {

        /* adds all possible spaces to list, shuffle it and returns subList of length n */
        List<Position> emptySpaces = new ArrayList<>();
        for (int i = upperLeft.x; i <= lowerRight.x; i++) {
            for (int j = upperLeft.y; j <= lowerRight.y; j++) {
                emptySpaces.add(new Position(i, j));
            }
        }
        Collections.shuffle(emptySpaces);
        return emptySpaces.subList(0, n);
    }

    /* constant List of 8 differences that are used to calculate position of neighbours by addition */
    private static final List<Position> neighboursDifferences = Arrays.asList(
            new Position(-1, -1),
            new Position(0, -1),
            new Position(1, -1),
            new Position(1, 0),
            new Position(1, 1),
            new Position(0, 1),
            new Position(-1, 1),
            new Position(-1, 0));

    /* returns a List of Positions neighbouring to this Position */
    public List<Position> getNeighbours() {
        return neighboursDifferences
                .stream()
                .map(differencePos -> differencePos.genAddedPos(this))
                .collect(Collectors.toList());
    }


    private boolean precedes(Position other) {
        return this.x <= other.x && this.y <= other.y;
    }

    private boolean follows(Position other) {
        return this.x >= other.x && this.y >= other.y;
    }

    private Position genAddedPos(Position other) {
        return new Position(x + other.x, y + other.y);
    }
}
