package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetWords {

	// a function that takes file path as an argument and returns a list of words in that text file
	public static List<String> extractWords(String filePath) throws IOException {
		List<String> words = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.replaceAll("[^a-zA-Z0-9]", " "); 
				String[] lineWords = line.split(" ");
				for(String word : lineWords) {
					// if word lower case is not in the list and word length is greater than 1
					if (!words.contains(word.toLowerCase()) && word.length() > 1) {
						// skip word that contain non-alphabetic characters
						if (word.matches("[a-zA-Z]+")) {
							words.add(word.toLowerCase());
						}	
					}
				}
			}
		}
		return words;
	}

	public static void scrape(String country) throws IOException {

		// get list of all files in the directory
		String source_path = "src/resources/textFiles/"+country+'/';
		File dir = new File(source_path);
		String files_list[] = dir.list();


		// get list of all words in all files
		List<String> words = new ArrayList<>();
		for (String file : files_list) {

			words.addAll(extractWords(source_path + file));
		}
		// System.out.println("Here"+ words);
		// if file exist at AllWordsFilePath path then read all words from AllWordsFilePath file and append to the list of words
		String dest_path = "src/resources/words/EnglishWords.txt";

		if (new File(dest_path).exists()) {
			words.addAll(extractWords(dest_path));
		}

		// eliminate duplicates
		List<String> uniqueWords = new ArrayList<>();
		for (String word : words) {
			if (!uniqueWords.contains(word)) {
				uniqueWords.add(word);
			}
		}

		// sort the list of words
		Collections.sort(uniqueWords);

		// save the list of words to a file with each word on a new line
		BufferedWriter writer = new BufferedWriter(new FileWriter(dest_path, false));
		//        java.io.PrintWriter writer = new java.io.PrintWriter(wS.AllWordsFilePath, "UTF-8"); 
		for (String word : uniqueWords) {
			writer.write(word + '\n');
		}
		writer.close();

		// print the number of words to the console
		System.out.println("Number of words: " + uniqueWords.size());
	}

	//public static void main(String[] args) throws IOException {
	//	scrape("canada");
	//}
}