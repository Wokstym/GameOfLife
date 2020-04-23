package codes.wokstym.GameOfLife.gui;

import codes.wokstym.GameOfLife.board.Utils.Position;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Scene;
import java.util.Set;

import static codes.wokstym.GameOfLife.gui.GameFrame.PIXEL_SIZE;

class GameScene extends Scene {

    private Group group;

    GameScene(Pane rootPane, int height, int width) {
        super(rootPane, width, height, Color.BLACK);
        group = new Group();
        rootPane.getChildren().add(group);
    }

    void refreshScene(Set<Position> aliveCells) {
        group.getChildren().clear();
        aliveCells
                .stream()
                .map(this::getRoomPosOnCanvas)
                .forEach(Position -> group.getChildren().add(genRectangle(Position.x, Position.y )));
    }

    private Rectangle genRectangle(int x, int y) {
        Rectangle rectangle = new Rectangle(PIXEL_SIZE, PIXEL_SIZE);
        rectangle.setFill(Color.WHITE);
        rectangle.setX(x);
        rectangle.setY(y);
        return rectangle;
    }

    private Position getRoomPosOnCanvas(Position relativeRoomPos) {
        return new Position(
                relativeRoomPos.x * PIXEL_SIZE,
                relativeRoomPos.y * PIXEL_SIZE
        );
    }
}
