package SnakeGameFX;

public class Corner {

    private static int width = 20;
    private static int height = 30;
    private static int corner_size = 25;

    private int position_x;
    private int position_y;

    public Corner(int position_x, int position_y) {
        this.position_x = position_x;
        this.position_y = position_y;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static int getCorner_size() {
        return corner_size;
    }

    public int getPosition_x() {
        return position_x;
    }

    public void setPosition_x(int position_x) {
        this.position_x = position_x;
    }

    public int getPosition_y() {
        return position_y;
    }

    public void setPosition_y(int position_y) {
        this.position_y = position_y;
    }
}
