package com.freight.dao;

import com.freight.model.Company;
import com.freight.model.Identification;
import com.freight.model.User;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import static java.util.Objects.requireNonNull;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class IdentificationDao extends BaseDao<Company> {

    @AssistedInject
    public IdentificationDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Identification.class);
    }

    /**
     * Creating identification for company
     * @return created Identification
     */
    public Identification createIdentification(final Company company,
                                               final Identification.DocumentType documentType,
                                               final String documentId) {
        requireNonNull(documentType);
        requireNonNull(documentId);
        getSessionProvider().startTransaction();
        final Identification identification = new Identification.Builder()
                .type(Identification.Type.COMPANY)
                .typeId(company.getId())
                .documentType(documentType)
                .documentId(documentId)
                .build();
        getSessionProvider().getSession().persist(identification);
        getSessionProvider().commitTransaction();
        return identification;
    }

    /**
     * Creating identification for individual
     * @return created Identification
     */
    public Identification createIdentification(final User user,
                                               final Identification.DocumentType documentType,
                                               final String documentId) {
        requireNonNull(documentType);
        requireNonNull(documentId);
        getSessionProvider().startTransaction();
        final Identification identification = new Identification.Builder()
                .type(Identification.Type.INDIVIDUAL)
                .typeId(user.getId())
                .documentType(documentType)
                .documentId(documentId)
                .build();
        getSessionProvider().getSession().persist(identification);
        getSessionProvider().commitTransaction();
        return identification;
    }
}
