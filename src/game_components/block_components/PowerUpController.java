package game_components.block_components;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dhanush Madabusi
 */
public class PowerUpController {

    private List<PowerUp> powerUpList;
    private Group powerUpGroup;

    public PowerUpController(){
        powerUpList = new ArrayList<>();
        powerUpGroup = new Group();
    }

    public Group getPowerUpGroup() {
        return powerUpGroup;
    }

    public void setPowerUpGroup(Group powerUpGroup) {
        this.powerUpGroup = powerUpGroup;
    }

    public List<PowerUp> getPowerUpList() {
        return powerUpList;
    }


    public void removePowerUp(PowerUp powerUp) {
        powerUpList.remove(powerUp);
        powerUpGroup.getChildren().remove(powerUp.getPowerUpGroup());
    }
}
