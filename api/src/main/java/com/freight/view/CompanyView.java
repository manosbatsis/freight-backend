package com.freight.view;

import com.freight.model.Company;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class CompanyView {

    private final Company company;

    public CompanyView(final Company company) {
        this.company = company;
    }

    public int getId() {
        return company.getId();
    }

    public String getName() {
        return company.getName();
    }

    public Company.Type getType() {
        return company.getType();
    }

    public Company.Status getStatus() {
        return company.getStatus();
    }
}
