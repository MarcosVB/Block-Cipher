package utils;

public class ArrayUtils {

	private ArrayUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static byte[][] reverse(byte[][] array) {
		byte[][] newArray = new byte[array.length][array[0].length];

		for (int i = 0; i < array.length; i++)
			newArray[i] = array[array.length - 1 - i];

		return newArray;
	}

}
