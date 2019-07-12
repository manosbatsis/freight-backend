package com.freight.persistence;

import com.freight.dao.AuthenticationDao;
import com.freight.dao.BulkTypeDao;
import com.freight.dao.CargoContractDao;
import com.freight.dao.CargoDao;
import com.freight.dao.CargoTypeDao;
import com.freight.dao.CompanyDao;
import com.freight.dao.ContainerTypeDao;
import com.freight.dao.ContractDao;
import com.freight.dao.IdentificationDao;
import com.freight.dao.PortDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.ShipCargoTypeDao;
import com.freight.dao.ShipDao;
import com.freight.dao.ShipmentDao;
import com.freight.dao.UserDao;

/**
 * Created by toshikijahja on 6/16/19.
 */
public interface DaoFactory {

    AuthenticationDao getAuthenticationDao(final SessionProvider sessionProvider);
    BulkTypeDao getBulkTypeDao(final SessionProvider sessionProvider);
    CargoDao getCargoDao(final SessionProvider sessionProvider);
    CargoContractDao getCargoContractDao(final SessionProvider sessionProvider);
    CargoTypeDao getCargoTypeDao(final SessionProvider sessionProvider);
    CompanyDao getCompanyDao(final SessionProvider sessionProvider);
    ContainerTypeDao getContainerTypeDao(final SessionProvider sessionProvider);
    ContractDao getContractDao(final SessionProvider sessionProvider);
    IdentificationDao getIdentificationDao(final SessionProvider sessionProvider);
    PortDao getPortDao(final SessionProvider sessionProvider);
    ShipDao getShipDao(final SessionProvider sessionProvider);
    ShipCargoTypeDao getShipCargoTypeDao(final SessionProvider sessionProvider);
    ShipmentDao getShipmentDao(final SessionProvider sessionProvider);
    UserDao getUserDao(final SessionProvider sessionProvider);
}
