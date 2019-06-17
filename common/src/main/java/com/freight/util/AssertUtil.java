package com.freight.util;

import com.freight.exception.FreightException;
import com.freight.exception.ResponseError;

/**
 * Created by toshikijahja on 6/16/19.
 */
public class AssertUtil {

    public static void assertNotNull(final Object object,
                                     final ResponseError responseError) throws FreightException {
        if (object == null) {
            throw new FreightException(responseError);
        }
    }
}
