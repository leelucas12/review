package CVRP2;

import java.io.IOException;

public class cvrp2 {
    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();    //获取开始时间
        ACO aco = new ACO();
        aco.init("./src/CVRP2/att49.txt.", 5);//城市信息文件，蚂蚁数量
        aco.run(100);
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");

    }
}
