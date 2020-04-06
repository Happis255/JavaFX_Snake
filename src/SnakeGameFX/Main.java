package SnakeGameFX;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        //Setting up game area
        VBox root = new VBox();
        Canvas canva = new Canvas(Corner.getCorner_size()*Corner.getWidth(), Corner.getCorner_size()*Corner.getHeight());
        GraphicsContext graphicsContext = canva.getGraphicsContext2D();
        root.getChildren().add(canva);
        Scene scene = new Scene(root, Corner.getCorner_size()*Corner.getWidth(), Corner.getCorner_size()*Corner.getHeight());

        graphicsContext.setFill(Color.web("a0ffe6"));
        graphicsContext.fillRect(0, 0, Corner.getWidth()*Corner.getCorner_size(), Corner.getHeight()*Corner.getCorner_size());

        //Initialize snake food
        Food.getNewFood(graphicsContext);

        //Setting up game timer
        //The class AnimationTimer allows to create a timer, that is called in each frame while it is active
        new AnimationTimer(){
            long last_tick = 0;

            @Override
            public void handle(long l) {
                if (l - last_tick > 1000000000 / Snake.getSpeed()) {
                    last_tick = l;
                    Snake.snakeMovement(graphicsContext);
                } else if (last_tick == 0){
                    last_tick = l;
                    Snake.snakeMovement(graphicsContext);
                }
            }
        }.start();

        //Ading WASD controllers
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                    if (key.getCode() == KeyCode.W) {
                        Snake.setDirection(Snake.SnakeDirection.top);
                    } else if (key.getCode() == KeyCode.A) {
                        Snake.setDirection(Snake.SnakeDirection.left);
                    } else if (key.getCode() == KeyCode.S) {
                        Snake.setDirection(Snake.SnakeDirection.down);
                    } else if (key.getCode() == KeyCode.D) {
                        Snake.setDirection(Snake.SnakeDirection.right);
                    };
        });

        //Create snake
        Snake.initializeSnake();

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
