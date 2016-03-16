package douglas.util.control;

/**
 * Author:  wgz
 * Date:    16/3/16
 * Time:    下午12:03
 * Desc:
 */
public class Response {

    protected int code;
    protected String msg;

    public Response() {
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(CodeMessage cm) {
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    class Success extends Response {
        public Success() {
            super(CodeMessage.SUCCESS);
        }
    }

}
