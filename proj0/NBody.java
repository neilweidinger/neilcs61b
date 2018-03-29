public class NBody {
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dT = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = NBody.readRadius(filename);
        Planet[] planets = NBody.readPlanets(filename);

        StdDraw.setXscale(-1 * radius, radius);
        StdDraw.setYscale(-1 * radius, radius);
        NBody.drawEverything(planets);

        StdDraw.enableDoubleBuffering();

        //main animation loop
        for (int i = 0; i <= T; i += dT) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            //calculates new x and y forces for each planet
            for (int j = 0; j < planets.length; j++) {
                xForces[j] = planets[j].calcNetForceExertedByX(planets);
                yForces[j] = planets[j].calcNetForceExertedByY(planets);

                planets[j].update(dT, xForces[j], yForces[j]);
            }

            NBody.drawEverything(planets);

            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%e\n", radius);
        for (Planet p : planets) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
        }
    }

    public static double readRadius(String name) {
        In in = new In(name);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String name) {
        In in = new In(name);
        int numPlanets = in.readInt();
        in.readDouble();

        Planet[] planets = new Planet[numPlanets];
        int i = 0;

        while (i != numPlanets) {
            planets[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(),
                                    in.readDouble(), in.readDouble(), in.readString());
            i++;
        }

        return planets;
    }

    public static void drawEverything(Planet[] planets) {
        StdDraw.picture(0, 0, "images/starfield.jpg");

        for (Planet p : planets) {
            StdDraw.picture(p.xxPos, p.yyPos, "images/" + p.imgFileName);
        }
    }
}
