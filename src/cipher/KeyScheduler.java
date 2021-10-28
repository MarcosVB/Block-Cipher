package cipher;

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

}
