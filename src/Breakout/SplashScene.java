package Breakout;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class implements the GameScene interface and creates the intro scene display for the game.
 *
 * @author Dhanush Madabusi
 */
public class SplashScene implements GameScene{

    //splash scene has unique dimensions and color compared to Level Scenes
    private static final Color SPLASH_BACKGROUND = Color.BURLYWOOD;
    private static final double SPLASH_WIDTH = 500.0;
    private static final double SPLASH_HEIGHT = 500.0;
    //private Scene scene;
    private int sceneCode;

    public SplashScene(){
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
        var scene = new Scene(root, SPLASH_WIDTH, SPLASH_HEIGHT, SPLASH_BACKGROUND);

        //placeholder title
        Text title = new Text();
        title.setFont(new Font(40.0));
        title.setX(60.0);
        title.setY(150.0);
        title.setText("Welcome to Breakout TEST");

        //placeholder "Play" button

        Button playButton = new Button("Play");
        playButton.setLayoutX(300);
        playButton.setLayoutY(250);
        playButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Game Started");
                sceneCode = GameScene.LEVEL_COMPLETE;
            }
        }));

        root.getChildren().addAll(title, playButton);
        sceneCode = GameScene.CURRENT_LEVEL;

        return scene;
    }
}
