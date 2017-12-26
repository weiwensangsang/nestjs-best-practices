package com.weiwensangsang.web.rest.vm;

import com.weiwensangsang.domain.bike.Location;
import com.weiwensangsang.domain.bike.Path;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.stream.Collectors;

public class TopoVM {

    private List<Node> nodes;

    private List<Link> links;


    private List<Location> locationList;

    private List<Path> paths;

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return "TopoVM{" +
            "locationList=" + locationList +
            ", paths=" + paths +
            '}';
    }

    public static TopoVM create(List<Location> locationList, List<Path> paths) {
        TopoVM data = new TopoVM();
        data.setLocationList(locationList);
        data.setPaths(paths);
        data.setNodes(
            locationList.stream().map(location -> new Node(location.getPositionX())).collect(Collectors.toList())
        );
        data.setLinks(
            paths.stream().map(path -> {
                Link e = new Link();
                e.source =  new Node(path.getFromWhere().getPositionX());
                e.target =  new Node(path.getToWhere().getPositionX());
                return e;
            }).collect(Collectors.toList())
        );
        return data;
    }
}
