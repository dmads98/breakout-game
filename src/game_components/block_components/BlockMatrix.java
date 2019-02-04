package game_components.block_components;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Dhanush Madabusi
 */
public class BlockMatrix {

    private static final double startXPos = 0.0;
    private static final double startYPos = 30.0;

    private Block[][] matrix;
    private Group blockGroup;
    private Group powerUpGroup;
    private List<PowerUp> powerUpList;
    private int numBlocksAlive;
    public int numBallsPossible = 1;


    public BlockMatrix(int levelNumber){
        createMatrix(levelNumber);
    }

    /**
     * The dimensions of the matrix for each level is pre-determined in PLAN.txt.
     * @param levelNumber number of level
     */
    private void createMatrix(int levelNumber) {
        switch (levelNumber){
            case 1:
                matrix = createLevel1Matrix();
                break;
            case 2:
                matrix = createLevel2Matrix();
                break;
            case 3:
                matrix = new Block[9][12];
                break;
            case 4:
                matrix = new Block[9][12];
                break;

        }
    }

    private Block[][] createLevel1Matrix() {
        int colorIdentifier = 9;
        double xPos = startXPos;
        double yPos = startYPos;
        var matrix = new Block[11][12];
        for (int row = 0; row < matrix.length; row++){
            if (row == 3 || row == 7){
                yPos += Block.BLOCK_HEIGHT;
                continue;
            }
            for(int col = 0; col < matrix[0].length; col++){
                matrix[row][col] = new ColorBlock(xPos, yPos, colorIdentifier);
                xPos += Block.BLOCK_WIDTH;
            }
            xPos = startXPos;
            yPos += Block.BLOCK_HEIGHT;
            colorIdentifier--;
        }
        return matrix;
    }

    private Block[][] createLevel2Matrix() {
        int colorIdentifier = 9;
        double xPos = startXPos;
        double yPos = startYPos;
        var matrix = new Block[12][12];
        for (int row = 0; row < matrix.length; row++){
            if (row == 3 || row == 7){
                yPos += Block.BLOCK_HEIGHT;
                continue;
            }
            if (row == matrix.length - 1){
                for(int col = 0; col < matrix[0].length; col++){
                    matrix[row][col] = new ToughBlock(xPos, yPos);
                    xPos += Block.BLOCK_WIDTH;
                }
                continue;
            }
            for(int col = 0; col < matrix[0].length; col++){
                if (Math.random() <= 0.1){
                    int powerUpIdentifier = generateRandomPowerUp();
                    if (powerUpIdentifier == 4){
                        numBallsPossible++;
                    }
                    matrix[row][col] = new PowerUp(xPos, yPos, colorIdentifier, powerUpIdentifier);
                    System.out.println(row + " " + col);
                }
                else{
                    matrix[row][col] = new ColorBlock(xPos, yPos, colorIdentifier);
                }
                xPos += Block.BLOCK_WIDTH;
            }
            xPos = startXPos;
            yPos += Block.BLOCK_HEIGHT;
            colorIdentifier--;
        }
        return matrix;
    }


    private int generateRandomPowerUp(){
        return (int)(Math.random() * PowerUp.numPowerUps) + 1;
    }

    public int getNumCols(){
        return matrix[0].length;
    }

    public int getNumRows(){
        return matrix.length;
    }

    public Block[][] getMatrix(){
        return matrix;
    }

    public int getNumBlocksAlive(){
        return numBlocksAlive;
    }

    public void createBlockGroupsAndList() {
        blockGroup = new Group();
        powerUpGroup = new Group();
        powerUpList = new ArrayList<>();
        for (int row = 0; row < this.getNumRows(); row++){
            for (int col = 0; col < this.getNumCols(); col++){
                var block = matrix[row][col];
                if (block != null) {
                    if (block instanceof PowerUp){
                        powerUpGroup.getChildren().add(((PowerUp) block).getPowerUpGroup());
                        powerUpList.add((PowerUp)block);
                    }
                    else {
                        blockGroup.getChildren().add(block.getBlockNode());
                    }
                    numBlocksAlive++;
                }
            }
        }
    }

    public Group getBlockGroup(){
        return blockGroup;
    }

    public Group getPowerUpGroup(){
        return powerUpGroup;
    }

    public List<PowerUp> getPowerUpList(){
        return powerUpList;
    }

    /**
     * This method contains logic for dealing with ToughBlocks, PowerUp, and regular ColorBlocks after ball-block
     * collisions.
     *
     * @param block the block that was hit
     * @param row   the row of the block in the block matrix
     * @param col   the column of the block in the block matrix
     */
    public void handleBlockHit(Block block, int row, int col){
        if ((block instanceof ToughBlock) && (!((ToughBlock) block).checkIfHitOnce())) {
            ((ToughBlock) block).setHitOnceColor();
        }
        else{
            if (block instanceof PowerUp){
                ((PowerUp) block).releasePowerUp();
                System.out.println(((PowerUp) block).getPowerUpIdentifier());
            }
            blockGroup.getChildren().remove(block.getBlockNode());
            matrix[row][col] = null;
            numBlocksAlive--;
        }
    }
}
