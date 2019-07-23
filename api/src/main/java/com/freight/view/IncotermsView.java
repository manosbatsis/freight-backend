package com.freight.view;

import com.freight.model.Incoterms;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class IncotermsView {

    private final Incoterms incoterms;

    public IncotermsView(final Incoterms incoterms) {
        this.incoterms = incoterms;
    }

    public int getId() {
        return incoterms.getId();
    }

    public String getDisplayName() {
        return incoterms.getDisplayName();
    }

    public String getType() {
        return incoterms.getType();
    }
}
