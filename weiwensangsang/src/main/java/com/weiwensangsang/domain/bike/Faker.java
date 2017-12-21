package com.weiwensangsang.domain.bike;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Task entity.@author The JHipster team.
 */
@ApiModel(description = "Task entity.@author The JHipster team.")
@Entity
@Table(name = "faker")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Faker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "state")
    private String state;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "created_date")
    private Instant createDate;

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
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
        Faker faker = (Faker) o;
        if (faker.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), faker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Faker{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", state='" + getState() + "'" +
            ", activated='" + isActivated() + "'" +
            "}";
    }

    private Faker() {
        createDate = Instant.now();
        activated = false;
    }

    public static Faker create(String phone) {
        Faker user = new Faker();
        user.setPhone(phone);
        user.setType("normal");
        return user;
    }
}
