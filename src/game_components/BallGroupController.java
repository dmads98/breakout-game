package game_components;

import javafx.scene.Group;

/**
 *
 * @author Dhanush Madabusi
 */
public class BallGroupController {

    private Ball[] ballArray;
    private Group ballGroup;
    private double groupXSpeed;
    private double groupYSpeed;
    private int ballsAlive = 0;

    public BallGroupController(int levelNumber, int numBallsPossible){
        ballArray = new Ball[numBallsPossible];
        ballGroup = new Group();
        setGroupSpeed(levelNumber);
    }

    private void setGroupSpeed(int levelNumber) {
        switch (levelNumber){
            case 1:
                groupXSpeed = 190.0;
                groupYSpeed = -160.0;
                break;
            case 2:
                groupXSpeed = 230.0;
                groupYSpeed = -190.0;
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    public Ball[] getBallArray() {
        return ballArray;
    }

    public Group getBallGroup(){
        return ballGroup;
    }

    public int getBallsAlive(){
        return ballsAlive;
    }

    public void initializeBall(Paddle paddle) {
        var ballXPos = paddle.getXPos() + paddle.getWidth()/2;
        var ballYPos = paddle.getYPos() - Ball.RADIUS;
        var ball = new Ball(ballXPos, ballYPos, groupXSpeed, groupYSpeed);
        for (int index = 0; index < ballArray.length; index++){
            if (ballArray[index] == null){
                ballArray[index] = ball;
                ballsAlive++;
                break;
            }
        }
        ballGroup.getChildren().add(ball.getBallNode());
    }

    /**
     * Method return true if ball is reset to ball, meaning life is lost in level.
     *
     * @param ball
     * @return
     */
    public boolean resetBall(Ball ball, int index) {
        ballArray[index] = null;
        ballsAlive--;
        ballGroup.getChildren().remove(ball.getBallNode());
        return ballsAlive == 0;
    }
}