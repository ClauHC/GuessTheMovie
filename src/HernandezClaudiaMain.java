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

        String tituloRandom = readTitlesFile();

        String menu =
                "\n🎯🎯🎯 Guess the Movie 🎯🎯🎯 " +
                "\n" + numCharacters(tituloRandom) +
                "\nYou are gessing: " + "asterisks" +
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
                    System.out.println("Error. The number must be between " + valueMin + " and " + exitNumber);
                }
            }else{
                System.out.println("Error. You have to enter a number");
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
            System.out.println("An exception has occurred: " + e);
        }finally{
            if (input!=null){input.close();}
        }

        if (!titles.isEmpty()) {
            int randomIndex = random.nextInt(titles.size());
            return titles.get(randomIndex);
        } else {
            return "No titles were found in the text file.";
        }
    }

    public String numCharacters(String title) {
        if (title == null || title.isEmpty()) {
            return "The title is empty or invalid.";
        }

        int numCharacters = title.length();
        if (title.contains(" ")) {
            return "The movie title has " + numCharacters + " characters (including spaces).";
        } else {
            return "The movie title has " + numCharacters + " characters.";
        }
    }

}