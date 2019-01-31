package Breakout;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This class implements the GameScene interface and creates the intro scene display for the game.
 *
 * @author Dhanush Madabusi
 */
public class SplashScene implements GameScene{

    //splash scene has unique dimensions and color compared to Level Scenes
    private static final Color SPLASH_BACKGROUND = Color.PINK;
    private static final double SPLASH_WIDTH = 700.0;
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
    public void setSceneCode(int sceneCode) {
        this.sceneCode = sceneCode;
    }

    @Override
    public Scene setUpScene() {
        var root = new Group();
        var scene = new Scene(root, SPLASH_WIDTH, SPLASH_HEIGHT, SPLASH_BACKGROUND);
        root.getChildren().addAll(createTitle(), createAuthorTag(), createPlayButton());
        sceneCode = GameScene.CURRENT_LEVEL;
        return scene;
    }

    private Text createAuthorTag() {
        Text title = new Text();
        title.setFont(Font.font("monaco", FontWeight.BOLD, 20));
        title.setText("created by Dhanush Madabusi");
        var textWidth = title.getLayoutBounds().getWidth();
        title.setX((SPLASH_WIDTH - textWidth)/2);
        title.setY(SPLASH_HEIGHT/2 - 40);
        return title;
    }

    private Text createTitle(){
        Text title = new Text();
        title.setFont(Font.font("impact", FontWeight.BOLD, FontPosture.REGULAR, 100));
        title.setFill(Color.ORCHID);
        title.setStrokeWidth(4);
        title.setStroke(Color.MAROON);
        title.setText("Breakout");
        var textWidth = title.getLayoutBounds().getWidth();
        title.setX((SPLASH_WIDTH - textWidth)/2);
        title.setY(SPLASH_HEIGHT/2 - 100);
        return title;
    }

    private Button createPlayButton(){
        Button playButton = new Button("Start Game");
        playButton.setPrefWidth(140.0);
        playButton.setLayoutX((SPLASH_WIDTH - playButton.getPrefWidth())/2);
        playButton.setLayoutY((SPLASH_HEIGHT - playButton.getHeight())/2);
        playButton.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                sceneCode = GameScene.LEVEL_COMPLETE;
            }
        }));

        playButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        playButton.setEffect(new DropShadow());
                    }
                });
        playButton.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        playButton.setEffect(null);
                    }
                });
        playButton.setStyle("-fx-background-color: #c3c4c4, linear-gradient(#d6d6d6 50%, white 100%), " +
                "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%); " +
                "-fx-background-radius: 30; -fx-background-insets: 0,1,1; -fx-text-fill: black; -fx-font-size: 20;");
        return playButton;
    }

    //no animation in SplashScene
    @Override
    public void step() { }
}