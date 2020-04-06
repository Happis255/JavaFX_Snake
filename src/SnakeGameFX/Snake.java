package SnakeGameFX;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private static List <Corner> snake = new ArrayList<>();
    private static int speed = 1;
    private static SnakeDirection direction = SnakeDirection.right;
    private static boolean game_finished = false;

    enum SnakeDirection {
        right, left, top, down;
    }

    public static List<Corner> getSnake() {
        return snake;
    }

    public static void setSnake(List<Corner> snake) {
        Snake.snake = snake;
    }

    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        Snake.speed = speed;
    }

    public static SnakeDirection getDirection() {
        return direction;
    }

    public static void setDirection(SnakeDirection direction) {
        Snake.direction = direction;
    }

    public static void initializeSnake(){
        for (int i = 0; i< 3; i++)
            snake.add(new Corner(Corner.getWidth()/2, Corner.getHeight()/2));
    }

    public static void snakeMovement(GraphicsContext graphicsContext){
        if (game_finished){
            graphicsContext.setFill(Color.RED);
            graphicsContext.setFont(new Font("",30));
            graphicsContext.fillText("Game Over :(", 100, 200);
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
                    if (snake.get(0).getPosition_y() > Corner.getHeight()) game_finished = true;
                    break;
                case right:
                    snake.get(0).setPosition_x(snake.get(0).getPosition_x()+1);
                    if (snake.get(0).getPosition_x() < 0) game_finished = true;
                    break;
                case left:
                    snake.get(0).setPosition_x(snake.get(0).getPosition_x()-1);
                    if (snake.get(0).getPosition_x() > Corner.getWidth()) game_finished = true;
                    break;
            }

            for (Corner corner : snake){
                graphicsContext.setFill(Color.web("679b9b"));
                graphicsContext.fillRect(corner.getPosition_x()*Corner.getCorner_size(), corner.getPosition_y()*Corner.getCorner_size(),
                        Corner.getCorner_size()-1, Corner.getCorner_size()-1);
                graphicsContext.setFill(Color.web("ffffdd"));
                graphicsContext.fillRect(corner.getPosition_x()*Corner.getCorner_size(), corner.getPosition_y()*Corner.getCorner_size(),
                        Corner.getCorner_size()-2, Corner.getCorner_size()-2);
            }

            //Check, if snake eats
            if (snake.get(0).getPosition_x() == Food.getFood_x() && snake.get(0).getPosition_y() == Food.getFood_y()){
                snake.add(new Corner(-1, -1));
                Food.getNewFood(graphicsContext);
            }

            //Check if have bitten himself
            for (int i = 1; i<snake.size(); i++){
                if (snake.get(0).getPosition_x() == snake.get(i).getPosition_x() &&
                        snake.get(0).getPosition_y() == snake.get(i).getPosition_y()) {
                    game_finished = true;
                    break;
                }
            }

            //Setting up background color and score
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.setFont(new Font("", 30));
            graphicsContext.fillText("Score: "+(speed-5), 10, 30);
        }
    }
}
