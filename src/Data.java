import java.util.HashMap;
import java.util.List;

public class Data implements Cloneable {
    private String result = "false";
    private HashMap<String, String> attrMap = new HashMap<>();

//    public Data(List<String> headerList, List<String> attrList, List<String> resultList){
//
//    }

    public void putValue(String key, String value) {
        this.attrMap.put(key, value);
    }


    public String getValue(String key) {
        return attrMap.get(key);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public HashMap<String, String> getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(HashMap<String, String> attrMap) {
        this.attrMap = attrMap;
    }

    @Override
    protected Object clone() {
        Data newData = new Data();
        String resultNewData = new String(this.getResult());
        newData.setResult(resultNewData);
        HashMap properyMap = (HashMap) this.getAttrMap().clone();
        //properyMap.remove(property);
        newData.setAttrMap(properyMap);
        return newData;
    }

    @Override
    public String toString(){
        return "result :["+result +"] map["+attrMap.toString()+"]";
    }
}