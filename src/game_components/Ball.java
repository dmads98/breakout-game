package game_components;

import Breakout.GameLauncher;
import game_components.block_components.Block;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This class is responsible constructing the Ball objects in the Game. It contains all basic getter and setter methods
 * for the various properties of the Ball object. It also handles updating the location of the Ball for each frame, as
 * well as checks and handles collisions with the paddle and blocks in a level. This class has very few dependencies. It
 * uses the GameLauncher.SECOND_DELAY to update ball location as well as imports the Block class. The set of Ball objects
 * are maintained by the BallGroupController class.
 *
 * @author Dhanush Madabusi
 */
public class Ball {

    static final double RADIUS = 8.0;
    private boolean resetBall = false;

    private Circle ball;
    private double xSpeed;
    private double ySpeed;

    Ball(double xPos, double yPos, double xSpeed, double ySpeed){

        ball = new Circle();
        ball.setCenterX(xPos);
        ball.setCenterY(yPos);
        ball.setRadius(RADIUS);
        ball.setFill(Color.GHOSTWHITE);
        ball.setStroke(Color.BLACK);
        ball.setEffect(new Lighting());


        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

    }

    /**
     * To add the Ball object to its respective Group object in the BallGroupController and for checking object collision,
     * we need the Ball's private Circle Node object.
     *
     * @return Circle Node for the Ball object
     */
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

    /**
     * This method returns the resetBall boolean and is called in updateBallLocations() in LevelScene. If this Ball
     * object goes out of play, the BallGroupController sets this Ball object to null and determines whether the level
     * must be reset or not.
     *
     * @return whether the BallGroupController must reset the ball or not
     */
    public boolean getResetBall(){
        return resetBall;
    }

    /**
     * This method updates the location of the Ball based and checks for boundary collisions. If the level is reset,
     * the ball will stick to the center of the paddle until game play begins again.
     *
     * @param levelWidth the width of the game play area
     * @param levelHeight the height of the game play area
     * @param levelReset whether or not the level has been reset or not
     * @param paddle the paddle for which the ball should be reset to if levelReset = true
     */
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
        if (leftBoundaryCollision()){
            this.setXSpeed(Math.abs(this.getXSpeed()));
        }
        if (rightBoundaryCollision(levelWidth)){
            this.setXSpeed(Math.abs(this.getXSpeed()) * -1);
        }
        if (topBoundaryCollision()){
            this.setYSpeed(Math.abs(this.getYSpeed()));
        }
        if (bottomBoundaryCollision(levelHeight)){
            resetBall = true;
        }
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

    /**
     * This method determines which changes the ball's x- and y- velocity based on which side of the paddle it collided
     * with.
     *
     * @param paddle the Paddle object for which to handle collisions
     */
    public void collideWithPaddle(Paddle paddle) {
        String side = determineSideCollidedWithPaddle(paddle);
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

    private String determineSideCollidedWithPaddle(Paddle paddle) {
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

    /**
     * This method determines which changes the ball's x- and y- velocity based on which side of the block it collided
     * with.
     *
     * @param block the Block object for which to handle collisions
     */
    public void collideWithBlock(Block block) {
        String side = determineSideCollidedWithBlock(block);
        switch (side){
            case "TOP":
                this.setYSpeed(Math.abs(this.getYSpeed()) * -1);
                break;
            case "BOTTOM":
                this.setYSpeed(Math.abs(this.getYSpeed()));
                break;
            case "LEFT":
                this.setXSpeed(Math.abs(this.getXSpeed()) * -1);
                break;
            case "RIGHT":
                this.setXSpeed(Math.abs(this.getXSpeed()));
                break;
            case "TOP_LEFT_CORNER":
                this.setXSpeed(Math.abs(this.getXSpeed()) * -1);
                this.setYSpeed(Math.abs(this.getYSpeed()) * -1);
                break;
            case "BOTTOM_LEFT_CORNER":
                this.setXSpeed(Math.abs(this.getXSpeed()) * -1);
                this.setYSpeed(Math.abs(this.getYSpeed()));
                break;
            case "TOP_RIGHT_CORNER":
                this.setXSpeed(Math.abs(this.getXSpeed()));
                this.setYSpeed(Math.abs(this.getYSpeed()) * -1);
                break;
            case "BOTTOM_RIGHT_CORNER":
                this.setXSpeed(Math.abs(this.getXSpeed()));
                this.setYSpeed(Math.abs(this.getYSpeed()));
                break;
        }
    }

    private String determineSideCollidedWithBlock(Block block) {
        //this conditional checks if collision occurred on either the left or right side of the block
        if ((this.getYPos() >= block.getYPos()) && (this.getYPos() <= block.getYPos() + block.getBlockHeight())){
            if (this.getXPos() <= block.getXPos()){
                return "LEFT";
            }
            else{
                return "RIGHT";
            }
        }
        //this conditional checks if collision occurred on either the top or bottom of the block
        if ((this.getXPos() >= block.getXPos()) && (this.getXPos() <= block.getXPos() + block.getBlockWidth())){
            if (this.getYPos() <= block.getYPos()){
                return "TOP";
            }
            else{
                return "BOTTOM";
            }
        }
        //the remaining conditionals determine with which corner of the block did the collision occur
        if (this.getXPos() <= block.getXPos()){
            if (this.getYPos() <= block.getYPos()){
                return "TOP_LEFT_CORNER";
            }
            else{
                return "BOTTOM_LEFT_CORNER";
            }
        }
        if ((this.getXPos() >= block.getXPos() + block.getBlockWidth()) &&
                (this.getYPos() <= block.getYPos())) {
                return "TOP_RIGHT_CORNER";
            }
        //only remaining option
        return "BOTTOM_RIGHT_CORNER";
    }
}
