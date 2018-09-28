/**
 * This document and its contents are protected by copyright 2005 and owned by Vobile Inc. The
 * copying and reproduction of this document and/or its content (whether wholly or partly) or any
 * incorporation of the same into any other material in any media or format of any kind is strictly
 * prohibited. All rights are reserved.
 *
 * Copyright (c) Vobile Inc. 2014
 */
package vdian.router.tool.xpath.jsoup;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>文件名称：NodeStorageHolder.java</p>
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2011-2099</p>
 * <p>公   司： 口袋购物 </p>
 * <p>内容摘要： </p>
 * <p>其他说明： </p>
 * <p>完成日期：2018年7月24日</p>
 *
 * @author dengkui@weidian.com
 * @version 1.0
 */
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
