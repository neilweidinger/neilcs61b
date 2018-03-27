public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static final double gConst = 6.67 / 1e11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        this(p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
    }

    public double calcDistance(Planet p) {
        double xxDist = p.xxPos - this.xxPos;
        double yyDist = p.yyPos - this.yyPos;

        return Math.sqrt((xxDist * xxDist) + (yyDist * yyDist));
    }

    public double calcForceExertedBy(Planet p) {
        double r = this.calcDistance(p);

        return (1/(r * r)) * (gConst * this.mass * p.mass);
    }

    public double calcForceExertedByX(Planet p) {
        return this.calcForceExertedBy(p) * ((p.xxPos - this.xxPos) / this.calcDistance(p));
    }

    public double calcForceExertedByY(Planet p) {
        return this.calcForceExertedBy(p) * ((p.yyPos - this.yyPos) / this.calcDistance(p));
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double total = 0.0;

        for (Planet p : planets) {
            if (!this.equals(p)) {
                total += this.calcForceExertedByX(p);
            }
        }

        return total;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double total = 0.0;

        for (Planet p : planets) {
            if (!this.equals(p)) {
                total += this.calcForceExertedByY(p);
            }
        }

        return total;
    }

    //dt = period of time to update, fX = x force, fY = y force
    public void update(double dt, double fX, double fY) {
        this.xxVel = this.xxVel + (dt * (fX / this.mass));
        this.yyVel = this.yyVel + (dt * (fY / this.mass));
        this.xxPos = this.xxPos + (dt * this.xxVel);
        this.yyPos = this.yyPos + (dt * this.yyVel);
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }
}
