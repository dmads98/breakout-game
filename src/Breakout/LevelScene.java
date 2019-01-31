package Breakout;

import game_components.Ball;
import game_components.BallGroupController;
import game_components.BlockMatrix;
import game_components.Paddle;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This class implements the GameScene interface and sets up the structure for creating the scene for each level in the
 * game.
 *
 * @author Dhanush Madabusi
 */
public class LevelScene implements GameScene{

    private static final Color LEVEL_BACKGROUND = Color.AQUAMARINE;
    public static final double GAME_WIDTH = 500.0;
    private static final double PANE_WIDTH = 200.0;
    private static final double LEVEL_HEIGHT = 500.0;
    private static final double DEFAULT_PADDLE_WIDTH = 100.0;
    private static final double BORDER_WIDTH = 5.0;
    private static final int MAX_LIVES_POSSIBLE = 9;

    private boolean levelReset;
    private int sceneCode;
    private int levelNumber;
    private Paddle paddle;
    private BallGroupController ballGroupController;
    private BlockMatrix blockMatrix;
    private Text levelResetLabel;
    private boolean pauseGame;
    private int livesLeft;
    private Text livesLabel;
    private Text scoreLabel;
    private int score;

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

    int getLivesLeft(){
        return livesLeft;
    }

    void setLivesLeft(int livesLeft){
        this.livesLeft = livesLeft;
    }

    int getScore() {
        return score;
    }

    void setScore(int score){
        this.score = score;
    }

    @Override
    public Scene setUpScene() {
        var root = new Group();
        var scene = new Scene(root, GAME_WIDTH + PANE_WIDTH, LEVEL_HEIGHT, LEVEL_BACKGROUND);

        paddle = new Paddle(GAME_WIDTH/2.0 - DEFAULT_PADDLE_WIDTH/2, LEVEL_HEIGHT - 50.0, DEFAULT_PADDLE_WIDTH);

        //add switch statement to set different speeds per level
        ballGroupController = new BallGroupController(levelNumber);
        ballGroupController.initializeBall(paddle);

        blockMatrix = new BlockMatrix(levelNumber);
        Group blockGroup = blockMatrix.createBlockGroup();
        createLevelResetLabel();
        Group sidePaneGroup = createSidePaneElements();

        ObservableList rootElements = root.getChildren();
        rootElements.addAll(paddle.getPaddleNode(), ballGroupController.getBallGroup(), levelResetLabel, blockGroup, sidePaneGroup);

        sceneCode = GameScene.CURRENT_LEVEL;
        pauseGame = false;
        levelReset = true;

        //sets up key press and release events and corresponding logic
        scene.setOnKeyPressed(keyEvent -> keyBoardPressEvent(keyEvent.getCode()));
        scene.setOnKeyReleased(keyEvent -> keyBoardReleaseEvent(keyEvent.getCode()));
        return scene;
    }

    private Group createSidePaneElements() {
        Group sidePane = new Group();
        sidePane.getChildren().addAll(createBorder(), createMenuButton(), createPauseButton(), createLevelLabel(),
                createLivesLabel(), createScoreLabel());
        return sidePane;
    }

    private Line createBorder() {
        var border = new Line();
        border.setStartX(GAME_WIDTH);
        border.setStartY(0.0);
        border.setEndX(GAME_WIDTH);
        border.setEndY(LEVEL_HEIGHT);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(BORDER_WIDTH);
        return border;
    }

