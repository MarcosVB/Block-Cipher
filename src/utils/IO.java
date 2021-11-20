package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

public class IO {

	private IO() {
		throw new IllegalStateException("Utility class");
	}

	public static InputStreamReader getReader(String path, String charset)
			throws FileNotFoundException, UnsupportedEncodingException {
		return new InputStreamReader(new FileInputStream(path), charset);
	}

	public static OutputStreamWriter getWriter(String path, String charset)
			throws FileNotFoundException, UnsupportedEncodingException {
		return new OutputStreamWriter(new FileOutputStream(path), charset);
	}

	public static List<Byte> getByteList(String filePath, String charset) throws IOException {
		InputStreamReader input = getReader(filePath, charset);
		LinkedList<Byte> bytes = new LinkedList<>();

		int character;

		while ((character = input.read()) != -1)
			bytes.add((byte) character);

		input.close();
		return bytes;
	}

	public static byte[][] read(String filePath, String charset) throws IOException {
		LinkedList<Byte> bytes = (LinkedList<Byte>) getByteList(filePath, charset);

		int i = 0;
		int j = 0;
		int k = 0;

		byte[][] blocks = new byte[(int) Math.floor(bytes.size() / 6.0 + 1)][6];

		while (bytes.size() > 5) {
			while (j < 6)
				blocks[i][j++] = bytes.removeFirst();
			i++;
			j = 0;
		}

		byte padding = 0;

		if (!bytes.isEmpty()) {
			while (!bytes.isEmpty())
				blocks[i][k++] = bytes.removeFirst();

			while (k < 5) {
				blocks[i][k++] = 0;
				padding++;
			}

			while (k < 6) {
				blocks[i][k++] = ++padding;
			}
		}

		if (padding == 0) {
			for (int m = 0; m < 6; m++) {
				blocks[blocks.length - 1][m] = padding;
			}
		}

		return blocks;
	}

	public static void write(String filePath, byte[][] blocks, String charset) throws IOException {
		OutputStreamWriter output = getWriter(filePath, charset);

		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				output.write(Byte.toUnsignedInt(blocks[i][j]));
			}
		}

		output.close();
	}

	public static byte[][] readWithoutPadding(String filePath, String charset) throws IOException {
		LinkedList<Byte> bytes = (LinkedList<Byte>) getByteList(filePath, charset);

		int i = 0;
		int j = 0;
		int k = 0;

		byte[][] blocks = new byte[(int) Math.ceil(bytes.size() / 6.0)][6];

		while (bytes.size() > 5) {
			while (j < 6)
				blocks[i][j++] = bytes.removeFirst();
			i++;
			j = 0;
		}

		if (!bytes.isEmpty()) {
			while (!bytes.isEmpty())
				blocks[i][k++] = bytes.removeFirst();
		}

		return blocks;
	}

	public static void writeWithoutPadding(String filePath, byte[][] blocks, String charset) throws IOException {
		OutputStreamWriter output = getWriter(filePath, charset);

		int lastRow = blocks.length - 1;
		int lastColumn = blocks[lastRow].length - 1;
		byte padding = blocks[lastRow][lastColumn];

		for (int i = 0; i < lastRow; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				output.write(Byte.toUnsignedInt(blocks[i][j]));
			}
		}

		if (padding > 0) {
			for (int j = 0; j < blocks[lastRow].length - padding; j++) {
				output.write(Byte.toUnsignedInt(blocks[lastRow][j]));
			}
		}

		output.close();
	}

}
