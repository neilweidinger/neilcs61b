public class TestPlanet {
    public static void main(String[] args) {
        Planet p1 = new Planet(1, 1, 2, 3, 10, "p1.jpg");
        Planet p2 = new Planet(3, 5, -1, 6, 8, "p2.jpg");

        System.out.println("Pairwise force: " + p1.calcForceExertedBy(p2));
    }
}