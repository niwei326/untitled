import java.io.IOException;
import java.util.*;

public class ID3DecisionTree {
    public static void main(String[] args) throws IOException {
        System.out.println("test12312312313");
        int[] entDset = {8,9};
        double entD = entD(entDset);
        System.out.println("entD:"+entD);
        int[] entDset1 = {3,3};
        double entD1 = entD(entDset1);
        System.out.println("entD1:"+ entD1);
        int[] entDset2 = {4,2};
        double entD2 = entD(entDset2);
        System.out.println("entD2:"+entD2);
        int[] entDset3 = {1,4};
        double entD3 = entD(entDset3);
        System.out.println("entD3:"+ entD3);
        double[] entDsets = {entD1,entD2,entD3};
        int[] dSampleCount = {6,6,5};
        double gainD = gain(entD,entDsets, dSampleCount);
        System.out.println("gainD:"+ gainD);
        //训练集合来自网页https://www.cnblogs.com/gfgwxw/p/9439482.html，可以再拿其他id3的例子测试下～～
        String[] headers = {"信用","收入","年龄","工作性质"};
        String[][] keys = {
                {"N","L","H","S"},//1
                {"N","L","H","N"},//2
                {"G","L","H","S"},//3
                {"B","N","H","S"},//4
                {"B","H","N","S"},//5
                {"B","H","N","N"},//6
                {"G","H","N","N"},//7
                {"N","N","H","S"},//8
                {"N","H","N","S"},//9
                {"B","N","N","S"},//10
                {"N","N","N","N"},//11
                {"G","N","H","N"},//12
                {"G","L","N","S"},//13
                {"B","N","H","N"}//14
        };
        String[] results = {"F","F","T","T","T","F","T","F","T","T"
                ,"T","T","T","F"};
        List<Data> testList =generateDataList(headers, keys,results);
        System.out.println(testList);
        //double gainMethodTest = Calculate.gain(testList, "testA", entD);
        HashSet<String> sets = new HashSet<>();
        sets.add("信用");
        sets.add("收入");
        sets.add("年龄");
        sets.add("工作性质");
        Map resultMap = Calculate.gain(testList,sets);
        //System.out.println(gainMethodTest);
        System.out.println(resultMap);
        System.out.println(Calculate.getBestGainAttr(resultMap));
        //测试buildTree
        TreeNode treenode = buildTree(testList,sets);
        System.out.println(treenode);
    }

    public static List<Data>  generateDataList(String[] headers, String[][] keys,String[] results){
        List<Data> dataList = new ArrayList<>();
        for(int i=0;i<results.length;i++){
            Data data = new Data();
            data.setResult(results[i]);
            for( int j=0;j<headers.length;j++){
                data.putValue(headers[j], keys[i][j]);
            }
            dataList.add(data);
        }
        return dataList;
    }

    /**
     * 构建树
     * @param dataList 数据集合
     * @param propertySet 候选属性集合
     * @return 树节点
     */
    public static TreeNode buildTree(List<Data> dataList, Set<String> propertySet){
        System.out.print("候选属性列表:"+propertySet.toString());
        TreeNode node = new TreeNode();
        node.setDatas(dataList);
        node.setCandAttr(propertySet);
        HashSet<String> resultSet = new HashSet<>();
        String maxResult = Calculate.getMaxResultFromDataList(dataList, resultSet);
        System.out.println("最大结果:"+maxResult);// #当前集合数量最大的结果
        System.out.println("结果类型个数"+resultSet.size());
        if (resultSet.size() == 1 || propertySet.size() == 1) {
            node.setPropertyName(maxResult);
            System.out.println("生成节点特征结果：" + maxResult);// #
            return node;
        }
        Map gainResultMap = Calculate.gain(dataList, propertySet);
        String bestGainProperty = Calculate.getBestGainAttr(gainResultMap);
        System.out.println("最佳分类特征索引为" + bestGainProperty);
        Set<String> bestPropValueSet = findPropValueSetByBestAttr(dataList, bestGainProperty);
        node.setPropertyName(bestGainProperty);
        //设置新的属性集集合，不包含最好的属性
        Set<String> newPropertySet = new HashSet<>();
        for(String prop : propertySet){
            if(!prop.equals(bestGainProperty)){
                newPropertySet.add(prop);
            }
        }
        System.out.println("剩余分类特征为" + newPropertySet);// #
        //设置新的数据集合
        for (String propValue:bestPropValueSet) {
            //获取当前值的新的数据集合
            List<Data> di = getNewDataListByPropValue(dataList, bestGainProperty, propValue);
            //终止条件:新的数据集合为空，或者剩余特征为空，返回最大分类结果
            if (di.size() == 0 || newPropertySet.size() == 0) {
                TreeNode leafNode = new TreeNode();
                leafNode.setPropertyName(maxResult);
                leafNode.setDatas(di);
                leafNode.setCandAttr(newPropertySet);
                node.getChildNodeMap().put(propValue, leafNode);
                System.out.println("生成节点特征"+propValue+",结果：" + maxResult);// #
            } else {
                //递归加入子节点
                TreeNode newNode = buildTree(di, newPropertySet);
                node.getChildNodeMap().put(propValue, newNode);
                System.out.println("剩余分类特征递归" + newPropertySet);// #
            }
        }
        return node;
    }

