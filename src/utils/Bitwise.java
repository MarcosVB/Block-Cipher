package utils;

public class Bitwise {

    private Bitwise() {
	throw new IllegalStateException("Utility class");
    }

    public static byte or(byte a, byte b) {
	return (byte) ((a & 0xff) | (b & 0xff));
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