    private Button createMenuButton() {
        Button menuButton = new Button("Menu");
        menuButton.setPrefWidth(100.0);
        menuButton.setLayoutX(GAME_WIDTH + PANE_WIDTH/2 - menuButton.getPrefWidth()/2 + BORDER_WIDTH/2);
        menuButton.setLayoutY(50.0);
        menuButton.setFocusTraversable(false);
        menuButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                sceneCode = GameScene.LEVEL_QUIT;
            }
        }));
        menuButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        menuButton.setEffect(new DropShadow());
                    }
                });
        menuButton.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        menuButton.setEffect(null);
                    }
                });
        menuButton.setStyle("-fx-background-color: #c3c4c4, linear-gradient(#d6d6d6 50%, white 100%), " +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%); " +
                "-fx-background-radius: 30; -fx-background-insets: 0,1,1; -fx-text-fill: black; -fx-font-size: 16;");
        return menuButton;
    }

    private Button createPauseButton() {
        Button pauseButton = new Button("Pause");
        pauseButton.setPrefWidth(100.0);
        pauseButton.setLayoutX(GAME_WIDTH + PANE_WIDTH/2 - pauseButton.getPrefWidth()/2 + BORDER_WIDTH/2);
        pauseButton.setLayoutY(110.0);
        pauseButton.setFocusTraversable(false);
        pauseButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (pauseButton.getText().equals("Pause")) {
                    pauseButton.setText("Resume");
                    pauseGame = true;
                }
                else{
                    pauseButton.setText("Pause");
                    pauseGame = false;
                }
            }
        }));
        pauseButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        pauseButton.setEffect(new DropShadow());
                    }
                });
        pauseButton.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        pauseButton.setEffect(null);
                    }
                });
        pauseButton.setStyle("-fx-background-color: #c3c4c4, linear-gradient(#d6d6d6 50%, white 100%), " +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%); " +
                "-fx-background-radius: 30; -fx-background-insets: 0,1,1; -fx-text-fill: black; -fx-font-size: 16;");
        return pauseButton;
    }

    private Text createLevelLabel() {
        Text levelLabel = new Text();
        levelLabel.setFont(Font.font("impact", FontWeight.EXTRA_BOLD, 60));
        levelLabel.setText("Level " + levelNumber);
        levelLabel.setStrokeWidth(2);
        levelLabel.setStroke(Color.MEDIUMVIOLETRED);
        var textWidth = levelLabel.getLayoutBounds().getWidth();
        levelLabel.setX((PANE_WIDTH - textWidth)/2 + GAME_WIDTH + BORDER_WIDTH/2);
        levelLabel.setY(230.0);
        return levelLabel;
    }


    private Text createLivesLabel() {
        livesLabel = new Text();
        livesLabel.setFont(Font.font("courier", FontWeight.EXTRA_BOLD, 35));
        livesLabel.setText("Lives: " + livesLeft);
        livesLabel.setStroke(Color.BLACK);
        livesLabel.setFill(Color.MEDIUMVIOLETRED);
        var textWidth = livesLabel.getLayoutBounds().getWidth();
        livesLabel.setX((PANE_WIDTH - textWidth)/2 + GAME_WIDTH + BORDER_WIDTH/2);
        livesLabel.setY(310.0);
        return livesLabel;
    }

    private void updateLivesLabel(){
        livesLabel.setText("Lives: " + livesLeft);
    }

    private Text createScoreLabel() {
        scoreLabel = new Text();
        scoreLabel.setFont(Font.font("impact", FontWeight.EXTRA_BOLD, 45));
        scoreLabel.setText(String.valueOf(score));
        scoreLabel.setStroke(Color.BLACK);
        scoreLabel.setFill(Color.FLORALWHITE);
        var textWidth = scoreLabel.getLayoutBounds().getWidth();
        scoreLabel.setX((PANE_WIDTH - textWidth)/2 + GAME_WIDTH + BORDER_WIDTH/2);
        scoreLabel.setY(410.0);
        return scoreLabel;
    }

    private void updateScoreLabel(){
        scoreLabel.setText(String.valueOf(score));
        var textWidth = scoreLabel.getLayoutBounds().getWidth();
        scoreLabel.setX((PANE_WIDTH - textWidth)/2 + GAME_WIDTH + BORDER_WIDTH/2);
    }

    private void createLevelResetLabel() {
        levelResetLabel = new Text();
        levelResetLabel.setFont(new Font(20.0));
        levelResetLabel.setText("Press the Space bar to start!");
        var textWidth = levelResetLabel.getLayoutBounds().getWidth();
        levelResetLabel.setX((GAME_WIDTH - textWidth)/2);
        levelResetLabel.setY(350.0);

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
        else {
            handleCheatKeys(code);
        }
    }

    private void handleCheatKeys(KeyCode code) {
        if (code == KeyCode.DIGIT1) {
            score = 0;
            setSceneCode(GameScene.CHEAT_LEVEL_1);
        } else if (code == KeyCode.DIGIT2) {
            score = 0;
            setSceneCode(GameScene.CHEAT_LEVEL_2);
        } else if (code == KeyCode.DIGIT3) {
            score = 0;
            setSceneCode(GameScene.CHEAT_LEVEL_3);
        } else if (code == KeyCode.DIGIT4) {
            score = 0;
            setSceneCode(GameScene.CHEAT_LEVEL_4);
        } else if (code == KeyCode.Q) {
            score = 0;
            setSceneCode(GameScene.LEVEL_QUIT);
        } else if (code == KeyCode.A){
            if (livesLeft < MAX_LIVES_POSSIBLE) {
                livesLeft++;
                updateLivesLabel();
            }
        } else {
            return;
        }
        levelReset = true;
    }

    private void keyBoardReleaseEvent(KeyCode code) {
        paddle.handleKeyReleaseInput(code);
    }

    @Override
    public void step() {
        if (!pauseGame) {
            paddle.updateLocation(GAME_WIDTH, LEVEL_HEIGHT);
            updateBallLocations();
            if (!levelReset) {
                checkAllBallPaddleCollisions();
                checkAllBallBlockCollisions();
            }
            checkIfLevelIsDone();
        }
    }

    private void checkIfLevelIsDone() {
        if (blockMatrix.getNumBlocksAlive() == 0){
            setSceneCode(GameScene.LEVEL_COMPLETE);
        }
        if (livesLeft == 0){
            setSceneCode(GameScene.LEVEL_LOST);
        }
    }

    private void checkAllBallBlockCollisions() {
        for (Ball ball: ballGroupController.getBallList()){
            for (int row = blockMatrix.getNumRows() - 1; row >= 0; row--){
                boolean rowHasBlockMissing = false;
                for (int col = 0; col < blockMatrix.getNumCols(); col++){
                    var block = blockMatrix.getMatrix()[row][col];
                    if (block != null) {
                        if (checkShapesIntersect(ball.getBallNode(), block.getBlockNode())){
                            ball.collideWithBlock(block);
                            blockMatrix.handleBlockHit(block, row, col);
                            score += block.getPointValue();
                            updateScoreLabel();
                            rowHasBlockMissing = true;
                        }
                    }
                    else{
                        rowHasBlockMissing = true;
                    }
                }
                if (!rowHasBlockMissing){
                    return;
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
            ball.updateLocation(GAME_WIDTH, LEVEL_HEIGHT, levelReset, paddle);
            if (ball.resetBall){
                levelReset = ballGroupController.resetBall(ball);
                livesLeft--;
                updateLivesLabel();
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