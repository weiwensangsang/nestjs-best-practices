package com.weiwensangsang.web.rest.vm;

public class Link {
    private Node source;
    private Node target;

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public Link(Node source, Node target) {
        this.setSource(source);
        this.setTarget(target);
    }

    public Link() {
    }

    @Override
    public String toString() {
        return "Link{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }
}