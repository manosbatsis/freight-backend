package com.freight.resource;

import com.freight.auth.UserAuth;
import com.freight.auth.UserScope;
import com.freight.dao.CargoShipmentDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.UserDao;
import com.freight.exception.FreightException;
import com.freight.model.CargoShipment;
import com.freight.model.Shipment;
import com.freight.model.User;
import com.freight.persistence.DaoProvider;
import com.freight.response.CargoShipmentListResponse;
import com.freight.view.CargoShipmentView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.freight.exception.BadRequest.USER_NOT_EXIST;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Api(tags = {"user", "public"})
@Path("/shipment")
public class ShipmentResource {

    @Inject
    private DaoProvider daoProvider;

    @Inject
    private Provider<UserScope> userScopeProvider;

    @GET
    @ApiOperation(value = "Get shipment by ship status")
    @Produces(MediaType.APPLICATION_JSON)
    @UserAuth(optional = false)
    public CargoShipmentListResponse getShipmentByStatus(
            @DefaultValue("upcoming") @QueryParam("status") final String statusList,
            @DefaultValue("0") @QueryParam("start") final int start,
            @DefaultValue("20") @QueryParam("limit") final int limit) {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final CargoShipmentDao cargoShipmentDao = daoProvider.getDaoFactory().getCargoShipmentDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);

            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));

            final List<String> statusListString = asList(statusList.split(","));
            final List<Shipment.Status> shipmentStatusList = statusListString.stream()
                    .map(Shipment.Status::getStatus)
                    .collect(toList());

            final List<CargoShipment> cargoShipments = cargoShipmentDao.getByUserIdAndShipmentStatusListSortedAndPaginated(
                    user.getId(), shipmentStatusList, start, limit);
            final List<CargoShipmentView> cargoShipmentViews = cargoShipments.stream()
                    .map(cargoShipment -> new CargoShipmentView(cargoShipment.getCargo(), cargoShipment.getShipment()))
                    .collect(toList());

            return new CargoShipmentListResponse(cargoShipmentViews);
        }
    }

//    @POST
//    @ApiOperation(value = "Create shipment")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @UserAuth(optional = false)
//    public ShipmentResponse createShipment(final ShipmentRequestBody shipmentRequestBody) {
//        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
//            final PortDao portDao = daoProvider.getDaoFactory().getPortDao(sessionProvider);
//            final ShipDao shipDao = daoProvider.getDaoFactory().getShipDao(sessionProvider);
//            final ShipmentDao shipmentDao = daoProvider.getDaoFactory().getShipmentDao(sessionProvider);
//            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);
//
//            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
//                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));
//            if (user.getCompanyId() == null) {
//                throw new FreightException(UNAUTHORIZED);
//            }
//
//            // Verify user's company and ship's company matches
//            final Ship ship = shipDao.getByIdOptional(shipmentRequestBody.getShipId())
//                    .orElseThrow(() -> new FreightException(SHIP_NOT_EXIST));
//            if (user.getCompanyId() != ship.getCompany().getId()) {
//                throw new FreightException(UNAUTHORIZED);
//            }
//
//            // Verify port existence
//            if (shipmentRequestBody.getOriginPortId() == shipmentRequestBody.getDestinationPortId()) {
//                throw new FreightException(DESTINATION_ORIGIN_PORT_CANNOT_BE_SAME);
//            }
//            final List<Location> locations = portDao.getByIds(asList(shipmentRequestBody.getOriginPortId(),
//                    shipmentRequestBody.getDestinationPortId()));
//            if (locations.size() != 2) {
//                throw new FreightException(PORT_NOT_EXIST);
//            }
//            final Map<Integer, Location> portIdMap = locations.stream().collect(toMap(Location::getId, Function.identity()));
//
//            // Verify departure and arrival time
//            if (shipmentRequestBody.getDeparture() <= Instant.now().getEpochSecond()) {
//                throw new FreightException(DEPARTURE_IN_PAST);
//            }
//            if (shipmentRequestBody.getArrival() <= Instant.now().getEpochSecond()) {
//                throw new FreightException(ARRIVAL_IN_PAST);
//            }
//            if (shipmentRequestBody.getArrival() <= shipmentRequestBody.getDeparture()) {
//                throw new FreightException(DEPARTURE_NOT_BEFORE_ARRIVAL);
//            }
//
//            final Shipment shipment = shipmentDao.createShipment(
//                    ship,
//                    portIdMap.get(shipmentRequestBody.getOriginPortId()),
//                    portIdMap.get(shipmentRequestBody.getDestinationPortId()),
//                    shipmentRequestBody.getDeparture(),
//                    shipmentRequestBody.getArrival());
//
//            return new ShipmentResponse(new ShipmentView(shipment));
//        }
//    }
}
