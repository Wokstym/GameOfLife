package codes.wokstym.GameOfLife.board.utils;

import lombok.*;
import codes.wokstym.GameOfLife.board.Board;

/**
 * Representation of a cell in {@link Board}
 */
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Cell {

    final public Position position;
    @Setter
    @Getter
    private boolean isAlive;

}
