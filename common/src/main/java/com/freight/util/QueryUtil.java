package com.freight.util;

import java.util.Collection;
import java.util.List;

import static java.lang.String.join;
import static java.util.stream.Collectors.toList;

/**
 * Created by toshikijahja on 10/23/18.
 */
public class QueryUtil<T> {

    public static String addQuote(final String string) {
        return "'" + string + "'";
    }

    /**
     * Will call toString() to Object passed
     * @param inputList
     * @return
     */
    public static String listObjectToSqlQuery(final Collection<? extends Object> inputList) {
        final List<String> quoteStrings = inputList.stream().map(Object::toString).collect(toList());
        return "(" + join(",", quoteStrings) + ") ";
    }

    /**
     * Will add quote and call toString() to Object passed
     * @param inputList
     * @return
     */
    public static String listStringToSqlQuery(final Collection<String> inputList) {
        final List<String> quoteStrings = inputList.stream().map(QueryUtil::addQuote).collect(toList());
        return "(" + join(",", quoteStrings) + ") ";
    }
}
