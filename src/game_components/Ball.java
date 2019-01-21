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
        if (topBoundaryCollision(levelHeight) || bottomBoundaryCollision(levelHeight)){
            this.setYSpeed(this.getYSpeed() * -1);
        }
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
        return (this.getYPos() + this.getRadius()) > levelHeight;
    }
}
