package FXExamples;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class Example extends Application{

    @Override
    public void start (Stage primaryStage) throws Exception{
        Line line = new Line();

        line.setStartX(50.0);
        line.setStartY(50.0);
        line.setEndX(100.0);
        line.setEndY(100.0);

        Text text = new Text();
        text.setFont(new Font(40.0));
        text.setX(60.0);
        text.setY(150.0);
        text.setText("Welcome to Breakout");

        Group root = new Group();

        ObservableList list = root.getChildren();
        list.add(line);
        list.add(text);

        Scene scene = new Scene(root, 600, 300);

        scene.setFill(Color.PINK);

        primaryStage.setTitle("Sample App");

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main (String args[]){
        launch(args);
    }
}
