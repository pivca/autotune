package minimax;

public class minimax {

    public class Turn{
        public short xBoard = 0;
        public short oBoard = 0;
        public int prio = 0;
        public int turn = 0;

        public Turn(short xBoard,short oBoard,int prio,int turn){
            this.xBoard = xBoard;
            this.oBoard = oBoard;
            this.prio = prio;
            this.turn = turn;
        }
    }

    Turn justplayed = new Turn((short)0,(short)0,0,0);

    public void playTurn(){
        this.justplayed = nextTurn(justplayed);
    }

    public char checkWin(Turn turn){
        int fullboard = (short)(turn.xBoard|turn.oBoard);
        if((fullboard&448) == 448 || (fullboard&56) == 56 || (fullboard&7) == 7 || (fullboard&291) == 291 || (fullboard&146) == 146 || (fullboard&73) == 73 || (fullboard&273) == 273 || (fullboard&84) == 84){
            if(turn.turn%2 == 0)
                return 'o';
            else
                return 'x';
        }
        return 'n';
    }


    public Turn nextTurn(Turn turn){
        Turn bestMove = null;
        short counter = 1;
        int min = -1000;
        int max = 1000;
        for(int i = 0;i<9;i++,counter<<=1){
            short fullboard = (short)(turn.oBoard|turn.xBoard);
            if((counter|fullboard) != fullboard){
                short newX = (turn.turn%2!=0)?(short)(turn.xBoard|counter):turn.xBoard;
                short newO = (turn.turn%2==0)?(short)(turn.oBoard|counter):turn.oBoard;
                char outcome = checkWin(turn);
                if(fullboard == 511)
                    return new Turn(newX,newO,0-turn.turn-1,turn.turn+1);

                if(outcome == 'x' || outcome == 'o'){
                    return (outcome=='x')?new Turn(newX,newO,10-turn.turn-1,turn.turn+1): new Turn(newX,newO,-10-turn.turn-1,turn.turn+1);
                }

                if(turn.turn%2 == 0){
                    if( (bestMove = nextTurn(new Turn(newX,newO,0,turn.turn+1))).prio > max){
                        max = bestMove.prio;
                    }
                }
                else{
                    if( (bestMove = nextTurn(new Turn(newX,newO,0,turn.turn+1))).prio < min){
                        min = bestMove.prio;
                    }
                }
            }
        }
        return bestMove;
    }


    public static void main(String[] args) {
        minimax ai = new minimax();

        ai.justplayed.xBoard = 256;
        ai.justplayed.oBoard = 128;
        ai.justplayed.turn = 2;
        ai.playTurn();

        System.out.println(ai.justplayed.xBoard);
    }
}
