package com.freight.resource;

import com.freight.auth.UserAuth;
import com.freight.auth.UserScope;
import com.freight.dao.CargoContractDao;
import com.freight.dao.CargoDao;
import com.freight.dao.ContractDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.ShipFacilityDao;
import com.freight.dao.UserDao;
import com.freight.exception.FreightException;
import com.freight.model.Cargo;
import com.freight.model.CargoContract;
import com.freight.model.Contract;
import com.freight.model.ShipFacility;
import com.freight.model.Type;
import com.freight.model.User;
import com.freight.persistence.DaoProvider;
import com.freight.request_body.ContractRequestBody;
import com.freight.response.CargoContractResponse;
import com.freight.response.ContractListResponse;
import com.freight.view.CargoContractView;
import com.freight.view.ContractView;
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
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.freight.exception.BadRequest.CARGO_NOT_EXIST;
import static com.freight.exception.BadRequest.CONTRACT_NOT_EXIST;
import static com.freight.exception.BadRequest.CONTRACT_NOT_FOR_USER;
import static com.freight.exception.BadRequest.CONTRACT_STATUS_NOT_ALLOWED;
import static com.freight.exception.BadRequest.USER_NOT_EXIST;
import static com.freight.exception.Unauthorized.UNAUTHORIZED;
import static com.freight.model.CargoContract.Status.CUSTOMER_ACCEPTED;
import static com.freight.model.CargoContract.Status.CUSTOMER_ACCEPT_OTHER_CONTRACT;
import static com.freight.model.CargoContract.Status.CUSTOMER_DECLINED;
import static com.freight.model.CargoContract.Status.CUSTOMER_EXPIRED;
import static com.freight.model.CargoContract.Status.CUSTOMER_NEGOTIATE;
import static com.freight.model.CargoContract.Status.TRANSPORTER_EXPIRED;
import static com.freight.model.CargoContract.Status.TRANSPORTER_OFFERED;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Api(tags = {"user"})
@Path("/contract")
public class ContractResource {

    private static Logger logger = LoggerFactory.getLogger(ContractResource.class);
    private static List<CargoContract.Status> CUSTOMER_STATUS = asList(
            CUSTOMER_ACCEPTED, CUSTOMER_DECLINED, CUSTOMER_EXPIRED, CUSTOMER_NEGOTIATE);
    private static List<CargoContract.Status> TRANSPORTER_STATUS = asList(TRANSPORTER_EXPIRED, TRANSPORTER_OFFERED);


    @Inject
    private DaoProvider daoProvider;

    @Inject
    private Provider<UserScope> userScopeProvider;

    @GET
    @ApiOperation(value = "Get list of contract by cargo id")
    @Produces(MediaType.APPLICATION_JSON)
    @UserAuth(optional = false)
    public ContractListResponse getContractsByCargoId(@QueryParam("cargoId") final int cargoId,
                                                      @DefaultValue("0") @QueryParam("start") final int start,
                                                      @DefaultValue("20") @QueryParam("limit") final int limit) {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final CargoDao cargoDao = daoProvider.getDaoFactory().getCargoDao(sessionProvider);
            final CargoContractDao cargoContractDao = daoProvider.getDaoFactory().getCargoContractDao(sessionProvider);
            final ContractDao contractDao = daoProvider.getDaoFactory().getContractDao(sessionProvider);
            final ShipFacilityDao shipFacilityDao = daoProvider.getDaoFactory().getShipFacilityDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);

            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));

            final Cargo cargo = cargoDao.getByIdOptional(cargoId).orElseThrow(() -> new FreightException(CARGO_NOT_EXIST));

            if (cargo.getUserId() != user.getId()) {
                throw new FreightException(UNAUTHORIZED);
            }

            final List<CargoContract> cargoContracts = cargoContractDao.getByCargoIdSortedAndPaginated(cargoId, start, limit);
            final Map<Integer, CargoContract> cargoContractsByContractId = cargoContracts.stream()
                    .collect(toMap(CargoContract::getContractId, Function.identity()));
            final List<Integer> contractIds = cargoContracts.stream().map(CargoContract::getContractId).collect(toList());
            final List<Contract> contracts = contractDao.getByIds(contractIds);
            final List<Integer> shipIds = contracts.stream().map(contract -> contract.getShip().getId()).collect(toList());
            final List<ShipFacility> shipFacilities = shipFacilityDao.getByShipIds(shipIds);
            final Map<Integer, List<ShipFacility>> shipFacilitiesByShipId = shipFacilities.stream().collect(groupingBy(ShipFacility::getShipId));
            final List<ContractView> contractViews = contracts.stream()
                    .map(contract -> new ContractView(
                            contract,
                            cargoContractsByContractId.get(contract.getId()),
                            shipFacilitiesByShipId.containsKey(contract.getShip().getId())
                                    ? shipFacilitiesByShipId.get(contract.getShip().getId())
                                    : emptyList()))
                    .collect(toList());

            return new ContractListResponse(contractViews);
        }
    }

    @POST
    @ApiOperation(value = "Update contract status")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @UserAuth(optional = false)
    public CargoContractResponse updateContractStatus(final ContractRequestBody contractRequestBody) {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final CargoContractDao cargoContractDao = daoProvider.getDaoFactory().getCargoContractDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);

            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));

            final CargoContract cargoContract = cargoContractDao.getByContractId(contractRequestBody.getContractId())
                    .orElseThrow(() -> new FreightException(CONTRACT_NOT_EXIST));

            validateRequest(cargoContract, user, contractRequestBody.getStatus());

            sessionProvider.startTransaction();
            cargoContractDao.updateStatusByContractId(contractRequestBody.getStatus(), cargoContract.getContractId());
            if (contractRequestBody.getStatus() == CUSTOMER_ACCEPTED) {
                // update contracts for cargo to CUSTOMER_ACCEPT_OTHER_CONTRACT
                cargoContractDao.updateStatusByCargoIdExcludeContractId(
                        CUSTOMER_ACCEPT_OTHER_CONTRACT, cargoContract.getCargoId(), cargoContract.getContractId());
            }
            sessionProvider.commitTransaction();

            return new CargoContractResponse(new CargoContractView(cargoContract));
        }
    }

    private void validateRequest(final CargoContract cargoContract,
                                 final User user,
                                 final CargoContract.Status statusRequest) {
        // User is CUSTOMER
        if (user.getType() == Type.CUSTOMER) {
            if (cargoContract.getCustomerId() != user.getId()) {
                throw new FreightException(CONTRACT_NOT_FOR_USER);
            }
            if (!CUSTOMER_STATUS.contains(statusRequest)) {
                throw new FreightException(CONTRACT_STATUS_NOT_ALLOWED);
            }

        // User is TRANSPORTER
        } else if (user.getType() == Type.TRANSPORTER) {
            if (cargoContract.getTransporterId() != user.getId()) {
                throw new FreightException(CONTRACT_NOT_FOR_USER);
            }
            if (!TRANSPORTER_STATUS.contains(statusRequest)) {
                throw new FreightException(CONTRACT_STATUS_NOT_ALLOWED);
            }
        }
    }
}
