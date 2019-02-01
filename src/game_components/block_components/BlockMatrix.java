package game_components.block_components;

import javafx.scene.Group;

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
    private int numBlocksAlive;


    public BlockMatrix(int levelNumber){
        createMatrix(levelNumber);
    }

    /**
     * The dimensions of the matrix for each level is pre-determined in PLAN.txt.
     * @param levelNumber
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
                matrix[row][col] = new ColorBlock(xPos, yPos, colorIdentifier);
                xPos += Block.BLOCK_WIDTH;
            }
            xPos = startXPos;
            yPos += Block.BLOCK_HEIGHT;
            colorIdentifier--;
        }
        return matrix;
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

    public void createBlockGroups() {
        blockGroup = new Group();
        powerUpGroup = new Group();
        for (int row = 0; row < this.getNumRows(); row++){
            for (int col = 0; col < this.getNumCols(); col++){
                var block = matrix[row][col];
                if (block != null) {
                    if (block instanceof PowerUp){
                        powerUpGroup.getChildren().add(((PowerUp) block).getPowerUpGroup());
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

    public void handleBlockHit(Block block, int row, int col){
        if ((block instanceof ToughBlock) && (!((ToughBlock) block).checkIfHitOnce())) {
            ((ToughBlock) block).setHitOnceColor();
        }
        else{
            if (block instanceof PowerUp){
                ((PowerUp) block).releasePowerUp();
            }
            blockGroup.getChildren().remove(block.getBlockNode());
            matrix[row][col] = null;
            numBlocksAlive--;
        }
    }
}
