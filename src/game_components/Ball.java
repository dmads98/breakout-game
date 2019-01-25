package game_components;

import Breakout.GameLauncher;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {

    public static final double RADIUS = 8.0;
    public boolean resetBall = false;

    private Circle ball;
    private double xSpeed;
    private double ySpeed;
    private static final double RESET_XPOS = 250.0;
    private static final double RESET_YPOS = 250.0;
    private static final double RESET_XSPEED = 195.0;
    private static final double RESET_YSPEED = -140.0;


    public Ball(double xPos, double yPos, double xSpeed, double ySpeed){

        ball = new Circle();
        ball.setCenterX(xPos);
        ball.setCenterY(yPos);
        ball.setRadius(RADIUS);
        ball.setFill(Color.TOMATO);
        ball.setStroke(Color.BLACK);
        ball.setEffect(new Lighting());


        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

    }

    public Circle getBallNode(){
        return ball;
    }

    private double getXPos(){
        return ball.getCenterX();
    }

    private void setXPos(double newXPos){
        ball.setCenterX(newXPos);
    }

    private double getYPos(){
        return ball.getCenterY();
    }

    private void setYPos(double newYPos){
        ball.setCenterY(newYPos);
    }

    private double getXSpeed(){
        return xSpeed;
    }

    private void setXSpeed(double newXSpeed){
        xSpeed = newXSpeed;
    }

    private double getYSpeed(){
        return ySpeed;
    }

    private void setYSpeed(double newYSpeed){
        ySpeed = newYSpeed;
    }

    private double getRadius(){
        return ball.getRadius();
    }

    public void updateLocation(double levelWidth, double levelHeight, boolean levelReset, Paddle paddle) {
        if (!levelReset) {
            updateCoordinates();
            checkBoundaryCollisions(levelWidth, levelHeight);
        }
        else{
            updateCoordinatesToPaddle(paddle);
        }
    }

    private void updateCoordinatesToPaddle(Paddle paddle) {
        this.setXPos(paddle.getXPos() + paddle.getWidth()/2);
        this.setYPos(paddle.getYPos() - Ball.RADIUS);
    }

    private void updateCoordinates(){
        this.setXPos(this.getXPos() + this.getXSpeed() * GameLauncher.SECOND_DELAY);
        this.setYPos(this.getYPos() + this.getYSpeed() * GameLauncher.SECOND_DELAY);
    }

    private void checkBoundaryCollisions(double levelWidth, double levelHeight){
        if (leftBoundaryCollision() || rightBoundaryCollision(levelWidth)){
            this.setXSpeed(this.getXSpeed() * -1);
        }
        if (topBoundaryCollision()){
            this.setYSpeed(this.getYSpeed() * -1);
        }
        if (bottomBoundaryCollision(levelHeight)){
            resetBall = true;
        }
    }

    void resetBallSpeed(double resetXSpeed, double resetYSpeed){
        this.setXSpeed(resetXSpeed);
        this.setYSpeed(resetYSpeed);
        this.resetBall = false;
    }

    private boolean leftBoundaryCollision() {
        return (this.getXPos() - this.getRadius()) < 0;
    }

    private boolean rightBoundaryCollision(double levelWidth) {
        return (this.getXPos() + this.getRadius()) > levelWidth;
    }

    private boolean topBoundaryCollision() {
        return (this.getYPos() - this.getRadius()) < 0;
    }

    private boolean bottomBoundaryCollision(double levelHeight) {
        return (this.getYPos() - this.getRadius()) > levelHeight;
    }

    public void collideWithPaddle(Paddle paddle) {
        String side = determineSideCollidedWith(paddle);
        switch (side){
            case "TOP_LEFT":
                this.setXSpeed(Math.abs(this.getXSpeed()) * -1);
                this.setYSpeed(Math.abs(this.getYSpeed()) * -1);
                break;

            case "TOP_MIDDLE":
                this.setYSpeed(Math.abs(this.getYSpeed()) * -1);
                break;
            case "TOP_RIGHT":
                this.setXSpeed(Math.abs(this.getXSpeed()));
                this.setYSpeed(Math.abs(this.getYSpeed()) * -1);
                break;
            case "LEFT":
                this.setXSpeed((Math.abs(this.getXSpeed()) + Math.abs(paddle.getPaddleSpeed())) * -1);
                break;
            case "RIGHT":
                this.setXSpeed(Math.abs(this.getXSpeed()) + Math.abs(paddle.getPaddleSpeed()));
                break;
        }
    }

    private String determineSideCollidedWith(Paddle paddle) {
        //this conditional checks if collision occurred on top of paddle
        if (this.getYPos() <= paddle.getYPos()){
            //these conditionals determine with which third of the paddle did the ball collide by checking the position
                //of the center of the ball
            //this conditional checks if ball collided with the left side of the top of paddle
            if (this.getXPos() < paddle.getXPos() + paddle.getWidth()/3){
                return "TOP_LEFT";
            }
            //this conditional checks if ball collided with the right side of the top of paddle
            if (this.getXPos() > paddle.getXPos() + (paddle.getWidth()/3) * 2){
                return "TOP_RIGHT";
            }
            return "TOP_MIDDLE";
        }
        else{
            //this conditional checks if collision occurred on left side of paddle
            if (this.getXPos() <= paddle.getXPos() + paddle.getWidth()/2){
                return "LEFT";
            }
        }
        //only other option
        return "RIGHT";
    }
}
