package game_components.block_components;

import Breakout.GameLauncher;
import javafx.scene.Group;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class PowerUp extends ColorBlock {

    private static final double POWERUP_ARC_WIDTH = 20.0;
    private static final double POWERUP_ARC_HEIGHT = 20.0;
    private static final Color POWERUP_COLOR = Color.WHITESMOKE;

    private Rectangle powerUpIcon;
    private Text powerUpLabel;
    private Group powerUpGroup;
    public boolean released = false;
    private static final double ySpeed = 100.0;

    public PowerUp(double xPos, double yPos, int colorIdentifier){
        super(xPos, yPos, colorIdentifier);
        createPowerUpIcon(xPos, yPos);
        powerUpIcon.setVisible(false);
        createPowerUpLabel();
        createPowerUpGroup();
    }

    private void createPowerUpGroup() {
        powerUpGroup = new Group();
        powerUpGroup.getChildren().addAll(powerUpIcon, powerUpLabel, getBlockNode());
    }

    Group getPowerUpGroup(){
        return powerUpGroup;
    }

    private void createPowerUpLabel() {
        powerUpLabel = new Text();
        powerUpLabel.setFont(Font.font("courier", 10));
        powerUpLabel.setText("P");
        //var textWidth = powerUpLabel.getLayoutBounds().getWidth();
        powerUpLabel.setX(getXPos() + getBlockWidth()/2);
        powerUpLabel.setY(getYPos() + getBlockHeight()/2);
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
        powerUpIcon.setStroke(Color.BLACK);
        powerUpIcon.setEffect(new Lighting());
    }

    void releasePowerUp(){
        released = true;
        powerUpLabel.setVisible(true);
        powerUpIcon.setVisible(true);
        powerUpGroup.getChildren().remove(getBlockNode());
    }

    public abstract void activatePowerUp();

    public void updateLocation() {
        powerUpIcon.setY(powerUpIcon.getY() + ySpeed * GameLauncher.SECOND_DELAY);
        powerUpLabel.setY(powerUpLabel.getY() + ySpeed * GameLauncher.SECOND_DELAY);
    }

    public boolean checkForBottomBoundaryCollision(double levelHeight){
        return powerUpIcon.getY() + powerUpIcon.getHeight() >= levelHeight;
    }
}
