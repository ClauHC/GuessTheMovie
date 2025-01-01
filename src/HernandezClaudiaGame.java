/*M03-UF2
Ejercicio: Guess the movie
Claudia Hernandez Colomer
Fp DAW La Salle
Profesora: Marta Bella
diciembre 2024 - enero 2025
 */

/*
Hola Marta. El Normalizer tuve que buscarlo y ver como manejarlo apoyandome en la IA porque no lo conocía y a raiz de que tenía un titulo de película
con acento vi que tenía que resolver este tipo de errores y no se me ocurría cómo.

Notas para estudiar y recordar funcionamiento en el futuro:

Normalizer es una clase de Java en el paquete java.text. Se utiliza para trabajar con normalización Unicode.
Unicode es un estándar que representa texto en computadoras. Permite que caracteres como á o é no solo se almacenen
como una letra con acento, sino también como dos partes separadas: la letra base (a, e) y la "marca" que representa el acento.

Normalizer.Form.NFD significa "Descomposición Normalizada".
NFD (Normalization Form Decomposed): Convierte caracteres compuestos (como á) en su forma descompuesta. Esto separa la letra base del acento.
Ejemplo:
Antes de NFD: á (un solo carácter).
Después de NFD: a + ´ (letra base a más la marca de acento).
*/

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.text.Normalizer;

public class HernandezClaudiaGame {

    //Atributos (los atributos de una clase deben ser de prefrerencia privados)
    private static final int MAX_TURNS = 10;
    private int remainingTurns;
    private String revealedTitle;
    private int points;
    private ArrayList<String> wrongLetters;
    private String randomTitle;
    private String guessedLetters;

    //Valores con los que parten algunos atributos
    public HernandezClaudiaGame() {
        this.remainingTurns = MAX_TURNS;
        this.points = 0;
        this.wrongLetters = new ArrayList<>();
        this.randomTitle = readTitlesFile();
        this.revealedTitle = convertToAsterisks(randomTitle);
        this.guessedLetters = "";
    }

    // Constructor
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

    /**
     * Este metodo es el que gestiona lo que introduce por teclado el usuario. Es un metodo de control de errores
     * @param mensaje será el menú que muestra el cuerpo de la aplicación con la que interactua el usuario
     * @param valueMin opción más baja del menú
     * @param valueMax opción más alta del menú
     * @return
     */
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

    /**
     * Metodo que lee el fichero de texto con la lista de peliculas
     * @return devuelve un titulo random de pelicula. Si el fichero está en blanco devuelve un mensaje con un texto de Error alert! que será identificado por otros metodos
     */
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

    /**
     * Metodo que cuenta la cantidad de caracteres que tiene el titulo de la pelicula
     * @param title
     * @return devuelve dos mensajes (según si tiene espacios o es una sola palabra) con el numero de caracteres
     */
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

