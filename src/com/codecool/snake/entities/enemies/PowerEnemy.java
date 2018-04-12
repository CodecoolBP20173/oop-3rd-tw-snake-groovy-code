package com.codecool.snake.entities.enemies;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.HelathText;
import com.codecool.snake.entities.powerups.Shoot;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.Random;

public class PowerEnemy extends GameEntity implements Animatable, Interactable{
    private static final int damage = 10;




    public PowerEnemy(Pane pane) {
        super(pane);
        setImage(Globals.fear);
        pane.getChildren().add(this);
        Random rnd = new Random();
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);


    }

    @Override
    public void step() {

        if (getX() < Globals.snakehead.getX()) {
            setX(getX() + 1);

        } else {
            setX(getX() - 1);
        }
        if (getY() < Globals.snakehead.getY()) {
            setY(getY() + 1);
        } else {
            setY(getY() - 1);
        }

    }

    @Override
    public void apply(SnakeHead player) {
        player.changeHealth(-damage);
        player.getText().changeHealth(player.getHealth(),player);
        destroy();
        
    }

    @Override
    public String getMessage() {
        return "10 damage";
    }

}


