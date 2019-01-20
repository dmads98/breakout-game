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


    int getSceneCode();

    void setSceneCode(int code);

    Scene setUpScene();


}
