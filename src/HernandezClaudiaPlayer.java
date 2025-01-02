/*M03-UF2
Ejercicio: Guess the movie
Claudia Hernandez Colomer
Fp DAW La Salle
Profesora: Marta Bella
diciembre 2024 - enero 2025
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class HernandezClaudiaPlayer {

    //Atributos (los atributos de una clase deben ser de prefrerencia privados)
    private String nickname;
    private int score;
    private ArrayList<String[]> ranking;

    /**
     * Constructor
     * @param score La puntuación final del jugador que le viene de la clase Game.
     */
    public HernandezClaudiaPlayer(int score) {
        this.nickname = requestNickname();
        this.score = score;
        this.ranking = new ArrayList<>();
        createOrLoadRankingFile();
        //updateRanking();
    }

    /**
     * Metodo que pide al usuario que introduzca su nickname por teclado. Solo permite letras y números, sin espacios.
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
                System.out.println("Invalid nickname. Try again.");
            }
        }
    }

    /**
     * Crea o carga el fichero binario del ranking.
     * Si no existe, se inicializa con un ranking vacío.
     */
    private void createOrLoadRankingFile() {
        /*
        Cuando hacemos new File no significa que se haya creado algo en la ruta especificada...
        Un objeto File es una representación abstracta que nos va a permitir trabajar con esta ruta (notas de clase)
         */
        File file = new File("ranking.data");

        //Si file ranking existe va a trabajar con el, si no va a llamar al metdo que que lo crea
        if (file.exists()) {
            try (ObjectInputStream lecturaObjeto = new ObjectInputStream(new FileInputStream(file))) {
                ranking = (ArrayList<String[]>) lecturaObjeto.readObject();
                System.out.println("Ranking loaded successfully.");
            } catch (Exception e) {
                System.out.println("Error alert! An exception has occurred: " + e);
            }
        } else {
            System.out.println("No ranking file found. Creating a new one.");
            createOrSaveRankingToFile();
        }
    }

    /**
     * Guarda el ranking actualizado en el fichero binario. Crea el ficheor binario si no existe
     */
    private void createOrSaveRankingToFile() {
        /*
        Para tratar ficheros binarios java diferencia si son de datos primitivos u objetos
        Queremos guardar ArrayList por lo que hay que hay que usar la forma para objetos
        Para escribir objetos en ficheros de bits java usa java.io.ObjectOutputStream

        No importo java.io.Serializable:
        ArrayList ya trae Serializable por defecto.
        Lo que hay en el ArrayList también es serializable (String es dato rpimitivo, osea serializable por defecto).
        Originalmente lo había puesto per buscando más info y ejemplos de esto vi que no hacía falta
        lo probé, funcionó, así que lo dejo y lo comento para recordarlo luego
         */

        try (ObjectOutputStream escrituraObjeto = new ObjectOutputStream(new FileOutputStream("ranking.data"))) {
            escrituraObjeto.writeObject(ranking);
            System.out.println("Ranking saved successfully.");
        } catch (Exception e) {
            System.out.println("Error alert! An exception has occurred: " + e);
        }
    }


}
