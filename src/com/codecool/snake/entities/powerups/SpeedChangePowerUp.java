package com.codecool.snake.entities.powerups;

import com.codecool.snake.Game;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;

public class SpeedChangePowerUp extends SimplePowerup implements Interactable {

    private final Game game;

    public SpeedChangePowerUp(Game pane) {
        super(pane);
        game = pane;
        setImage(Globals.powerupBerry);
    }

    public void apply(SnakeHead snakeHead){
        snakeHead.changeSnakeSpeed();
        destroy();
        snakeHead.changeHealth(30);
        new SpeedChangePowerUp(game);
    }
}
