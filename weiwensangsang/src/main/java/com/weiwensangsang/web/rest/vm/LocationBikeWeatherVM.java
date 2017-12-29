package com.weiwensangsang.web.rest.vm;

import com.weiwensangsang.domain.bike.ElectricBike;
import com.weiwensangsang.domain.bike.LocationElectricBike;

import java.util.List;

public class LocationBikeWeatherVM {

    private List<LocationElectricBike> locationElectricBikes;

    private ElectricBike bike;

    private String weather;

    public List<LocationElectricBike> getLocationElectricBikes() {
        return locationElectricBikes;
    }

    public void setLocationElectricBikes(List<LocationElectricBike> locationElectricBikes) {
        this.locationElectricBikes = locationElectricBikes;
    }

    public ElectricBike getBike() {
        return bike;
    }

    public void setBike(ElectricBike bike) {
        this.bike = bike;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "LocationBikeWeatherVM{" +
            "locationElectricBikes=" + locationElectricBikes +
            ", bike=" + bike +
            ", weather='" + weather + '\'' +
            '}';
    }

    public static LocationBikeWeatherVM create(List<LocationElectricBike> locationElectricBikes, ElectricBike bike, String weather) {
        LocationBikeWeatherVM data = new LocationBikeWeatherVM();
        data.setBike(bike);
        data.setLocationElectricBikes(locationElectricBikes);
        data.setWeather(weather);
        return data;
    }

    public static LocationBikeWeatherVM create(List<LocationElectricBike> locationElectricBikes, String weather) {
        LocationBikeWeatherVM data = new LocationBikeWeatherVM();
        data.setLocationElectricBikes(locationElectricBikes);
        data.setWeather(weather);
        return data;
    }
}
