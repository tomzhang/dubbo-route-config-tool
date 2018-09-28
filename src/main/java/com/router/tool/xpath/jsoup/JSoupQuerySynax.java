package com.router.tool.xpath.jsoup;

import org.apache.commons.lang3.StringUtils;

public class JSoupQuerySynax {

    public static boolean isValid(String query) {
        boolean flag = false;
        // Check for null
        if (StringUtils.isEmpty(query)) {
            flag = false;
        }
        try {
            QueryParser.parse(query);
            flag = true;
        } catch (Exception e) // these exceptions are thrown if something is not ok
        {
            // ignore
            flag = false; // If something is not ok, the query is invalid
        }

        return flag; // All ok, query is valid
    }
}
