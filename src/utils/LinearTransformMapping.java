package utils;

public class LinearTransformMapping {
    public static int linearTransformMapping(int a, int b, int c, int d, int gray) {
        int res;
        // super(), uninitialized
        if (a == b) {
            return 0;
        }
        if (gray < a) {
            res = c;
        } else if (gray > b) {
            res = d;
        } else {
            res = (gray - a) * (d - c) / (b - a) + c;
        }
        return res;
    }
}
