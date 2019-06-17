package com.freight.resource;

import com.freight.dao.PortDao;
import com.freight.dao.SessionProvider;
import com.freight.model.Port;
import com.freight.persistence.DaoProvider;
import com.freight.response.PortListResponse;
import com.freight.view.PortView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Api(tags = {"public"})
@Path("/port")
public class PortResource {

    @Inject
    private DaoProvider daoProvider;

    @GET
    @ApiOperation(value = "Get ports")
    @Produces(MediaType.APPLICATION_JSON)
    public PortListResponse getPorts() {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final PortDao portDao = daoProvider.getDaoFactory().getPortDao(sessionProvider);
            final List<Port> ports = portDao.getAll();
            return new PortListResponse(ports.stream().map(PortView::new).collect(toList()));
        }
    }
}
