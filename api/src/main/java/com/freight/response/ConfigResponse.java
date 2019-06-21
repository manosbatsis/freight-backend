package com.freight.response;


import com.freight.view.BulkTypeView;
import com.freight.view.CargoTypeView;
import com.freight.view.ContainerTypeView;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class ConfigResponse extends BaseResponse {

    private final List<CargoTypeView> cargoTypes;
    private final List<BulkTypeView> bulkTypes;
    private final List<ContainerTypeView> containerTypes;

    public ConfigResponse(final Builder builder) {
        this.cargoTypes = builder.cargoTypes;
        this.bulkTypes = builder.bulkTypes;
        this.containerTypes = builder.containerTypes;
    }

    public List<CargoTypeView> getCargoTypes() {
        return cargoTypes;
    }

    public List<BulkTypeView> getBulkTypes() {
        return bulkTypes;
    }

    public List<ContainerTypeView> getContainerTypes() {
        return containerTypes;
    }

    public static class Builder {
        private List<CargoTypeView> cargoTypes = emptyList();
        private List<BulkTypeView> bulkTypes = emptyList();
        private List<ContainerTypeView> containerTypes = emptyList();

        public Builder cargoTypes(final List<CargoTypeView> cargoTypes) {
            this.cargoTypes = cargoTypes;
            return this;
        }

        public Builder bulkTypes(final List<BulkTypeView> bulkTypes) {
            this.bulkTypes = bulkTypes;
            return this;
        }

        public Builder containerTypes(final List<ContainerTypeView> containerTypes) {
            this.containerTypes = containerTypes;
            return this;
        }

        public ConfigResponse build() {
            return new ConfigResponse(this);
        }
    }
}
