package anagram;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class AnagramDemo {

	
	private void printAnagrams(String filename){
	Map<String, Set<String>> anagramMap = new HashMap<>();

	Path path = Paths.get(filename);
	try (
			BufferedReader in = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
	    while(true) {
	        String word = in.readLine();
	        if (word == null) {
	            break;
	        }
	        String key = sortEachWord(word);
	        Set<String> words = anagramMap.get(key);
	        if (words == null) {
	            words = new TreeSet<String>();
	            anagramMap.put(key, words);
	        }
	        words.add(word);
	    }
	}
	catch (IOException e){
	//e.printStackTrace();	
		System.out.println("Invalid File or Filename");
	}
	catch (Exception e){
		//e.printStackTrace();	
			System.out.println("Error Occured while reading the file");
		}
	
	for (Set<String> anagrams : anagramMap.values()) {
	    if (anagrams.size() > 1) {
	        System.out.println(anagrams);
	    }
	}
	}
	
	
	static String sortEachWord(String word) {
	    char[] letters = word.toCharArray();
	    Arrays.sort(letters);
	    return new String(letters);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AnagramDemo anagramDemo =new AnagramDemo();
		System.out.println("************************Program Started*****************");
		Scanner input=new Scanner(System.in);
		System.out.println("Kindly Enter the path of the file to read");
		String filename=input.nextLine();
		input.close();
		anagramDemo.printAnagrams(filename);
	}

}
