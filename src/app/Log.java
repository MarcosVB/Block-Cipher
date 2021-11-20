package app;

import java.io.File;

public class Log {

	private static final File file = new File(Log.class.getProtectionDomain().getCodeSource().getLocation().getPath());

	public static void path() {
		System.out.println("\n" + file.getPath());
	}

	public static void log(String log) {
		System.out.println(log);
	}

	public static void welcome() {
		System.out.println("\n\n**** TEORIA DA INFORMAÇÃO 202102 - T4 - BLOCK CIPHER ****");
		System.out.println("\nUse command <help> to learn how to use it");
	}

	public static void help() {
		System.out.println("\nUse command line to call features");
		commandStructure();
		examples();
		keyDefinition();
		System.out.println("\nPath of the file is relative to the .jar location.");
		System.out.println("\nUse command <q> to leave");
	}

	public static void commandStructure() {
		System.out.println("\nCommand structure:");
		System.out.println("encrypt <filePath> <fileCharset> <key>");
		System.out.println("decrypt <filePath> <fileCharset> <key>");
	}

	public static void examples() {
		System.out.println("\nExamples:");
		System.out.println("> encrypt alice29.txt utf-8 101-102-103-104");
		System.out.println("> encrypt alice29.txt utf-8 1100101110011011001111101000");
		System.out.println("> decrypt alice29.txt utf-8 101-102-103-104");
		System.out.println("> decrypt sum Cp1252 1100101110011011001111101000");
		System.out.println("> encrypt ../folder/alice29.txt Cp1252 101-102-103-104");
	}

	public static void keyDefinition() {
		System.out.println("\nKeys:");
		System.out.println("This block cipher encrypts using the CBC structure.");
		System.out.println("The block size is 6 bytes, the provided key must have the maximum size of 6 bytes.");
		System.out.println("Smaller keys will be expanded, bigger keys will not be accepted.");
	}

}
