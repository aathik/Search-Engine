package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class BoyerSearch {

	//search the pattern
	static long searchByBoyerMoore(File file, String pattern) {
		long offset = 0;
		BoyerMoore boyerMoore;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, " \'+\t\n\r\f.,;:()-\"");
				while (tokenizer.hasMoreTokens()) {
					String a = tokenizer.nextToken();
					boyerMoore = new BoyerMoore(pattern);
					int temp = boyerMoore.search(a);
					if (temp == a.length()) {
						offset += 0;
					} else {
						offset += 1;
					}

				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		return offset;

	}

	//search the course through pattern matching
	public static void search_course() {

		long numOfBachelorInCanada = 0;
		long numOfMasterInCanada = 0;
		long numOfDiplomaInCanada = 0;


		// Specify the folder containing the HTML files
		File folder = new File("src/resources/textFiles/"+Main.get_country());
		// Get a list of all the HTML files in the folder
		File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
		// Loop through each HTML file and process it
		for (File file : files) {
			numOfBachelorInCanada += BoyerSearch.searchByBoyerMoore(file, "Bachelor");
			numOfMasterInCanada += BoyerSearch.searchByBoyerMoore(file, "Master");
			numOfDiplomaInCanada += BoyerSearch.searchByBoyerMoore(file, "Diploma");
		}
		System.out.println("======================"+Main.get_country().toUpperCase()+"===============================");
		System.out.println("No. of Bachelor Programs in Canada:" + numOfBachelorInCanada);
		System.out.println("No. of Master Programs in Canada:" + numOfMasterInCanada);
		System.out.println("No. of Diploma Courses in Canada:" + numOfDiplomaInCanada+"\n\n");
	}
}
