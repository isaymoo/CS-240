package hangman;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class EvilHangman {
    public static void main(String[] args) {
        String dictionaryName = "";
        int wordLength = 0;
        int numAttempts = 0;
        if (args.length == 3){
            dictionaryName = args[0];
            wordLength = Integer.parseInt(args[1]);
            numAttempts = Integer.parseInt(args[2]);
        }
        try{
            EvilHangmanGame game = new EvilHangmanGame();
            Path filePath = Paths.get(dictionaryName);
            File dictionary = filePath.toFile();
            game.startGame(dictionary, wordLength);

            while(numAttempts > 0){

                System.out.println("You have " + numAttempts + " guesses left");
                System.out.println("UsedLetters: " + game.getGuessedLetters().toString());
                System.out.println("Word: " + game.returnPartiallyGuessedWord());
                int prevChars = game.getGuessedLetters().size();
                while(prevChars == game.getGuessedLetters().size()) {
                    try {
                        System.out.print("Enter guess: ");
                        //input

                        String input = "";
                        InputStream in = System.in;
                        Scanner scanner = new Scanner(in);
                        scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
                        input = scanner.next();
                        if (input.length() != 1) throw new GuessAlreadyMadeException();
                        input = input.toLowerCase();
                        if (input.charAt(0) < 'a' | input.charAt(0) > 'z') throw new GuessAlreadyMadeException();

                        game.makeGuess(input.charAt(0));
                        numAttempts--;
                        if (game.wasFound()){
                            numAttempts++;
                            System.out.println("Yes, there is " + game.timesWasFound() + " " + input.charAt(0));
                            if (game.returnSuggestions().size() == 1){
                                System.out.println("You Win! The guessed word: " + game.returnSuggestions().first());
                                System.exit(0);
                            }
                        }
                        else {
                            System.out.println("Sorry, there are no " + input.charAt(0));
                        }
                        if (numAttempts == 0){
                            System.out.println("Sorry, you lost! The word was: " + game.returnSuggestions().first());
                        }




                    } catch (GuessAlreadyMadeException e) {
                        System.out.println("Please try a new input");
                    }
                }
            }

        }
        catch(EmptyDictionaryException | IOException e){
            System.out.println("Please Try Again");
        }
    }

}
