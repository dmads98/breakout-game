package Breakout;

import game_components.*;
import game_components.block_components.Block;
import game_components.block_components.BlockMatrix;
import game_components.block_components.PowerUp;
import game_components.block_components.PowerUpController;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Iterator;

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
    private static final double BORDER_WIDTH = 5.0;
    private static final int MAX_LIVES_POSSIBLE = 9;
    private static final int DEFAULT_LIVES = 6;
    private static final int TIMED_POWERUP_LENGTH = 15;

    private boolean levelReset;
    private int sceneCode;
    private int levelNumber;
    private Paddle paddle;
    private BallGroupController ballGroupController;
    private PowerUpController powerUpController;
    private BlockMatrix blockMatrix;
    private Text levelResetLabel;
    private boolean pauseGame;
    private int livesLeft;
    private Text livesLabel;
    private Text scoreLabel;
    private int score;
    private int doublePointsTimeCounter = 0;
    private boolean doublePoints = false;
    private int framesPerSecond;
    private boolean stretchedPaddle = false;
    private int stretchedPaddleTimeCounter = 0;
    private boolean laserPaddle = false;
    private int laserPaddleTimeCounter = 0;
    private int laserShootTimeCounter = 0;
    private Group bulletGroup = new Group();

    /**
     * The constructor sets the levelNumber to parameter and sets the sceneCode to INITIALIZE_LEVEL.
     * EXPLAIN!
     * @param levelNumber the number of this level
     */
    LevelScene(int levelNumber, int framesPerSecond){
        this.levelNumber = levelNumber;
        sceneCode = GameScene.INITIALIZE_LEVEL;
        this.framesPerSecond = framesPerSecond;
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

        paddle = new Paddle(GAME_WIDTH/2.0 - Paddle.DEFAULT_PADDLE_WIDTH/2, LEVEL_HEIGHT - 50.0);

        blockMatrix = new BlockMatrix(levelNumber);
        blockMatrix.createBlockGroupsAndList();
        Group blockGroup = blockMatrix.getBlockGroup();
        powerUpController = new PowerUpController();
        powerUpController.setPowerUpGroup(blockMatrix.getPowerUpGroup());
        powerUpController.setPowerUpList(blockMatrix.getPowerUpList());

        ballGroupController = new BallGroupController(levelNumber, blockMatrix.numBallsPossible);
        ballGroupController.initializeBall(paddle);

        createLevelResetLabel();
        Group sidePaneGroup = createSidePaneElements();

        var bullet = new LaserBullet(50.0, 350.0);
        bullet.getBulletNode().setVisible(false);

        bulletGroup.getChildren().add(bullet.getBulletNode());

        ObservableList rootElements = root.getChildren();
        rootElements.addAll(paddle.getPaddleNode(), ballGroupController.getBallGroup(), levelResetLabel, blockGroup,
                powerUpController.getPowerUpGroup(), sidePaneGroup, bulletGroup);

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
        boolean scoreAndLivesReset = true;
        if (code == KeyCode.DIGIT1) {
            setSceneCode(GameScene.CHEAT_LEVEL_1);
        } else if (code == KeyCode.DIGIT2) {
            setSceneCode(GameScene.CHEAT_LEVEL_2);
        } else if (code == KeyCode.DIGIT3) {
            setSceneCode(GameScene.CHEAT_LEVEL_3);
        } else if (code == KeyCode.DIGIT4) {
            setSceneCode(GameScene.CHEAT_LEVEL_4);
        } else if (code == KeyCode.Q) {
            setSceneCode(GameScene.LEVEL_QUIT);
        } else if (code == KeyCode.A){
            if (livesLeft < MAX_LIVES_POSSIBLE) {
                livesLeft++;
                updateLivesLabel();
            }
            scoreAndLivesReset = false;
        } else{
            return;
        }
        if (scoreAndLivesReset){
            score = 0;
            livesLeft = DEFAULT_LIVES;
        }
    }

    private void keyBoardReleaseEvent(KeyCode code) {
        paddle.handleKeyReleaseInput(code);
    }

    @Override
    public void step() {
        if (!pauseGame) {
            handleTimedPowerUps();
            paddle.updateLocation(GAME_WIDTH, LEVEL_HEIGHT);
            updateBallLocations();
            if (!levelReset) {
                checkAllBallPaddleCollisions();
                checkAllBallBlockCollisions();
            }
            managePowerUps();
            checkIfLevelIsDone();
        }
    }

    private void handleTimedPowerUps() {
        handleDoublePoints();
        handleStretchedPaddle();
        handleLaserPaddle();
    }

    private void handleLaserPaddle() {
        if (laserPaddle){
            System.out.println("laser paddle");
            if (laserShootTimeCounter == framesPerSecond){
                shootLasers();
                laserShootTimeCounter = 0;
            }
            else{
                laserShootTimeCounter++;
            }
            updateBulletLocations();
            if (levelReset || laserPaddleTimeCounter == framesPerSecond * TIMED_POWERUP_LENGTH){
                laserPaddle = false;
                bulletGroup = new Group();
            }
            else {
                laserPaddleTimeCounter++;
            }
        }
    }

    private void handleStretchedPaddle() {
        if (stretchedPaddle){
            if (levelReset || stretchedPaddleTimeCounter == framesPerSecond * TIMED_POWERUP_LENGTH){
                paddle.setWidth(Paddle.DEFAULT_PADDLE_WIDTH);
                stretchedPaddle = false;
            }
            else {
                stretchedPaddleTimeCounter++;
            }
        }
    }

    private void handleDoublePoints() {
        if (doublePoints){
            if (levelReset || doublePointsTimeCounter == framesPerSecond * TIMED_POWERUP_LENGTH){
                doublePoints = false;
            }
            else {
                doublePointsTimeCounter++;
            }
        }
    }

    private void updateBulletLocations() {
        ObservableList<Node> bulletList = bulletGroup.getChildren();
        //System.out.println(bulletList.size());
        Iterator<Node> i = bulletList.iterator();
        while (i.hasNext()) {
            Rectangle bullet = (Rectangle) i.next();
            int blockCol = (int)(bullet.getX()/Block.BLOCK_WIDTH);
            bullet.setY(bullet.getY() - LaserBullet.BULLET_SPEED * GameLauncher.SECOND_DELAY);
            if (bullet.getY() <= 0){
                bullet.setVisible(false);
                //i.remove();
                continue;
            }
            for (int row = blockMatrix.getNumRows() - 1; row >= 0; row--){
                boolean rowHasBlockMissing = false;
                for (int col = blockCol; col <= blockCol + 1; col++){
                    if (col >= blockMatrix.getNumCols()){
                        break;
                    }
                    var block = blockMatrix.getMatrix()[row][col];
                    if (block != null) {
                        if (checkShapesIntersect(bullet, block.getBlockNode())){
                            //System.out.println("block Hit");
                            bullet.setVisible(false);
                            //i.remove();
                            blockMatrix.handleBlockHit(block, row, col);
                            if (doublePoints) {
                                score += (block.getPointValue() * 2);
                            }
                            else {
                                score += block.getPointValue();
                            }
                            updateScoreLabel();
                            rowHasBlockMissing = false;
                        }
                    }
                    else{
                        rowHasBlockMissing = true;
                    }
                }
                if (!rowHasBlockMissing){
                    break;
                }
            }
        }
    }

    private void shootLasers() {
        var paddleXPos = paddle.getXPos();
        var paddleYPos = paddle.getYPos();
        LaserBullet leftBullet = new LaserBullet(paddleXPos + 15, paddleYPos - LaserBullet.BULLET_HEIGHT);
        LaserBullet rightBullet = new LaserBullet(paddleXPos + paddle.getWidth() - LaserBullet.BULLET_WIDTH - 15,
                paddleYPos - LaserBullet.BULLET_HEIGHT);
        bulletGroup.getChildren().addAll(leftBullet.getBulletNode(), rightBullet.getBulletNode());
    }

    private void managePowerUps() {
        var pList = powerUpController.getPowerUpList();
        for (int index = 0; index < pList.size(); index++) {
            var powerUp = pList.get(index);
            if (powerUp == null){
                continue;
            }
            if (powerUp.released) {
                if (levelReset){
                    powerUpController.removePowerUp(powerUp, index);
                    continue;
                }
                if (checkShapesIntersect(powerUp.getPowerUpIcon(), paddle.getPaddleNode())){
                    activatePowerUp(powerUp.getPowerUpIdentifier());
                    powerUpController.removePowerUp(powerUp, index);
                }
                else{
                    powerUp.updateLocation();
                    if (powerUp.checkForBottomBoundaryCollision(LEVEL_HEIGHT)){
                        powerUpController.removePowerUp(powerUp, index);
                    }
                }
            }
        }
    }

    private void activatePowerUp(String powerUpIdentifier){
        switch(powerUpIdentifier){
            case "Extra Life":
                livesLeft++;
                updateLivesLabel();
                break;
            case "Bonus Points":
                score += 50;
                updateScoreLabel();
                break;
            case "Extra Ball":
                ballGroupController.initializeBall(paddle);
                break;
            case "Double Points":
                doublePoints = true;
                doublePointsTimeCounter = 0;
                break;
            case "Stretched Paddle":
                if (paddle.getXPos() + Paddle.STRECHED_PADDLE_WIDTH > GAME_WIDTH){
                    paddle.setXPos(GAME_WIDTH - Paddle.STRECHED_PADDLE_WIDTH);
                }
                paddle.setWidth(Paddle.STRECHED_PADDLE_WIDTH);
                stretchedPaddle = true;
                stretchedPaddleTimeCounter = 0;
            case "Laser Paddle":
                laserPaddle = true;
                laserPaddleTimeCounter = 0;
                laserShootTimeCounter = 0;
        }
    }

    private void checkIfLevelIsDone() {
        if (blockMatrix.getNumBlocksAlive() == 0){
            boolean allPowerUpsNull = true;
            for (PowerUp powerUp: powerUpController.getPowerUpList()){
                if (powerUp != null){
                    allPowerUpsNull = false;
                    break;
                }
            }
            if (allPowerUpsNull) {
                setSceneCode(GameScene.LEVEL_COMPLETE);
            }
        }
        if (livesLeft == 0){
            setSceneCode(GameScene.LEVEL_LOST);
        }
    }

    private void checkAllBallBlockCollisions() {
        var ballArray = ballGroupController.getBallArray();
        int count = 0;
        for (int index = 0; index < ballArray.length; index++){
            var ball = ballArray[index];
            if (ball == null){
                continue;
            }
            count++;
            for (int row = blockMatrix.getNumRows() - 1; row >= 0; row--){
                boolean rowHasBlockMissing = false;
                for (int col = 0; col < blockMatrix.getNumCols(); col++){
                    var block = blockMatrix.getMatrix()[row][col];
                    if (block != null) {
                        if (checkShapesIntersect(ball.getBallNode(), block.getBlockNode())){
                            ball.collideWithBlock(block);
                            blockMatrix.handleBlockHit(block, row, col);
                            if (doublePoints) {
                                score += (block.getPointValue() * 2);
                            }
                            else {
                                score += block.getPointValue();
                            }
                            updateScoreLabel();
                            rowHasBlockMissing = true;
                        }
                    }
                    else{
                        rowHasBlockMissing = true;
                    }
                }
                if (!rowHasBlockMissing){
                    break;
                }
            }
            if (count == ballGroupController.getBallsAlive()){
                break;
            }
        }
    }

    private void checkAllBallPaddleCollisions() {
        int count = 0;
        var ballArray = ballGroupController.getBallArray();
        for (Ball ball : ballArray){
            if (ball == null){
                continue;
            }
            count++;
            if (checkShapesIntersect(ball.getBallNode(), paddle.getPaddleNode())){
                ball.collideWithPaddle(paddle);
            }
            if (count == ballGroupController.getBallsAlive()){
                break;
            }
        }
    }

    private void updateBallLocations() {
        var ballArray = ballGroupController.getBallArray();
        int count = 0;
        for (int index = 0; index < ballArray.length; index++){
            var ball = ballArray[index];
            if (ball == null){
                continue;
            }
            count++;
            ball.updateLocation(GAME_WIDTH, LEVEL_HEIGHT, levelReset, paddle);
            if (ball.getResetBall()){
                levelReset = ballGroupController.resetBall(ball, index);
                if (levelReset){
                    ballGroupController.initializeBall(paddle);
                    livesLeft--;
                    updateLivesLabel();
                    levelResetLabel.setVisible(true);
                }
            }
            if (count == ballGroupController.getBallsAlive()){
                break;
            }
        }
    }

    private boolean checkShapesIntersect(Shape shape1, Shape shape2) {
        return (Shape.intersect(shape1, shape2).getBoundsInLocal().getWidth() > 0);
    }
}