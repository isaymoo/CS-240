package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;
import hangman.EmptyDictionaryException;
import hangman.GuessAlreadyMadeException;

public class EvilHangman {

    public static void main(String[] args) throws IOException, EmptyDictionaryException {
        String dictionaryName = "";
        int wordLength = 0;
        int numGuesses = 0;
        if (args.length == 3){
            dictionaryName = args[0];
            wordLength = Integer.parseInt(args[1]);
            numGuesses = Integer.parseInt(args[2]);
        }

        EvilHangmanGame game = new EvilHangmanGame();
        File dictionary = new File(dictionaryName);
        Scanner input = new Scanner(System.in);
        try {
            game.startGame(dictionary, wordLength);

            while (numGuesses > 0) {

                SortedSet<Character> guessedLetters = new TreeSet<Character>();
                guessedLetters = (game.getGuessedLetters());
                System.out.println("You have " + numGuesses + " guesses left");
                System.out.print("Used Letters:");
                if (guessedLetters != null) {
                    for (Character letter : guessedLetters) {
                        System.out.print(" " + letter);
                    }
                }
                System.out.print("\n");
                String pattern = game.returnGuessedWord();
                System.out.println("Word: " + pattern);

                String inputChar = "";
                while (true) {
                    try {
                        System.out.print("Enter Guess: ");
                        inputChar = input.nextLine();
                        System.out.print('\n');
                        if (inputChar.length() != 1) throw new GuessAlreadyMadeException();

                        Set<String> pointless = game.makeGuess(inputChar.charAt(0));
                        pattern = game.returnGuessedWord();
                        int numChars = 0;
                        numGuesses--;
                        for (int i = 0; i < pattern.length(); i++) {
                            if (pattern.charAt(i) == inputChar.charAt(0)) numChars++;
                        }
                        if (numChars > 0) {
                            if (game.numRemainingPossibilities() != 1) {
                                System.out.println("Yes, there is " + numChars + " " + inputChar.charAt(0));
                                numGuesses++;
                                break;
                            }
                            else {
                                System.out.println("You Win!");
                                System.out.println("The Word was: " + game.returnActualWord());
                                System.exit(0);
                            }
                        } else if (numGuesses > 0) {
                            System.out.println("Sorry, there are no " + inputChar.charAt(0) + "'s");
                            break;
                        }
                        else {
                            System.out.println("You lose!");
                            System.out.println("The Word was: " + game.returnActualWord());
                            System.exit(0);
                        }


                    } catch (GuessAlreadyMadeException e) {
                        continue;
                    }

                }
            }
        }
        catch(EmptyDictionaryException e){

        }
        if (input != null) input.close();

    }

}