    public static List<Data> getNewDataListByPropValue(List<Data> dataList, String bestGainProperty, String propValue){
        ArrayList<Data> newDataList = new ArrayList();
        for(Data data:dataList){
            if(propValue.equals(data.getValue(bestGainProperty))){
                Data newData = (Data)data.clone();
                //去掉已分类的属性
                newData.putValue(bestGainProperty, null);
                newDataList.add(newData);
            }
        }
        return newDataList;
    }

    public static Set<String> findPropValueSetByBestAttr(List<Data> dataList, String bestGainProperty){
        Set<String> propValueSet = new HashSet<>();
        for(Data data:dataList){
            propValueSet.add(data.getValue(bestGainProperty));
        }
        return propValueSet;
    }

    public static double entD(int[] sampleCounts){
        double result = 0.0;
        double sum = 0.0;
        for(int samlpleCount : sampleCounts){
            sum += samlpleCount;
        }
        for(int samlpleCount : sampleCounts){
            //没有log(2)的api，所以用ln/log(2)计算
            double pi = samlpleCount/sum;
            result += pi *(Math.log(pi)/Math.log(2.0));
        }
        return -1*result;
    }

    public static double gain(double entD,double[] entDi,int[] sampleCountDi){
        double result = entD;
        double sigmaEnt = 0.0;
        double sum = 0.0;
        for(int samlpleCount : sampleCountDi){
            sum += samlpleCount;
        }
        for(int i=0;i < sampleCountDi.length; i++){
            double pi = sampleCountDi[i]/sum;
            sigmaEnt += pi * entDi[i];
        }
        return result - sigmaEnt;
    }



    /**
     *  计算类，提供各种计算结果
     */
    static class Calculate{
        public static double ent(List<Data> dataList){
            Set<String> resultSet = new HashSet<>();
            HashMap<String, Integer> countMap = getCountResultMap(dataList, resultSet);
            double result = 0.0;
            double sum = dataList.size();
            for(String resultStr : resultSet){
                //没有log(2)的api，所以用ln/log(2)计算
                Integer samlpleCount = countMap.get(resultStr);
                if(samlpleCount!=null) {
                    double pi = samlpleCount / sum;
                    result += pi * (Math.log(pi) / Math.log(2.0));
                }
            }
            return -1*result;
        }

        public static HashMap<String, Integer> getCountResultMap(List<Data> dataList, Set<String> resultSet) {
            //统计数据中不同结果的计数
            HashMap<String,Integer> countMap = new HashMap();
            for (Data data:dataList) {
                if(countMap.containsKey(data.getResult())){
                    int newCount = countMap.get(data.getResult()) + 1;
                    countMap.put(data.getResult(), newCount);
                }else{
                    resultSet.add(data.getResult());
                    countMap.put(data.getResult(), 1);
                }
            }
            return countMap;
        }

        public static String getMaxResultFromDataList(List<Data> dataList, Set<String> resultSet){
            Map<String,Integer> result = getCountResultMap(dataList, resultSet);
            String maxProperty = null;
            Integer maxCount = 0;
            for(Map.Entry<String,Integer> entry:result.entrySet()){
                if(maxCount.intValue() < entry.getValue().intValue()){
                    maxProperty = entry.getKey();
                    maxCount = entry.getValue();
                }
            }
            return maxProperty;
        }

        public static Map<String, Double> gain(List<Data> dataList, Set<String> propertySet){
            double entD = ent(dataList);
            HashMap<String,Double> resultMap = new HashMap<>();
            for(String property: propertySet){
                resultMap.put(property, gain(dataList, property, entD));
            }
            return resultMap;
        }

        public static String getBestGainAttr(Map<String, Double> gainResult){
            String tempKey = null;
            double tempResult = 0.0;
            for(Map.Entry<String, Double> entry : gainResult.entrySet()){
                if(entry.getValue() > tempResult){
                    tempResult = entry.getValue();
                    tempKey = entry.getKey();
                }
            }
            return tempKey;
        }

        public static double gain(List<Data> dataList, String property, double entD){
            double result = entD;
            double sigmaEnt = 0.0;
            double sum = dataList.size();
            //根据属性值拆分数据
            HashMap<String, List<Data>> propertyListData = new HashMap();
            for(Data data: dataList){
                List<Data> newDataList;
                String propertyValue = data.getValue(property);
                Data newData = (Data)data.clone();
                 if(propertyListData.containsKey(propertyValue)) {
                     newDataList = propertyListData.get(propertyValue);
                     newDataList.add(newData);
                     //propertyListData.put(property,newDataList);
                 }else {
                     newDataList = new ArrayList();
                     newDataList.add(newData);
                     propertyListData.put(propertyValue,newDataList);
                 }
            }
            //计算每个属性值的gain
            for(Map.Entry<String, List<Data>> entry:propertyListData.entrySet()){
                double sampleSize = entry.getValue().size();
                double ent = ent(entry.getValue());
                double pi = sampleSize/sum;
                sigmaEnt += pi * ent;
            }
            return entD - sigmaEnt;
        }


    }



}
