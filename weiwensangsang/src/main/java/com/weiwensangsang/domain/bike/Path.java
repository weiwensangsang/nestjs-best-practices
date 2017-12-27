package com.weiwensangsang.domain.bike;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A Path.
 */
@Entity
@Table(name = "path")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Path implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "state")
    private String state;

    @Column(name = "length")
    private Long length;

    @ManyToOne
    private Location fromWhere;

    @ManyToOne
    private Location toWhere;

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

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Location getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(Location location) {
        this.fromWhere = location;
    }

    public Location getToWhere() {
        return toWhere;
    }

    public void setToWhere(Location location) {
        this.toWhere = location;
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
        Path path = (Path) o;
        if (path.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), path.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Path{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", state='" + getState() + "'" +
            ", length='" + getLength() + "'" +
            "}";
    }

    public static List<Path> init(List<Location> locations) {
        List<Path> paths = new ArrayList<Path>();
        Path l1 = new Path();
        l1.setFromWhere(locations.get(0));
        l1.setToWhere(locations.get(1));
        paths.add(l1);
        Path l2 = new Path();
        l2.setFromWhere(locations.get(1));
        l2.setToWhere(locations.get(2));
        paths.add(l2);
        return paths;
    }

    public static Path create(Location from, Location to) {
        Path l = new Path();
        l.setFromWhere(from);
        l.setToWhere(to);
        return l;
    }
}
