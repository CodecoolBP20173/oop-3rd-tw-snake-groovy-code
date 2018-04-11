package com.codecool.snake.entities.powerups;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Game;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

import java.util.Random;

public class SpeedPowerUp extends GameEntity implements Interactable{
    public static boolean turn;
    private final Game game;

    public SpeedPowerUp(Game pane){
        super(pane);
        game=pane;
        setImage(Globals.speedBerry);
        pane.getChildren().add(this);

        Random rnd = new Random();
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
    }
    @Override
    public void apply(SnakeHead snakeHead) {
        snakeHead.addPart(4);
        destroy();
        game.newSpeedPowerUp();
        turn = !turn;
    }

    @Override
    public String getMessage() {
        return "Got speed-up :)";
    }

    public static void speedPowerUp() {
    }
}
