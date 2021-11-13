package utils;

import java.util.Arrays;

public class Bitwise {

	private Bitwise() {
		throw new IllegalStateException("Utility class");
	}

	public static byte or(byte a, byte b) {
		return (byte) ((a & 0xff) | (b & 0xff));
	}

	public static byte xor(byte a, byte b) {
		return (byte) ((a & 0xff) ^ (b & 0xff));
	}

	public static byte xor(byte a, byte b, byte c) {
		return xor(a, xor(b, c));
	}

	public static byte xor(byte a, byte b, byte c, byte d) {
		return xor(a, xor(b, c, d));
	}

	public static byte xor(byte a, byte b, byte c, byte d, byte e) {
		return xor(a, xor(b, c, d, e));
	}

	public static byte xor(byte a, byte b, byte c, byte d, byte e, byte f) {
		return xor(a, xor(b, c, d, e, f));
	}

	public static byte[] xor(byte[] a, byte[] b) {
		if (a.length != b.length)
			throw new IllegalArgumentException("Byte array sizes are different: " + a.length + " != " + b.length);

		byte[] array = new byte[a.length];

		for (int i = 0; i < array.length; i++)
			array[i] = (byte) ((a[i] & 0xff) ^ (b[i] & 0xff));

		return array;
	}

	public static byte leftShift(byte number, int shift) {
		return (byte) ((number & 0xff) << shift);
	}

	public static byte rightShift(byte number, int shift) {
		return (byte) ((number & 0xff) >> shift);
	}

	public static byte rightShiftUnsigned(byte number, int shift) {
		return (byte) ((number & 0xff) >>> shift);
	}

	public static byte circularLeftShift(byte number, int shift) {
		int remainingShift = shift;
		int currentShift;
		byte shifted = number;

		while (remainingShift > 0) {
			currentShift = remainingShift < Byte.SIZE ? remainingShift : Byte.SIZE - 1;
			shifted = or(leftShift(shifted, currentShift), rightShiftUnsigned(shifted, Byte.SIZE - currentShift));
			remainingShift -= currentShift;
		}

		return shifted;
	}

	public static byte[] circularLeftShift(byte[] source, int shift) {
		int remainingShift = shift;
		int currentShift;
		byte bitsFromFirstByte;
		byte[] array = Arrays.copyOf(source, source.length);

		while (remainingShift > 0) {
			currentShift = remainingShift < Byte.SIZE ? remainingShift : Byte.SIZE - 1;
			bitsFromFirstByte = rightShiftUnsigned(array[0], Byte.SIZE - currentShift);

			for (int i = 0; i < array.length - 1; i++) {
				array[i] = or(leftShift(array[i], currentShift),
						rightShiftUnsigned(array[i + 1], Byte.SIZE - currentShift));
			}

			array[array.length - 1] = or(leftShift(array[array.length - 1], currentShift), bitsFromFirstByte);

			remainingShift -= currentShift;
		}

		return array;
	}

	public static byte[] circularRightShift(byte[] source, int shift) {
		int remainingShift = shift;
		int currentShift;
		byte bitsFromLastByte;
		byte[] array = Arrays.copyOf(source, source.length);

		while (remainingShift > 0) {
			currentShift = remainingShift < Byte.SIZE ? remainingShift : Byte.SIZE - 1;
			bitsFromLastByte = leftShift(array[array.length - 1], Byte.SIZE - currentShift);

			for (int i = array.length - 1; i > 0; i--) {
				array[i] = or(rightShiftUnsigned(array[i], currentShift),
						leftShift(array[i - 1], Byte.SIZE - currentShift));
			}

			array[0] = or(rightShiftUnsigned(array[0], currentShift), bitsFromLastByte);

			remainingShift -= currentShift;
		}

		return array;
	}

}
