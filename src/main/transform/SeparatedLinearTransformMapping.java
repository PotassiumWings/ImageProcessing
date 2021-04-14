package main.transform;

public class SeparatedLinearTransformMapping {
    public static int linearTransformMapping(int a, int b, int c, int d, int gray) {
        int res;
        // super(), uninitialized
        if (a == b) {
            return 0;
        }
        if (gray < a) {
            res = gray * c / a;
        } else if (gray > b) {
            res = (gray - b) * (255 - d) / (255 - b) + d;
        } else {
            res = (gray - a) * (d - c) / (b - a) + c;
        }
        return res;
    }
}
