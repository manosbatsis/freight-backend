package com.freight.response;


import com.freight.view.CompanyView;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class CompanyResponse extends BaseResponse {

    private final CompanyView company;

    public CompanyResponse(final CompanyView company) {
        this.company = company;
    }

    public CompanyView getCompany() {
        return company;
    }
}
