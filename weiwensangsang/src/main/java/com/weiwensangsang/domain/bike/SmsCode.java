package com.weiwensangsang.domain.bike;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SmsCode.
 */
@Entity
@Table(name = "sms_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SmsCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "code")
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        SmsCode smsCode = (SmsCode) o;
        if (smsCode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), smsCode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SmsCode{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", code='" + getCode() + "'" +
            ", type='" + getType() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }

    private SmsCode() {
        createDate =Instant.now();
        type = "A";
    }

    public static SmsCode create(String phone, String code) {
        SmsCode c = new SmsCode();
        c.setPhone(phone);
        c.setCode(code);
        return c;
    }
}
