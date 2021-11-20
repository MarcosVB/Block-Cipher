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
			mixedMessage = sBox(mixedMessage);
			mixedMessage = Bitwise.circularLeftShift(mixedMessage, i + 1);
		}

		return mixedMessage;
	}

	public static byte[] unFunction(byte[] message, byte[][] keys) {
		byte[] mixedMessage = Arrays.copyOf(message, message.length);

		for (int i = 0; i < keys.length; i++) {
			mixedMessage = Bitwise.circularRightShift(mixedMessage, keys.length - i);
			mixedMessage = sBoxInverse(mixedMessage);
			mixedMessage = Bitwise.xor(mixedMessage, keys[i]);
		}

		return mixedMessage;
	}

	public static byte[] sBox(byte[] message) {
		byte[] confusedMessage = new byte[message.length];

		for (int i = 0; i < confusedMessage.length; i++) {
			confusedMessage[i] = Bitwise.xor(
					message[i],
					Bitwise.circularLeftShift(message[i], 1),
					Bitwise.circularLeftShift(message[i], 2),
					Bitwise.circularLeftShift(message[i], 3),
					Bitwise.circularLeftShift(message[i], 4),
					(byte) 0b01100011);
		}

		return confusedMessage;
	}

	public static byte[] sBoxInverse(byte[] confusedMessage) {
		byte[] message = new byte[confusedMessage.length];

		for (int i = 0; i < message.length; i++) {
			message[i] = Bitwise.xor(
					Bitwise.circularLeftShift(confusedMessage[i], 1),
					Bitwise.circularLeftShift(confusedMessage[i], 3),
					Bitwise.circularLeftShift(confusedMessage[i], 6),
					(byte) 0b00000101);
		}

		return message;
	}

}
