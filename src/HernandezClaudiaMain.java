import java.util.Scanner;

public class HernandezClaudiaMain {

    public static void main(String[] args) {
        HernandezClaudiaMain programa = new HernandezClaudiaMain();
        programa.inicio();
    }

    public void inicio(){

        String menu =
                "\n🎯🎯🎯 Guess the Movie 🎯🎯🎯 " +
                "\nThe movie title has " + "numCharacters" + " ifHasSpaces" +
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
                    System.out.println("Error. El número debe estar entre " + valueMin + " y " + exitNumber);
                }
            }else{
                System.out.println("Error. Tienes que introducir un número");
                input.nextLine();
            }
        }while(!validInput);
        return value;
    }

}