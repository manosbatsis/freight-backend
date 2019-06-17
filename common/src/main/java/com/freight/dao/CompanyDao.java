package com.freight.dao;

import com.freight.model.Company;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import static com.freight.exception.BadRequest.COMPANY_NAME_NOT_EXIST;
import static com.freight.exception.BadRequest.TYPE_NOT_EXIST;
import static com.freight.util.AssertUtil.assertNotNull;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class CompanyDao extends BaseDao<Company> {

    @AssistedInject
    public CompanyDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Company.class);
    }

    public Company createCompany(final String name,
                                 final Company.Type type) {
        assertNotNull(name, COMPANY_NAME_NOT_EXIST);
        assertNotNull(type, TYPE_NOT_EXIST);
        getSessionProvider().startTransaction();
        final Company company = new Company.Builder()
                .name(name)
                .type(type)
                .build();
        getSessionProvider().getSession().persist(company);
        getSessionProvider().commitTransaction();
        return company;
    }
}
