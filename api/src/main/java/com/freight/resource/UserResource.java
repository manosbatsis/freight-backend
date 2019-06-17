package com.freight.resource;

import com.freight.auth.UserAuth;
import com.freight.auth.UserScope;
import com.freight.dao.SessionProvider;
import com.freight.dao.UserDao;
import com.freight.exception.FreightException;
import com.freight.model.User;
import com.freight.persistence.DaoProvider;
import com.freight.view.UserView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static com.freight.exception.BadRequest.USER_NOT_EXIST;

@Api(tags = {"user"})
@Path("/user")
public class UserResource {

    @Inject
    private DaoProvider daoProvider;

    @Inject
    private Provider<UserScope> userScopeProvider;

    @GET
    @ApiOperation(value = "Get user")
    @Produces(MediaType.APPLICATION_JSON)
    @UserAuth(optional = false)
    public UserView getUser() {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);
            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));
            return new UserView(user);
        }
    }
}
