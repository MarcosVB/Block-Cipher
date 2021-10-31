package cipher;

import utils.ArrayUtils;
import utils.Bitwise;

public class CBC {

    private static final int SHIFT_SIZE = 1;
    private static final int KEY_AMOUNT = 8;
    private static final byte[] INIT_VECTOR = { 101 };

    public byte[][] encrypt(byte[][] message, byte[] key) {
	byte[][] ans = new byte[message.length][message[0].length];
	byte[][] keys = KeyScheduler.generateKeys(key, SHIFT_SIZE, message[0].length, KEY_AMOUNT);

	ans[0] = Function.function(Bitwise.xor(message[0], KeyScheduler.expandKey(INIT_VECTOR, message[0].length)),
		keys);

	for (int i = 1; i < message.length; i++)
	    ans[i] = Function.function(Bitwise.xor(message[i], ans[i - 1]), keys);

	return ans;
    }

    public byte[][] decrypt(byte[][] message, byte[] key) {
	byte[][] ans = new byte[message.length][message[0].length];
	byte[][] keys = KeyScheduler.generateKeys(key, SHIFT_SIZE, message[0].length, KEY_AMOUNT);

	keys = ArrayUtils.reverse(keys);

	ans[0] = Bitwise.xor(Function.function(message[0], keys),
		KeyScheduler.expandKey(INIT_VECTOR, message[0].length));

	for (int i = 1; i < message.length; i++)
	    ans[i] = Bitwise.xor(Function.function(message[i], keys), message[i - 1]);

	return ans;
    }

}
