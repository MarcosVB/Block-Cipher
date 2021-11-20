package app;

import java.io.IOException;
import java.util.Scanner;

import cipher.CBC;
import utils.IO;

public class App {
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		String[] commandSteps;
		String command;

		Log.welcome();
		Log.path();

		while (!(command = scanner.nextLine()).equals("q")) {

			commandSteps = command.split(" ");

			try {
				router(commandSteps);
			} catch (Exception e) {
				Log.log(e.getMessage());
				e.printStackTrace();
			}

			Log.path();
		}

		Log.log("End of execution.\n");
		scanner.close();
	}

	public static void router(String[] commandSteps) throws IOException {
		String response;

		switch (commandSteps[0]) {
			case "encrypt":
				Log.log("Encrypting...");
				response = encrypt(commandSteps[1], commandSteps[2], commandSteps[3]);
				Log.log("Encrypted: " + response);
				break;

			case "decrypt":
				Log.log("Decrypting...");
				response = decrypt(commandSteps[1], commandSteps[2], commandSteps[3]);
				Log.log("Decrypted: " + response);
				break;

			case "help":
				Log.help();
				break;

			default:
				Log.log("Invalid command: " + commandSteps[0]);
		}
	}

	public static String encrypt(String filePath, String fileCharset, String key) throws IOException {
		String filePathEncrypted = filePath + ".brt";
		byte[][] blocks = IO.read(filePath, fileCharset);
		byte[] cKey = buildKey(key);

		blocks = CBC.encrypt(blocks, cKey);

		IO.write(filePathEncrypted, blocks, fileCharset);

		return filePathEncrypted;
	}

	public static String decrypt(String filePath, String fileCharset, String key) throws IOException {
		String filePathWithoutType = filePath.replace(".brt", "");
		String[] pathSplited = filePathWithoutType.split("\\.");
		String filePathDecrypted = pathSplited[0] + "_decrypted";

		for (int i = 1; i < pathSplited.length; i++)
			filePathDecrypted += "." + pathSplited[i];

		byte[][] blocks = IO.readWithoutPadding(filePath, fileCharset);
		byte[] cKey = buildKey(key);

		blocks = CBC.decrypt(blocks, cKey);

		IO.writeWithoutPadding(filePathDecrypted, blocks, fileCharset);

		return filePathDecrypted;
	}

	private static byte[] buildKey(String key) {
		byte[] ckey;

		if (key.matches("[0-1]+") && key.length() >= Byte.SIZE) {
			ckey = new byte[key.length() / Byte.SIZE];

			for (int i = 0; i < ckey.length; i++) {
				String s = key.substring(i * Byte.SIZE, i * Byte.SIZE + Byte.SIZE - 1);
				ckey[i] = Byte.valueOf(s, 2);
			}

			return ckey;
		}

		if (key.matches("^[0-9]+(-[0-9]+)*[0-9+]$")) {
			String[] numbers = key.split("-");

			ckey = new byte[numbers.length];

			for (int i = 0; i < ckey.length; i++)
				ckey[i] = (byte) Integer.parseInt(numbers[i]);

			return ckey;
		}

		throw new IllegalArgumentException("Invalid key format: " + key);
	}

}
