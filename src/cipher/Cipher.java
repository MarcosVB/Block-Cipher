package cipher;

import java.util.Arrays;

import utils.Bitwise;

public class Cipher {

	private Cipher() {
		throw new IllegalStateException("Non-instantiable class");
	}

	public static byte[] function(byte[] message, byte[][] keys) {
		byte[] mixedMessage = Arrays.copyOf(message, message.length);

		for (int i = 0; i < keys.length; i++) {
			mixedMessage = Bitwise.xor(mixedMessage, keys[i]);
			mixedMessage = Bitwise.circularLeftShift(mixedMessage, 5); // issue with certain shift sizes
		}

		return mixedMessage;
	}

	public static byte[] unFunction(byte[] message, byte[][] keys) {
		byte[] mixedMessage = Arrays.copyOf(message, message.length);

		for (int i = 0; i < keys.length; i++) {
			mixedMessage = Bitwise.circularRightShift(mixedMessage, 5); // issue with certain shift sizes
			mixedMessage = Bitwise.xor(mixedMessage, keys[i]);
		}

		return mixedMessage;
	}

}
