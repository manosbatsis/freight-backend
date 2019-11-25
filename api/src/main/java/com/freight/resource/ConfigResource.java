package com.freight.resource;

import com.freight.dao.BaseDao;
import com.freight.dao.BulkTypeDao;
import com.freight.dao.CargoTypeDao;
import com.freight.dao.ContainerTypeDao;
import com.freight.dao.SessionProvider;
import com.freight.model.BulkType;
import com.freight.model.Cargo;
import com.freight.model.CargoType;
import com.freight.model.ContainerType;
import com.freight.persistence.DaoProvider;
import com.freight.response.ConfigResponse;
import com.freight.view.BulkTypeView;
import com.freight.view.CargoTypeView;
import com.freight.view.ContainerTypeView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Api(tags = {"public"})
@Path("/config")
public class ConfigResource {

    @Inject
    private DaoProvider daoProvider;

    @GET
    @ApiOperation(value = "Get config")
    @Produces(MediaType.APPLICATION_JSON)
    public ConfigResponse getConfig() {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final BulkTypeDao bulkTypeDao = daoProvider.getDaoFactory().getBulkTypeDao(sessionProvider);
            final CargoTypeDao cargoTypeDao = daoProvider.getDaoFactory().getCargoTypeDao(sessionProvider);
            final ContainerTypeDao containerTypeDao = daoProvider.getDaoFactory().getContainerTypeDao(sessionProvider);

            final List<CargoType> cargoTypes = cargoTypeDao.getAll();
            final List<CargoTypeView> cargoTypeViews = cargoTypes.stream().map(CargoTypeView::new).collect(toList());

            final List<BulkType> bulkTypes = bulkTypeDao.getAllSorted("displayName", BaseDao.Sort.ASC);
            final List<BulkTypeView> bulkTypeViews = bulkTypes.stream().map(BulkTypeView::new).collect(toList());

            final List<ContainerType> containerTypes = containerTypeDao.getAll();
            final List<ContainerTypeView> containerTypeViews = containerTypes.stream().map(ContainerTypeView::new).collect(toList());

            return new ConfigResponse.Builder()
                    .cargoTypes(cargoTypeViews)
                    .bulkTypes(bulkTypeViews)
                    .containerTypes(containerTypeViews)
                    .weightUnits(asList(Cargo.WeightUnit.values()))
                    .volumeUnits(asList(Cargo.VolumeUnit.values()))
                    .dimensionUnits(asList(Cargo.DimensionUnit.values()))
                    .build();
        }
    }
}
