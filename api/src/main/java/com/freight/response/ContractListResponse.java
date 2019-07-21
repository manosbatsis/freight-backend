package com.freight.response;


import com.freight.view.ContractView;

import java.util.List;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class ContractListResponse extends BaseResponse {

    private final List<ContractView> contracts;

    public ContractListResponse(final List<ContractView> contracts) {
        this.contracts = contracts;
    }

    public List<ContractView> getContracts() {
        return contracts;
    }
}
