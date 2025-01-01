import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HernandezClaudiaGame {

    private static final int MAX_TURNS = 10; // Constante para los intentos iniciales
    private int remainingTurns;
    private String revealedTitle; // Título revelado progresivamente
    private int points;
    private ArrayList<String> wrongLetters;
    private String randomTitle;

    public HernandezClaudiaGame() {
        this.remainingTurns = MAX_TURNS;
        this.points = 0;
        this.wrongLetters = new ArrayList<>();
        this.randomTitle = readTitlesFile();
        this.revealedTitle = convertToAsterisks(randomTitle);
    }

    public void start() {
        String menu;
        int menuOption;
        boolean showWrongLetters = false;

        do {
            menu =
                "\n🎯🎯🎯 Guess the Movie 🎯🎯🎯 " +
                "\n" + numCharacters(randomTitle) +
                "\nYou are guessing: " + revealedTitle +
                "\nRemaining turns: " + remainingTurns +
                "\nPoints: " + points;

            if (showWrongLetters && !wrongLetters.isEmpty()) {
                menu += "\nWrong letters: " + String.join(", ", wrongLetters);
            }

            menu += "\nChoose an option:\n [1] Guess a letter \n [2] Guess the movie's title \n [3] Exit";

            menuOption = getIntFromConsole(menu, 1, 3);
            switch (menuOption) {
                case 1:
                    guessingLetter();
                    showWrongLetters = true;
                    break;
                case 2:
                    guessingTitle();
                    break;
                case 3:
                    System.out.println("You have decided to leave the game. This makes you lose");
                    // Salir implica perder el juego.
                    remainingTurns = 0;
                    points = 0;
                    break;
            }

            if (remainingTurns == 0) {
                System.out.println("Game over!");
                System.out.println("The correct movie title was: " + randomTitle);
                System.out.println("Your final score: " + points);
                break;
            }
        } while (menuOption != 3);
    }

    private int getIntFromConsole(String mensaje, int valueMin, int valueMax) {
        Scanner input = new Scanner(System.in);
        int value = 0;
        boolean validInput = false;
        do {
            System.out.println(mensaje);
            if (input.hasNextInt()) {
                value = input.nextInt();
                input.nextLine();
                // Salir del bucle solo si el input es valido, es decir está en rango, este bucle es solo control de error, si el input es valido es que no hay error y se sale
                if (value >= valueMin && value <= valueMax) {
                    validInput = true;
                } else {
                    System.out.println("The number must be between " + valueMin + " and " + valueMax);
                }
            } else {
                System.out.println("You have to enter a number");
                input.nextLine();
            }
        } while (!validInput);
        return value;
    }

    private String readTitlesFile() {
        File file = new File("titulos.txt");
        Scanner input = null;
        ArrayList<String> titles = new ArrayList<>();
        Random random = new Random();

        try {
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String title = input.nextLine().toLowerCase();
                titles.add(title);
            }
        } catch (Exception e) {
            System.out.println("Error alert! An exception has occurred: " + e);
        } finally {
            if (input != null) {
                input.close();
            }
        }

        if (!titles.isEmpty()) {
            int randomIndex = random.nextInt(titles.size());
            return titles.get(randomIndex);
        } else {
            return "Error alert! No titles were found in the text file.";
        }
    }

    private String numCharacters(String title) {
        if (title == null || title.isEmpty() || title.contains("Error alert!")) {
            return "The title is empty or invalid.";
        }

        int numCharacters = title.length();
        if (title.contains(" ")) {
            return "The movie title has " + numCharacters + " characters (including spaces).";
        } else {
            return "The movie title has " + numCharacters + " characters.";
        }
    }

    private String convertToAsterisks(String title) {
        if (title == null || title.isEmpty() || title.contains("Error alert!")) {
            return "";
        }

        String result = "";
        for (int i = 0; i < title.length(); i++) {
            char currentChar = title.charAt(i);

            /*
            IsLetterOrDigit(String, Int32) Indicates whether the character at the specified position in a specified string is categorized as a letter or a decimal digit.
            IsLetterOrDigit(Char) Indicates whether the specified Unicode character is categorized as a letter or a decimal digit.

            Firma del métod:
            public static boolean isLetterOrDigit(char ch)
            Parámetro de entrada:
            ch, Un valor de tipo char (un solo carácter) que será evaluado.
            Valor de retorno:
            Devuelve true si el carácter es una letra (mayúscula o minúscula) o un dígito (del 0 al 9).
            Devuelve false si el carácter no es ni una letra ni un dígito (por ejemplo, si es un espacio, un símbolo de puntuación, una marca de acento, etc.).
             */
            /*
            isLetter
            Valor de retorno:
            Devuelve true si el carácter es una letra (mayúscula o minúscula).
            Devuelve false si el carácter no es una letra.
             */
            if (Character.isLetter(currentChar)) {
                result += "*";
            } else {
                result += currentChar;
            }
        }
        return result;
    }

    private void guessingLetter() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a single letter (a-z): ");
        String charInput = input.next().toLowerCase();

        if (charInput.length() != 1 || !Character.isLetter(charInput.charAt(0))) {
            System.out.println("Error. You must enter a single valid LETTER.");
            return;
        }

        char guessedChar = charInput.charAt(0);
        if (randomTitle.contains(String.valueOf(guessedChar))) {
            String updatedTitle = "";
            for (int i = 0; i < randomTitle.length(); i++) {
                // Reemplaza el asterisco por la letra adivinada
                if (randomTitle.charAt(i) == guessedChar) {
                    updatedTitle += guessedChar;
                } else {
                    // Mantiene el estado actual de revealedTitle
                    updatedTitle += revealedTitle.charAt(i);
                }
            }
            revealedTitle = updatedTitle;
            // Disminuir intentos restantes
            remainingTurns--;
            points += 10;
            System.out.println("Correct guess! Updated title: " + revealedTitle);
        } else {
            System.out.println("Attempt failed.");
            if (!wrongLetters.contains(String.valueOf(guessedChar))) {
                wrongLetters.add(String.valueOf(guessedChar));
            }
            remainingTurns--;
            points -= 10;
        }
    }

    private void guessingTitle() {
        Scanner input = new Scanner(System.in);
        System.out.print("You already have it?! \nWhich movie do you think it is? ");
        String titleGuess = input.nextLine().toLowerCase();

        if (titleGuess.equals(randomTitle)) {
            System.out.println("It's the right movie! You rock in this game! \nYou win!");
            points += 20;
            // Si no quedan turnos el juego inmediatamente salta al cierre
            remainingTurns = 0;
        } else {
            System.out.println("Wrong guess!");
            points -= 20;
            remainingTurns = 0;
        }
    }
}
