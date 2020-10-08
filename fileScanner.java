import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.Arrays;
import java.io.PrintWriter;

public class fileScanner {
    
    /*
     * Counts the number of letters and other characters then, adds the number of letters 
     * to the number of characters to get total characters.
     * 
     * Other characters include anything that is not a letter that can be represented
     * from a-z with the toLowerCase() function. Everything else, including spaces,
     * are counted as an other character.
     * 
     * Returns array of {numLetters, numOtherChars, totalChars}.
     * 
     *  @param String line
     *  @return int[]
     */
    public static int[] charCounter(String line) {
        int[] returnArr = {0,0,0}; //{numLetters, numOtherChars, totalChars}
        char currChar = ' ';
        
        for(int i = 0; i<line.length();i++) {
            currChar = line.charAt(i);
            if(isLetter(currChar)) {
                returnArr[0]++; //increment letter
            }else {
                returnArr[1]++; //increment other char
            }
        }
        
        returnArr[2] = returnArr[0] + returnArr[1]; //totalize values
        
        return returnArr;
    }
    
    /*
     * Determines if a character is a letter. Returns true if so, false if not.
     * 
     * @param char c
     * @return boolean
     */
    public static boolean isLetter(char c) {
        c = Character.toLowerCase(c); //make char parameter lower case.

        if(c>='a' && c<='z') {
            return true;
        }else {
            return false;
        }
    }
    
    /*
     * Returns a String array of the figures in a String array parameter.
     * The method defines anything that starts with a digit to be a "figure"
     * 
     * @param String[] arr
     * @return String[]
     */
    public static String[] returnFigs(String[] arr) {
        String[] returnArr = new String[arr.length]; //make new array with plenty of space.
        int arrSize = 0;
        String currFig = "";
        
        for(int i = 0; i<arr.length;i++) {
            currFig = arr[i].trim();                       
            if(currFig.length()>0) {
                if(Character.isDigit(currFig.charAt(0))) { //if currFig is digit, put in returnArr, incr. size.
                    returnArr[arrSize] = currFig;
                    arrSize++;
                }
            }
        }
        return Arrays.copyOfRange(returnArr,0,arrSize); //Only return the array section with data.
    }
    
    /*
     * Counts the number of words and figures in a String.
     * Returns int[] = {numWords,numFigures}.
     * 
     * @param String line
     * @return int[]
     */
    public static int[] wordCounter(String line) {
        
        line = line.replaceAll("[^a-zA-Z0-9\\s]", ""); //get rid of any symbols, except spaces
        String[] strArr = line.split(" ");             //split based on spaces
        String[] words = returnWords(strArr);          // store words
        String[] figures = returnFigs(strArr);         // store figures
        
        int[] returnArr = {words.length,figures.length}; // {numWords, numFigures}

        return returnArr;
    }
    
    /*
     * Counts the length of each word in a string parameter.
     * Counts the number of words of the same length and returns in int[].
     * 
     * @param String line
     * @return int[]
     */
    public static int[] lengthWords(String line) {
        line = line.replaceAll("[^a-zA-Z0-9\\s]", ""); //get rid of symbols, except spaces.
        String[] strArr = line.split(" ");             //split based on spaces
        String[] words = returnWords(strArr);          //store words
        int[] wordSizes = new int[100];                //create new array with lots of space.
        int arrSize = 0;
        String currWord = "";
        
        for(int i = 0; i<words.length;i++) {           //for each word in words
            currWord = words[i].trim();
            
            wordSizes[currWord.length()-1]++;          //store # of words of same length in oversized array
                                                       //1 letter words stored at index 0, 2 letter @ index 1, etc.
            
            if(currWord.length() > arrSize) {          //keep track of maximum word length
                arrSize = currWord.length();
            }
        }
        
        return Arrays.copyOfRange(wordSizes,0,arrSize);//return appropriately sized array.
    }
    /*
     * Returns words from String Array parameter.
     * Defines words as any string that starts with a letter.
     * 
     * @param String[] arr
     * @return String[]
     */
    public static String[] returnWords(String[] arr) {
        String[] returnArr = new String[arr.length];    //Create array with plenty of size
        int arrSize = 0;
        String currWord = "";
        
        for(int i = 0; i<arr.length;i++) {              //For each word, make lowercase
            currWord = arr[i].toLowerCase().trim(); 
            if(currWord.length()>0) {                   //If word, add to returnArr, keep track of size
                if(currWord.charAt(0)>='a' && currWord.charAt(0)<='z') {
                    returnArr[arrSize] = currWord;
                    arrSize++;
                }
            }
        }
        return Arrays.copyOfRange(returnArr,0,arrSize);//Return appropriately sized array.
    }
    
    /*
     * Takes in input file. Counts number of lines, number letters, number other chars,
     * total chars, number words, number figures, and number of words of every character length.
     * 
     * Writes results to output.txt.
     * 
     * Can be run from the command line by putting <filename> into args[0] position.
     */

    public static void main(String[] args) {
        
        String fileInput = "";
        
        if(args.length == 1) {
            fileInput = args[0];
        }else {
            fileInput = "input.txt";
        }
        
        final String fileOutput = "output.txt";
        FileInputStream fileIn = null;
        Scanner scnr = null;
        int[] currArr;
        String currLine = "";
        int[] wordLengths = new int[100];
        
        int numLines = 0;
        int numLetters = 0;
        int numOtherChars = 0;
        int totalChars = 0;
        int numWords = 0;
        int numFigs = 0;
        
        try {
            fileIn = new FileInputStream(new File(fileInput));
        }catch(FileNotFoundException e) {
            System.out.println(fileOutput + " not found");
        }
        
        scnr = new Scanner(fileIn);
        
        while(scnr.hasNextLine()) {
            currLine = scnr.nextLine();
            
            numLines++;
            
            currArr = charCounter(currLine); //{numLetters, numOtherChars, totalChars}
            numLetters += currArr[0];
            numOtherChars += currArr[1];
            totalChars += currArr[2];
            
            currArr = wordCounter(currLine); // {numWords, numFigures}
            numWords += currArr[0];
            numFigs += currArr[1];
            
            int[] wordLengthArr = lengthWords(currLine);
            
            for(int i = 0; i<wordLengthArr.length;i++) {
                wordLengths[i]+=wordLengthArr[i];
            }
        }
        scnr.close();
        
        PrintWriter pw = null;
        
        try{
            pw = new PrintWriter(new File(fileOutput));
            
            pw.printf("File name: %s\\%s%n", System.getProperty("user.dir"),fileInput); //Prints directory that txt file is stored in.
            pw.printf("Number of lines: %d%n", numLines);
            pw.printf("Number of characters (total): %d%n", totalChars);
            pw.printf("Number of letters: %d%n", numLetters);
            pw.printf("Number of figures: %d%n", numFigs);
            pw.printf("Number of other characters: %d%n", numOtherChars);
            pw.printf("Number of words: %d%n", numWords);
            
            for(int i = 0; i<wordLengths.length;i++) {
                if(wordLengths[i] > 0) {
                    pw.printf("Number of %d letter words: %d%n", i+1, wordLengths[i]);
                }
            }

        }catch(FileNotFoundException e) {
            System.out.println(fileOutput + " not found.");
        }finally {
            pw.close();
        }

    }

    

}
