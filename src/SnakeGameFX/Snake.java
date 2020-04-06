package SnakeGameFX;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private static List <Corner> snake = new ArrayList<>();
    private static int speed = 10;
    private static SnakeDirection direction = SnakeDirection.right;
    private static boolean game_finished = false;
    private static boolean gameoverscreen_showed = false;
    private static int score = 0;
    private static int counter = 0;

    enum SnakeDirection {
        right, left, top, down;
    }

    public static List<Corner> getSnake() {
        return snake;
    }

    public static int getSpeed() {
        return speed;
    }

    public static void setDirection(SnakeDirection direction) {
        Snake.direction = direction;
    }

    public static SnakeDirection getDirection() {
        return direction;
    }

    public static int getScore() {
        return score;
    }

    public static void upSpeed(){
        speed++;
    }

    public static void giveOneScoreUp() {
        score++;
    }

    public static boolean isGame_finished() {
        return game_finished;
    }

    public static void initializeSnake(){
        for (int i = 0; i< 3; i++)
            snake.add(new Corner(Corner.getWidth()/2, Corner.getHeight()/2));
    }

    public static void snakeMovement(GraphicsContext graphicsContext){
        if (game_finished){
            if(!gameoverscreen_showed) {
                graphicsContext.setFill(Color.rgb(0, 0, 0, 0.5));
                graphicsContext.fillRect(0, 0,Corner.getCorner_size()*Corner.getWidth(), Corner.getCorner_size()*Corner.getHeight());

                graphicsContext.setFill(Color.rgb(255, 255, 255));
                graphicsContext.setFont(Font.font("Calibri", FontWeight.SEMI_BOLD, 90));
                graphicsContext.fillText("GAME OVER", 20, Corner.getCorner_size()*Corner.getHeight()/2-40);

                graphicsContext.setFont(Font.font("Calibri", FontWeight.NORMAL, 50));
                graphicsContext.fillText("Score: " + score, 170, Corner.getCorner_size()*Corner.getHeight()/2+10);
                gameoverscreen_showed = true;
            }

        } else {
            //Move all snakes parts without the head
            for (int i = snake.size() - 1; i >=1; i--){
                snake.get(i).setPosition_x( snake.get(i-1).getPosition_x());
                snake.get(i).setPosition_y( snake.get(i-1).getPosition_y());
            }
            //Move the head
            switch(direction){
                case top:
                    snake.get(0).setPosition_y(snake.get(0).getPosition_y()-1);
                    if (snake.get(0).getPosition_y() < 0) game_finished = true;
                    break;
                case down:
                    snake.get(0).setPosition_y(snake.get(0).getPosition_y()+1);
                    if (snake.get(0).getPosition_y() >= Corner.getHeight()) game_finished = true;
                    break;
                case right:
                    snake.get(0).setPosition_x(snake.get(0).getPosition_x()+1);
                    if (snake.get(0).getPosition_x() >= Corner.getWidth()) game_finished = true;
                    break;
                case left:
                    snake.get(0).setPosition_x(snake.get(0).getPosition_x()-1);
                    if (snake.get(0).getPosition_x() < 0) game_finished = true;
                    break;
            }

            //Check, if snake eats
            if (snake.get(0).getPosition_x() == Food.getFood_x() && snake.get(0).getPosition_y() == Food.getFood_y()){
                snake.add(new Corner(-1, -1));
                giveOneScoreUp();
                Food.getNewFood();
                counter++;
                if (counter == 5){
                    Snake.upSpeed();
                    counter = 0;
                }
            }

            //Check if have bitten himself
            for (int i = 1; i<snake.size(); i++){
                if (snake.get(0).getPosition_x() == snake.get(i).getPosition_x() &&
                        snake.get(0).getPosition_y() == snake.get(i).getPosition_y()) {
                    game_finished = true;
                    break;
                }
            }
        }
    }
}
