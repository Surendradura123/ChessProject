class Move{
  Square start;
  Square landing;
/*
 if you extend the class Move to include an evaluation of the move... the output of the utitlity
 function you wont need to creste an additional data structure for keeping track of the scores
 for each of the movements.
 */
  public Move(Square x, Square y){
    start = x;
    landing = y;
  }

  public Move(){

  }

  public Square getStart(){
    return start;
  }

  public Square getLanding(){
    return landing;
  }
}
