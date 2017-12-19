package com.weiwensangsang.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ElectricBike.
 */
@Entity
@Table(name = "electric_bike")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ElectricBike implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bike_order")
    private Long bikeOrder;

    @Column(name = "name")
    private String name;

    @Column(name = "oil")
    private Long oil;

    @Column(name = "distance")
    private Long distance;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "state")
    private String state;

    @Column(name = "information")
    private String information;

    @Column(name = "integrity")
    private Long integrity;

    @Column(name = "sum_path")
    private String sumPath;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBikeOrder() {
        return bikeOrder;
    }

    public void setBikeOrder(Long bikeOrder) {
        this.bikeOrder = bikeOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOil() {
        return oil;
    }

    public void setOil(Long oil) {
        this.oil = oil;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Long getIntegrity() {
        return integrity;
    }

    public void setIntegrity(Long integrity) {
        this.integrity = integrity;
    }

    public String getSumPath() {
        return sumPath;
    }

    public void setSumPath(String sumPath) {
        this.sumPath = sumPath;
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
        ElectricBike electricBike = (ElectricBike) o;
        if (electricBike.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), electricBike.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ElectricBike{" +
            "id=" + getId() +
            ", bikeOrder='" + getBikeOrder() + "'" +
            ", name='" + getName() + "'" +
            ", oil='" + getOil() + "'" +
            ", distance='" + getDistance() + "'" +
            ", type='" + getType() + "'" +
            ", state='" + getState() + "'" +
            ", information='" + getInformation() + "'" +
            ", integrity='" + getIntegrity() + "'" +
            ", sumPath='" + getSumPath() + "'" +
            "}";
    }
}
