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
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.Instant;

/**
 * Created by toshikijahja on 3/26/19.
 */
@Entity
@IdClass(Identification.IdentificationPK.class)
public class Identification {

    @Id
    @Column
    private int typeId;

    @Id
    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column
    private String documentId;

    @Column
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public enum Type {
        INDIVIDUAL,
        COMPANY
    }

    public enum DocumentType {
        NPWP
    }

    public Identification() {}

    private Identification(final Builder builder) {
        this.typeId = builder.typeId;
        this.type = builder.type;
        this.documentId = builder.documentId;
        this.documentType = builder.documentType;
    }

    public int getTypeId() {
        return this.typeId;
    }

    public Type getType() {
        return this.type;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public static class IdentificationPK implements Serializable {
        private int typeId;
        private Type type;

        public IdentificationPK() {}

        public IdentificationPK(final int typeId, final Type type) {
            this.typeId = typeId;
            this.type = type;
        }
    }

    public static class Builder {
        private int typeId;
        private Type type;
        private String documentId;
        private DocumentType documentType;

        public Builder typeId(final int typeId) {
            this.typeId = typeId;
            return this;
        }

        public Builder type(final Type type) {
            this.type = type;
            return this;
        }

        public Builder documentId(final String documentId) {
            this.documentId = documentId;
            return this;
        }

        public Builder documentType(final DocumentType documentType) {
            this.documentType = documentType;
            return this;
        }

        public Identification build() {
            return new Identification(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(typeId)
                .append(type)
                .append(documentId)
                .append(documentType)
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

        final Identification that = (Identification) o;
        return new EqualsBuilder()
                .append(typeId, that.typeId)
                .append(type, that.type)
                .append(documentId, that.documentId)
                .append(documentType, that.documentType)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("typeId", typeId)
                .append("type", type)
                .append("documentId", documentId)
                .append("documentType", documentType)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
