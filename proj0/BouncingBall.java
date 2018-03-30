import java.awt.Color;

public class BouncingBall {
    public static int color = 1;

    public static void main(String[] args) {
        StdDraw.setScale(-100, 100);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        double time = 0;

        double x = 70;
        double y = 60;
        double vx = 1 * 2;
        double vy = 1.3 * 2;
        double radius = 10;

        while (true) {

            //if next position of ball would hit right or left wall
            if (Math.abs(x + vx) > (100 - radius)) {
                vx *= -1;
            }
            //if next position of ball would hit top or bottom wall
            if (Math.abs(y + vy) > (100 - radius)) {
                vy *= -1;
            }

            //if next position of ball is within square
            if((x + vx) > (-25 - radius) && (x + vx) < (25 + radius) &&
               (y + vy) > (-25 - radius) && (y + vy) < (25 + radius)) {

                //if current position of ball is hitting right or left wall of square
                if (x <= (-25 - radius) || x >= (25 + radius)) {
                    vx *= -1;
                    System.out.println("x\t" + time + " ms");
                }
                //if current position of ball is hitting top or bottom wall of square
                else {
                    vy *= -1;
                    System.out.println("y\t" + time + " ms");
                }

                BouncingBall.changeColor();
                System.out.println("color = " + color);
            }

            x += vx;
            y += vy;

            StdDraw.clear(StdDraw.BLACK);

            StdDraw.setPenColor(BouncingBall.returnColor());
            StdDraw.filledCircle(x, y, radius);

            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.square(0, 0, 25);

            StdDraw.show();
            StdDraw.pause(10);

            time += 10;
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
