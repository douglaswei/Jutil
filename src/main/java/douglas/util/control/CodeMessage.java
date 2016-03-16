package douglas.util.control;

/**
 * Author:  wgz
 * Date:    16/3/16
 * Time:    下午2:44
 * Desc:
 */
enum CodeMessage {
    SUCCESS(200, "success"),
    FAIL(-1, "success");

    private int code;

    private String msg;

    CodeMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
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

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
