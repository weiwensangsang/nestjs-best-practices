package com.weiwensangsang.domain.bike;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Task entity.@author The JHipster team.
 */
@ApiModel(description = "Task entity.@author The JHipster team.")
@Entity
@Table(name = "topo_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TopoConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tense")
    private Long tense;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "state")
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTense() {
        return tense;
    }

    public void setTense(Long tense) {
        this.tense = tense;
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

    @Override
    public String toString() {
        return "TopoConfig{" +
            "id=" + id +
            ", tense=" + tense +
            ", type='" + type + '\'' +
            ", state='" + state + '\'' +
            '}';
    }
}
