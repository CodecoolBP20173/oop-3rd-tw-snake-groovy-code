package com.codecool.snake.entities.enemies;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;
import jdk.nashorn.internal.objects.Global;


public class PowerEnemy extends SimpleEnemy{
    private int stepX, stepY;
    private double playerXPos, playerYPos;
    private static int damage = 20;

    public PowerEnemy(Pane pane) {
        super(pane);
        startingPosition();
        setImage(Globals.fear);
    }

    public void getPlayerPosition(){
        for (GameEntity entity: Globals.getGameObjects()){
            if (entity instanceof SnakeHead){
                this.playerXPos = entity.getX();
                this.playerYPos = entity.getY();
            }
        }
    }

    public void moveTowardsPlayer(){
        getPlayerPosition();

        if (playerXPos > this.getX()){
            stepX = 1;
        } else if (playerXPos < this.getX()){
            stepX = -1;
        } else {
            stepX = 0;
        }

        if (playerYPos > this.getY()){
            stepY = 1;
        } else if (playerYPos < this.getY()){
            stepY = -1;
        } else {
            stepY = 0;
        }

        setX(getX()+  stepX);
        setY(getY()+ stepY);
        setRotate(Math.toDegrees(Math.atan2(playerYPos-this.getY(), playerXPos-this.getX())) + 90);
    }


    @Override
    public void step() {
        if (isOutOfBounds()){
            collisionHandling();
        }
        moveTowardsPlayer();
    }

    @Override
    public void destroy(){
        if (Globals.numberOfEnemies < Globals.MAX_ENEMIES) {
            new PowerEnemy(pane);
            Globals.numberOfEnemies++;
        }
        Globals.numberOfEnemies--;
        super.destroy();
    }

    @Override
    public void apply(SnakeHead player) {
        Globals.healthText.changeHealth(player.getHealth());
        player.changeHealth(-damage);
        destroy();
        new PowerEnemy(pane);

    }

    @Override
    public String getMessage() {
        return "10 damage";
    }

}


