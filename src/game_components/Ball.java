package game_components;

import Breakout.GameLauncher;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {

    public static final double RADIUS = 8.0;

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

    public void updateLocation(double levelWidth, double levelHeight) {
        updateCoordinates();
        checkBoundaryCollisions(levelWidth, levelHeight);
    }

    private void updateCoordinates(){
        this.setXPos(this.getXPos() + this.getXSpeed() * GameLauncher.SECOND_DELAY);
        this.setYPos(this.getYPos() + this.getYSpeed() * GameLauncher.SECOND_DELAY);
    }

    private void checkBoundaryCollisions(double levelWidth, double levelHeight){
        if (leftBoundaryCollision(levelWidth) || rightBoundaryCollision(levelWidth)){
            this.setXSpeed(this.getXSpeed() * -1);
        }
        if (topBoundaryCollision(levelHeight)){
            this.setYSpeed(this.getYSpeed() * -1);
        }
        if (bottomBoundaryCollision(levelHeight)){
            resetBall();
        }
    }

    private void resetBall() {
        this.setXPos(RESET_XPOS);
        this.setYPos(RESET_YPOS);
        this.setXSpeed(RESET_XSPEED);
        this.setYSpeed(RESET_YSPEED);

    }

    private boolean leftBoundaryCollision(double levelWidth) {
        return (this.getXPos() - this.getRadius()) < 0;
    }

    private boolean rightBoundaryCollision(double levelWidth) {
        return (this.getXPos() + this.getRadius()) > levelWidth;
    }

    private boolean topBoundaryCollision(double levelHeight) {
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
