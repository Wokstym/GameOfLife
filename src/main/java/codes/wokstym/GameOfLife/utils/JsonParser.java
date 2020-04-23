package codes.wokstym.GameOfLife.utils;

import codes.wokstym.GameOfLife.BoardOperator;
import codes.wokstym.GameOfLife.board.Board;
import codes.wokstym.GameOfLife.board.Utils.Position;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class JsonParser {

    private static final String RESOURCES_PATH = "src/main/resources/";
    private static JsonParser instance;
    private Gson gson;
    private JSONParser parser;

    private JsonParser() {
        this.gson = new Gson();
        this.parser = new JSONParser();
    }

    /* get instance of class - following singleton pattern */
    public static JsonParser getInstance() {
        if (instance == null) {
            instance = new JsonParser();
        }
        return instance;
    }

    public BoardOperator parseSettings() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(RESOURCES_PATH + "settings.json"));
        return gson.fromJson(reader, BoardOperator.class);
    }

    public Board parsePositionList(String fileName, int width, int height) throws Exception {

        ArrayList<Position> starterPositions;
        Reader reader = Files.newBufferedReader(Paths.get(RESOURCES_PATH + fileName));

        Object obj = parser.parse(reader);
        if (obj instanceof JSONObject) {
            double ratio = (Double) ((JSONObject) obj).get("aliveToAllRatio");
            Position upperLeft = new Position(0, 0);
            Position lowerRight = new Position(width - 1, height - 1);
            return new Board(Position.genRandomInBoundary((int) (width * height *ratio), upperLeft, lowerRight), upperLeft, lowerRight);
        }

        reader = Files.newBufferedReader(Paths.get(RESOURCES_PATH + fileName));
        Position[] posArr = gson.fromJson(reader, Position[].class);
        starterPositions = new ArrayList<>(Arrays.asList(posArr));
        return new Board(starterPositions, width, height);
    }


}
