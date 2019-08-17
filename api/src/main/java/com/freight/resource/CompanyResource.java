package com.freight.resource;

/**
 * Created by toshikijahja on 6/16/19.
 */

import com.freight.auth.UserAuth;
import com.freight.auth.UserScope;
import com.freight.dao.CompanyDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.UserDao;
import com.freight.exception.FreightException;
import com.freight.model.Company;
import com.freight.model.User;
import com.freight.persistence.DaoProvider;
import com.freight.request_body.CompanyRequestBody;
import com.freight.response.CompanyResponse;
import com.freight.view.CompanyView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static com.freight.exception.BadRequest.COMPANY_NOT_EXIST;
import static com.freight.exception.BadRequest.USER_NOT_EXIST;

@Api(tags = {"user", "public"})
@Path("/company")
public class CompanyResource {

    @Inject
    private DaoProvider daoProvider;

    @Inject
    private Provider<UserScope> userScopeProvider;

    @GET
    @ApiOperation(value = "Get company")
    @Produces(MediaType.APPLICATION_JSON)
    @UserAuth(optional = false)
    public CompanyResponse getCompany() {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final CompanyDao companyDao = daoProvider.getDaoFactory().getCompanyDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);
            final User user = userDao.getByGuid(userScopeProvider.get().getGuid())
                    .orElseThrow(() -> new FreightException(USER_NOT_EXIST));

            if (user.getCompanyId() == null) {
                throw new FreightException(COMPANY_NOT_EXIST);
            }

            final Company company = companyDao.getByIdOptional(user.getCompanyId())
                    .orElseThrow(() -> new FreightException(COMPANY_NOT_EXIST));
            return new CompanyResponse(new CompanyView(company));
        }
    }

    @GET
    @ApiOperation(value = "Get company by companyId")
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CompanyResponse getCompanyById(@PathParam("id") final int companyId) {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final CompanyDao companyDao = daoProvider.getDaoFactory().getCompanyDao(sessionProvider);
            final Company company = companyDao.getByIdOptional(companyId)
                    .orElseThrow(() -> new FreightException(COMPANY_NOT_EXIST));
            return new CompanyResponse(new CompanyView(company));
        }
    }

    @POST
    @ApiOperation(value = "Create company and update user's company")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @UserAuth(optional = false)
    public CompanyResponse createCompany(final CompanyRequestBody companyRequestBody) {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final CompanyDao companyDao = daoProvider.getDaoFactory().getCompanyDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);
            sessionProvider.startTransaction();
            final Company company = companyDao.createCompany(companyRequestBody.getName(), companyRequestBody.getType());
            userDao.updateCompany(userScopeProvider.get().getGuid(), company.getId());
            sessionProvider.commitTransaction();
            return new CompanyResponse(new CompanyView(company));
        }
    }
}
