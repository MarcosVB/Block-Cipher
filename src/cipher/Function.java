package cipher;

public class Function {

    private Function() {
	throw new IllegalStateException("Non-instantiable class");
    }

    public static int function(int message, int[] keys) {
	int result = message;

	for (int i = 0; i < keys.length; i++) {
	    result ^= keys[i];
	}

	return result;
    }

}
