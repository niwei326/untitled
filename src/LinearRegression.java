import java.io.IOException;

public class LinearRegression {
    public static double [] getLinePara(Double [] [] points ) {
        double dbRt [] =new double [2];
        double dbXSum=0;
        for(int i=0;i<points[0].length;i++) {
            dbXSum=dbXSum+points[0][i];
        }
        double dbXAvg=dbXSum/points[0].length;
        double dbWHeadVal=0;
        for(int i=0;i<points[0].length;i++) {
            dbWHeadVal=dbWHeadVal+(points[0][i]-dbXAvg)*points[1][i];
        }
        double dbWDown=0;
        double dbWDownP=0;
        dbXSum=0;
        for(int i=0;i<points[0].length;i++) {
            dbWDownP=dbWDownP+points[0][i]*points[0][i];
            dbXSum=dbXSum+points[0][i];
        }
        dbWDown=dbWDownP-(dbXSum*dbXSum/points[0].length);
        double dbW=dbWHeadVal/dbWDown;
        dbRt[0]=dbW;
        double dbBSum=0;
        for(int i=0;i<points[0].length;i++) {
            dbBSum=dbBSum+(points[1][i]-dbW*points[0][i]);
        }
        double dbB=dbBSum/points[0].length;
        dbRt[1]=dbB;
        return dbRt;
    }




    public static void main(String[] args) {
        Double [][] arrPoints= {{1.0,2.0,3.0,5.0},{1.0,2.0,3.0,5.0}};
        System.out.println(getLinePara(arrPoints)[0]);
        System.out.println(getLinePara(arrPoints)[1]);
    }
}
