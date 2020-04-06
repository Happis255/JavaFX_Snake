package SnakeGameFX;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    public void draw(GraphicsContext graphicsContext){

        graphicsContext.drawImage(new Image("Assets/Grass.jpg"), 0, 0);
        boolean head = false;

        //Snake
        for (Corner corner : Snake.getSnake()){
            if (!head){
                switch(Snake.getDirection()){
                    case right: graphicsContext.drawImage(new Image("Assets/snakehead_right.png"),corner.getPosition_x() * Corner.getCorner_size(), corner.getPosition_y() * Corner.getCorner_size());
                        break;
                    case top: graphicsContext.drawImage(new Image("Assets/snakehead_up.png"),corner.getPosition_x() * Corner.getCorner_size(), corner.getPosition_y() * Corner.getCorner_size());
                        break;
                    case left:graphicsContext.drawImage(new Image("Assets/snakehead_left.png"),corner.getPosition_x() * Corner.getCorner_size(), corner.getPosition_y() * Corner.getCorner_size());
                        break;
                    case down: graphicsContext.drawImage(new Image("Assets/snakehead_down.png"),corner.getPosition_x() * Corner.getCorner_size(), corner.getPosition_y() * Corner.getCorner_size());
                        break;
                }
                head = true;
            } else {
                graphicsContext.drawImage(new Image("Assets/snakebody.png"), corner.getPosition_x() * Corner.getCorner_size(), corner.getPosition_y() * Corner.getCorner_size());
            }
        }

        //Food
        graphicsContext.drawImage(new Image("Assets/apple.png"), Food.getFood_x()*Corner.getCorner_size(), Food.getFood_y()*Corner.getCorner_size());

        //Shadow score
        graphicsContext.setFill(Color.web("141414"));
        graphicsContext.setFont(Font.font("Impact", FontWeight.BOLD, 40));
        graphicsContext.fillText("Score: "+ Snake.getScore(), 15, 743);

        //Score
        graphicsContext.setFill(Color.web("ccff8a"));
        graphicsContext.setFont(Font.font("Impact", FontWeight.BOLD, 40));
        graphicsContext.fillText("Score: "+ Snake.getScore(), 13, 740);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Setting up game area
        VBox root = new VBox();
        Canvas canva = new Canvas(Corner.getCorner_size()*Corner.getWidth(), Corner.getCorner_size()*Corner.getHeight());
        GraphicsContext graphicsContext = canva.getGraphicsContext2D();
        root.getChildren().add(canva);
        Scene scene = new Scene(root, Corner.getCorner_size()*Corner.getWidth(), Corner.getCorner_size()*Corner.getHeight());

        //Initialize snake food
        Food.getNewFood();

        //Create snake
        Snake.initializeSnake();

        //Setting up game timer
        //The class AnimationTimer allows to create a timer, that is called in each frame while it is active
        new AnimationTimer(){
            long last_tick = 0;

            @Override
            public void handle(long l) {
                if (l - last_tick > 1000000000 / Snake.getSpeed()) {
                    last_tick = l;
                    Snake.snakeMovement(graphicsContext);
                    if(!Snake.isGame_finished()) draw(graphicsContext);
                } else if (last_tick == 0){
                    last_tick = l;
                    Snake.snakeMovement(graphicsContext);
                    if(!Snake.isGame_finished())draw(graphicsContext);
                }
            }
        }.start();

        //Ading WASD controllers
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                    if (key.getCode() == KeyCode.W) {
                        if (Snake.getDirection() != Snake.SnakeDirection.down)
                            Snake.setDirection(Snake.SnakeDirection.top);
                    } else if (key.getCode() == KeyCode.A) {
                        if (Snake.getDirection() != Snake.SnakeDirection.right)
                         Snake.setDirection(Snake.SnakeDirection.left);
                    } else if (key.getCode() == KeyCode.S) {
                        if (Snake.getDirection() != Snake.SnakeDirection.top)
                            Snake.setDirection(Snake.SnakeDirection.down);
                    } else if (key.getCode() == KeyCode.D) {
                        if (Snake.getDirection() != Snake.SnakeDirection.left)
                            Snake.setDirection(Snake.SnakeDirection.right);
                    };
        });

        primaryStage.setTitle("Snake game Java FX");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("Assets/icon.jpg"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
