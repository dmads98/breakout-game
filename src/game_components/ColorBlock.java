package game_components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Dhanush Madabusi
 */
public class ColorBlock extends Block{

    private Rectangle colorBlock;

    ColorBlock(double xPos, double yPos, int colorIdentifier){
        super(xPos, yPos);
        colorBlock = getBlockNode();
        pointValue = colorIdentifier;
        setColor(colorIdentifier);
    }

    public void setColor(int colorIdentifier) {
        switch(colorIdentifier){
            case 1:
                colorBlock.setFill(Color.RED);
                break;
            case 2:
                colorBlock.setFill(Color.ORANGE);
                break;
            case 3:
                colorBlock.setFill(Color.YELLOW);
                break;
            case 4:
                colorBlock.setFill(Color.FORESTGREEN);
                break;
            case 5:
                colorBlock.setFill(Color.BLUE);
                break;
            case 6:
                colorBlock.setFill(Color.PURPLE);
                break;
            case 7:
                colorBlock.setFill(Color.DEEPSKYBLUE);
                break;
            case 8:
                colorBlock.setFill(Color.SPRINGGREEN);
                break;
            case 9:
                colorBlock.setFill(Color.DEEPPINK);
                break;
        }
    }
}
