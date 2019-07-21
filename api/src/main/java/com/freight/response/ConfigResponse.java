package com.freight.response;


import com.freight.model.Cargo;
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
    private final List<Cargo.WeightUnit> weightUnits;
    private final List<Cargo.VolumeUnit> volumeUnits;
    private final List<Cargo.DimensionUnit> dimensionUnits;

    public ConfigResponse(final Builder builder) {
        this.cargoTypes = builder.cargoTypes;
        this.bulkTypes = builder.bulkTypes;
        this.containerTypes = builder.containerTypes;
        this.weightUnits = builder.weightUnits;
        this.volumeUnits = builder.volumeUnits;
        this.dimensionUnits = builder.dimensionUnits;
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

    public List<Cargo.WeightUnit> getWeightUnits() {
        return weightUnits;
    }

    public List<Cargo.VolumeUnit> getVolumeUnits() {
        return volumeUnits;
    }

    public List<Cargo.DimensionUnit> getDimensionUnits() {
        return dimensionUnits;
    }

    public static class Builder {
        private List<CargoTypeView> cargoTypes = emptyList();
        private List<BulkTypeView> bulkTypes = emptyList();
        private List<ContainerTypeView> containerTypes = emptyList();
        private List<Cargo.WeightUnit> weightUnits = emptyList();
        private List<Cargo.VolumeUnit> volumeUnits = emptyList();
        private List<Cargo.DimensionUnit> dimensionUnits = emptyList();

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

        public Builder weightUnits(final List<Cargo.WeightUnit> weightUnits) {
            this.weightUnits = weightUnits;
            return this;
        }

        public Builder volumeUnits(final List<Cargo.VolumeUnit> volumeUnits) {
            this.volumeUnits = volumeUnits;
            return this;
        }

        public Builder dimensionUnits(final List<Cargo.DimensionUnit> dimensionUnits) {
            this.dimensionUnits = dimensionUnits;
            return this;
        }

        public ConfigResponse build() {
            return new ConfigResponse(this);
        }
    }
}
