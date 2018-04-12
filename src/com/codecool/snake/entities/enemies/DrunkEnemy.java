package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
//import com.codecool.snake.entities.HealthText;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.powerups.Shoot;
import com.codecool.snake.entities.snakes.SnakeBody;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// a simple enemy TODO make better ones.
public class DrunkEnemy extends GameEntity implements Animatable, Interactable {

    private Point2D heading;
    private double speed, direction;
    private int damage = 15;

    public DrunkEnemy(Pane pane) {
        super(pane);
        setImage(Globals.drunkEnemy);
        pane.getChildren().add(this);
        Random rnd = Utils.rand;
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
        setMovementAndDamage();

        ScaleTransition enlarge = new ScaleTransition(Duration.seconds(2), this);
        enlarge.setByX(1.2);
        enlarge.setByY(1.2);
        enlarge.setCycleCount(Timeline.INDEFINITE);
        enlarge.setAutoReverse(true);
        enlarge.play();

    }

    private void setMovementAndDamage(){
        damage = Utils.randInt(10, 25);
        speed = Utils.randDouble(0.5, 3);
        direction += Utils.randDouble(-15, 15) % 360;
        setRotate(direction);
        heading = Utils.directionToVector(direction, speed);
    }

    public double randomDirection(){
        Random rnd = new Random();
        return rnd.nextDouble() * 360;
    }

    public void startingPosition(){
        List<Double> playerXPos = new ArrayList<>();
        List<Double> playerYPos = new ArrayList<>();

        for (GameEntity entity: Globals.getGameObjects()){
            if (entity instanceof SnakeHead || entity instanceof SnakeBody){
                playerXPos.add(entity.getX());
                playerYPos.add(entity.getY());
            }
        }
        Random rnd = new Random();
        Double enemyXPos = rnd.nextDouble()*Globals.WINDOW_WIDTH;
        Double enemyYPos = rnd.nextDouble()*Globals.WINDOW_HEIGHT;

        for (Double snakeXPos: playerXPos){
            if (enemyXPos < 700 && enemyXPos > 300){
                if(enemyXPos < 600) {
                    enemyXPos -= snakeXPos / 2;
                } else if (enemyYPos >= 400){
                    enemyXPos += snakeXPos / 2;
                }
            }
        }

        for (Double snakeYPos: playerYPos){
            if (enemyYPos < 700 && enemyYPos > 300){
                if (enemyYPos < 600){
                    enemyYPos -= snakeYPos / 2;
                } else if (enemyYPos >= 400){
                    enemyYPos += snakeYPos / 2;
                }
            }
        }

        setX(enemyXPos);
        setY(enemyYPos);

    }

    public void collisionHandling(){
        int speed = 1;
        if (this.getX() < 10) {
            setX(10);
        } else if (this.getX() > 940) {
            setX(940);
        } else if (this.getY() < 10){
            setY(10);
        } else if (this.getY() < 900) {
            setY(900);
        }
        this.direction = randomDirection();
        setRotate(this.direction);
        heading = Utils.directionToVector(this.direction, speed);
    }

    @Override
    public void step() {
        if (isOutOfBounds()) {
            collisionHandling();
        }

        setMovementAndDamage();

        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(SnakeHead player) {
        player.changeHealth(-damage);
        Globals.healthText.changeHealth(player.getHealth());
        destroy();
    }

    @Override
    public void destroy(){
        if (Globals.numberOfEnemies < Globals.MAX_ENEMIES){
            new DrunkEnemy(pane);
            Globals.numberOfEnemies++;
        }
        Globals.numberOfEnemies--;
        super.destroy();
    }

    @Override
    public String getMessage() {
        return String.valueOf(damage) + " damage";
    }
}
