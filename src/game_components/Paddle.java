package game_components;

import Breakout.GameLauncher;
import javafx.scene.effect.Lighting;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {

    private static final double INITIAL_SPEED = 0.0;
    private static final double PADDLE_SPEED = 200.0;
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

    public double getPaddleSpeed() {
        return paddleSpeed;
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
        if (this.getXPos() < -this.getWidth()){
            this.setXPos(levelWidth - this.getWidth());
        }
        if (this.getXPos() > levelHeight + this.getWidth()){
            this.setXPos(0);
        }
        this.setXPos(this.getXPos() + getPaddleSpeed() * GameLauncher.SECOND_DELAY);
    }
}
