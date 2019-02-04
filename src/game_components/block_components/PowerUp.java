package game_components.block_components;

import Breakout.GameLauncher;
import javafx.scene.Group;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PowerUp extends ColorBlock {

    private static final double POWERUP_ARC_WIDTH = 30.0;
    private static final double POWERUP_ARC_HEIGHT = 50.0;
    private static final Color POWERUP_COLOR = Color.WHITE;
    private static final double ySpeed = 80.0;
    static final int numPowerUps = 6;

    private Rectangle powerUpIcon;
    private Text powerUpLabel;
    private Group powerUpGroup;
    private String powerUpIdentifier;
    private boolean released = false;


    PowerUp(double xPos, double yPos, int colorIdentifier, int powerUpCode){
        super(xPos, yPos, colorIdentifier);
        createPowerUpIcon(xPos, yPos);
        powerUpIcon.setVisible(false);
        createPowerUpLabel();
        createPowerUpGroup();
        setPowerUpIdentifier(powerUpCode);
    }

    private void createPowerUpGroup() {
        powerUpGroup = new Group();
        powerUpGroup.getChildren().addAll(powerUpIcon, powerUpLabel, getBlockNode());
    }

    Group getPowerUpGroup(){
        return powerUpGroup;
    }

    public boolean isReleased() {
        return released;
    }

    public String getPowerUpIdentifier(){
        return powerUpIdentifier;
    }

    private void setPowerUpIdentifier(int powerUpCode){
        switch (powerUpCode){
            case 1:
                powerUpIdentifier = "Bonus Points";
                break;
            case 2:
                powerUpIdentifier = "Double Points";
                break;
            case 3:
                powerUpIdentifier = "Extra Life";
                break;
            case 4:
                powerUpIdentifier = "Extra Ball";
                break;
            case 5:
                powerUpIdentifier = "Stretched Paddle";
                break;
            case 6:
                powerUpIdentifier = "Laser Paddle";
                break;

        }
    }

    private void createPowerUpLabel() {
        powerUpLabel = new Text();
        powerUpLabel.setFont(Font.font("courier", FontWeight.BOLD, 20));
        powerUpLabel.setText("P");
        var textWidth = powerUpLabel.getLayoutBounds().getWidth();
        var textHeight = powerUpLabel.getLayoutBounds().getHeight();
        powerUpLabel.setX(getXPos() + getBlockWidth()/2 - textWidth/2);
        powerUpLabel.setY(getYPos() + getBlockHeight()/2 + textHeight/2 - 5.0);
        powerUpLabel.setVisible(false);
    }

    private void createPowerUpIcon(double xPos, double yPos){
        powerUpIcon = new Rectangle();
        powerUpIcon.setX(xPos);
        powerUpIcon.setY(yPos);
        powerUpIcon.setWidth(Block.BLOCK_WIDTH);
        powerUpIcon.setHeight(Block.BLOCK_HEIGHT);
        powerUpIcon.setArcWidth(POWERUP_ARC_WIDTH);
        powerUpIcon.setArcHeight(POWERUP_ARC_HEIGHT);
        powerUpIcon.setFill(POWERUP_COLOR);
        powerUpIcon.setStroke(Color.RED);
        powerUpIcon.setStrokeWidth(2);
        powerUpIcon.setEffect(new Lighting());
    }

    public Rectangle getPowerUpIcon(){
        return powerUpIcon;
    }

    void releasePowerUp(){
        released = true;
        powerUpLabel.setVisible(true);
        powerUpIcon.setVisible(true);
        powerUpGroup.getChildren().remove(getBlockNode());
    }

    public void updateLocation() {
        powerUpIcon.setY(powerUpIcon.getY() + ySpeed * GameLauncher.SECOND_DELAY);
        powerUpLabel.setY(powerUpLabel.getY() + ySpeed * GameLauncher.SECOND_DELAY);
    }

    public boolean checkForBottomBoundaryCollision(double levelHeight){
        return powerUpIcon.getY() + powerUpIcon.getHeight() >= levelHeight;
    }
}
