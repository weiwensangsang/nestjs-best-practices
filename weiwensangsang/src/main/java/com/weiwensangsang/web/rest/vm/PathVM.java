package com.weiwensangsang.web.rest.vm;

import com.weiwensangsang.domain.bike.Location;

import java.util.ArrayList;
import java.util.List;

public class PathVM {
    private List<Long> primary;

    private List<Location> primaryLocations;

    private List<Link> primaryLinks;

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

    @Override
    public String toString() {
        return "PathVM{" +
                "primary=" + primary +
                ", primaryLocations=" + primaryLocations +
                ", primaryLinks=" + primaryLinks +
                ", second=" + second +
                '}';
    }

    public static PathVM create(List<Long> primary, List<Long> second){
        PathVM data = new PathVM();
        data.second = second;
        data.primary = primary;
        return data;
    }

    public static PathVM createPrimary(List<Long> primary, List<Location> primaryLocation){
        PathVM data = new PathVM();
        data.primary = primary;
        data.setPrimaryLocations(primaryLocation);
        List<Link> links = new ArrayList<Link>();
        if (primary.size() >= 2) {
            for (int i = 0; i<= primary.size() - 2; i++) {
                links.add(new Link(primary.get(i), primary.get(i+1)));
            }
        }
        data.setPrimaryLinks(links);
        return data;
    }
}
