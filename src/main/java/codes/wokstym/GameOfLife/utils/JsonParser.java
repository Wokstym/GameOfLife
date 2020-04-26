package codes.wokstym.GameOfLife.utils;

import codes.wokstym.GameOfLife.board.operator.BoardOperator;
import codes.wokstym.GameOfLife.board.operator.patternHolder.PatternHolder;
import codes.wokstym.GameOfLife.board.operator.patternHolder.RandomPatternHolder;
import codes.wokstym.GameOfLife.board.utils.Position;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Responsible for parsing json files to BoardOperator and PatternHolders
 */
public class JsonParser {

    private static final String RESOURCES_PATH = "src/main/resources/";
    private static JsonParser instance;
    private Gson gson;
    private JSONParser parser;

    private JsonParser() {
        this.gson = new Gson();
        this.parser = new JSONParser();
    }

    /**
     * Static function to assure there is only one instance of parser
     * following a singleton pattern
     *
     * @return instance of a class
     */
    public static JsonParser getInstance() {
        if (instance == null)
            instance = new JsonParser();

        return instance;
    }

    /**
     * Parses settings json, generates BoardOperator based on them
     * and for every pattern defined in settings generates and adds a PatternHolder
     *
     * @return BoardOperator object with parsed settings
     */
    public BoardOperator parseSettings() throws IOException, ParseException {
        Reader reader = Files.newBufferedReader(Paths.get(RESOURCES_PATH + "settings.json"));
        BoardOperator boardOperator = gson.fromJson(reader, BoardOperator.class);

        Position upperLeft = new Position(0, 0);
        Position lowerRight = new Position(boardOperator.width - 1, boardOperator.height- 1);

        for (String pattern : boardOperator.getPatterns())
            boardOperator.addPatternHolder(parsePositionList(pattern, upperLeft, lowerRight));

        return boardOperator;
    }

    private PatternHolder parsePositionList(String fileName, Position upperLeft, Position lowerRight) throws IOException, ParseException {

        ArrayList<Position> starterPositions;
        Reader reader = Files.newBufferedReader(Paths.get(RESOURCES_PATH + fileName));

        Object obj = parser.parse(reader);
        if (obj instanceof JSONObject) {
            double ratio = (Double) ((JSONObject) obj).get("aliveToAllRatio");
            return new RandomPatternHolder(ratio, upperLeft, lowerRight);
        }

        reader = Files.newBufferedReader(Paths.get(RESOURCES_PATH + fileName));

        Position[] posArr = gson.fromJson(reader, Position[].class);
        starterPositions = new ArrayList<>(Arrays.asList(posArr));
        return new PatternHolder(starterPositions, upperLeft, lowerRight);
    }


}
