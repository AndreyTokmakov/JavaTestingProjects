/**
 * 
 */
package ClassesUnderTests;

/**
 * @author a.tokmakov
 *
 */
public class Transformer {
	
	public Transformer() {
		System.err.println("Creating Transformer");
	}
	
	public String concatString(String str1, String str2) {
		return str1 + str2;
	}
	
	public int sumInts(int val1, int val2) {
		return val1 + val2;
	}

	public void close() {
		System.err.println("Closing Transformer");
	}

	public static void main(String[] args) {

	}
}
