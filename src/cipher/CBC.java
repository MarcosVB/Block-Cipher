package cipher;

import utils.ArrayUtils;

public class CBC {

    private static final int SHIFT_SIZE = 1;
    private static final int KEY_SIZE = 32;
    private static final int KEY_AMOUNT = 8;
    private static final int INIT_VECTOR = 0b10101010110011001111000011111111;

    public int[] encrypt(int[] message, int key) {

	int[] keys = KeyScheduler.generateKeys(key, SHIFT_SIZE, KEY_SIZE, KEY_AMOUNT);
	int[] ans = new int[message.length];

	ans[0] = Function.function(message[0] ^ INIT_VECTOR, keys);

	for (int i = 1; i < message.length; i++) {
	    ans[i] = Function.function(message[i] ^ ans[i - 1], keys);
	}

	return ans;
    }

    public int[] decrypt(int[] message, int key) {

	int[] keys = KeyScheduler.generateKeys(key, SHIFT_SIZE, KEY_SIZE, KEY_AMOUNT);
	int[] ans = new int[message.length];

	keys = ArrayUtils.reverseArray(keys);

	ans[0] = Function.function(message[0], keys) ^ INIT_VECTOR;

	for (int i = 1; i < message.length; i++) {
	    ans[i] = Function.function(message[i], keys) ^ message[i - 1];
	}

	return ans;
    }

}
