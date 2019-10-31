import java.util.ArrayList;
import java.util.List;

public class FindList3 {

    //整体思路：从可能的最大值入手（即总长的平均值），优先选取最长的钢条，如果钢条和最大值相等，则记录当前值
    //否则，从大到小遍历集合，找到总量=最大值的记录集合
    //  如果找到，返回结果，将原数组去掉已经找到的集合，再迭代整个过程2次
    //如果迭代过程中始终无法找到最大值的组合，则舍弃当前排序最大的元素，重新计算，直到得到结果，或者找不到结果
    public static void main(String[] args) {
        List valueList = new ArrayList();
        //从大到小排序增加参数
        valueList.add(6);
        valueList.add(5);
        valueList.add(4);
        valueList.add(3);
        valueList.add(2);
        valueList.add(1);
        //计算平均值作为初始最大值
        int sumTarget = getBarLenght(valueList);;
        System.out.println(sumTarget);
        while(sumTarget >0){
            List tempList = new ArrayList();
            List indexList = (List)((ArrayList) valueList).clone();
            //三个选择列表
            List selectIndex1 = new ArrayList<>();
            List selectIndex2 = new ArrayList<>();
            List selectIndex3 = new ArrayList<>();
            sumTarget = getBarLenght(valueList);
            //当前列表统计值
            int selectSum1 = 0;
            int selectSum2 = 0;
            int selectSum3 = 0;
            //策略：贪心，选择最大的条子，去凑目标平均值
            int firstBar = (int)indexList.remove(0);
            //当第一个条子就已经比算数平均值大，那么这跟条子就能扔了，重新计算平均值
            if(firstBar > sumTarget){
                valueList.remove(0);
                break;
            }
            selectIndex1.add(firstBar);
            selectSum1 += firstBar;
            //尝试从剩余条子中凑出平均值
            while (selectSum1 < sumTarget&&indexList.size()>0) {
                int nextBar = (int)indexList.remove(0);
                int tempSum = selectSum1 + nextBar;
                if(tempSum == sumTarget){
                    selectSum1 = selectSum1 + nextBar;
                    selectIndex1.add(nextBar);
                    tempList.addAll(indexList);
                    break;
                }else{
                    tempList.add(nextBar);
                }
            }
            if(selectSum1!=sumTarget){
                //如果当前值不够大，那么将目标函数平均值调整-1，继续下一个循环
                sumTarget = sumTarget - 1;
                continue;
            }else{
                //继续计算第二根
                indexList = (List)((ArrayList) tempList).clone();
                tempList.clear();
                firstBar = (int)indexList.remove(0);
                selectIndex2.add(firstBar);
                selectSum2 += firstBar;
                //尝试从剩余条子中凑出平均值
                while (selectSum2 < sumTarget&&indexList.size()>0) {
                    int nextBar = (int)indexList.remove(0);
                    int tempSum = selectSum2 + nextBar;
                    if(tempSum == sumTarget){
                        selectSum2 = selectSum2 + nextBar;
                        selectIndex2.add(nextBar);
                        tempList.addAll(indexList);
                        break;
                    }else{
                        tempList.add(nextBar);
                    }
                }
                if(selectSum2!=sumTarget){
                    //如果当前值不够大，那么将目标函数平均值调整-1，继续下一个循环
                    sumTarget = sumTarget - 1;
                    continue;
                }else{
                    //计算最后一根
                    indexList = (List)((ArrayList) tempList).clone();
                    tempList.clear();
                    firstBar = (int)indexList.remove(0);
                    selectIndex3.add(firstBar);
                    selectSum3 += firstBar;
                    //尝试从剩余条子中凑出平均值
                    while (selectSum3 < sumTarget&&indexList.size()>0) {
                        int nextBar = (int)indexList.remove(0);
                        int tempSum = selectSum3 + nextBar;
                        if(tempSum == sumTarget){
                            selectSum3 = selectSum3 + nextBar;
                            selectIndex3.add(nextBar);
                            break;
                        } else{
                            tempList.add(nextBar);
                        }
                    }
                    if(selectSum3!=sumTarget){
                        //如果当前值不够大，那么将目标函数平均值调整-1，继续下一个循环
                        sumTarget = sumTarget - 1;
                        continue;
                    }else{
                        //输出结果
                        System.out.println("第1排"+selectIndex1);
                        System.out.println("第2排"+selectIndex2);
                        System.out.println("第3排"+selectIndex3);
                        break;
                    }
                }

            }
        }
    }

    //算出现有钢条算术平均值
    public static int getBarLenght(List valueList){
        int sum = 0;
        for(int i=0;i<valueList.size();i++){
            sum+= (int)valueList.get(i);
        }
        int bar = sum/3;
        return bar;
    }
}
