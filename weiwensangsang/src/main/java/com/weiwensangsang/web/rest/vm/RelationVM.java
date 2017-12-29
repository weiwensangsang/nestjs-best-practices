package com.weiwensangsang.web.rest.vm;

import com.weiwensangsang.domain.bike.ElectricBike;
import com.weiwensangsang.domain.bike.Location;

import java.util.List;

/**
 * Created by xiazhen on 2017/12/28.
 */
public class RelationVM {

    private Location location;

    private List<ElectricBike> bikes;

    private String weather;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<ElectricBike> getBikes() {
        return bikes;
    }

    public void setBikes(List<ElectricBike> bikes) {
        this.bikes = bikes;
    }

    public String getWeather() {
        return weather;
    }

    @Override
    public String toString() {
        return "RelationVM{" +
                "location=" + location +
                ", bikes=" + bikes +
                ", weather='" + weather + '\'' +
                '}';
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public static RelationVM create(Location location, List<ElectricBike> bikes) {
        RelationVM vm = new RelationVM();
        vm.setLocation(location);
        vm.setBikes(bikes);
        return vm;
    }

    public static RelationVM create(Location location, List<ElectricBike> bikes, String weather) {
        RelationVM vm = new RelationVM();
        vm.setLocation(location);
        vm.setBikes(bikes);
        vm.setWeather(weather);
        return vm;
    }
}
