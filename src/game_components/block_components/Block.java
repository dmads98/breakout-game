package game_components.block_components;

import Breakout.LevelScene;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Dhanush Madabusi
 */
public abstract class Block {
    static final double BLOCK_HEIGHT = 20.0;
    public static final double BLOCK_WIDTH = LevelScene.GAME_WIDTH/12;

    private Rectangle block;
    int pointValue;

    Block(double xPos, double yPos){
        block = new Rectangle();
        block.setX(xPos);
        block.setY(yPos);
        block.setWidth(BLOCK_WIDTH);
        block.setHeight(BLOCK_HEIGHT);
        block.setStroke(Color.BLACK);
        block.setEffect(new Lighting());
        pointValue = 0;
    }

    public Rectangle getBlockNode(){
        return block;
    }

    public double getXPos() {
        return block.getX();
    }

    public double getYPos() {
        return block.getY();
    }

    public double getBlockHeight(){
        return  block.getHeight();
    }

    public double getBlockWidth(){
        return block.getWidth();
    }

    public int getPointValue(){
        return pointValue;
    }
}