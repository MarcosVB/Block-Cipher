package cipher;

import java.util.Arrays;

import utils.Bitwise;

public class KeyScheduler {

	private KeyScheduler() {
		throw new IllegalStateException("Non-instantiable class");
	}

	public static byte[][] generateKeys(byte[] key, int shiftSize, int keySize, int amount) {
		byte[][] keys = new byte[amount][keySize];

		keys[0] = Bitwise.circularLeftShift(expandKey(key, keySize), shiftSize);

		for (int i = 1; i < keys.length; i++)
			keys[i] = Bitwise.circularLeftShift(keys[i - 1], shiftSize);

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

}
