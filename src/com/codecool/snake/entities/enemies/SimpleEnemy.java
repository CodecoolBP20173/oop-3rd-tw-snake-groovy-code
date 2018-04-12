package com.codecool.snake.entities.enemies;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.HealthText;
import com.codecool.snake.entities.snakes.SnakeBody;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// a simple enemy TODO make better ones.
public class SimpleEnemy extends GameEntity implements Animatable, Interactable {

    private Point2D heading;
    private static final int damage = 10;
    HealthText text;
    private double direction;


    public SimpleEnemy(Pane pane) {
        super(pane);
        Globals.numberOfEnemies++;
        setImage(Globals.simpleEnemy);
        pane.getChildren().add(this);
        int speed = 1;
        startingPosition();
        this.direction = randomDirection();
        setRotate(this.direction);
        heading = Utils.directionToVector(this.direction, speed);
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
    public void destroy(){
        if (Globals.numberOfEnemies < Globals.MAX_ENEMIES) {
            new SimpleEnemy(pane);
            Globals.numberOfEnemies++;
        }
        Globals.numberOfEnemies--;
        super.destroy();
    }

    @Override
    public void step() {
        if (isOutOfBounds()) {
            collisionHandling();
        }
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
    public String getMessage() {
        return "10 damage";
    }
}
