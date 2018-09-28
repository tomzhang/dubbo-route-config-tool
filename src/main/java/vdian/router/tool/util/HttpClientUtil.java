package vdian.router.tool.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
public class HttpClientUtil {

    public static String httpGetRequest(String url) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        }
        return StringUtils.EMPTY;
    }

}
