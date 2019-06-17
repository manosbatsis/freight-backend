package com.freight.resource;

/**
 * Created by toshikijahja on 6/16/19.
 */

import com.freight.auth.UserAuth;
import com.freight.auth.UserScope;
import com.freight.dao.CargoTypeDao;
import com.freight.dao.CompanyDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.ShipCargoTypeDao;
import com.freight.dao.ShipDao;
import com.freight.dao.UserDao;
import com.freight.exception.FreightException;
import com.freight.model.CargoType;
import com.freight.model.Company;
import com.freight.model.Ship;
import com.freight.model.User;
import com.freight.persistence.DaoProvider;
import com.freight.request_body.ShipRequestBody;
import com.freight.response.ShipResponse;
import com.freight.view.ShipView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.freight.exception.BadRequest.CARGO_TYPE_NOT_EXIST;
import static com.freight.exception.BadRequest.COMPANY_NOT_EXIST;
import static com.freight.exception.BadRequest.SHIP_NOT_EXIST;
import static com.freight.exception.BadRequest.USER_NOT_EXIST;
import static com.freight.exception.Unauthorized.UNAUTHORIZED;

@Api(tags = {"user", "public"})
@Path("/ship")
public class ShipResource {

    @Inject
    private DaoProvider daoProvider;

    @Inject
    private Provider<UserScope> userScopeProvider;

    @GET
    @ApiOperation(value = "Get ship by shipId")
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ShipResponse getShip(@PathParam("id") final int shipId) {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final ShipDao shipDao = daoProvider.getDaoFactory().getShipDao(sessionProvider);
            final Ship ship = shipDao.getByIdOptional(shipId)
                    .orElseThrow(() -> new FreightException(SHIP_NOT_EXIST));
            return new ShipResponse(new ShipView(ship));
        }
    }

    @POST
    @ApiOperation(value = "Create ship")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @UserAuth(optional = false)
    public ShipResponse createShip(final ShipRequestBody shipRequestBody) {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final CargoTypeDao cargoTypeDao = daoProvider.getDaoFactory().getCargoTypeDao(sessionProvider);
            final CompanyDao companyDao = daoProvider.getDaoFactory().getCompanyDao(sessionProvider);
            final ShipDao shipDao = daoProvider.getDaoFactory().getShipDao(sessionProvider);
            final ShipCargoTypeDao shipCargoTypeDao = daoProvider.getDaoFactory().getShipCargoTypeDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);

            // Verify if user's company match ship's company
            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));
            if (user.getCompanyId() == null) {
                throw new FreightException(COMPANY_NOT_EXIST);
            }
            if (user.getCompanyId() != shipRequestBody.getCompanyId()) {
                throw new FreightException(UNAUTHORIZED);
            }

            final Company company = companyDao.getByIdOptional(user.getCompanyId()).
                    orElseThrow(() -> new FreightException(COMPANY_NOT_EXIST));

            // Get and validate cargo type
            if (CollectionUtils.isEmpty(shipRequestBody.getCargoTypes())) {
                throw new FreightException(CARGO_TYPE_NOT_EXIST);
            }
            final List<CargoType> cargoTypes = cargoTypeDao.getByTypes(shipRequestBody.getCargoTypes());

            if (cargoTypes.size() != shipRequestBody.getCargoTypes().size()) {
                throw new FreightException(CARGO_TYPE_NOT_EXIST);
            }

            sessionProvider.startTransaction();
            final Ship ship = shipDao.createShip(
                    shipRequestBody.getName(),
                    company,
                    shipRequestBody.getYearBuiltOptional().orElse(null),
                    shipRequestBody.getGrossTonnageOptional().orElse(null));
            shipCargoTypeDao.createShipCargoTypes(ship, cargoTypes);
            sessionProvider.commitTransaction();
            return new ShipResponse(new ShipView(ship));
        }
    }
}
