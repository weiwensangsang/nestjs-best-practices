package com.weiwensangsang.web.rest.vm;

import java.util.List;

public class PathVM {
    private List<Long> primary;

    private List<Long> second;

    public List<Long> getPrimary() {
        return primary;
    }

    public void setPrimary(List<Long> primary) {
        this.primary = primary;
    }

    public List<Long> getSecond() {
        return second;
    }

    public void setSecond(List<Long> second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "PathVM{" +
            "primary=" + primary +
            ", second=" + second +
            '}';
    }

    public static PathVM create(List<Long> primary, List<Long> second){
        PathVM data = new PathVM();
        data.second = second;
        data.primary = primary;
        return data;
    }

    public static PathVM create(List<Long> primary){
        PathVM data = new PathVM();
        data.primary = primary;
        return data;
    }
}
