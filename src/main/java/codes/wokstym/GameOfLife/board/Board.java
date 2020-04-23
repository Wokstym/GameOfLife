package codes.wokstym.GameOfLife.board;

import codes.wokstym.GameOfLife.board.Utils.Position;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Board {

    private Set<Position> aliveCells;

    private Position upperLeft;
    private Position lowerRight;

    /* Position in map starts from upper right corner */
    public Board(List<Position> starterPositions, Position upperLeft, Position lowerRight) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
        aliveCells = new HashSet<>();
        if (starterPositions != null)
            aliveCells.addAll(starterPositions);
    }

    public Board(List<Position> starterPositions, int width, int height) {
        this(starterPositions, new Position(0, 0), new Position(width - 1, height - 1));
    }

    private int countNeighboursNr(Position position) {
        return (int) position
                .getNeighbours()
                .stream()
                .filter(neighbourPos -> neighbourPos.isInBoundary(upperLeft, lowerRight) && aliveCells.contains(neighbourPos))
                .count();
    }

    public void removeCells(int smallerOrEqThan, int greaterOrEqThan) {
        aliveCells = aliveCells
                .stream()
                .filter(cell -> {
                    int neighboursNr = countNeighboursNr(cell);
                    return neighboursNr <= smallerOrEqThan && neighboursNr >= greaterOrEqThan;
                })
                .collect(Collectors.toSet());
    }

    public void addCell(Position cell) {
        aliveCells.add(cell);
    }

    /* testing */
    public String toString() {
        StringBuilder x = new StringBuilder();
        x.append("\n========================================================\n");
        for (int i = 0; i < lowerRight.x; i++) {
            for (int j = 0; j < lowerRight.y; j++) {
                if (aliveCells.contains(new Position(j, i)))
                    x.append("X");
                else
                    x.append(" ");
            }
            x.append("\n");
        }
        x.append("========================================================\n");

        return x.toString();

    }

}
