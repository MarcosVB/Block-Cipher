package cipher;

import utils.Bitwise;

public class KeyScheduler {

    private KeyScheduler() {
	throw new IllegalStateException("Non-instantiable class");
    }

    public static int[] generateKeys(int input, int shiftSize, int keySize, int amount) {
	int shifted = input;
	int[] keys = new int[amount];

	for (int i = 0; i < amount; i++) {
	    shifted = (shifted >>> shiftSize) | (shifted << (Integer.SIZE - shiftSize));
	    keys[i] = shifted >>> (Integer.SIZE - keySize);
	}

	return keys;
    }

    public static byte[] circularShift(byte[] array, int shift) {
	int remainingShift = shift;
	int currentShift;
	byte bitsFromFirstByte;

	while (remainingShift > 0) {
	    currentShift = remainingShift < Byte.SIZE ? remainingShift : Byte.SIZE - 1;
	    bitsFromFirstByte = Bitwise.rightShiftUnsigned(array[0], Byte.SIZE - currentShift);

	    for (int i = 0; i < array.length - 1; i++) {
		array[i] = Bitwise.or(Bitwise.leftShift(array[i], currentShift),
			Bitwise.rightShiftUnsigned(array[i + 1], Byte.SIZE - currentShift));
	    }

	    array[array.length - 1] = Bitwise.or(Bitwise.leftShift(array[array.length - 1], currentShift),
		    bitsFromFirstByte);

	    remainingShift -= currentShift;
	}

	return array;
    }

}
