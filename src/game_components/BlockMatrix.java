package game_components;

import javafx.scene.Group;

/**
 *
 * @author Dhanush Madabusi
 */
public class BlockMatrix {

    private static final double startXPos = 0.0;
    private static final double startYPos = 30.0;

    private Block [][] matrix;
    private Group blockGroup;
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
                matrix = new Block[9][12];
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
        var matrix = new Block[9][12];
        for (int row = 0; row < 9; row++){
            for(int col = 0; col < 12; col++){
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

    public Group createBlockGroup() {
        var blockGroup = new Group();
        for (int row = 0; row < this.getNumRows(); row++){
            for (int col = 0; col < this.getNumCols(); col++){
                if (matrix[row][col] != null) {
                    blockGroup.getChildren().add(matrix[row][col].getBlockNode());
                    numBlocksAlive++;
                }
            }
        }
        this.blockGroup = blockGroup;
        return blockGroup;
    }

    public void removeBlock(Block block, int row, int col){
        blockGroup.getChildren().remove(block.getBlockNode());
        matrix[row][col] = null;
        numBlocksAlive--;
    }
}
