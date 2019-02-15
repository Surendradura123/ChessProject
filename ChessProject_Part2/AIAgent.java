import java.util.*;

public class AIAgent{
  Random rand;
  ChessProject ChessProject;
  boolean possibleAttack = false;

  public AIAgent(){
    rand = new Random();
  }

/*
  The method randomMove takes as input a stack of potential moves that the AI agent
  can make. The agent uses a rondom number generator to randomly select a move from
  the inputted Stack and returns this to the calling agent.
*/

  public Move randomMove(Stack possibilities){

    int moveID = rand.nextInt(possibilities.size());
    System.out.println("Agent randomly selected move : "+moveID);
    for(int i=1;i < (possibilities.size()-(moveID));i++){
      possibilities.pop();
    }
    Move selectedMove = (Move)possibilities.pop();
    return selectedMove;
  }
/*
The strategy does really care about what happens once the move is made...

This could mean that th AI agent could take a piece even though the player will immediately gain
some advantage...

The AI agent takes a pawn with his Queen and in respose to this attact the player takes the
queen (AI agents queen) with another pawn.

AI after making the move is up one point as the pawn is worth 1
however the Queen has a value of nine points and when the plaer takes AI agents queen it is down
 eight points.

  Pawn:1
  Knight/Bishop: 3
  Rook : 5
  Queen: 9
  King is worth the game

get all the possible moves hust like above with the Random Agent and then apply a utitlity function to work output
which move to make.

*/
/*
  public Move nextBestMove(Stack possibilities){

    Move selectedMove = new Move();

    return selectedMove;
  }
*/

  public Move nextBestMove(Stack whiteStack,Stack blackStack) {

        Stack whitePieces = (Stack) whiteStack.clone();
        Stack black = (Stack) blackStack.clone();
        Move whiteMove, currentMove, bestMove;
        Square blackPosition;
        int points = 0;
        int highestpoints = 0;
        bestMove = null;

        while (!whiteStack.empty()) {
            whiteMove = (Move) whiteStack.pop();
            currentMove = whiteMove;

            // checks the center of the board and assigns a point to the x axis 3/4 for a move
            if ((currentMove.getStart().getYC() < currentMove.getLanding().getYC())
            && (currentMove.getLanding().getXC() == 3)&&(currentMove.getLanding().getYC() == 3)
            || (currentMove.getLanding().getXC() == 4)&& (currentMove.getLanding().getYC() == 3)
            || (currentMove.getLanding().getXC() == 3) && (currentMove.getLanding().getYC() == 4)
            || (currentMove.getLanding().getXC() == 4) && (currentMove.getLanding().getYC() == 4)) {
              points = 0;

            //update bestmove
            if (points > highestpoints) {
                    highestpoints = points;
                    bestMove = currentMove;
                }
            }
            //compare white landing positions to black positions, if eating a piece is possible it takes it and if not it does a random move
            while (!black.isEmpty()) {
                points = 1;
                blackPosition = (Square) black.pop();
                if ((currentMove.getLanding().getXC() == blackPosition.getXC()) && (currentMove.getLanding().getYC() == blackPosition.getYC())) {
                    //adds black piece points
                    if(blackPosition.getName().contains("BlackPawn")){
                      points = 1;
                    }
                    else if(blackPosition.getName().contains("BlackBishop")){
                      points = 3;
                    }
                    else if(blackPosition.getName().contains("BlackKnight")){
                      points = 3;
                    }
                    else if(blackPosition.getName().contains("BlackRook")){
                      points = 5;
                    }
                    else if(blackPosition.getName().contains("BlackQueen")){
                      points = 9;
                    }

                }
                // updates the best next move
                if (points > highestpoints) {
                    highestpoints = points;
                    bestMove = currentMove;
                }
            }

            //reloads the black squares
            black = (Stack) blackStack.clone();
        }
        // uses the  best move  available or just go random
        if (highestpoints > 0) {
            System.out.println("Best move selected by AI agent:" + highestpoints);
            return bestMove;
        }
        return randomMove(whitePieces);

    }
/*
This Agent extends the function of the agent above......
this agent looks ahead and tries to determine what the player is going to do next......

Sounds familar...just like a min max routine.

We know to get the possible movements of all the pieces as we need this functionality for making random moves.
We now we to be able to capture the movements/potential movements of the players places exactly as we did for the white pieces.
Once we have this Stack of movements.....
we need a utitlity function to be able to calculated the value of the movements and then estimate which movements the player will make and then the agent respons to this movement.

Random      ---> get all possible movements for white2Move
            ----> select a random moves

nextBestMove----> get all possible movements for white
            -----> create a utility function based in the current move...
                       this could be if we take a piece we score same points.
             ------>loop through the stack of movements and a check if we are taking a piece and if so make this movements.

twoLevelsDeep --->get all possible movements for white(Stack)
                  then for each of these movements we find the best possible respons for the players
                  ----->get all the possible movements for black(Stack) after the board changes for each of the movements for white
              --->rank these according to a utility functionality
              --->the agent makes the best possible that it can make with the least best response from the player.

*/
/*  public Move twoLevelsDeep(Stack possibilities){
    Move selectedMove = new Move();
    return selectedMove;
  }*/

  public Move twoLevelsDeep(Stack whiteStack,Stack blackStack) {

        Stack whitePieces = (Stack) whiteStack.clone();
        Stack black = (Stack) blackStack.clone();
        Move whiteMove, currentMove, bestMove;
        Square blackPosition;
        int points = 0;
        int highestpoints = 0;
        bestMove = null;

        while (!whiteStack.empty()) {
            whiteMove = (Move) whiteStack.pop();
            currentMove = whiteMove;

            // checks the center of the board and assigns a point to the x axis 3/4 for a move
            if ((currentMove.getStart().getYC() < currentMove.getLanding().getYC())
            && (currentMove.getLanding().getXC() == 3)&&(currentMove.getLanding().getYC() == 3)
            || (currentMove.getLanding().getXC() == 4)&& (currentMove.getLanding().getYC() == 3)
            || (currentMove.getLanding().getXC() == 3) && (currentMove.getLanding().getYC() == 4)
            || (currentMove.getLanding().getXC() == 4) && (currentMove.getLanding().getYC() == 4)) {
              points = 0;

            //update bestmove
            if (points > highestpoints) {
                    highestpoints = points;
                    bestMove = currentMove;
                }
            }
            //compare white landing positions to black positions, if eating a piece is possible it takes it and if not it does a random move
            while (!black.isEmpty()) {
                points = 0;
                blackPosition = (Square) black.pop();
                if ((currentMove.getLanding().getXC() == blackPosition.getXC()) && (currentMove.getLanding().getYC() == blackPosition.getYC())) {
                    //adds black piece points
                    if(blackPosition.getName().contains("BlackPawn")){
                      points = 1;
                    }
                    else if(blackPosition.getName().contains("BlackBishop")){
                      points = 3;
                    }
                    else if(blackPosition.getName().contains("BlackKnight")){
                      points = 3;
                    }
                    else if(blackPosition.getName().contains("BlackRook")){
                      points = 5;
                    }
                    else if(blackPosition.getName().contains("BlackQueen")){
                      points = 9;
                    }

                }
                // updates the best next move
                if (points > highestpoints) {
                    highestpoints = points;
                    bestMove = currentMove;
                }

            }

            //reloads the black squares
            black = (Stack) blackStack.clone();
        }
        // uses the  best move  available or just go random
        if (highestpoints > 0) {
            System.out.println("Two step best movw selected by the AI agent:" + highestpoints);
            return bestMove;
        }
        return randomMove(whitePieces);

    }
}
