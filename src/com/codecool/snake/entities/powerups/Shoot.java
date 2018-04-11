package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;


public class Shoot extends GameEntity implements Animatable{

    private static final float speed = 3;
    public double dir;

    public Shoot(Pane pane, double xc, double yc,double direct){
        super(pane);
        setImage(Globals.snakeHead);
        setX(xc);
        setY(yc);
        pane.getChildren().add(this);
        this.dir=direct;
    }

    public void step(){
        setRotate(this.dir);
        Point2D heading = Utils.directionToVector(this.dir, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());


        for (GameEntity entity : Globals.getGameObjects()) {
            if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (entity instanceof SimpleEnemy) {
                    removeEnemy((SimpleEnemy) entity);
                    destroy();
                }
            }
        }
        if (isOutOfBounds()){
            destroy();
        }
    }
    public void removeEnemy(SimpleEnemy enemy){
        enemy.destroy();
    }

}

