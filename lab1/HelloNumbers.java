public class HelloNumbers {
	public static void main(String[] args) {
		int count = 0;

		for (int i = 0; i < 10; i++) {
			System.out.print((count += i) + " ");
		}

		System.out.println("");
	}
}