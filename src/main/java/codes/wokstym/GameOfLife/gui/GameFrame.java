package codes.wokstym.GameOfLife.gui;

import codes.wokstym.GameOfLife.BoardOperator;
import codes.wokstym.GameOfLife.utils.JsonParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends Application {

    static final int PIXEL_SIZE = 16;

    public void show(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Game of Life");
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> Platform.exit());

        BoardOperator boardOperator = JsonParser.getInstance().parseSettings();

        if (boardOperator.getPatterns().isEmpty())
            throw new NullPointerException("No patterns defined in Json file");
        boardOperator.genNewBoard(0);

        int stageHeight = boardOperator.getHeight() * PIXEL_SIZE;
        int stageWidth = boardOperator.getWidth() * PIXEL_SIZE;

        Pane rootNode = new Pane();
        GameScene gameScene = new GameScene(rootNode, stageHeight, stageWidth);
        gameScene.setOnKeyPressed(event -> {

            if (event.getCode().equals(KeyCode.ENTER)) {
                try {
                    boardOperator.switchPattern();
                    gameScene.refreshScene(boardOperator.getCurrentPatternBoard().getAliveCells());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stage.setScene(gameScene);
        stage.show();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    boardOperator.tick();
                    gameScene.refreshScene(boardOperator.getCurrentPatternBoard().getAliveCells());
                });
            }
        }, 0, 1000 / boardOperator.getFrequency());

    }

}
