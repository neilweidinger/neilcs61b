import java.awt.Color;

public class Mouse {

    public static int color = 1;
    public static double time = 0;
    public static double x = 70;
    public static double y = 60;
    public static double vx = 1 * 2;
    public static double vy = 1.3 * 2;
    public static double radius = 10;

    public static void main(String[] args) {
        StdDraw.setScale(-100, 100);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        while (true) {

            //if next position of ball would hit right or left wall or
            //next position of ball would hit top or bottom wall
            Mouse.wallCollide();

            //if next position of ball is within square
            Mouse.squareCollide();

            //update position of ball
            x += vx;
            y += vy;

            StdDraw.clear(StdDraw.BLACK);

            StdDraw.setPenColor(Mouse.returnColor());
            StdDraw.filledCircle(x, y, radius);

            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledSquare(StdDraw.mouseX(), StdDraw.mouseY(), 25);

            StdDraw.show();
            StdDraw.pause(10);

            time += 10;
        }
    }

    public static void squareCollide() {
        if((x + vx) > (StdDraw.mouseX() - 25 - radius) && (x + vx) < (StdDraw.mouseX() + 25 + radius) &&
           (y + vy) > (StdDraw.mouseY() - 25 - radius) && (y + vy) < (StdDraw.mouseY() + 25 + radius)) {

            //if current position of ball is hitting right or left wall of square
            if (x <= (StdDraw.mouseX() - 25 - radius) || x >= (StdDraw.mouseX() + 25 + radius)) {
                vx *= -1;
                System.out.println("x\t" + time + " ms");
            }
            //if current position of ball is hitting top or bottom wall of square
            else {
                vy *= -1;
                System.out.println("y\t" + time + " ms");
            }

            Mouse.changeColor();
            System.out.println("color = " + color);
        }
    }

    public static void wallCollide() {
        if (Math.abs(x + vx) > (100 - radius)) {
                vx *= -1;
        }

        if (Math.abs(y + vy) > (100 - radius)) {
                vy *= -1;
        }
    }

    public static Color returnColor() {
        switch(color) {
            case 1:
                return StdDraw.BLUE;
            case 2:
                return StdDraw.MAGENTA;
            case 3:
                return StdDraw.GREEN;
            default:
                return StdDraw.BLACK;
        }
    }

    public static void changeColor() {
        color++;
        if (color > 3) {color = 1;}
    }
}
