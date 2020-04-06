package SnakeGameFX;

import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class Food {

    private static int food_x = 0;
    private static int food_y = 0;

    public static int getFood_x() {
        return food_x;
    }

    public static int getFood_y() {
        return food_y;
    }

    //Generating new food for the snake
    public static void getNewFood(){
        Random randomGenerator = new Random();
        start: while (true){
            food_x = randomGenerator.nextInt(Corner.getWidth());
            food_y = randomGenerator.nextInt(Corner.getHeight());

            for (Corner snakeCorner : Snake.getSnake()) {
                if (snakeCorner.getPosition_x() == food_x && snakeCorner.getPosition_y() == food_y) {
                    continue start;
                }
            }
            break;
        }
    }
}
