package com.freight.resource;

import com.freight.auth.UserAuth;
import com.freight.auth.UserScope;
import com.freight.dao.CargoContractDao;
import com.freight.dao.CargoDao;
import com.freight.dao.ContractDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.UserDao;
import com.freight.exception.FreightException;
import com.freight.model.Cargo;
import com.freight.model.CargoContract;
import com.freight.model.Contract;
import com.freight.model.User;
import com.freight.persistence.DaoProvider;
import com.freight.response.ContractListResponse;
import com.freight.view.ContractView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.freight.exception.BadRequest.CARGO_NOT_EXIST;
import static com.freight.exception.BadRequest.USER_NOT_EXIST;
import static com.freight.exception.Unauthorized.UNAUTHORIZED;
import static java.util.stream.Collectors.toList;

@Api(tags = {"user"})
@Path("/contract")
public class ContractResource {

    private static Logger logger = LoggerFactory.getLogger(ContractResource.class);

    @Inject
    private DaoProvider daoProvider;

    @Inject
    private Provider<UserScope> userScopeProvider;

    @GET
    @ApiOperation(value = "Get list of contract by cargoId")
    @Produces(MediaType.APPLICATION_JSON)
    @UserAuth(optional = false)
    public ContractListResponse getContractsByCargoId(@QueryParam("cargoId") final int cargoId,
                                                      @DefaultValue("0") @QueryParam("start") final int start,
                                                      @DefaultValue("20") @QueryParam("limit") final int limit) {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final CargoDao cargoDao = daoProvider.getDaoFactory().getCargoDao(sessionProvider);
            final CargoContractDao cargoContractDao = daoProvider.getDaoFactory().getCargoContractDao(sessionProvider);
            final ContractDao contractDao = daoProvider.getDaoFactory().getContractDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);

            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));

            final Cargo cargo = cargoDao.getByIdOptional(cargoId).orElseThrow(() -> new FreightException(CARGO_NOT_EXIST));

            if (cargo.getUserId() != user.getId()) {
                throw new FreightException(UNAUTHORIZED);
            }

            final List<CargoContract> cargoContracts = cargoContractDao.getByCargoIdSortedAndPaginated(cargoId, start, limit);
            final List<Integer> contractIds = cargoContracts.stream().map(CargoContract::getContractId).collect(toList());
            final List<Contract> contracts = contractDao.getByIds(contractIds);
            final List<ContractView> contractViews = contracts.stream().map(ContractView::new).collect(toList());

            return new ContractListResponse(contractViews);
        }
    }
}
