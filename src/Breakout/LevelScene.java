package Breakout;

import game_components.Ball;
import game_components.BallGroupController;
import game_components.BlockMatrix;
import game_components.Paddle;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
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
    public static final double LEVEL_WIDTH = 500.0;
    private static final double LEVEL_HEIGHT = 500.0;
    private static final double DEFAULT_PADDLE_WIDTH = 100.0;

    private boolean levelReset = true;
    private int sceneCode;
    private int levelNumber;
    private Paddle paddle;
    private BallGroupController ballGroupController;
    private BlockMatrix blockMatrix;
    private Text levelResetLabel;

    /**
     * The constructor sets the levelNumber to parameter and sets the sceneCode to INITIALIZE_LEVEL.
     * EXPLAIN!
     * @param levelNumber the number of this level
     */
    LevelScene(int levelNumber){
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

        paddle = new Paddle(LEVEL_WIDTH/2.0 - DEFAULT_PADDLE_WIDTH/2, LEVEL_HEIGHT - 50.0, DEFAULT_PADDLE_WIDTH);

        //add switch statement to set different speeds per level
        ballGroupController = new BallGroupController(195.0,-140.0);
        ballGroupController.initializeBall(paddle);

        levelResetLabel = new Text();
        levelResetLabel.setFont(new Font(20.0));
        levelResetLabel.setX(40.0);
        levelResetLabel.setY(350.0);
        levelResetLabel.setText("Press the Space bar to start!");

        blockMatrix = new BlockMatrix(levelNumber);
        Group blockGroup = blockMatrix.createBlockGroup();

        ObservableList rootElements = root.getChildren();
        rootElements.add(paddle.getPaddleNode());
        rootElements.add(ballGroupController.getBallGroup());
        rootElements.add(levelResetLabel);
        rootElements.add(blockGroup);

        sceneCode = GameScene.CURRENT_LEVEL;

        //sets up key press and release events and corresponding logic
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
        if (code == KeyCode.SPACE) {
            levelReset = false;
            levelResetLabel.setVisible(false);
        }
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
        paddle.updateLocation(LEVEL_WIDTH, LEVEL_HEIGHT);
        updateBallLocations();
        if (!levelReset) {
            checkAllBallPaddleCollisions();
            checkAllBallBlockCollisions();
        }
        checkIfLevelIsDone();
    }

    private void checkIfLevelIsDone() {
        if (blockMatrix.getNumBlocksAlive() == 0){
            setSceneCode(GameScene.LEVEL_COMPLETE);
        }
    }

    private void checkAllBallBlockCollisions() {
        for (Ball ball: ballGroupController.getBallList()){
            for (int row = 0; row < blockMatrix.getNumRows(); row++){
                for (int col = 0; col < blockMatrix.getNumCols(); col++){
                    var block = blockMatrix.getMatrix()[row][col];
                    if (block != null) {
                        if (checkShapesIntersect(ball.getBallNode(), block.getBlockNode())){
                            ball.collideWithBlock(block);
                            blockMatrix.removeBlock(block, row, col);
                        }
                    }
                }
            }
        }
    }

    private void checkAllBallPaddleCollisions() {
        for (Ball ball: ballGroupController.getBallList()){
            if (checkShapesIntersect(ball.getBallNode(), paddle.getPaddleNode())){
                ball.collideWithPaddle(paddle);
            }
        }
    }

    private void updateBallLocations() {
        var ballList = ballGroupController.getBallList();
        for (Ball ball: ballList){
            ball.updateLocation(LEVEL_WIDTH, LEVEL_HEIGHT, levelReset, paddle);
            if (ball.resetBall){
                levelReset = ballGroupController.resetBall(ball);
                if (levelReset){
                    levelResetLabel.setVisible(true);
                }
            }
        }
    }

    private boolean checkShapesIntersect(Shape shape1, Shape shape2) {
        return (Shape.intersect(shape1, shape2).getBoundsInLocal().getWidth() > 0);
    }
}