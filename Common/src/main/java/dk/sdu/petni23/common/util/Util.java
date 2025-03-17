package dk.sdu.petni23.common.util;

public class Util
{
    public static boolean arrayContains(int[] array, int i) {
        if (array == null)
            return false;
        for (int a : array) {
            if (a == i)
                return true;
        }
        return false;
    }

    public static int largestValue(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int i : array)
            max = Math.max(i, max);
        return max;
    }
}
