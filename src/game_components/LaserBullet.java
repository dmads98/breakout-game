package game_components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Dhanush Madabusi
 */
public class LaserBullet {

    public static final double BULLET_SPEED = 80.0;
    public static final double BULLET_HEIGHT = 20.0;
    public static final double BULLET_WIDTH = 5.0;

    private Rectangle bullet;

    public LaserBullet(double xPos, double yPos){
        bullet = new Rectangle();
        bullet.setX(xPos);
        bullet.setY(yPos);
        bullet.setHeight(BULLET_HEIGHT);
        bullet.setWidth(BULLET_WIDTH);
        bullet.setStroke(Color.BLACK);
        bullet.setFill(Color.DARKRED);
        bullet.setVisible(true);
    }

    public Rectangle getBulletNode(){
        return bullet;
    }
}
