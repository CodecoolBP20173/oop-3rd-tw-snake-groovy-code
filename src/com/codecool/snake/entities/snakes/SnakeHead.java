package com.codecool.snake.entities.snakes;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.HealthText;
import com.codecool.snake.entities.powerups.InverseDirectionPowerUp;
import com.codecool.snake.entities.powerups.Shoot;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.Random;

public class SnakeHead extends GameEntity implements Animatable {

    private static float speed = 2;
    private static float turnRate = 2;
    private GameEntity tail; // the last element. Needed to know where to add the next part.
    private int health;
    private boolean isGameOver;

    public SnakeHead(Pane pane, int xc, int yc) {
        super(pane);
        setX(xc);
        setY(yc);
        health = 100;
        tail = this;
        setImage(Globals.snakeHead);
        pane.getChildren().add(this);
        addPart(4);
        Globals.healthText = new HealthText(pane,this);
    }

    public void step() {
        double dir = getRotate();
        if (Globals.leftKeyDown) {
            if (!InverseDirectionPowerUp.turn){
                dir = dir - turnRate;
            }else{
                dir = dir + turnRate;
            }
        }
        if (Globals.rightKeyDown) {
            if(!InverseDirectionPowerUp.turn){
                dir = dir + turnRate;
            }else{
                dir = dir - turnRate;
            }

        }
        setRotate(dir);
        if (Globals.spaceDown){
            boolean isShooting=false;
            for (GameEntity entity : Globals.getGameObjects()){
                if (entity instanceof Shoot){
                    isShooting=true;
                }
            }
            if (!isShooting){
                new Shoot(this.pane,getX(),getY(),dir);
            }
        }
        // set rotation and position
        Point2D heading = Utils.directionToVector(dir, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());



        // check if collided with an enemy or a powerup
        for (GameEntity entity : Globals.getGameObjects()) {
            if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (entity instanceof Interactable) {
                    Interactable interactable = (Interactable) entity;
                    interactable.apply(this);
                    System.out.println(interactable.getMessage());
                }
            }
        }

        // check for game over condition
        if (isOutOfBounds() || health <= 0) {
            if (!isGameOver) {
                gameOver();
            }
            isGameOver = true;
        }
    }

    public void gameOver() {
        Globals.gameLoop.stop();
        System.out.println("game over");
        Globals.game.gameOverDialog();

    }

    public void addPart(int numParts) {
        for (int i = 0; i < numParts; i++) {
            SnakeBody newPart = new SnakeBody(pane, tail);
            tail = newPart;
        }
    }

    public void changeSnakeSpeed(){
        Random random = new Random();
        speed = random.nextInt(6 - 1 + 1) + 1;
        turnRate = speed;
    }

    public static float getSpeed() {
        return speed;
    }

    public void changeHealth ( int diff){
            health += diff;
        }

    public int getHealth() {
        return health;
    }


}

