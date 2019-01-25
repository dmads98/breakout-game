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

    public BallGroupController(double xSpeed, double ySpeed){
        ballList = new ArrayList<>();
        ballGroup = new Group();
        groupXSpeed = xSpeed;
        groupYSpeed = ySpeed;
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
     * @param paddle
     * @return
     */
    public boolean resetBall(Ball ball, Paddle paddle) {
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
