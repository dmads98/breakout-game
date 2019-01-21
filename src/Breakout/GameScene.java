package Breakout;

import javafx.scene.Scene;

/**
 * interface for display on each stage of game (splash, level 1-4, etc.)
 *
 * @author Dhanush Madabusi
 */
public interface GameScene {

    //all these fields are public static final by definition
    //these are the level or scene codes
    //each code will set off an operation
    int INITIALIZE_LEVEL = 0;
    int CURRENT_LEVEL = 1;
    int LEVEL_COMPLETE = 2;
    int LEVEL_LOST = 3;
    int LEVEL_QUIT = 4;
    int CHEAT_LEVEL_1 = 5;
    int CHEAT_LEVEL_2 = 6;
    int CHEAT_LEVEL_3 = 7;
    int CHEAT_LEVEL_4 = 8;


    /**
     * Gets sceneCode of this scene
     *
     * @return value of sceneCode
     */
    int getSceneCode();

    /**
     * Sets sceneCode of this scene to code
     *
     * @param sceneCode value of new sceneCode
     */
    void setSceneCode(int sceneCode);

    /**
     * Creates scene for this section of game.
     *
     * @return scene for this section of game
     */
    Scene setUpScene();

    /**
     * Runs one step in animation for this scene
     */
    void step();


}
