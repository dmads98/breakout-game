Breakout
====

This project implements the game of Breakout.

Name: Dhanush Madabusi

### Timeline

Start Date: 1/13/19

Finish Date: 1/21/19

Hours Spent: 40

### Resources Used

Online JavaFx tutorial
StackOverflow


### Running the Program

Main class: GameLauncher

Data files needed: None

Key/Mouse inputs: Left and Right arrow control Paddle. Mouse input controls "Start Game", "Menu", and "Pause" buttons

Cheat keys:
 - "Q": Quit game and return to menu
 - "1": Jump to Level 1
 - "2": Jump to Level 2
 - "3": Jump to Level 3
 - "4": Jump to Level 4
 - "A": Add one life

Known Bugs:
- Laser Paddle powerup was not completed, latest commit removes laser paddle code from working files
- Levels 3 and 4 were not setup
- Program works fine otherwise except for the occasional lag; I have worked hard to try to remove all performance
bottlenecks
- All objects collisions in game follow simple sign flipping of x- and y- velocities
    - true physics is followed to fullest extent

### Notes
Level 3 would have been more or less the same as Level 2, except for a diamond block configuration and faster ball 
speed. Level 4 would have had the same block configuration and block speed as Level 3, except a second paddle would have
been at the top of the screen. Balls could be lost hit both the top and bottom walls. Additionally, I had planned to add
a high score screen for when the user completed the game (win or loss). The score screen would have kept track of high
scores for the user session. I also wanted to add an extra credit feature starting from Level 3 that involved a "bomb" 
that descended from a random point at the top of the screen every 15 seconds. If the paddle was hit by the bomb, the 
user would lose a life.

### Impressions
This assignment was a great experience to learn how to code a project (and a game) from scratch. Through this hands-on 
project, I was able to gain a solid grasp on JavaFx that I would not have been able to gain through other methods.
Additionally, this project demonstrated to me the importance of planning ahead and making design decisions for proper
encapsulation in accordance with the open/close principle. It is vital to create structured and well-written code for
readability, debugging, and ability to add new features. 

