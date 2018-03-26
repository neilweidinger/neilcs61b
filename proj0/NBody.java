public class NBody {
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dT = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = NBody.readRadius(filename);
        Planet[] planets = NBody.readPlanets(filename);

        StdDraw.setXscale(-1 * radius, radius);
        StdDraw.setYscale(-1 * radius, radius);
        StdDraw.picture(0, 0, "images/starfield.jpg");

        for (Planet p : planets) {
            p.draw();
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
}