package com.weiwensangsang.domain.bike;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_x")
    private Long positionX;

    @Column(name = "position_y")
    private Long positionY;

    @Column(name = "city")
    private String city;

    @Column(name = "e_bike_number")
    private Long eBikeNumber;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "state")
    private String state;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPositionX() {
        return positionX;
    }

    public void setPositionX(Long positionX) {
        this.positionX = positionX;
    }

    public Long getPositionY() {
        return positionY;
    }

    public void setPositionY(Long positionY) {
        this.positionY = positionY;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long geteBikeNumber() {
        return eBikeNumber;
    }

    public void seteBikeNumber(Long eBikeNumber) {
        this.eBikeNumber = eBikeNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + id +
            ", positionX=" + positionX +
            ", positionY=" + positionY +
            ", city='" + city + '\'' +
            ", eBikeNumber=" + eBikeNumber +
            ", type='" + type + '\'' +
            ", state='" + state + '\'' +

            '}';
    }

    private Location() {
        eBikeNumber = 0L;
    }

    public static Location create(Long x, Long y) {
        Location l = new Location();
        l.setPositionX(x);
        l.setPositionY(y);
        return l;
    }

    public static Location create(Long x) {
        Location l = new Location();
        l.setPositionX(x);
        l.setPositionY(x);
        return l;
    }

    public static List<Location> init() {
        List<Location> locations = new ArrayList<Location>();
        for(int i = 0; i<= 2; i++) {
            Location l = new Location();
            l.setPositionX(Integer.toUnsignedLong(i));
            locations.add(l);
        }
        return locations;
    }

}
