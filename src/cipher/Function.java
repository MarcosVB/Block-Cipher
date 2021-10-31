package cipher;

import java.util.Arrays;

import utils.Bitwise;

public class Function {

	private Function() {
		throw new IllegalStateException("Non-instantiable class");
	}

	public static byte[] function(byte[] message, byte[][] keys) {
		byte[] mixedMessage = Arrays.copyOf(message, message.length);

		for (int i = 0; i < keys.length; i++)
			mixedMessage = Bitwise.xor(mixedMessage, keys[i]);

		return mixedMessage;
	}

}
