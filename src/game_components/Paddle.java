package game_components;

import javafx.scene.Node;
import javafx.scene.effect.Lighting;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {

    private static final double PADDLE_SPEED = 20.0;

    private Rectangle paddle;
    private double xPos;
    private double yPos;
    private double width;
    private double height;

    public Node node;


    public Paddle(double xPos, double yPos, double width){
        this.paddle = new Rectangle();
        paddle.setX(xPos);
        paddle.setY(yPos);
        paddle.setWidth(width);
        paddle.setHeight(20.0);
        paddle.setArcWidth(20.0);
        paddle.setArcHeight(20.0);
        paddle.setFill(Color.GRAY);
        paddle.setStroke(Color.BLACK);
        paddle.setEffect(new Lighting());
        //paddle.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        node = paddle;
    }

    public double getXPos() {
        return paddle.getX();
    }

    public void setXPos(double xPos) {
        paddle.setX(xPos);
    }

    public double getYPos() {
        return paddle.getY();
    }

    public void setYPos(double yPos) {
        paddle.setY(yPos);
    }

    public double getWidth() {
        return paddle.getWidth();
    }

    public void setWidth(double width) {
        paddle.setWidth(width);
    }

    public void handleKeyInput(KeyCode code) {
        if (code == KeyCode.L) {
            paddle.setX(paddle.getX() + PADDLE_SPEED);
        }
        else if (code == KeyCode.J) {
            paddle.setX(paddle.getX() - PADDLE_SPEED);
        }
    }
}
