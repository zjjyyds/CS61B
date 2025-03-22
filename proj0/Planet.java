public class Planet {
    public double xxPos; // 其当前的 x 位置
    public double yyPos; // 其当前的 y 位置
    public double xxVel; // 其当前在 x 方向的速度
    public double yyVel; // y 方向的当前速度
    public double mass;  // 质量
    public String imgFileName; // 与描绘身体的图像相对应的文件的名称（例如jupiter.gif）

    public static final double  G = 6.67e-11;
    public Planet(double xP, double yP, double xV,double yV, double m, String img){
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }
    public Planet(Planet b){
        this.xxPos = b.xxPos;
        this.yyPos = b.yyPos;
        this.xxVel = b.xxVel;
        this.yyVel = b.yyVel;
        this.mass = b.mass;
        this.imgFileName = b.imgFileName;
    }

    public double calcDistance(Planet p){
        double pXPos = p.xxPos;
        double pYPos = p.yyPos;
        double dX = Math.abs(pXPos-this.xxPos);
        double dY = Math.abs(pYPos-this.yyPos);
        return Math.sqrt(dX*dX+dY*dY);
    }

    public double calcForceExertedBy(Planet p){
        double distance = calcDistance(p);
        double F = G*this.mass*p.mass/(distance*distance);
        return F;
    }

    public double calcForceExertedByX(Planet p){
        double dX = p.xxPos-this.xxPos;
        double r = calcDistance(p);
        double F = calcForceExertedBy(p);
        return F*dX/r;
    }
    public double calcForceExertedByY(Planet p){
        double dY = p.yyPos-this.yyPos;
        double r = calcDistance(p);
        double F = calcForceExertedBy(p);
        return F*dY/r;
    }

    
    public double calcNetForceExertedByX(Planet[] p){
        double xF=0;
        for (Planet p2 : p) {
            if(this.equals(p2)){
                continue;
            }
            xF+=calcForceExertedByX(p2);
        }
        return xF;
    }
    public double calcNetForceExertedByY(Planet[] p){
        double yF=0;
        for (Planet p2 : p) {
            if(this.equals(p2)){
                continue;
            }
            yF+=calcForceExertedByY(p2);
        }
        return yF;
    }

    public void update(double time,double xforce, double yforce){
        double newVx = this.xxVel + xforce/this.mass*time;
        double newVy = this.yyVel + yforce/this.mass*time;
        this.xxVel = newVx;
        this.yyVel = newVy;
        this.xxPos = this.xxPos + time * newVx;
        this.yyPos = this.yyPos + time * newVy;
    }

    public void draw(){
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
