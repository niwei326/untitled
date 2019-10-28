import java.util.ArrayList;
import java.util.List;

public class FindMaxCollectionInList {


    //整体思路：从可能的最大值入手（即总长的平均值），优先选取最长的钢条，如果钢条和最大值相等，则记录当前值
    //否则，从大到小遍历集合，找到总量=最大值的记录集合
    //  如果找到，返回结果，将原数组去掉已经找到的集合，再迭代整个过程2次
    //如果迭代过程中始终无法找到最大值的组合，则舍弃当前排序最大的元素，重新计算，直到得到结果，或者找不到结果
    public static void main(String[] args) {
        List valueList = new ArrayList();
        valueList.add(6);
        valueList.add(6);
        valueList.add(3);
        valueList.add(2);
        valueList.add(1);
        if(valueList.size()<3){
            System.out.println("已经不可能得到结果了！~~~");
        }
        //todo:计算平均值作为初始最大值
        int sumTarget = 6;
        List selectIndex = new ArrayList<>();
        int i = 0;
        int maxSum = 0;
        maxSum += (int)(valueList.get(i));
        selectIndex.add(i);
        if (maxSum == sumTarget) {
            //输出并返回结果
            System.out.println(selectIndex);
        }
        i++;
        while(i<valueList.size()) {
            int temp = (maxSum+(int)valueList.get(i));
            if (temp == sumTarget) {
                //输出并返回结果
                maxSum +=  (int)valueList.get(i);
                selectIndex.add(i);
                System.out.println(selectIndex);
                System.out.println(maxSum);
                break;
            } else if(temp < sumTarget){
                maxSum += (int)valueList.get(i);
                selectIndex.add(i);
            }
            i++;
        }
        if(maxSum < sumTarget){
            System.out.println("not sumTarget:"+maxSum);
            System.out.println("not sumTarget index:"+selectIndex);
        }else{
            List newValueList = new ArrayList();
            for(int j=0;j<valueList.size();j++){
                if(!selectIndex.contains(j)){
                    newValueList.add((int)valueList.get(j));
                }
            }
            System.out.println("new arrayList"+newValueList);
        }
        //todo 后续迭代还没写出来…………
        //1 舍弃最大的数字，生成新序列
        //2 重新计算平均值作为新的数据集合
    }
}
