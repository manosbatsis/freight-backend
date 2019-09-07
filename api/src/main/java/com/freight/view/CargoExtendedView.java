package com.freight.view;

import com.freight.model.Cargo;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class CargoExtendedView extends CargoView {

    private long actionCount;

    public CargoExtendedView(final Cargo cargo,
                             final long actionCount) {
        super(cargo);
        this.actionCount = actionCount;
    }

    public long getActionCount() {
        return this.actionCount;
    }
}
