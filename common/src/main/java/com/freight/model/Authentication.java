package com.freight.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

import static com.freight.model.Authentication.Status.UNVERIFIED;

@Entity
public class Authentication {

    @Id
    private String guid;

    @Column
    private String email;

    @Column
    private Long phone;

    @Column(nullable = false)
    private String password;

    @Column
    private String verificationCode;

    @Column
    private Instant verificationExpiry;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column
    private String token;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public enum Status {
        VERIFIED,
        UNVERIFIED
    }

    public Authentication() {}

    private Authentication(final Builder builder) {
        this.guid = builder.guid;
        this.email = builder.email;
        this.phone = builder.phone;
        this.password = builder.password;
        this.verificationCode = builder.verificationCode;
        this.verificationExpiry = builder.verificationExpiry;
        this.status = builder.status;
        this.type = builder.type;
        this.token = builder.token;
    }

    public String getGuid() {
        return this.guid;
    }

    public String getEmail() {
        return this.email;
    }

    public Long getPhone() {
        return this.phone;
    }

    public String getPassword() {
        return this.password;
    }

    public String getVerificationCode() {
        return this.verificationCode;
    }

    public Instant getVerificationExpiry() {
        return this.verificationExpiry;
    }

    public Status getStatus() {
        return this.status;
    }

    public Type getType() {
        return this.type;
    }

    public String getToken() {
        return this.token;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public static class Builder {
        private String guid = String.valueOf(UUID.randomUUID());
        private String email;
        private Long phone;
        private String password;
        private String verificationCode;
        private Instant verificationExpiry;
        private Status status = UNVERIFIED;
        private Type type;
        private String token = String.valueOf(UUID.randomUUID());

        public Builder guid(final String guid) {
            this.guid = guid;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder phone(final Long phone) {
            this.phone = phone;
            return this;
        }

        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        public Builder verificationCode(final String verificationCode) {
            this.verificationCode = verificationCode;
            return this;
        }

        public Builder verificationExpiry(final Instant verificationExpiry) {
            this.verificationExpiry = verificationExpiry;
            return this;
        }

        public Builder status(final Status status) {
            this.status = status;
            return this;
        }

        public Builder type(final Type type) {
            this.type = type;
            return this;
        }

        public Builder token(final String token) {
            this.token = token;
            return this;
        }

        public Authentication build() {
            return new Authentication(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(guid)
                .append(email)
                .append(phone)
                .append(password)
                .append(verificationCode)
                .append(verificationExpiry)
                .append(status)
                .append(type)
                .append(token)
                .append(created)
                .append(lastModified)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Authentication that = (Authentication) o;
        return new EqualsBuilder()
                .append(guid, that.guid)
                .append(email, that.email)
                .append(phone, that.phone)
                .append(password, that.password)
                .append(verificationCode, that.verificationCode)
                .append(verificationExpiry, that.verificationExpiry)
                .append(status, that.status)
                .append(type, that.type)
                .append(token, that.token)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("guid", guid)
                .append("email", email)
                .append("phone", phone)
                .append("password", password)
                .append("verificationCode", verificationCode)
                .append("verificationExpiry", verificationExpiry)
                .append("status", status)
                .append("type", type)
                .append("token", token)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