    /**
     * Metodo que oculta los carateres del titulo, reconoce entre espacios en blanco y caracteres
     * @param title
     * @return result
     */
    private String convertToAsterisks(String title) {
        if (title == null || title.isEmpty() || title.contains("Error alert!")) {
            return "";
        }

        String result = "";
        for (int i = 0; i < title.length(); i++) {
            char currentChar = title.charAt(i);

            /*
            Notas para estudiar y recordar funcionamiento en el futuro:

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

    /**
     * Metodo principal de clase, maneja todas las letras que va entrando el usuario y dispone de ellas según las necesidades del programa
     */
    private void guessingLetter() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a single letter (a-z): ");
        String charInput = input.next().toLowerCase();

        //Control de errores. Verifivar que no entró un valor inválido
        if (charInput.length() != 1 || !Character.isLetter(charInput.charAt(0))) {
            System.out.println("Error. You must enter a single valid LETTER.");
            return;
        }

        //se crea la variable que almacenará las letras que introduce el usuario
        char guessedChar = charInput.charAt(0);

        /*
        Control de errores: gestión letra repetida
        El bucle de verificación de letras repetidas se hace en un metodo aparte porque este metodo ya está bastante largo y se hace dificil leerlo
        también es más fácil de corregir de esta forma y por si luego hace falta volver a usarlo en otra parte (divide y vencerás) recordar que los métodos
        pueden llamar a otrso métodos
         */
        if (wasGuessed(guessedChar)) {
            System.out.println("You have already guessed this letter. Try a different one.");
            return;
        }

        // Agregar letra a las ya dichas
        guessedLetters += guessedChar;


        //Cuerpo principal del metodo

        String updatedTitle = "";

        for (int i = 0; i < randomTitle.length(); i++) {
            char currentChar = randomTitle.charAt(i);

            /*Control de errores: Llamada al metodo normalizeChar para normalizar caracteres
            (llevarlos a su forma básica sin acentos ni nada) para compararlos. Nuevamente
            se usa un metodo aparte para esto para optimizar la lectura y entendimieto de este
            y porque se va a volver a usar en el titulo
             */
            char normalizedCharFromTitle = normalizeChar(currentChar);
            char normalizedCharFromGuess = normalizeChar(guessedChar);

            if (normalizedCharFromTitle == normalizedCharFromGuess) {
                updatedTitle += currentChar;
                System.out.println("Correct guess! Updated title: " + revealedTitle);
                points += 10;
            } else {
                updatedTitle += revealedTitle.charAt(i);
                System.out.println("Attempt failed.");
                if (!wrongLetters.contains(String.valueOf(guessedChar))) {
                    wrongLetters.add(String.valueOf(guessedChar));
                }
                points -= 10;
            }

            revealedTitle = updatedTitle;
            remainingTurns--;

        }


        /*

        if (randomTitle.contains(String.valueOf(guessedChar))) {

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

            System.out.println("Correct guess! Updated title: " + revealedTitle);
            points += 10;

        } else {
            System.out.println("Attempt failed.");
            if (!wrongLetters.contains(String.valueOf(guessedChar))) {
                wrongLetters.add(String.valueOf(guessedChar));
            }
            points -= 10;
        }

        // Disminuir intentos restantes
        remainingTurns--;

         */
    }

    /**
     * El bucle de verificación de letras repetidas. Metodo que apoya al metodo principal
     * @param letter
     * @return devuelve un boolean
     */
    private boolean wasGuessed(char letter) {
        for (int i = 0; i < guessedLetters.length(); i++) {
            if (guessedLetters.charAt(i) == letter) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo para normalizar caracteres. Metodo que apoya al metodo principal
     * @param c espera recibir un caracter
     * @return devuelve la letra sin puntuacion
     */
    private char normalizeChar(char c) {
        String normalized = Normalizer.normalize(String.valueOf(c), Normalizer.Form.NFD);
        //replaceAll("\\p{M}", "") Reemplazar tod y una expresione regular \\p{M} que le dice lo que tiene que reemplazar por "" nada
        // las expresione sregulares son muy útiles pero me lleva por la calle de la amargura hacerlas, esto se lo pedí a la IA
        return normalized.replaceAll("\\p{M}", "").charAt(0);
    }

    /**
     * Metodo para normalizar strings. Metodo que apoya al metodo principal
     * @param s espera recibir un string
     * @return devuelve el string sin puntuacion
     */
    private String normalizeString(String s) {
        String normalized = Normalizer.normalize(s, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }

    /**
     * Segundo metodo principal de la clase. Maneja la entrada de un string que pretende ser igual al titulo a adivinar
     */
    private void guessingTitle() {
        Scanner input = new Scanner(System.in);
        System.out.print("You already have it?! \nWhich movie do you think it is? ");
        String titleGuess = input.nextLine().toLowerCase();

        // Normalizar entrada y título para comparación. Lo mismo de antes
        String normalizedGuessTitle = normalizeString(titleGuess);
        String normalizedRandomTitle = normalizeString(randomTitle);

        if (normalizedGuessTitle.equals(normalizedRandomTitle)) {
            System.out.println("It's the right movie! You rock in this game! \nYou win!");
            points += 20;
        } else {
            System.out.println("Wrong guess!");
            points -= 20;
        }
        // Si no quedan turnos el juego inmediatamente salta al cierre
        remainingTurns = 0;
    }
}
