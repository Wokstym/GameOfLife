package codes.wokstym.GameOfLife.gui;

import codes.wokstym.GameOfLife.board.utils.Cell;
import codes.wokstym.GameOfLife.board.utils.Position;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static codes.wokstym.GameOfLife.gui.GameFrame.PIXEL_SIZE;

/**
 * Scene which holds cell rectangles and is responsible for changing
 * colors depending or them being alive or not
 */
class GameScene extends Scene {

    private Map<Position, Rectangle> rectangles;

    /**
     * Generate black rectangles, store them in a HasMap and add
     * them to scene
     *
     * @param rootPane rootPane which stores objects shown
     * @param height height of scene
     * @param width width of scene
     * @param allCells set of positions of cells used for reference to generate rectangles
     */
    GameScene(Pane rootPane, int height, int width, Set<Position> allCells) {
        super(rootPane, width, height, Color.BLACK);

        Group group = new Group();
        rectangles = allCells
                .stream()
                .collect(Collectors.toMap(position -> position, this::generateRectangle));

        allCells.stream()
                .map(position -> rectangles.get(position))
                .forEach(rectangle -> group.getChildren().add(rectangle));

        rootPane.getChildren().add(group);
    }

    /**
     * For each rectangle checks if cell, corresponding to it, is alive
     * and based on that set its color
     *
     * @param allCells HashMap of cells in a board used for defying their state
     */
    void refreshScene(Map<Position, Cell> allCells) {
        for (Position position : allCells.keySet()) {
            Rectangle rectangle = rectangles.get(position);
            Color color = getColor(allCells.get(position).isAlive());
            rectangle.setFill(color);
        }
    }

    private Rectangle generateRectangle(Position boardPos) {
        Position canvasPos = getRoomPosOnCanvas(boardPos);
        Rectangle rectangle = new Rectangle(PIXEL_SIZE, PIXEL_SIZE);
        rectangle.setX(canvasPos.x);
        rectangle.setY(canvasPos.y);
        return rectangle;
    }

    private Position getRoomPosOnCanvas(Position relativeRoomPos) {
        return new Position(
                relativeRoomPos.x * PIXEL_SIZE,
                relativeRoomPos.y * PIXEL_SIZE
        );
    }

    private Color getColor(boolean isAlive) {
        return isAlive ? Color.WHITE : Color.BLACK;
    }
}
