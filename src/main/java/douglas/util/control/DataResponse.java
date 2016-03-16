package douglas.util.control;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:  wgz
 * Date:    16/3/16
 * Time:    上午11:58
 * Desc:
 */
public class DataResponse extends Response.Success {

    private Map<String, Object> data;

    public DataResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = new HashMap<>();
    }

    public DataResponse add(String name, Object value) {
        this.data.put(name, value);
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }
}

