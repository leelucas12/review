package ceShi;

import java.util.Random;

public class Ant {
    public int[] tour;//参观城市顺序 -1未访问;
    int[] unvisitedcity;//unvisitedcity 取值是1或-1，-1表示没有访问过，1表示访问过
    public double tourlength;//某蚂蚁所走路程总长度。
    int citycount;//城市个数

    //初始化
    public void init(int citys){
        citycount = citys;
        unvisitedcity = new int[citycount+1];
        tour = new int[citycount + 1];
        tourlength = 999999;
        for (int i = 0; i < citycount+1; i++) {
            tour[i] = -1;
            unvisitedcity[i] = -1;
        }
        //默认最后一个城市为出发城市
        tour[0]=citycount-1;
        unvisitedcity[citycount-1]=1;


    }

    public void selectcitys(int index, double[][] tao, double[][] distance){
        double[] p= new double[citycount];//下一步要走的城市的选中概率
        //计算选中概率所需系数。
        double alpha = 1.0;
        double beta = 2.0;
        double sum = 0;
        int currentcity = tour[index - 1];//蚂蚁所处当前城市
        //计算公式中的分母部分（为下一步计算选中概率使用）
        for (int i = 0; i < citycount; i++) {
            if (unvisitedcity[i] == -1)//没走过
                sum += (Math.pow(tao[currentcity][i], alpha) *
                        Math.pow(1.0 / distance[currentcity][i], beta));
        }
        for (int i = 0; i < citycount; i++) {
            if (unvisitedcity[i] == 1)
                p[i] = 0.0;//城市走过了，选中概率就是0
            else {
                //没走过，下一步要走这个城市的概率是？
                p[i] = (Math.pow(tao[currentcity][i], alpha) *
                        Math.pow(1.0 / distance[currentcity][i], beta)) / sum;
            }
        }
        //轮盘赌法
        Random r = new Random();
        //轮盘赌选择一个城市；
        double sumselect = 0;
        double zong=0;
        int selectcity = -1;
        //城市选择随机，直到n个概率加起来大于随机数，则选择该城市
        for (int i = 0; i < citycount; i++) {//每次都是顺序走。。。。。
            zong += p[i];
        }
        double selectp = r.nextDouble()*zong;

        //轮盘赌选择一个城市；
        for(int i=0;i<citycount;i++){
            sumselect+=p[i];
            if(sumselect>=selectp){
                selectcity=i;
                break;
            }
        }
        if (selectcity==-1)
            System.out.println("Error");;
        tour[index]=selectcity;
        unvisitedcity[selectcity]=1;
    }


    public void CalTourLength(double[][] distance) {
        tourlength = 0;
        tour[citycount] = tour[0];//第一个城市等于最后一个要到达的城市
        for (int i = 0; i < citycount; i++) {
            tourlength += distance[tour[i]][tour[i + 1]];//从A经过每个城市仅一次，最后回到A的总长度。
        }
    }
}
