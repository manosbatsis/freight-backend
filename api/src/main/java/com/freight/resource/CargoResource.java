package com.freight.resource;

import com.freight.auth.UserAuth;
import com.freight.auth.UserScope;
import com.freight.dao.BulkTypeDao;
import com.freight.dao.CargoDao;
import com.freight.dao.CargoTypeDao;
import com.freight.dao.ContainerTypeDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.ShipCargoTypeDao;
import com.freight.dao.ShipmentDao;
import com.freight.dao.UserDao;
import com.freight.exception.FreightException;
import com.freight.model.BulkType;
import com.freight.model.Cargo;
import com.freight.model.CargoType;
import com.freight.model.ContainerType;
import com.freight.model.ShipCargoType;
import com.freight.model.Shipment;
import com.freight.model.User;
import com.freight.persistence.DaoProvider;
import com.freight.request_body.CargoRequestBody;
import com.freight.response.CargoResponse;
import com.freight.response.ShipmentListResponse;
import com.freight.view.CargoView;
import com.freight.view.ShipmentView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.freight.exception.BadRequest.BULK_INPUT_EMPTY;
import static com.freight.exception.BadRequest.BULK_TYPE_NOT_EXIST;
import static com.freight.exception.BadRequest.CARGO_TYPE_NOT_EXIST;
import static com.freight.exception.BadRequest.CONTAINER_TYPE_NOT_EXIST;
import static com.freight.exception.BadRequest.DIMENSION_EMPTY;
import static com.freight.exception.BadRequest.FCL_INPUT_EMPTY;
import static com.freight.exception.BadRequest.LCL_INPUT_EMPTY;
import static com.freight.exception.BadRequest.SHIPMENT_NOT_EXIST;
import static com.freight.exception.BadRequest.SHIP_CARGO_TYPE_NOT_MATCH;
import static com.freight.exception.BadRequest.USER_NOT_EXIST;
import static com.freight.exception.BadRequest.WEIGHT_EMPTY;
import static java.util.stream.Collectors.toList;

@Api(tags = {"user"})
@Path("/cargo")
public class CargoResource {

    @Inject
    private DaoProvider daoProvider;

    @Inject
    private Provider<UserScope> userScopeProvider;

    @GET
    @ApiOperation(value = "List available/open shipment")
    @Produces(MediaType.APPLICATION_JSON)
    public ShipmentListResponse getOpenShipment() {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final ShipmentDao shipmentDao = daoProvider.getDaoFactory().getShipmentDao(sessionProvider);
            final List<Shipment> shipments = shipmentDao.getByStatus(Shipment.Status.OPEN);
            return new ShipmentListResponse(shipments.stream().map(ShipmentView::new).collect(toList()));
        }
    }

    @POST
    @ApiOperation(value = "Create cargo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @UserAuth(optional = false)
    public CargoResponse createCargo(final CargoRequestBody cargoRequestBody) {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final BulkTypeDao bulkTypeDao = daoProvider.getDaoFactory().getBulkTypeDao(sessionProvider);
            final CargoDao cargoDao = daoProvider.getDaoFactory().getCargoDao(sessionProvider);
            final CargoTypeDao cargoTypeDao = daoProvider.getDaoFactory().getCargoTypeDao(sessionProvider);
            final ContainerTypeDao containerTypeDao = daoProvider.getDaoFactory().getContainerTypeDao(sessionProvider);
            final ShipCargoTypeDao shipCargoTypeDao = daoProvider.getDaoFactory().getShipCargoTypeDao(sessionProvider);
            final ShipmentDao shipmentDao = daoProvider.getDaoFactory().getShipmentDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);

            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));

            final CargoType cargoType = cargoTypeDao.getByIdOptional(cargoRequestBody.getCargoTypeId())
                    .orElseThrow(() -> new FreightException(CARGO_TYPE_NOT_EXIST));
            final Optional<BulkType> bulkTypeOptional = cargoRequestBody.getBulkTypeIdOptional()
                    .map(bulkTypeId -> bulkTypeDao.getByIdOptional(bulkTypeId)
                            .orElseThrow(() -> new FreightException(BULK_TYPE_NOT_EXIST)));
            final Optional<ContainerType> containerTypeOptional = cargoRequestBody.getContainerTypeIdOptional()
                    .map(containerTypeId -> containerTypeDao.getByIdOptional(containerTypeId)
                            .orElseThrow(() -> new FreightException(CONTAINER_TYPE_NOT_EXIST)));
            final Shipment shipment = shipmentDao.getByIdOptional(cargoRequestBody.getShipmentId())
                    .orElseThrow(() -> new FreightException(SHIPMENT_NOT_EXIST));
            final List<ShipCargoType> shipCargoTypes = shipCargoTypeDao.getByShipIdAndCargoTypeId(
                    shipment.getShip().getId(), cargoType.getId());
            final List<Integer> shipCargoTypeIds = shipCargoTypes.stream()
                    .map(shipCargoType -> shipCargoType.getCargoType().getId()).collect(toList());
            validateInput(cargoType, shipCargoTypeIds, bulkTypeOptional, containerTypeOptional, cargoRequestBody);

            final Cargo cargo = cargoDao.createCargo(
                    shipment,
                    user.getId(),
                    cargoType,
                    cargoRequestBody.getQuantity(),
                    cargoRequestBody.getWeightOptional(),
                    cargoRequestBody.getWeightUnit(),
                    cargoRequestBody.getLengthOptional(),
                    cargoRequestBody.getWidthOptional(),
                    cargoRequestBody.getHeightOptional(),
                    cargoRequestBody.getDimensionUnit(),
                    containerTypeOptional,
                    bulkTypeOptional);

            return new CargoResponse(new CargoView(cargo));
        }
    }

    private void validateInput(final CargoType cargoType,
                               final List<Integer> shipCargoTypeIds,
                               final Optional<BulkType> bulkTypeOptional,
                               final Optional<ContainerType> containerTypeOptional,
                               final CargoRequestBody cargoRequestBody) {
        if (!shipCargoTypeIds.contains(cargoType.getId())) {
            throw new FreightException(SHIP_CARGO_TYPE_NOT_MATCH);
        }

        if (Objects.equals(cargoType.getType(), "FCL")) {
            containerTypeOptional.orElseThrow(() -> new FreightException(FCL_INPUT_EMPTY));
        } else if (Objects.equals(cargoType.getType(), "LCL")) {
            if (cargoRequestBody.getDimensionUnit() == Cargo.DimensionUnit.NOT_USED
                    && cargoRequestBody.getWeightUnit() == Cargo.WeightUnit.NOT_USED) {
                throw new FreightException(LCL_INPUT_EMPTY);
            }
        } else if (Objects.equals(cargoType.getType(), "Bulk")) {
            bulkTypeOptional.orElseThrow(() -> new FreightException(BULK_INPUT_EMPTY));
        }

        if (cargoRequestBody.getWeightOptional().isPresent()
                ^ cargoRequestBody.getWeightUnit() != Cargo.WeightUnit.NOT_USED) {
            throw new FreightException(WEIGHT_EMPTY);
        }

        if (cargoRequestBody.getLengthOptional().isPresent()
                ^ (cargoRequestBody.getWidthOptional().isPresent()
                && cargoRequestBody.getHeightOptional().isPresent()
                && cargoRequestBody.getDimensionUnit() != Cargo.DimensionUnit.NOT_USED)) {
            throw new FreightException(DIMENSION_EMPTY);
        }
    }
}
