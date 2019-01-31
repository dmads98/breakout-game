package game_components;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dhanush Madabusi
 */
public class BallGroupController {

    private List<Ball> ballList;
    private Group ballGroup;
    private double groupXSpeed;
    private double groupYSpeed;

    public BallGroupController(int levelNumber){
        ballList = new ArrayList<>();
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

    public List<Ball> getBallList() {
        return ballList;
    }

    public Group getBallGroup(){
        return ballGroup;
    }

    public void initializeBall(Paddle paddle) {
        var ballXPos = paddle.getXPos() + paddle.getWidth()/2;
        var ballYPos = paddle.getYPos() - Ball.RADIUS;
        var ball = new Ball(ballXPos, ballYPos, groupXSpeed, groupYSpeed);
        ballList.add(ball);
        ballGroup.getChildren().add(ball.getBallNode());
    }

    /**
     * Method return true if ball is reset to ball, meaning life is lost in level.
     *
     * @param ball
     * @return
     */
    public boolean resetBall(Ball ball) {
        if (ballList.size() > 1){
            ballList.remove(ball);
            ballGroup.getChildren().remove(ball.getBallNode());
            return false;
        }
        else{
            ball.resetBallSpeed(groupXSpeed, groupYSpeed);
            return true;
        }
    }
}