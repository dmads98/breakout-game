package game_components;

import Breakout.GameLauncher;
import javafx.scene.effect.Lighting;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {

    private static final double INITIAL_SPEED = 0.0;
    private static final double PADDLE_SPEED = 400.0;
    private static final double PADDLE_HEIGHT = 20.0;
    private static final double PADDLE_ARC_WIDTH = 20.0;
    private static final double PADDLE_ARC_HEIGHT = 20.0;

    private Rectangle paddle;
    private double paddleSpeed;


    public Paddle(double xPos, double yPos, double width){
        paddle = new Rectangle();
        paddle.setX(xPos);
        paddle.setY(yPos);
        paddle.setWidth(width);
        paddle.setHeight(PADDLE_HEIGHT);
        paddle.setArcWidth(PADDLE_ARC_WIDTH);
        paddle.setArcHeight(PADDLE_ARC_HEIGHT);
        paddle.setFill(Color.GRAY);
        paddle.setStroke(Color.BLACK);
        paddle.setEffect(new Lighting());
        paddleSpeed = INITIAL_SPEED;
    }

    public Rectangle getPaddleNode(){
        return paddle;
    }

    double getPaddleSpeed() {
        return paddleSpeed;
    }

    double getXPos() {
        return paddle.getX();
    }

    private void setXPos(double xPos) {
        paddle.setX(xPos);
    }

    double getYPos() {
        return paddle.getY();
    }

    double getWidth() {
        return paddle.getWidth();
    }

    public void setWidth(double width) {
        paddle.setWidth(width);
    }

    public void handleKeyPressInput(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            paddleSpeed = PADDLE_SPEED;
        }
        else if (code == KeyCode.LEFT) {
            paddleSpeed = -PADDLE_SPEED;
        }
    }

    public void handleKeyReleaseInput(KeyCode code) {
        if (code == KeyCode.RIGHT || code == KeyCode.LEFT) {
            paddleSpeed = INITIAL_SPEED;
        }
    }

    public void updateLocation(double levelWidth, double levelHeight) {
        var newXPos = this.getXPos() + getPaddleSpeed() * GameLauncher.SECOND_DELAY;
        if (newXPos < 0.0){
            this.setXPos(0.0);
        }
        else if (newXPos > levelWidth - getWidth()){
            this.setXPos(levelWidth - getWidth());
        }
        else{
            this.setXPos(newXPos);
        }
    }
}
