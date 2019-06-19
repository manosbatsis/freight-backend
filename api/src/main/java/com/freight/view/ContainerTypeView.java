package com.freight.view;

import com.freight.model.ContainerType;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class ContainerTypeView {

    private final ContainerType containerType;

    public ContainerTypeView(final ContainerType containerType) {
        this.containerType = containerType;
    }

    public int getId() {
        return containerType.getId();
    }

    public String getDisplayName() {
        return containerType.getDisplayName();
    }

    public String getType() {
        return containerType.getType();
    }
}
