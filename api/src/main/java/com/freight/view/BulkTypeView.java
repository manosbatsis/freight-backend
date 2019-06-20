package com.freight.view;

import com.freight.model.BulkType;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class BulkTypeView {

    private final BulkType bulkType;

    public BulkTypeView(final BulkType bulkType) {
        this.bulkType = bulkType;
    }

    public int getId() {
        return bulkType.getId();
    }

    public String getDisplayName() {
        return bulkType.getDisplayName();
    }

    public String getType() {
        return bulkType.getType();
    }
}
