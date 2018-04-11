package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.Shoot;
import com.codecool.snake.entities.powerups.SimplePowerup;
import com.codecool.snake.entities.powerups.SpeedPowerUp;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Game extends Pane {

    public Game() {create();}

    void create(){
        new SnakeHead(this, 500, 500);

        spawnEnemies("simple", Globals.MAX_ENEMIES);


        new SimplePowerup(this);
        new SimplePowerup(this);
        new SimplePowerup(this);
        new SimplePowerup(this);
        new SpeedPowerUp(this);
    }

    public void spawnEnemy(String type){
        switch (type){
            case "simple" : new SimpleEnemy(this);
        }
    }

    public void spawnEnemies(String type, int count){
        for (int i = 0; i < count; i++) {
            spawnEnemy(type);
        }
    }

    public void resume(){
        Globals.getGameObjects();
        Globals.pKeyDown = false;
        start();
    }

    public void restart(){
        for (GameEntity entity: Globals.getGameObjects()){
            entity.destroy();
        }
        Globals.pKeyDown = false;
        create();
        Globals.gameLoop = new GameLoop();
        Globals.gameLoop.start();
    }

    public void exit(){
        System.exit(0);
    }

    public void pauseDialog(){
        Scene scene = getScene();
        Button resumeButton = new Button("Resume");
        Button restartButton = new Button("Restart");
        Button exitButton = new Button("Exit");


        final Stage pauseDialog = new Stage();
        pauseDialog.initModality(Modality.APPLICATION_MODAL);
        pauseDialog.initOwner(scene.getWindow());
        VBox dialogWindow = new VBox(25);
        dialogWindow.getChildren().addAll(new Text("The game is paused"),
                                            resumeButton,
                                            restartButton,
                                            exitButton);
        Scene dialogScene = new Scene(dialogWindow, 500, 300);
        pauseDialog.setScene(dialogScene);
        pauseDialog.show();

        resumeButton.setOnMouseClicked(event -> {
            resume();
            pauseDialog.close();

        });

        restartButton.setOnMouseClicked(event -> {
            restart();
            pauseDialog.close();
        });

        exitButton.setOnMouseClicked(event -> {
            exit();
        });

    }

    public void gameOverDialog() {

        Scene scene = getScene();
        Button newGameButton = new Button("New Game");
        Button exitButton = new Button("Exit");

        final Stage gameOverDialog = new Stage();
        gameOverDialog.initModality(Modality.APPLICATION_MODAL);
        gameOverDialog.initOwner(scene.getWindow());
        VBox dialogWindow = new VBox(25);
        dialogWindow.getChildren().addAll(new Text("Game Over"),
                newGameButton,
                exitButton);
        Scene dialogScene = new Scene(dialogWindow, 500, 300);
        gameOverDialog.setScene(dialogScene);
        gameOverDialog.show();

        newGameButton.setOnMouseClicked(event -> {
            restart();
            gameOverDialog.close();
        });

        exitButton.setOnMouseClicked(event -> {
            exit();
        });
    }

    public void start() {

        Scene scene = getScene();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:  Globals.leftKeyDown  = true; break;
                case RIGHT: Globals.rightKeyDown  = true; break;
                case SPACE: Globals.spaceDown = true; break;
                case P: Globals.pKeyDown = true;
                    pauseDialog();
                    break;
                case R: Globals.rKeyDown = true;
                    restart();
                    break;
                case K: Globals.kKeyDown = true;
                    exit();
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:  Globals.leftKeyDown  = false; break;
                case RIGHT: Globals.rightKeyDown  = false; break;
                case SPACE: Globals.spaceDown = false; break;
            }
        });
        Globals.gameLoop = new GameLoop();
        Globals.gameLoop.start();
    }
    public  void newPowerUp(){
        new SimplePowerup(this);
    }
    public  void newSpeedPowerUp(){
        new SpeedPowerUp(this);
    }


}
