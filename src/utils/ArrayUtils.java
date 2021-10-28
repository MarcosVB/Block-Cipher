package utils;

public class ArrayUtils {

    private ArrayUtils() {
	throw new IllegalStateException("Utility class");
    }

    public static int[] reverseArray(int[] array) {
	int[] newArray = new int[array.length];

	for (int i = 0; i < array.length; i++)
	    newArray[i] = array[array.length - 1 - i];

	return newArray;
    }

}
