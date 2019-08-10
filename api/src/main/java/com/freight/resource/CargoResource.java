package com.freight.resource;

import com.freight.auth.UserAuth;
import com.freight.auth.UserScope;
import com.freight.dao.BulkTypeDao;
import com.freight.dao.CargoDao;
import com.freight.dao.CargoTypeDao;
import com.freight.dao.ContainerTypeDao;
import com.freight.dao.LocationDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.UserDao;
import com.freight.exception.FreightException;
import com.freight.model.BulkType;
import com.freight.model.Cargo;
import com.freight.model.CargoType;
import com.freight.model.ContainerType;
import com.freight.model.Location;
import com.freight.model.User;
import com.freight.persistence.DaoProvider;
import com.freight.request_body.CargoRequestBody;
import com.freight.request_body.LocationRequestBody;
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
import static com.freight.exception.BadRequest.DESTINATION_EMPTY;
import static com.freight.exception.BadRequest.DIMENSION_EMPTY;
import static com.freight.exception.BadRequest.FCL_INPUT_EMPTY;
import static com.freight.exception.BadRequest.LCL_INPUT_EMPTY;
import static com.freight.exception.BadRequest.ORIGIN_EMPTY;
import static com.freight.exception.BadRequest.USER_NOT_EXIST;
import static com.freight.exception.BadRequest.VOLUME_EMPTY;
import static com.freight.exception.BadRequest.WEIGHT_EMPTY;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Arrays.asList;
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
    public CargoListResponse getCargoByStatus(@DefaultValue("inquiry") @QueryParam("status") final String statusList,
                                              @DefaultValue("0") @QueryParam("start") final int start,
                                              @DefaultValue("20") @QueryParam("limit") final int limit) {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final CargoDao cargoDao = daoProvider.getDaoFactory().getCargoDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);

            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));

            final List<String> statusListString = asList(statusList.split(","));
            final List<Cargo.Status> cargoStatusList = statusListString.stream()
                    .map(Cargo.Status::getStatus)
                    .collect(toList());
            final List<Cargo> cargos = cargoDao.getByUserIdAndStatusListSortedAndPaginated(user.getId(), cargoStatusList, start, limit);

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
            final LocationDao locationDao = daoProvider.getDaoFactory().getLocationDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);

            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));

            validateLocation(cargoRequestBody.getOrigin(), cargoRequestBody.getDestination());
            final Location originLocation = locationDao.getOrCreateIfNotExist(
                    cargoRequestBody.getOrigin().getExternalId(),
                    cargoRequestBody.getOrigin().getMainName(),
                    cargoRequestBody.getOrigin().getSecondaryName(),
                    cargoRequestBody.getOrigin().getLat(),
                    cargoRequestBody.getOrigin().getLon(),
                    cargoRequestBody.getOrigin().getRoute(),
                    cargoRequestBody.getOrigin().getLocality(),
                    cargoRequestBody.getOrigin().getVillage(),
                    cargoRequestBody.getOrigin().getSubdistrict(),
                    cargoRequestBody.getOrigin().getCity(),
                    cargoRequestBody.getOrigin().getProvince(),
                    cargoRequestBody.getOrigin().getCountry()
            );
            final Location destinationLocation = locationDao.getOrCreateIfNotExist(
                    cargoRequestBody.getDestination().getExternalId(),
                    cargoRequestBody.getDestination().getMainName(),
                    cargoRequestBody.getDestination().getSecondaryName(),
                    cargoRequestBody.getDestination().getLat(),
                    cargoRequestBody.getDestination().getLon(),
                    cargoRequestBody.getDestination().getRoute(),
                    cargoRequestBody.getDestination().getLocality(),
                    cargoRequestBody.getDestination().getVillage(),
                    cargoRequestBody.getDestination().getSubdistrict(),
                    cargoRequestBody.getDestination().getCity(),
                    cargoRequestBody.getDestination().getProvince(),
                    cargoRequestBody.getDestination().getCountry()
            );

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
                    originLocation,
                    destinationLocation,
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

    private void validateLocation(final LocationRequestBody origin,
                                  final LocationRequestBody destination) {
        if (origin == null
                || isNullOrEmpty(origin.getMainName())
                || isNullOrEmpty(origin.getSecondaryName())
                || (origin.getLat() == null)
                || (origin.getLon() == null)) {
            throw new FreightException(ORIGIN_EMPTY);
        }

        if (destination == null
                || isNullOrEmpty(destination.getMainName())
                || isNullOrEmpty(destination.getSecondaryName())
                || (destination.getLat() == null)
                || (destination.getLon() == null)) {
            throw new FreightException(DESTINATION_EMPTY);
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
