package dlmu.edu.original;

import java.io.*;

/**
 * Created by lenovo on 2017-03-16.
 */
public class ACO {
    //定义蚂蚁群
    Ant[]ants;
    int antCount;//蚂蚁的数量，传入参数
    int [][]distance;//表示城市间距离矩阵，数据第二、三列求得
    double [][]tao;//信息素矩阵，初始化时，都是0.1
    int cityCount;//城市数量，数据第一行
    int[] bestTour;//求解的最佳路径
    int bestlength;//求的最优解的长度，初始化是最大整数，2的31次方-1 的常量
    /** 初始化函数
     *@param fileName tsp数据文件
     *@param antNum 系统用到蚂蚁的数量
     */
    public void init(String fileName,int antNum) throws FileNotFoundException, IOException {
        antCount =antNum;
        ants=new Ant[antCount];
        //读取数据
        int[] x;
        int[] y;
        String strBuff;
        BufferedReader tspdata = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        strBuff = tspdata.readLine();
        cityCount = Integer.valueOf(strBuff);//城市数量是数据的第一行
        distance = new int[cityCount][cityCount];
        x = new int[cityCount];//第二列
        y = new int[cityCount];//第三列
        for (int citys = 0; citys < cityCount; citys++) {
            strBuff = tspdata.readLine();
            String[] strcol = strBuff.split(" ");
            x[citys] = Integer.valueOf(strcol[1]);
            y[citys] = Integer.valueOf(strcol[2]);
        }
        //计算距离矩阵
        for (int city1 = 0; city1 < cityCount - 1; city1++) {
            distance[city1][city1] = 0;
            for (int city2 = city1 + 1; city2 < cityCount; city2++) {
                distance[city1][city2] = (int) (Math.sqrt((x[city1] - x[city2]) * (x[city1] - x[city2])
                        + (y[city1] - y[city2]) * (y[city1] - y[city2])) + 0.5);
                distance[city2][city1] = distance[city1][city2];
            }
        }
        distance[cityCount - 1][cityCount - 1] = 0;
        //初始化信息素矩阵
        tao=new double[cityCount][cityCount];
        for(int i = 0; i< cityCount; i++)
        {
            for(int j = 0; j< cityCount; j++){
                tao[i][j]=0.1;
            }
        }
        bestlength=Integer.MAX_VALUE;
        bestTour =new int[cityCount +1];
        //随机放置50只（antCount）蚂蚁，第i个蚂蚁随机选择城市
        System.out.println("bestlength"+bestlength);
        for(int i = 0; i< antCount; i++){
            ants[i]=new Ant();
            ants[i].RandomSelectCity(cityCount);
        }
    }
    /**
     * ACO的运行过程
     * @param maxgen ACO的最多循环次数
     *
     */
    public void run(int maxgen){
        for(int runtimes=0;runtimes<maxgen;runtimes++){
            //每一只蚂蚁跑完全程的移动的过程
            for(int i = 0; i< antCount; i++){
                for(int j = 1; j< cityCount; j++){
                    ants[i].SelectNextCity(j,tao,distance);
                }
                //计算蚂蚁获得的路径长度
                ants[i].CalTourLength(distance);
                if(ants[i].tourLength <bestlength){
                    //保留最优路径
                    bestlength=ants[i].tourLength;
                    System.out.println("第"+runtimes+"代，发现新的解"+bestlength);
                    for(int j = 0; j< cityCount +1; j++)
                        bestTour[j]=ants[i].tour[j];
                }
            }
            //更新信息素矩阵
            UpdateTao();
            //重新随机设置蚂蚁
            for(int i = 0; i< antCount; i++){
                ants[i].RandomSelectCity(cityCount);
            }
        }
    }
    /**
     * 更新信息素矩阵
     */
    private void UpdateTao(){
        double rou=0.5;
        //信息素挥发
        for(int i = 0; i< cityCount; i++)
            for(int j = 0; j< cityCount; j++)
                tao[i][j]=tao[i][j]*(1-rou);
        //信息素更新
        for(int i = 0; i< antCount; i++){
            for(int j = 0; j< cityCount; j++){
                tao[ants[i].tour[j]][ants[i].tour[j+1]]+=1.0/ants[i].tourLength;
            }
        }
    }
    /**
     * 输出程序运行结果
     */
    public void ReportResult(){
        System.out.println("最优路径长度是"+bestlength);
        System.out.print("最优路径是:");
        for (int each:
             bestTour) {
            System.out.print(each+"  ");
        }
        System.out.println();
    }

}
