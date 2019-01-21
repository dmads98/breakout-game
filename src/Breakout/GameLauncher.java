package Breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * This class setups and runs the Breakout game.
 *
 * @author Dhanush Madabusi
 */
public class GameLauncher extends Application {
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int TOTAL_LEVELS = 4;

    private int currentSceneIndex;
    private GameScene[] breakoutScenes = new GameScene[TOTAL_LEVELS + 1];

    @Override
    public void start(Stage stage){

        //set current scene to SplashScene index
        currentSceneIndex = 0;
        breakoutScenes[0] = new SplashScene();
        for (int levelNum = 1; levelNum <= TOTAL_LEVELS; levelNum++){
            breakoutScenes[levelNum] = new LevelScene(levelNum);
        }
        stage.setScene(breakoutScenes[0].setUpScene());
        stage.setTitle("Breakout");
        stage.show();

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> levelSelect(stage));
        var gameTime = new Timeline();
        gameTime.setCycleCount(Timeline.INDEFINITE);
        gameTime.getKeyFrames().add(frame);
        gameTime.play();
    }

    /**
     * Step method is run on each frame of the Timeline. This method determines which scene to display at each step.
     *
     * @param stage stage for GameLauncher
     */
    private void levelSelect(Stage stage) {
        var currentSceneCode = breakoutScenes[currentSceneIndex].getSceneCode();
        switch (currentSceneCode){
            case GameScene.INITIALIZE_LEVEL:
                stage.setScene(breakoutScenes[currentSceneIndex].setUpScene());
                break;
            case GameScene.CURRENT_LEVEL:
                breakoutScenes[currentSceneIndex].step();
                break;
            case GameScene.LEVEL_COMPLETE:
                if (currentSceneIndex == TOTAL_LEVELS){
                    switchScene(0);
                }
                else{
                    switchScene(currentSceneIndex + 1);
                }
                break;
            case GameScene.LEVEL_LOST:
                switchScene(0);
                break;
            case GameScene.LEVEL_QUIT:
                switchScene(0);
                break;
            case GameScene.CHEAT_LEVEL_1:
                switchScene(1);
                break;
            case GameScene.CHEAT_LEVEL_2:
                switchScene(2);
                break;
            case GameScene.CHEAT_LEVEL_3:
                switchScene(3);
                break;
            case GameScene.CHEAT_LEVEL_4:
                switchScene(4);
                break;

        }
    }

    private void switchScene(int nextSceneIndex){
        currentSceneIndex = nextSceneIndex;
        breakoutScenes[currentSceneIndex].setSceneCode(GameScene.INITIALIZE_LEVEL);
    }

    /**
    private Scene setUpGame(int width, int height, Paint background){
        var root = new Group();
        var scene = new Scene(root, width, height, background);
        var paddle = new Paddle(GAME_WIDTH/2.0 - PADDLE_WIDTH/2, GAME_HEIGHT - 50.0, PADDLE_WIDTH);
        root.getChildren().add(paddle.node);

        scene.setOnKeyPressed(e -> paddle.handleKeyInput(e.getCode()));
        return scene;
    }
     **/

    /**
     * Starts the program.
     *
     * @param args
     */
    public static void main (String args[]){
        launch(args);
    }
}
