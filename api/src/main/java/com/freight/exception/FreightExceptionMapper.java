package com.freight.exception;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static com.freight.exception.ExceptionResponseBuilder.buildResponse;
import static com.freight.exception.GenericError.INTERNAL_ERROR;

/**
 * Created by toshikijahja on 3/26/19.
 */
@Singleton
public class FreightExceptionMapper implements ExceptionMapper<Exception> {

    private static Logger logger = LoggerFactory.getLogger(FreightExceptionMapper.class);

    @Override
    public Response toResponse(final Exception exception) {
        final ResponseError responseError = getResponseError(exception);
        return buildResponse(responseError);
    }

    private ResponseError getResponseError(final Exception exception) {
        if (exception instanceof FreightException) {
            return ((FreightException) exception).getResponseError();

        } else if (exception instanceof ClientErrorException) {
            final Response response = ((ClientErrorException) exception).getResponse();
            return new ResponseError() {
                @Override
                public String getErrorKey() {
                    return response.getStatusInfo().getReasonPhrase().toUpperCase().replace(' ', '_');
                }

                @Override
                public String getErrorDescription() {
                    return response.getStatusInfo().getReasonPhrase().toUpperCase();
                }

                @Override
                public int getHttpResponseCode() {
                    return response.getStatus();
                }
            };
        }

        logger.error("UNEXPECTED exception={}", exception);
        return INTERNAL_ERROR;
    }
}

