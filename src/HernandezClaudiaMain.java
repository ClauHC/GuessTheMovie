import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HernandezClaudiaMain {

    public static void main(String[] args) {
        HernandezClaudiaMain programa = new HernandezClaudiaMain();
        programa.inicio();
    }

    public void inicio(){

        String randomTitle = readTitlesFile();

        System.out.println(randomTitle);

        String menu =
                "\n🎯🎯🎯 Guess the Movie 🎯🎯🎯 " +
                "\n" + numCharacters(randomTitle) +
                "\nYou are gessing: " + convertToAsterisks(randomTitle) +
                "\nRemaining turns: " + "remainingTurns" +
                "\nPoints: " + "points" +
                "\nChoose an option:\n [1] Guess a letter \n [2] Guess the movies's title \n [3] Exit";
        int menuOption = 0;
        do{
            menuOption = getIntFromConsole(menu, 1, 2);
            switch (menuOption){
                case 1:
                    System.out.println("guessingLetter");
                    //guessingLetter();
                    break;
                case 2:
                    System.out.println("guessingTitle");
                    //guessingTitle();
                    break;
            }
        }while(menuOption!=3);


    }

    public int getIntFromConsole(String mensaje, int valueMin, int valueMax){
        Scanner input= new Scanner(System.in);
        int value = 0;
        int exitNumber = 3;
        boolean validInput = false;
        do{
            System.out.println(mensaje);
            if (input.hasNextInt()){
                value = input.nextInt();
                input.nextLine();
                // Salir del bucle solo si el input es valido, es decir está en rango o es el numero de salida, este bucle es solo control de error, si el input es valido es que no hay error y se sale
                if (value == exitNumber){
                    validInput = true;
                }
                else if (value>=valueMin && value<=valueMax){
                    validInput = true;
                }else{
                    System.out.println("The number must be between " + valueMin + " and " + exitNumber);
                }
            }else{
                System.out.println("You have to enter a number");
                input.nextLine();
            }
        }while(!validInput);
        return value;
    }

    public String readTitlesFile (){
        File file= new File("titulos.txt");
        Scanner input= null;
        ArrayList<String> titles = new ArrayList<>();
        Random random = new Random();

        try {
            input = new Scanner(file);
            while (input.hasNextLine()){
                String title = input.nextLine();
                titles.add(title);
            }
        } catch (Exception e) {
            System.out.println("Error alert! An exception has occurred: " + e);
        }finally{
            if (input!=null){input.close();}
        }

        if (!titles.isEmpty()) {
            int randomIndex = random.nextInt(titles.size());
            return titles.get(randomIndex);
        } else {
            return "Error alert! No titles were found in the text file.";
        }
    }

    public String numCharacters(String title) {
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

    public String convertToAsterisks(String title) {
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
            if (Character.isLetterOrDigit(currentChar)) {
                result += "*";
            } else {
                // Sumar a la cadena los espacios y otros caracteres (que no colaron arriba) tal y como están
                result += currentChar;
            }
        }

        return result;
    }
}