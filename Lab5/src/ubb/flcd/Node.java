package ubb.flcd;

public class Node {
    private Integer parent;
    private String value;
    private Integer index;
    private Integer sibling;
    private Boolean hasRight;

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSibling() {
        return sibling;
    }

    public void setSibling(Integer sibling) {
        this.sibling = sibling;
    }

    public Boolean getHasRight() {
        return hasRight;
    }

    public void setHasRight(Boolean hasRight) {
        this.hasRight = hasRight;
    }
}
