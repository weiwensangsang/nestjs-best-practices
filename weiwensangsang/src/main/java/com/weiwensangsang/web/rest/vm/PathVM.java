package com.weiwensangsang.web.rest.vm;

import com.weiwensangsang.domain.bike.Location;

import java.util.ArrayList;
import java.util.List;

public class PathVM {
    private List<Long> primary;

    private List<Location> primaryLocations;

    private List<Link> primaryLinks;

    private Long sum;

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public List<Location> getPrimaryLocations() {
        return primaryLocations;
    }

    public void setPrimaryLocations(List<Location> primaryLocations) {
        this.primaryLocations = primaryLocations;
    }

    public List<Link> getPrimaryLinks() {
        return primaryLinks;
    }

    public void setPrimaryLinks(List<Link> primaryLinks) {
        this.primaryLinks = primaryLinks;
    }

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

    public static PathVM create(List<Long> primary, List<Long> second){
        PathVM data = new PathVM();
        data.second = second;
        data.primary = primary;
        return data;
    }

    @Override
    public String toString() {
        return "PathVM{" +
            "primary=" + primary +
            ", primaryLocations=" + primaryLocations +
            ", primaryLinks=" + primaryLinks +
            ", sum=" + sum +
            ", second=" + second +
            '}';
    }

    public static PathVM create(List<Long> primary, List<Location> primaryLocation , List<Link> links, Long sum){
        PathVM data = new PathVM();
        data.primary = primary;
        data.setPrimaryLocations(primaryLocation);
        data.primaryLinks = links;
        data.sum = sum;
        return data;
    }
}
