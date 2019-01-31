package game_components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Dhanush Madabusi
 */
class ToughBlock extends Block {

    private Rectangle toughBlock;
    private static final Color hitOnceColor = Color.GRAY;
    private boolean hitOnce = false;

    ToughBlock(double xPos, double yPos) {
        super(xPos, yPos);
        toughBlock = getBlockNode();
        toughBlock.setFill(Color.LIGHTGRAY);
    }

    void setHitOnceColor() {
        toughBlock.setFill(hitOnceColor);
        hitOnce = true;
    }

    boolean checkIfHitOnce(){
        return hitOnce;
    }
}
