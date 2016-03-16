package douglas.util;

/**
 * Author:  wgz
 * Date:    16/2/23
 * Time:    下午2:26
 * Desc:
 */
public class RawArrays {

    public static Integer[] asList(int[] arr) {
        Integer[] res = new Integer[arr.length];
        for (int idx = 0; idx < arr.length; ++idx) {
            res[idx] = arr[idx];
        }
        return res;
    }

    public static Long[] asList(long[] arr) {
        Long[] res = new Long[arr.length];
        for (int idx = 0; idx < arr.length; ++idx) {
            res[idx] = arr[idx];
        }
        return res;
    }

    public static Float[] asList(float[] arr) {
        Float[] res = new Float[arr.length];
        for (int idx = 0; idx < arr.length; ++idx) {
            res[idx] = arr[idx];
        }
        return res;
    }

    public static Double[] asList(double[] arr) {
        Double[] res = new Double[arr.length];
        for (int idx = 0; idx < arr.length; ++idx) {
            res[idx] = arr[idx];
        }
        return res;
    }

}
