package cipher;

import java.util.Arrays;

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

    public static byte[][] generateKeys(byte[] key, int shiftSize, int keySize, int amount) {
	byte[] sourceKey = Arrays.copyOf(key, key.length);
	byte[][] keys = new byte[amount][keySize];

	sourceKey = expandKey(sourceKey, keySize);

	for (int i = 0; i < keys.length; i++)
	    keys[i] = (sourceKey = circularShift(sourceKey, shiftSize));

	return keys;
    }

    public static byte[] expandKey(byte[] key, int size) {
	if (key.length > size)
	    throw new IllegalArgumentException("Key is bigger than defined size: " + key.length + " > " + size);

	if (key.length == size)
	    return key;

	byte[] expandedKey = Arrays.copyOf(key, size);

	int i = key.length;
	int j = 0;

	while (i < size) {
	    if (j >= key.length)
		j = 0;

	    expandedKey[i++] = key[j++];
	}

	return expandedKey;
    }

    public static byte[] circularShift(byte[] source, int shift) {
	int remainingShift = shift;
	int currentShift;
	byte bitsFromFirstByte;
	byte[] array = Arrays.copyOf(source, source.length);

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
