/*M03-UF2
Ejercicio: Guess the movie
Claudia Hernandez Colomer
Fp DAW La Salle
Profesora: Marta Bella
diciembre 2024 - enero 2025
*/

import java.util.Scanner;

public class HernandezClaudiaPlayer {

    //Atributos (los atributos de una clase deben ser de prefrerencia privados)
    private String nickname;
    private int score;

    /**
     * Constructor
     * @param score La puntuación final del jugador que le viene de la clase Game.
     */
    public HernandezClaudiaPlayer(int score) {
        this.nickname = requestNickname();
        this.score = score;
    }

    /**
     *  Metodo que pide al usuario que introduzca su nickname por teclado
     * @return devuelve el nickname si está correcto. Imprime un mensaje de error si no
     */
    private String requestNickname() {
        Scanner input = new Scanner(System.in);
        String nickname;

        while (true) {
            System.out.print("Enter your nickname (letters and numbers only, no spaces): ");
            nickname = input.next();

            //La expresiones regulares se las pido a la IA porque no me aclaro con ellas. Se usarlas pero no escribirlas, aunque esta es bastante simple de entender
            if (nickname.matches("[a-zA-Z0-9]+")) {
                System.out.println("Nickname: " + nickname);
                return nickname;
            } else {
                System.out.println("Invalid nickname");
            }
        }
    }





}
