import java.util.*;

public class TreeNode{
    private String propertyName; // 节点名（分裂属性的名称）
    private Map<String, TreeNode> childNodeMap = new HashMap(); // 子结点集合,key为分裂属性的属性值，值为子树节点
    private List<Data> datas = new ArrayList<>(); // 划分到该结点的训练元组
    private Set<String> candAttr = new HashSet<>(); // 划分到该结点的候选属性名称

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Map<String, TreeNode> getChildNodeMap() {
        return childNodeMap;
    }

    public void setChildNodeMap(Map<String, TreeNode> childNodeMap) {
        this.childNodeMap = childNodeMap;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public Set<String> getCandAttr() {
        return candAttr;
    }

    public void setCandAttr(Set<String> candAttr) {
        this.candAttr = candAttr;
    }


}