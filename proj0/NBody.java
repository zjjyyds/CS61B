public class NBody {

    public static void main(String[] args) {
        // 1. 从命令行参数中读取 T、dt 和 filename
        double T = Double.parseDouble(args[0]); // 总时间
        double dt = Double.parseDouble(args[1]); // 时间步长
        String filename = args[2]; // 文件名

        // 2. 从文件中读取行星数据和宇宙半径
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        // 3. 设置 StdDraw 的缩放比例
        StdDraw.setScale(-radius, radius);

        // 4. 启用双缓冲
        StdDraw.enableDoubleBuffering();

        // 5. 创建时间变量并开始模拟
        double time = 0;
        while (time < T) {
            // 6. 计算每个行星的净力
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            // 7. 更新每个行星的位置、速度和加速度
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            // 8. 绘制背景
            StdDraw.picture(0, 0, "images/starfield.jpg");

            // 9. 绘制所有行星
            for (Planet planet : planets) {
                planet.draw();
            }

            // 10. 显示屏幕外缓冲区
            StdDraw.show();

            // 11. 暂停动画 10 毫秒
            StdDraw.pause(10);

            // 12. 增加时间变量
            time += dt;
        }  
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                          planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }

    public static double readRadius(String filename){
        In in = new In(filename);
        in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String filename){
        In in =new In(filename);
        int num = in.readInt();
        in.readDouble();
        Planet[] planet = new Planet[num];

        for (int i = 0; i < num; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();

            // 创建 Planet 对象并存储到数组中
            planet[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }

        return planet;

    }
}
