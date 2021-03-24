package minimax;

import org.jetbrains.annotations.NotNull;

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



    //ne radi provera ova ako bi radila algoritam bi mogao mozda da radi
    public char checkWin(@NotNull Turn turn){
        int fullboard = (turn.xBoard|turn.oBoard);
        if(((fullboard&448) == 448) || ((fullboard&56) == 56) || ((fullboard&7) == 7) || ((fullboard&291) == 291) || ((fullboard&146) == 146) || ((fullboard&73) == 73) || ((fullboard&273) == 273) || ((fullboard&84) == 84)){
            if(((turn.turn)%2) == 0)
                return 'o';
            else
                return 'x';
        }
        return 'n';
    }


    public Turn nextTurn(Turn turn){
        Turn bestMove = null;
        short counter = 1;
        int min = -10000;
        int max = 10000;
        short fullboard = (short)(turn.oBoard|turn.xBoard);

        char outcome = checkWin(turn);
        if((fullboard) == 511){
            turn.prio = -turn.turn - 1;
            return turn;
        }

        if(outcome == 'x'){
            turn.prio = 10 - turn.turn-1;
            return turn;
        }
        else if(outcome == 'o'){
            turn.prio = -10-turn.turn-1;
            return turn;
        }


        for(int i = 0;i<9;i++){
            if((counter|fullboard) != fullboard){
                short newX = ((turn.turn+1)%2!=0)?(short)(turn.xBoard|counter):turn.xBoard;
                short newO = ((turn.turn+1)%2==0)?(short)(turn.oBoard|counter):turn.oBoard;

                if((turn.turn+1)%2 == 0){
                    if( (bestMove = nextTurn(new Turn(newX,newO,0,turn.turn+1))).prio < max){
                        max = bestMove.prio;
                    }
                }
                else{
                    if( (bestMove = nextTurn(new Turn(newX,newO,0,turn.turn+1))).prio > min){
                        min = bestMove.prio;
                    }
                }
            }
            counter<<=1;
        }
        return bestMove;
    }

    public static void main(String[] args) {
        minimax ai = new minimax();

        ai.justplayed.xBoard = 320;
        ai.justplayed.oBoard = 144;
        ai.justplayed.turn = 4;
        ai.playTurn();

        System.out.println(ai.justplayed.xBoard);
    }
}