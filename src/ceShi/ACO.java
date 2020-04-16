package ceShi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ACO {
    Ant[] ants; //定义蚂蚁群
    int antcount;//蚂蚁的数量
    double [][]distance;//表示城市间距离
    double [][]tao;//信息素矩阵
    int citycount;//城市数量
    int []besttour;//求解的最佳路径
    double  bestlength=Integer.MAX_VALUE;;//求的最优解的长度
    public void init(String filename, int antnum) throws IOException {
        int[] x;
        int[] y;
        BufferedReader tspdata = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String strbuff = tspdata.readLine();//读取第一行，城市总数（按文件格式读取）
        citycount = Integer.valueOf(strbuff);
        distance = new double[citycount][citycount];
        x = new int[citycount];
        y = new int[citycount];
        for (int citys = 0; citys < citycount; citys++) {
            strbuff = tspdata.readLine();
            String[] strcol = strbuff.split(" ");
            x[citys] = Integer.valueOf(strcol[1]);//读取每排数据的第2二个数字即横坐标
            y[citys] = Integer.valueOf(strcol[2]);//读取每排数据的第3三个数字即纵坐标
            //capacitys[citys]=Integer.valueOf(strcol[3]);//读取每排数据的第3二个数字即纵坐标
        }
        //计算两个城市之间的距离矩阵，并更新距离矩阵
        for (int city1 = 0; city1 < citycount - 1; city1++) {
            distance[city1][city1] = 0;
            for (int city2 = city1 + 1; city2 < citycount; city2++) {
                distance[city1][city2] = (Math.sqrt((x[city1] - x[city2]) * (x[city1] - x[city2])
                        + (y[city1] - y[city2]) * (y[city1] - y[city2])));
                distance[city2][city1] = distance[city1][city2];//距离矩阵是对称矩阵
            }
        }
        distance[citycount - 1][citycount - 1] = 0;
        //输出距离矩阵
//        System.out.println("距离矩阵");
//        for (int city1 = 0; city1 < citycount ; city1++) {
//            for (int city2 = 0; city2 < citycount; city2++) {
//                System.out.print(String.format("%.2f", distance[city1][city2]) +"  ");//距离矩阵是对称矩阵
//            }
//            System.out.println();
//        }

        TxTsp ts = new TxTsp(citycount);
        ts.init("att48.txt",distance);
        //ts.printinit();
        double tanlan=ts.solve();

        antcount=citycount;
        //初始化信息素矩阵
        tao=new double[citycount][citycount];
        for(int i=0;i<citycount;i++)
        {
            for(int j=0;j<citycount;j++){
                tao[i][j]=antcount/tanlan;
            }
        }



        ants=new Ant[antnum];
        //初始化蚂蚁位置
        for(int i=0;i<antcount;i++){
            ants[i]=new Ant();
            ants[i].init(citycount);
        }
        besttour=new int[citycount+1];




    }


    public void run(int number){
        for (int i=0;i<number;i++) {//次数循环
            //蚂蚁循环
            for (int j = 0; j < antcount; j++) {
                for (int k = 1; k < antcount; k++) {//遍历城市
                    ants[j].selectcitys(k, tao, distance);
                }

                ants[j].CalTourLength(distance);

                if(ants[j].tourlength<bestlength){
                    //保留最优路径
                    bestlength= ants[j].tourlength;
                    //runtimes仅代表最大循环次数，但是只有当，有新的最优路径的时候才会显示下列语句。
                    //如果后续没有更优解（收敛），则最后直接输出。
                    System.out.println("第"+i+"代(次迭代)，发现新的最优路径长度："+bestlength);
                    for(int t=0;t<citycount+1;t++) {
                        System.out.print(ants[i].tour[t]+"  ");
                        besttour[t] = ants[i].tour[t];//更新路径
                    }
                    System.out.println();
                }
            }
            //更新信息素矩阵
            UpdateTao();
            //重新随机设置蚂蚁
            for(int k=0;k<antcount;k++){
                ants[k].init(citycount);
            }
            }
        }

    private void UpdateTao(){
        double rou=0.5;
        //信息素挥发
        for(int i=0;i<citycount;i++)
            for(int j=0;j<citycount;j++)
                tao[i][j]=tao[i][j]*(1-rou);
        //信息素更新
        for(int i=0;i<antcount;i++){
            for(int j=0;j<citycount;j++){
                tao[ants[i].tour[j]][ants[i].tour[j+1]]+=1.0/ ants[i].tourlength;
            }
        }
    }


    public void ReportResult(){
        System.out.println("最优路径长度是"+bestlength);
        System.out.println("蚁群算法最优路径输出：");
        for(int j=0;j<citycount+1;j++) {
            if (j % 9 == 8) {
                System.out.println();
            }
            System.out.print(besttour[j] + 1 + ">>");//输出最优路径

        }
    }
}

