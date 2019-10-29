import java.util.ArrayList;
import java.util.List;

public class Stock {
    public static void main(String args[]) {
        //int[] prices = new int[]{7, 1, 5, 3, 6, 4, 3, 4, 5, 1, 4};
        int[] prices = new int[]{3,2,6,5,0,3};
        int max = maxProfitWhenK1(prices);
        System.out.println(max);
        int[][] tempResult2 = new int[2][];
        List resultList2 = new ArrayList();
        spiltAarrayWithIndex(prices,2, tempResult2, resultList2);
        int k = 2;
        System.out.println(resultList2);
        max = getMax(max, k, resultList2);
        System.out.println(max);
        int[][] tempResult = new int[3][];
        List resultList = new ArrayList();
        spiltAarrayWithIndex(prices,3, tempResult, resultList);
        k = 3;
        max = getMax(max, k, resultList);
        System.out.println(resultList);
        System.out.println(max);
    }

    private static int getMax(int max, int k, List resultList) {
        for (Object o:resultList) {
            int[][] result = (int[][])o;
            int thisMax = 0;
            for(int i=0;i<k;i++){
                thisMax += maxProfitWhenK1(result[i]);
            }
            max = Math.max(max, thisMax);
        }
        return max;
    }

    //从一个数组中找到最大的，固定算法，见leetcode
    public static int maxProfitWhenK1(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int min = prices[0];
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            max = Math.max(max, prices[i] - min);
            min = Math.min(min, prices[i]);
        }
        return max;
    }

    //处理数组分裂问题，这样就能把k>1的问题分解为若干个k=1的问题
    public static void spiltAarrayWithIndex(int[] prices, int k, int[][] tempResult, List resultList){
        if(prices.length<2*k){
            System.out.println("need Not calculate" );
            return;
        }
        if(k<=1){
            tempResult[0] = prices;
            resultList.add(tempResult.clone());
            System.out.println("return "+outPutArray(prices));
            //返回点
            return;
        }else{
            int l = k-1;
            //没必要对<2的index进行处理
            //{0,1,2,3,4,5,6,7,8},len=9,stop at 6
            for(int firstIndex = 1;firstIndex<prices.length - 2;firstIndex++){
                //生成第一部分数组
                int[] firstPart = new int[firstIndex+1];
                for(int j=0;j<=firstIndex;j++) {
                    firstPart[j] = prices[j];
                }
                tempResult[l] = firstPart;
                System.out.println("when k = "+k+"firstIndex:"+ firstIndex +" first part" + outPutArray(firstPart));
                //递归产生第二条数组
                int[] secondPart = new int[prices.length-firstIndex-1];
                for(int n = 0;n<prices.length-firstIndex-1;n++) {
                    secondPart[n] = prices[firstIndex+1+n];
                }
                System.out.println("when k = "+k+"firstIndex:"+  firstIndex +" second part to split" + outPutArray(secondPart));
                spiltAarrayWithIndex(secondPart , l, tempResult, resultList);
            }
        }
    }

    public static String outPutArray(int[] array){
        StringBuilder sb = new StringBuilder("[");
        for(int a:array) {
            sb.append(a).append(",");
        }
        sb.append("]");
        return sb.toString();
    }


}
