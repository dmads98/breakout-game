package Breakout;

import game_components.Paddle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * This class setups and runs the Breakout game.
 *
 * @author Dhanush Madabusi
 */
public class GameLauncher extends Application {
    public static final int GAME_WIDTH = 500;
    public static final int GAME_HEIGHT = 500;
    public static final double PADDLE_WIDTH = 100.0;
    public static final Paint BACKGROUND = Color.PINK;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Scene myScene;

    @Override
    public void start(Stage stage){

        myScene = setUpGame(GAME_WIDTH,GAME_HEIGHT, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle("Breakout");
        stage.show();

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY));
        var gameTime = new Timeline();
        gameTime.setCycleCount(Timeline.INDEFINITE);
        gameTime.getKeyFrames().add(frame);
        gameTime.play();
    }

    private Scene setUpGame(int width, int height, Paint background){
        var root = new Group();
        var scene = new Scene(root, width, height, background);
        var paddle = new Paddle(GAME_WIDTH/2.0 - PADDLE_WIDTH/2, GAME_HEIGHT - 50.0, PADDLE_WIDTH);
        root.getChildren().add(paddle.node);

        scene.setOnKeyPressed(e -> paddle.handleKeyInput(e.getCode()));
        return scene;
    }

    /**
     * Starts the program.
     *
     * @param args
     */
    public static void main (String args[]){
        launch(args);
    }
}
