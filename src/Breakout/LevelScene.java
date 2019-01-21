package Breakout;

import game_components.Paddle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class implements the GameScene interface and sets up the structure for creating the scene for each level in the
 * game.
 *
 * @author Dhanush Madabusi
 */
public class LevelScene implements GameScene{

    private static final Color LEVEL_BACKGROUND = Color.AQUAMARINE;
    private static final double LEVEL_WIDTH = 500.0;
    private static final double LEVEL_HEIGHT = 500.0;
    private static final double DEFAULT_PADDLE_WIDTH = 100.0;

    private int sceneCode;
    private int levelNumber;
    private Paddle paddle;

    /**
     * The constructor sets the levelNumber to parameter and sets the sceneCode to INITIALIZE_LEVEL.
     * EXPLAIN!
     * @param levelNumber the number of this level
     */
    public LevelScene(int levelNumber){
        this.levelNumber = levelNumber;
        sceneCode = GameScene.INITIALIZE_LEVEL;
    }

    @Override
    public int getSceneCode() {
        return sceneCode;
    }

    @Override
    public void setSceneCode(int code) {
        sceneCode = code;
    }

    @Override
    public Scene setUpScene() {
        var root = new Group();
        var scene = new Scene(root, LEVEL_WIDTH, LEVEL_HEIGHT, LEVEL_BACKGROUND);

        //placeholder title
        Text title = new Text();
        title.setFont(new Font(40.0));
        title.setX(LEVEL_WIDTH/2);
        title.setY(LEVEL_HEIGHT/2 - 100);
        title.setText("Level" + levelNumber);

        paddle = new Paddle(LEVEL_WIDTH/2.0 - DEFAULT_PADDLE_WIDTH/2, LEVEL_HEIGHT - 50.0, DEFAULT_PADDLE_WIDTH);


        root.getChildren().addAll(title, paddle.getPaddle());
        sceneCode = GameScene.CURRENT_LEVEL;

        //set ups key press events and corresponding logic
        scene.setOnKeyPressed(keyEvent -> keyBoardPressEvent(keyEvent.getCode()));
        scene.setOnKeyReleased(keyEvent -> keyBoardReleaseEvent(keyEvent.getCode()));

        return scene;
    }

    /**
     * Handles keyboard input events
     *
     * @param code value for keycode for event
     */
    private void keyBoardPressEvent(KeyCode code) {
        paddle.handleKeyPressInput(code);
        handleCheatKeys(code);
    }

    private void handleCheatKeys(KeyCode code) {
        switch (code){
            case DIGIT1:
                setSceneCode(GameScene.CHEAT_LEVEL_1);
                break;
            case DIGIT2:
                setSceneCode(GameScene.CHEAT_LEVEL_2);
                break;
            case DIGIT3:
                setSceneCode(GameScene.CHEAT_LEVEL_3);
                break;
            case DIGIT4:
                setSceneCode(GameScene.CHEAT_LEVEL_4);
                break;
            case Q:
                setSceneCode(GameScene.LEVEL_QUIT);
                break;

        }

    }

    private void keyBoardReleaseEvent(KeyCode code) {
        paddle.handleKeyReleaseInput(code);
    }

    @Override
    public void step() {
        paddle.updateLocation();

    }
}
