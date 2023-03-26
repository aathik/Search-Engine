package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

public class BruteForce_PageRank {
	public static int c;
	public static Map<String, Integer> map= new HashMap<String, Integer>();

	//search the pattern in the file
	public static void search(String pat, String txt)
	{
		pat=pat.toLowerCase();
		txt=txt.toLowerCase();
		int l1 = pat.length();
		int l2 = txt.length();
		int i = 0, j = l2 - 1;

		for (i = 0, j = l2 - 1; j < l1;) {

			if (txt.equals(pat.substring(i, j + 1))) {
				c++;
			}
			i++;
			j++;
		}
	}

	//show the contents of the matching word
	public static void show_occurences(String pat, String txt)
	{
		pat=pat.toLowerCase();
		txt=txt.toLowerCase();
		int l1 = pat.length();
		int l2 = txt.length();
		int i = 0, j = l2 - 1;

		for (i = 0, j = l2 - 1; j < l1;) {

			if (txt.equals(pat.substring(i, j + 1))) {
				String[] words = pat.split(";"); // split on commas, exclamation marks, question marks, and whitespace
				System.out.println("Name of the University: "+words[1]+"\nCourse offered: "+words[0]+"\nDegree offered: "+words[2]+"");
				System.out.println("==================================================\n");
			}
			i++;
			j++;
		}
	}

	public static void page_rank(String city) throws FileNotFoundException {

		File directory=new File("src/resources/textFiles/"+Main.get_country());
		File filesList[] = directory.listFiles();

		//read all files of a country
		for(File file : filesList) {
			try {
				Scanner myReader = new Scanner(file);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					search(data,city);
				}
				myReader.close();
				//map the file with occurrence
				map.put(file.getName(), c);
				c=0;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		// add elements to list and sort according to the values
		List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
		list.sort((o1, o2) -> o1.getValue().compareTo(o2.getValue()));

		// put the sorted elements back into a LinkedHashMap
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		List<String> keys = new ArrayList<>(sortedMap.keySet());
		ListIterator<String> iterator = keys.listIterator(keys.size());

		System.out.println("Top 5 ranked pages for the place - "+city+": ");
		System.out.println("--------------------------------------------------");

		int count = 0;
		while (iterator.hasPrevious() && count < 5) {
			String key = iterator.previous();
			Integer value = sortedMap.get(key);
			System.out.println("Page = " + key + ", Occcurences = " + value);
			count++;
		}

		iterator = keys.listIterator(keys.size());
		System.out.println("\nCourse details from the top 5 ranked pages for the place - "+city+": ");
		System.out.println("--------------------------------------------------");

		count = 0;
		while (iterator.hasPrevious() && count < 5) {
			String key = iterator.previous();
			count++;

			File file1=new File("src/resources/textFiles/"+Main.get_country()+"/"+key);
			Scanner myReader = new Scanner(file1);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				show_occurences(data,city);
			}
			myReader.close();
		}

	}
}

