package ceShi;


import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();    //获取开始时间
        ACO aco = new ACO();
        aco.init("att48.txt", 500);//城市信息文件，蚂蚁数量
        aco.run(100);//迭代次数
        aco.ReportResult();

        long endTime = System.currentTimeMillis();    //获取结束时间
        System.out.println();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }
}
