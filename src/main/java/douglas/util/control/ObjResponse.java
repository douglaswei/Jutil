package douglas.util.control;

/**
 * Author:  wgz
 * Date:    16/3/16
 * Time:    下午2:31
 * Desc:
 */
public class ObjResponse<T> extends Response.Success {
    private T data;

    public ObjResponse() {
        super();
    }

    public ObjResponse(T data) {
        super();
        this.data = data;
    }

    public ObjResponse(String msg, T data) {
        super();
        this.msg = msg;
        this.data = data;
    }
}
