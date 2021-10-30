package utils;

public class Bitwise {

    private Bitwise() {
	throw new IllegalStateException("Utility class");
    }

    public static byte or(byte a, byte b) {
	return (byte) ((a & 0xff) | (b & 0xff));
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

}
