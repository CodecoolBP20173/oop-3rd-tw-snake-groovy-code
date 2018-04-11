package com.codecool.snake.entities;

import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;


public class HelathText extends Text{
    public HelathText(Pane pane, SnakeHead sneak){
        String texthealth= String.valueOf(sneak.getHealth());
        this.setText("Health: "+texthealth);
        setX(10);
        setY(50);
        pane.getChildren().add(this);
    }
    public void changeHealth(int health,SnakeHead sneak){
        this.setText("Health: "+String.valueOf(health));
    }
}
