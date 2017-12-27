package com.weiwensangsang.web.rest.vm;

public class Node {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Node (Long id) {
        this.id = id;
    }

    public Node () {
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                '}';
    }
}