//ENES GELDİ
import java.io.*;
import java.util.*;

public class Main {
    static void countSpecific(HashMap<String, Integer> wordList, String word) {
        if (wordList.get(word) == null) {
            System.out.println("This word is not exist...");
        } else {
            System.out.println("The word '" + word + "' appears " + wordList.get(word) + " times");
        }

    }

    static void mostFreqWord(HashMap<String, Integer> wordList, int num) {
        String topWords[] = new String[num];
        int mostFreq[] = new int[num];

        for (int i = 0; i < num; i++) {
            topWords[i] = "";
            mostFreq[i] = -1;
        }

        for (Map.Entry<String, Integer> entry : wordList.entrySet()) {
            String word = entry.getKey();
            int frequency = entry.getValue();
            for (int i = 0; i < num; i++) {
                if (mostFreq[i] < frequency) {
                    for (int j = num - 1; j > i; j--) {
                        mostFreq[j] = mostFreq[j - 1];
                        topWords[j] = topWords[j - 1];
                    }
                    mostFreq[i] = frequency;
                    topWords[i] = word;
                    break;
                }
            }
        }

        System.out.print("The " + num + " most frequent words are [");
        for (int i = 0; i < num; i++) {
            if (topWords[i] != null) {
                System.out.print(topWords[i]);
            }
            if (i < num - 1 && topWords[i + 1] != null) {
                System.out.print(",");

            }
        }

        System.out.println("]");
        return;
    }

    static void leastFreqWord(HashMap<String, Integer> wordList, int num) {
        String leastWord[] = new String[num];
        int leastFreq[] = new int[num];
        for (int i = 0; i < num; i++) {
            leastFreq[i] = Integer.MAX_VALUE;
        }
        for (Map.Entry<String, Integer> entry : wordList.entrySet() ) {
            String word= entry.getKey();
            int frequency= entry.getValue();
            for(int i=0; i<num;i++){
                if(leastFreq[i]>frequency){
                    for(int j=num-1; j>i ;j--){
                        leastFreq[j]=leastFreq[j-1];
                        leastWord[j]=leastWord[j-1];
                    }
                    leastFreq[i]=frequency;
                    leastWord[i]=word;
                    break;
                }
            }
        }
        System.out.print("The " + num + " least frequent words are [");
        for(int i=0; i<num; i++){
            if(leastWord[i]!=null){
                System.out.print(leastWord[i]);
            }
            if (i < num - 1 && leastWord[i + 1] != null) {
                System.out.print(",");
            }
        }


        System.out.println("]");

    }

    public static void main(String[] args) throws IOException {
        HashMap<String, Integer> wordCountList = new HashMap<>();
        try {
            FileInputStream fis = new FileInputStream("input.txt");
            Scanner sc = new Scanner(fis);
            while (sc.hasNext()) {

                String word = sc.next().toLowerCase().replaceAll("[^a-zA-Z]+", "");
                if (word.isEmpty()) {
                    continue;
                }
                Integer count = wordCountList.get(word);

                if (count == null) {
                    count = 0;
                }
                wordCountList.put(word, count + 1);

            }
            System.out.println("Input file content has been read from sentences.txt");
            Scanner input = new Scanner(System.in);


            System.out.println("""
                    1. Get the count of a specific word
                    2. Get the most frequent words
                    3. Get the least frequent words
                    4. Exit     """);
            System.out.print("Please select an option:");
            int select = input.nextInt();
            while (!(select == 4)) {

                if (select == 1) {
                    Scanner input1 = new Scanner(System.in);
                    System.out.print("Enter the word:");
                    String userWord = input1.nextLine();
                    countSpecific(wordCountList, userWord);
                    break;

                } else if (select == 2) {
                    Scanner input1 = new Scanner(System.in);
                    System.out.print("Enter the number of words:");
                    int userSelect = input1.nextInt();
                    mostFreqWord(wordCountList, userSelect);
                    break;
                } else if (select == 3) {
                    Scanner input1 = new Scanner(System.in);
                    System.out.print("Enter the number of words:");
                    int userSelect = input1.nextInt();
                    leastFreqWord(wordCountList, userSelect);
                    break;

                }
            }
            System.out.println("Exiting...");

            sc.close();


        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
