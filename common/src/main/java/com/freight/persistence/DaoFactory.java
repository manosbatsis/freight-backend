package com.freight.persistence;

import com.freight.dao.AuthenticationDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.UserDao;

/**
 * Created by toshikijahja on 6/16/19.
 */
public interface DaoFactory {

    AuthenticationDao getAuthenticationDao(final SessionProvider sessionProvider);
    UserDao getUserDao(final SessionProvider sessionProvider);

}
