package tsuro;

public class Main {

    /**
     * the main method which runs the program
     *
     * @param args the user input
     */
    public static void main(String[] args) {
        try {
            // initializes a game of Tsuro with no input
            if (args.length == 0) {
                Tsuro game = new Tsuro();
            }
            //initializes a game of Tsuro with input for the number of players
            else if (args.length == 1) {
                Tsuro game = new Tsuro(Integer.parseInt(args[0]));
            }
            //initializes a game of Tsuro with input for the rows and column sizes of the board
            else if (args.length == 2) {
                Tsuro game = new Tsuro(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            }
            //initializes a game of Tsuro with input for the row and column size of the board and hand size of the players
            else if (args.length == 3) {
                Tsuro game = new Tsuro(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            }
            //initializes a game of Tsuro with input for the number of players, the row and column size of the board and handsize of the players
            else if (args.length == 4) {
                Tsuro game = new Tsuro(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            } else
                System.out.println("Input does not match legal inputs, please try again");
        } catch (NumberFormatException e) {
            System.out.println("Input does not match legal inputs, please try again");
        }

    }
}

