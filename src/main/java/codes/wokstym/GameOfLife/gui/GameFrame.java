package codes.wokstym.GameOfLife.gui;

import codes.wokstym.GameOfLife.board.operator.BoardOperator;
import codes.wokstym.GameOfLife.utils.JsonParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends Application {

    static final int PIXEL_SIZE = 16;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Main function for setting basic settings, declaring key event on Enter
     * and starting application timer
     */
    @Override
    public void start(Stage stage) throws IOException, ParseException {

        stage.setTitle("Game of Life");
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> Platform.exit());

        BoardOperator boardOperator = JsonParser.getInstance().parseSettings();

        if (boardOperator.getPatterns().isEmpty())
            throw new NullPointerException("No patterns defined in Json file");
        boardOperator.setPattern(0);

        int stageHeight = boardOperator.height * PIXEL_SIZE;
        int stageWidth = boardOperator.width * PIXEL_SIZE;

        Pane rootNode = new Pane();
        GameScene gameScene = new GameScene(rootNode, stageHeight, stageWidth, boardOperator.getCurrentPatternBoard().getAllCells().keySet());
        gameScene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                boardOperator.switchPattern();
                gameScene.refreshScene(boardOperator.getCurrentPatternBoard().getAllCells());
            }
        });

        stage.setScene(gameScene);
        stage.show();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    boardOperator.tick();
                    gameScene.refreshScene(boardOperator.getCurrentPatternBoard().getAllCells());
                });
            }
        }, 0, 1000 / boardOperator.frequency);

    }

}
