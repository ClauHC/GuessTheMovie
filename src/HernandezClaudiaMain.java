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
        1 - reconocer la letra aunque tenga acentos en el titulo
        2 - letra repetida. El usuario puede equivocarse y repetir alguna letra (bien se encuentre en el título o no).
            ¿Qué hacemos si sucede esto? No lo tengas en cuenta, es decir no le restes intentos ni puntos, sino que le avises
            de que esa letra ya la ha dicho, y hagas que la aplicación vuelva a solicitar una nueva letra.
        3 - Que el usuario escoja la opcion 3 exit implica perder el juego
        4 - Al finalizar el juego, mostraremos al usuario cual era el título de la película y su puntuación definitiva, así como si ha ganado o ha perdido
        5 - hacer ranking
        6 - comentarios javadocs en metodos
        7 - Definición de tipos de datos (class Game, class Player, ..) que permitan encapsular correctamente la información con atributos(private), constructores y métodos con las funcionalidades de acuerdo a requisitos
    */
}