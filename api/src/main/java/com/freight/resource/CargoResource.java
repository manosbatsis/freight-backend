package com.freight.resource;

import com.freight.auth.UserAuth;
import com.freight.auth.UserScope;
import com.freight.dao.BulkTypeDao;
import com.freight.dao.CargoDao;
import com.freight.dao.CargoTypeDao;
import com.freight.dao.ContainerTypeDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.UserDao;
import com.freight.exception.FreightException;
import com.freight.model.BulkType;
import com.freight.model.Cargo;
import com.freight.model.CargoType;
import com.freight.model.ContainerType;
import com.freight.model.User;
import com.freight.persistence.DaoProvider;
import com.freight.request_body.CargoRequestBody;
import com.freight.response.CargoListResponse;
import com.freight.response.CargoResponse;
import com.freight.view.CargoView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.freight.exception.BadRequest.BULK_INPUT_EMPTY;
import static com.freight.exception.BadRequest.BULK_TYPE_NOT_EXIST;
import static com.freight.exception.BadRequest.CARGO_TYPE_NOT_EXIST;
import static com.freight.exception.BadRequest.CONTAINER_TYPE_NOT_EXIST;
import static com.freight.exception.BadRequest.DEPARTURE_IN_PAST;
import static com.freight.exception.BadRequest.DIMENSION_EMPTY;
import static com.freight.exception.BadRequest.FCL_INPUT_EMPTY;
import static com.freight.exception.BadRequest.LCL_INPUT_EMPTY;
import static com.freight.exception.BadRequest.USER_NOT_EXIST;
import static com.freight.exception.BadRequest.VOLUME_EMPTY;
import static com.freight.exception.BadRequest.WEIGHT_EMPTY;
import static java.util.stream.Collectors.toList;

@Api(tags = {"user"})
@Path("/cargo")
public class CargoResource {

    private static Logger logger = LoggerFactory.getLogger(CargoResource.class);

    @Inject
    private DaoProvider daoProvider;

    @Inject
    private Provider<UserScope> userScopeProvider;

//    @GET
//    @ApiOperation(value = "List available/open shipment")
//    @Produces(MediaType.APPLICATION_JSON)
//    public ShipmentListResponse getOpenShipment() {
//        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
//            final ShipmentDao shipmentDao = daoProvider.getDaoFactory().getShipmentDao(sessionProvider);
//            final List<Shipment> shipments = shipmentDao.getByStatus(Shipment.Status.OPEN);
//            return new ShipmentListResponse(shipments.stream().map(ShipmentView::new).collect(toList()));
//        }
//    }

    @GET
    @ApiOperation(value = "Get list of cargo by status")
    @Produces(MediaType.APPLICATION_JSON)
    @UserAuth(optional = false)
    public CargoListResponse getCargoByStatus(@DefaultValue("inquiry") @QueryParam("status") final String status,
                                              @DefaultValue("0") @QueryParam("start") final int start,
                                              @DefaultValue("20") @QueryParam("limit") final int limit) {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final CargoDao cargoDao = daoProvider.getDaoFactory().getCargoDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);

            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));

            final Cargo.Status cargoStatus = Cargo.Status.getStatus(status);
            final List<Cargo> cargos = cargoDao.getByUserIdAndStatusSortedAndPaginated(user.getId(), cargoStatus, start, limit);

            return new CargoListResponse(cargos.stream().map(CargoView::new).collect(toList()));
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

            validateInput(cargoType, bulkTypeOptional, containerTypeOptional, cargoRequestBody);

            final Cargo cargo = cargoDao.createCargo(
                    user.getId(),
                    cargoType,
                    cargoRequestBody.getQuantity(),
                    Instant.ofEpochSecond(cargoRequestBody.getDeparture()),
                    cargoRequestBody.getWeightOptional(),
                    cargoRequestBody.getWeightUnit(),
                    cargoRequestBody.getVolumeOptional(),
                    cargoRequestBody.getVolumeUnit(),
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
                               final Optional<BulkType> bulkTypeOptional,
                               final Optional<ContainerType> containerTypeOptional,
                               final CargoRequestBody cargoRequestBody) {
        if (Objects.equals(cargoType.getType(), "FCL")) {
            containerTypeOptional.orElseThrow(() -> new FreightException(FCL_INPUT_EMPTY));
            if (!cargoRequestBody.getContainerTypeIdOptional().isPresent()) {
                throw new FreightException(FCL_INPUT_EMPTY);
            }
        } else if (Objects.equals(cargoType.getType(), "LCL")) {
            if (cargoRequestBody.getDimensionUnit() == Cargo.DimensionUnit.NOT_USED
                    && cargoRequestBody.getWeightUnit() == Cargo.WeightUnit.NOT_USED) {
                throw new FreightException(LCL_INPUT_EMPTY);
            }
        } else if (Objects.equals(cargoType.getType(), "Bulk")) {
            bulkTypeOptional.orElseThrow(() -> new FreightException(BULK_INPUT_EMPTY));
            if (cargoRequestBody.getWeightUnit() == Cargo.WeightUnit.NOT_USED
                    && cargoRequestBody.getVolumeUnit() == Cargo.VolumeUnit.NOT_USED) {
                throw new FreightException(BULK_INPUT_EMPTY);
            }
        }

        if (cargoRequestBody.getWeightOptional().isPresent()
                ^ cargoRequestBody.getWeightUnit() != Cargo.WeightUnit.NOT_USED) {
            throw new FreightException(WEIGHT_EMPTY);
        }

        if (cargoRequestBody.getVolumeOptional().isPresent()
                ^ cargoRequestBody.getVolumeUnit() != Cargo.VolumeUnit.NOT_USED) {
            throw new FreightException(VOLUME_EMPTY);
        }

        if (cargoRequestBody.getLengthOptional().isPresent()
                ^ (cargoRequestBody.getWidthOptional().isPresent()
                && cargoRequestBody.getHeightOptional().isPresent()
                && cargoRequestBody.getDimensionUnit() != Cargo.DimensionUnit.NOT_USED)) {
            throw new FreightException(DIMENSION_EMPTY);
        }

        if (Instant.ofEpochSecond(cargoRequestBody.getDeparture()).isBefore(Instant.now())) {
            throw new FreightException(DEPARTURE_IN_PAST);
        }
    }
}
