package main.crypto;

public class Splittu {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String patchString = "C:\\Users\\Ja\\Documents\\AppDzienniksql.sql";
		String[] ppStrings = patchString.split("\\\\");
		patchString = patchString.replaceAll("\\.[a-zA-Z]+$", "."+"KUKU");
		System.out.println(patchString);
		System.out.println(ppStrings[ppStrings.length - 1]);

	}

}
