package com.freight.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

import static com.freight.model.User.Status.ACTIVE;
import static com.freight.model.User.Type.NOT_KNOWN;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String guid;

    @Column
    @Length(min = 3, max = 20)
    private String username;

    @Column
    private String email;

    @Column
    private Integer phone;

    @Column
    private Integer companyId;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public enum Type {
        CUSTOMER,
        TRANSPORTER,
        CUSTOMER_TRANSPORTER,
        NOT_KNOWN
    }

    public enum Status {
        ACTIVE,
        SUSPENDED
    }

    public User() {}

    private User(final Builder builder) {
        this.id = builder.id;
        this.guid = builder.guid;
        this.username = builder.username;
        this.email = builder.email;
        this.phone = builder.phone;
        this.companyId = builder.companyId;
        this.type = builder.type;
        this.status = builder.status;
    }

    public int getId() {
        return this.id;
    }

    public String getGuid() {
        return this.guid;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public Integer getPhone() {
        return this.phone;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public Type getType() {
        return this.type;
    }

    public Status getStatus() {
        return this.status;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public static class Builder {
        private int id;
        private String guid;
        private String username;
        private String email;
        private Integer phone;
        private Integer companyId;
        private Type type = NOT_KNOWN;
        private Status status = ACTIVE;

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder guid(final String guid) {
            this.guid = guid;
            return this;
        }

        public Builder username(final String username) {
            this.username = username;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder phone(final Integer phone) {
            this.phone = phone;
            return this;
        }

        public Builder companyId(final Integer companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder type(final Type type) {
            this.type = type;
            return this;
        }

        public Builder status(final Status status) {
            this.status = status;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(guid)
                .append(username)
                .append(email)
                .append(phone)
                .append(companyId)
                .append(type)
                .append(status)
                .append(created)
                .append(lastModified)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {return false;
        }

        final User that = (User) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(guid, that.guid)
                .append(username, that.username)
                .append(email, that.email)
                .append(phone, that.phone)
                .append(companyId, that.companyId)
                .append(type, that.type)
                .append(status, that.status)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("guid", guid)
                .append("username", username)
                .append("email", email)
                .append("phone", phone)
                .append("companyId", companyId)
                .append("type", type)
                .append("status", status)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
