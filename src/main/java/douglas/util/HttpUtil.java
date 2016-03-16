package douglas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Author:  wgz
 * Date:    16/2/18
 * Time:    下午5:34
 * Desc:
 */
public class HttpUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    public static InputStream get(String urlStr, int retry) throws IOException {

        if (retry < 0) {
            String message = String.format("fail to get from url [%s]", urlStr);
            throw new IOException(message);
        }

        try {
            URL url = new URL(urlStr);

            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(3 * 1000);
            connection.setReadTimeout(5 * 1000);
            connection.connect();
            return connection.getInputStream();
        } catch (IOException e) {
            if (e instanceof ConnectException
                    || e instanceof SocketTimeoutException) {
                return get(urlStr, retry - 1);
            } else {
                throw e;
            }
        }
    }
}
