package dlmu.edu.original;

import java.util.Random;

/**
 * Created by lenovo on 2017-03-16.
 */
public class Ant {
    /**
     * 蚂蚁获得的路径
     */
    public int[]tour;
    /**
     * 访问城市记录，1表示没有访问过，0表示访问过
     */
    int[] unVisitedCity;//48个数组
    /**
     * 蚂蚁获得的路径长度
     */
    public int tourLength;
    /**
     * 城市数量
     */
    int citys;
    /**
     * 随机分配蚂蚁到某个城市中
     * 同时完成蚂蚁包含字段的初始化工作
     * @param cityCount 总的城市数量
     */
    public void RandomSelectCity(int cityCount){
        citys=cityCount;
        unVisitedCity =new int[cityCount];//初始化后，都是1
        tour=new int[cityCount+1];//初始化后，前面都是-1，tour[48]=0
        tourLength =0;
        for(int i=0;i<cityCount;i++){
            tour[i]=-1;
            unVisitedCity[i]=1;
        }
        long r1 = System.currentTimeMillis();

        Random rnd=new Random(r1);
        int firstCity=rnd.nextInt(cityCount);
        unVisitedCity[firstCity]=0;
        tour[0]=firstCity;
    }
    /**
     * 选择下一个城市
     * @param index 需要选择第index个城市了
     * @param tao   全局的信息素信息
     * @param distance  全局的距离矩阵信息
     */
    public void SelectNextCity(int index,double[][]tao,int[][]distance){
        double []p;
        p=new double[citys];//概率
        double alpha=1.0;
        double beta=2.0;
        double sum=0;
        int currentcity=tour[index-1];
        //计算公式中的分母部分
        for(int i=0;i<citys;i++){
            if(unVisitedCity[i]==1)
                sum+=(Math.pow(tao[currentcity][i], alpha)*
                        Math.pow(1.0/distance[currentcity][i], beta));
        }
        //计算每个城市被选中的概率
        for(int i=0;i<citys;i++){
            if(unVisitedCity[i]==0)
                p[i]=0.0;
            else{
                p[i]=(Math.pow(tao[currentcity][i], alpha)*
                        Math.pow(1.0/distance[currentcity][i], beta))/sum;
            }
        }
        long r1 = System.currentTimeMillis();
        Random rnd=new Random(r1);
        double selectp=rnd.nextDouble();
        //轮盘赌选择一个城市；
        double sumselect=0;
        int selectcity=-1;
        for(int i=0;i<citys;i++){
            sumselect+=p[i];
            if(sumselect>=selectp){
                selectcity=i;
                break;
            }
        }
        if (selectcity==-1)
            System.out.println();
        tour[index]=selectcity;
        unVisitedCity[selectcity]=0;
    }
    /**
     * 计算蚂蚁获得的路径的长度
     * @param distance  全局的距离矩阵信息
     */
    public void CalTourLength(int [][]distance){
        tourLength =0;
        tour[citys]=tour[0];
        for(int i=0;i<citys;i++){
            tourLength +=distance[tour[i]][tour[i+1]];
        }
    }

}
