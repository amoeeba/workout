package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Anagrams {

	public static void main(String[] args) throws IOException {

		File directory = null;
		String str = null;
		BufferedReader br = null;

		String inputDirectory = "input/sample.txt";

		directory = new File(inputDirectory);
		List<String> list = new ArrayList<String>();

		if (!inputDirectory.isEmpty()) {

			br = new BufferedReader(new FileReader(directory));

			while ((str = br.readLine()) != null) {

				list.add(str);

			}

		} else {

			System.out.println("File is empty..");
		}

		Anagrams.isAnagram(list);

		if (br != null) {

			br.close();
		}
	}

	public static void isAnagram(List<String> list) {

		boolean result = false;
		String first = null;
		String one = null;
		Set<String> temp = new HashSet<String>();

		if (!list.isEmpty()) {

			first = list.get(0);

			one = first.replaceAll("[\\s+\\W+]", "").toLowerCase();
		}

		for (String value : list) {

			final String two = value.replaceAll("[\\s+\\W+]", "").toLowerCase();

			if (one.length() == two.length()) {

				final char[] oneArray = one.toCharArray();

				final char[] twoArray = two.toCharArray();

				Arrays.sort(oneArray);

				Arrays.sort(twoArray);

				result = Arrays.equals(oneArray, twoArray);

				if (result) {

					temp.add(one);
					temp.add(two);
				}

			}

		}

		if (temp.size() > 1) {
			System.out.println(temp);
		}

		if (!list.isEmpty()) {

			list.removeAll(temp);
			isAnagram(list);
		}

	}

}
