package com.codecool.snake.entities.enemies;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.HealthText;
import com.codecool.snake.entities.powerups.Shoot;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

// a simple enemy TODO make better ones.
public class DrunkEnemy extends GameEntity implements Animatable, Interactable {

    private Point2D heading;
    private double speed, direction;
    private int damage = 15;
    HealthText text;

    public DrunkEnemy(Pane pane) {
        super(pane);
        setImage(Globals.drunkEnemy);
        pane.getChildren().add(this);
        Random rnd = Utils.rand;
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
        setMovementAndDamage();

        ScaleTransition enlarge = new ScaleTransition(Duration.seconds(2), this);
        enlarge.setByX(1.5);
        enlarge.setByY(1.3);
        enlarge.setCycleCount(Timeline.INDEFINITE);
        enlarge.setAutoReverse(true);
        enlarge.play();

    }

    private void setMovementAndDamage(){
        damage = Utils.randInt(-5, 15);
        speed = Utils.randDouble(0.5, 3);
        direction += Utils.randDouble(-15, 15) % 360;
        setRotate(direction);
        heading = Utils.directionToVector(direction, speed);
    }

    @Override
    public void step() {
        if (isOutOfBounds()) {
            destroy();
        }

        setMovementAndDamage();

        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(SnakeHead player) {
        player.changeHealth(-damage);
        player.getText().changeHealth(player.getHealth(),player);
        destroy();
    }

    @Override
    public void destroy(){
        if (Globals.numberOfEnemies < Globals.MAX_ENEMIES) new DrunkEnemy(pane);
        Globals.numberOfEnemies--;
        super.destroy();
    }

    @Override
    public String getMessage() {
        return String.valueOf(damage) + " damage";
    }
}