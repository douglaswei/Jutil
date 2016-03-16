package douglas.util.control;

/**
 * Author:  wgz
 * Date:    16/3/16
 * Time:    下午12:00
 * Desc:
 */
public class DataRequest <T> {

    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}