/*M03-UF2
Ejercicio: Guess the movie
Claudia Hernandez Colomer
Fp DAW La Salle
Profesora: Marta Bella
diciembre 2024 - enero 2025
*/

public class HernandezClaudiaMain {

    public static void main(String[] args) {
        HernandezClaudiaMain programa = new HernandezClaudiaMain();
        programa.inicio();
    }

    public void inicio(){
        HernandezClaudiaGame game = new HernandezClaudiaGame();
        game.start();

    }

    /*todo:
        5 - terminar ranking
        7 - terminar Clase Player
        input close, trycatch ahorra usarlo, verificar si está bien puesto
    */
}