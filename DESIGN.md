###Game Design

In completing this project, my main goal was to create a well-encapsulated game that privatized information within a
class as much as possible and that allowed for seamless addition of new features. I believed I was generally successful
in accomplishing this goal given my initial novice understanding of JavaFx. In regards to the structure of the source
classes, I separated classes that involved constructing the scenes to display from classes that dealt with components
of the game, such as Ball, Paddle, Block, etc. In the game_components, I added another package that contained classes
related to block components, such as BlockMatrix, PowerUp, etc. I also had container or Controller classes that 
controlled components, such as Ball and PowerUp, since there were multiple of these objects present in the game at once.
These controllers classes dealt with maintain the set of objects (balls, powerups, etc.) and dealt with operations
related to those objects. Through this structure of encapsulation, I found I was able to always maintain a thorough
understanding of my code, improving my ability to debug code and add features.

Adding features to this project is very simple due to my design structure. To add a new level to the game, one must add
a method in the BlockMatrix that constructs the block configuration and assigns various powerups. To add a new powerup,
one must add the powerUpIndentifier String to the PowerUp class and implement its effects in the LevelScene class in the
managePowerups() method. Adding another paddle to a level (which is what I had planned for Level 4) would involve first
constructing a PaddleGroupController class (which I also was not able to complete). This controller class would maintain
the set of paddles and update locations and handle collisions accordingly. Any other sort of feature would require new
classes and would have to be handled and designed by the programmer.

One design choice involved create an array of scenes (SplashScene and LevelScene) at the start of the main method. Based
on the state of the game, the main class levelSelect() method would switch scenes and display the correct visuals. This
design choice allow for further encapsulation and made it clear how the game visuals were handled throughout the game.
Another major design choice involved my addition for the SidePane, which contains the Menu and Pause buttons, the level
Number, the livesNumber, and the score. I thought this was a great addition, as it did not interfere or obstruct with 
the game play. The state of the game was cleanly displayed and updated and improved overall gaming experience. I also
initially planned to make my PowerUp class to be abstract, extending the class for various powerups added to the game.
However, I realized that the effects of the powerUp were the only difference between the powerUps. These effects could
easily be implemented directly in the LevelScene. With additional powerups with more complicated effects, it might make
more sense to create separate classes that handled the powerUp effects (such as for the Laser Paddle powerUp).

For the latest version of code, I did a great job to ironing out all bugs to the best of my knowledge. The only real
assumption I made was in regards to ball collisions with the paddle and blocks. All collisions involved the flipping the
sign of the x- and y-velocities of the ball within completing accounting for true physics. Yet this is a game, so I can
really do whatever I want as the creator. Also the current latest version of the code does not account for possible
ball-ball collisions.