package com.weiwensangsang.domain.bike;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A LocationElectricBike.
 */
@Entity
@Table(name = "location_electric_bike")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LocationElectricBike implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "state")
    private String state;

    @Column(name = "created_date")
    private Instant createDate;

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    @ManyToOne
    private ElectricBike electricBike;

    @ManyToOne
    private Location location;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ElectricBike getElectricBike() {
        return electricBike;
    }

    public void setElectricBike(ElectricBike electricBike) {
        this.electricBike = electricBike;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
        LocationElectricBike locationElectricBike = (LocationElectricBike) o;
        if (locationElectricBike.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locationElectricBike.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocationElectricBike{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }

    private LocationElectricBike() {
        createDate = Instant.now();
    }

    public static LocationElectricBike create(ElectricBike electricBike, Location location) {
        LocationElectricBike relation = new LocationElectricBike();
        relation.setLocation(location);
        relation.setElectricBike(electricBike);
        return relation;
    }
}
