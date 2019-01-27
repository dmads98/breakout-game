package game_components;

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
    static final double BLOCK_WIDTH = LevelScene.LEVEL_WIDTH/12;

    private Rectangle block;

    Block(double xPos, double yPos){
        block = new Rectangle();
        block.setX(xPos);
        block.setY(yPos);
        block.setWidth(BLOCK_WIDTH);
        block.setHeight(BLOCK_HEIGHT);
        block.setStroke(Color.BLACK);
        block.setEffect(new Lighting());
    }

    public Rectangle getBlockNode(){
        return block;
    }

    double getXPos() {
        return block.getX();
    }

    private void setXPos(double xPos) {
        block.setX(xPos);
    }

    double getYPos() {
        return block.getY();
    }

    private void setYPos(double yPos) {
        block.setY(yPos);
    }

    double getBlockHeight(){
        return  block.getHeight();
    }

    double getBlockWidth(){
        return block.getWidth();
    }
}