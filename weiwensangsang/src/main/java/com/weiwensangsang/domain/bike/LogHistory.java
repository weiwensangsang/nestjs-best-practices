package com.weiwensangsang.domain.bike;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * A LogHistory.
 */
@Entity
@Table(name = "log_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LogHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "state")
    private String state;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private Faker creator;

    @Column(name = "created_date")
    private Instant createDate;

    public Instant getCreateDate() {

        return createDate;
    }

    public LocalDateTime getTime() {
        ZoneId zoneId  = ZoneId.systemDefault();
        return createDate.atZone(zoneId).toLocalDateTime();
    }

    public void setTime(LocalDateTime LocalDateTime) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Faker getCreator() {
        return creator;
    }

    public void setCreator(Faker faker) {
        this.creator = faker;
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
        LogHistory logHistory = (LogHistory) o;
        if (logHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogHistory{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", state='" + getState() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }

    private LogHistory() {
        createDate = Instant.now();
    }

    public static LogHistory create(String content) {
        LogHistory log = new LogHistory();
        log.type = "网络";
        log.content = content;
        return log;
    }

    public static LogHistory create(String content, Faker faker) {
        LogHistory log = new LogHistory();
        log.type = "用户";
        log.creator = faker;
        log.content = content;
        return log;
    }
}
