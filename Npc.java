/**
 *
 * This class was created by me and it represents the Computer
 * that the player will play against if they choose to do so (the NPC).
 * It is quite short class as in essence it is where the computer decides
 * what move to make.
 *
 *
 * s2063615
 */

public final class Npc {

    private final Model model;


    //the constructor of this class passes through the model class, for its methods to be used.

    public Npc(Model model) {
        this.model = model;
    }


    //this method is where the NPC makes its move, we pass through the columns in order to loop through them
    public int getMove(int cols) {

        //checks if there is a winning move to be played, and that is is valid.
        for (int c = 0; c < cols; c++) {
            if (model.checkMove(c) && model.isMoveValid(c)) {
                //plays winning move
                return c;
            }
        }

        //the second loop checks if there is a winning move available for the opponent, and that it is valid.
        for (int c = 0; c < cols; c++) {
            if (model.checkOpponentsMove(c) && model.isMoveValid(c)) {
                //plays win blocking move
                return c;
            }
        }

       //here we choose a random move
        int randomMove = (int) (Math.random() * (cols));

        //checking random move is valid
      while (!model.isMoveValid(randomMove)) {
          randomMove = (int) (Math.random() * (cols));
      }

      //returning the validated random move
      return randomMove;
    }

}