package com.freight.resource;

import com.freight.auth.UserAuth;
import com.freight.auth.UserScope;
import com.freight.dao.PortDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.ShipDao;
import com.freight.dao.ShipmentDao;
import com.freight.dao.UserDao;
import com.freight.exception.FreightException;
import com.freight.model.Port;
import com.freight.model.Ship;
import com.freight.model.Shipment;
import com.freight.model.User;
import com.freight.persistence.DaoProvider;
import com.freight.request_body.ShipmentRequestBody;
import com.freight.response.ShipmentResponse;
import com.freight.view.ShipmentView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.freight.exception.BadRequest.DESTINATION_ORIGIN_PORT_CANNOT_BE_SAME;
import static com.freight.exception.BadRequest.ESTIMATED_ARRIVAL_IN_PAST;
import static com.freight.exception.BadRequest.ESTIMATED_DEPARTURE_IN_PAST;
import static com.freight.exception.BadRequest.ESTIMATED_DEPARTURE_NOT_BEFORE_ESTIMATED_ARRIVAL;
import static com.freight.exception.BadRequest.PORT_NOT_EXIST;
import static com.freight.exception.BadRequest.SHIP_NOT_EXIST;
import static com.freight.exception.BadRequest.USER_NOT_EXIST;
import static com.freight.exception.Unauthorized.UNAUTHORIZED;
import static com.google.common.primitives.Ints.asList;
import static java.util.stream.Collectors.toMap;

@Api(tags = {"user", "public"})
@Path("/shipment")
public class ShipmentResource {

    @Inject
    private DaoProvider daoProvider;

    @Inject
    private Provider<UserScope> userScopeProvider;

    @POST
    @ApiOperation(value = "Create shipment")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @UserAuth(optional = false)
    public ShipmentResponse createShipment(final ShipmentRequestBody shipmentRequestBody) {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final PortDao portDao = daoProvider.getDaoFactory().getPortDao(sessionProvider);
            final ShipDao shipDao = daoProvider.getDaoFactory().getShipDao(sessionProvider);
            final ShipmentDao shipmentDao = daoProvider.getDaoFactory().getShipmentDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);

            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));
            if (user.getCompanyId() == null) {
                throw new FreightException(UNAUTHORIZED);
            }

            // Verify user's company and ship's company matches
            final Ship ship = shipDao.getByIdOptional(shipmentRequestBody.getShipId())
                    .orElseThrow(() -> new FreightException(SHIP_NOT_EXIST));
            if (user.getCompanyId() != ship.getCompany().getId()) {
                throw new FreightException(UNAUTHORIZED);
            }

            // Verify port existence
            if (shipmentRequestBody.getOriginPortId() == shipmentRequestBody.getDestinationPortId()) {
                throw new FreightException(DESTINATION_ORIGIN_PORT_CANNOT_BE_SAME);
            }
            final List<Port> ports = portDao.getByIds(asList(shipmentRequestBody.getOriginPortId(),
                    shipmentRequestBody.getDestinationPortId()));
            if (ports.size() != 2) {
                throw new FreightException(PORT_NOT_EXIST);
            }
            final Map<Integer, Port> portIdMap = ports.stream().collect(toMap(Port::getId, Function.identity()));

            // Verify estimated departure and arrival time
            if (shipmentRequestBody.getEstimatedDeparture() <= Instant.now().getEpochSecond()) {
                throw new FreightException(ESTIMATED_DEPARTURE_IN_PAST);
            }
            if (shipmentRequestBody.getEstimatedArrival() <= Instant.now().getEpochSecond()) {
                throw new FreightException(ESTIMATED_ARRIVAL_IN_PAST);
            }
            if (shipmentRequestBody.getEstimatedArrival() <= shipmentRequestBody.getEstimatedDeparture()) {
                throw new FreightException(ESTIMATED_DEPARTURE_NOT_BEFORE_ESTIMATED_ARRIVAL);
            }

            final Shipment shipment = shipmentDao.createShipment(
                    ship,
                    portIdMap.get(shipmentRequestBody.getOriginPortId()),
                    portIdMap.get(shipmentRequestBody.getDestinationPortId()),
                    shipmentRequestBody.getEstimatedDeparture(),
                    shipmentRequestBody.getEstimatedArrival());

            return new ShipmentResponse(new ShipmentView(shipment));
        }
    }
}
