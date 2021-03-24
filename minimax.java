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


    public char checkWin(@NotNull Turn turn){
        if(((turn.xBoard&448) == 448) || ((turn.xBoard&56) == 56) || ((turn.xBoard&7) == 7) || ((turn.xBoard&291) == 291) || ((turn.xBoard&146) == 146) || ((turn.xBoard&73) == 73) || ((turn.xBoard&273) == 273) || ((turn.xBoard&84) == 84)){
                return 'x';
        }
        if(((turn.oBoard&448) == 448) || ((turn.oBoard&56) == 56) || ((turn.oBoard&7) == 7) || ((turn.oBoard&291) == 291) || ((turn.oBoard&146) == 146) || ((turn.oBoard&73) == 73) || ((turn.oBoard&273) == 273) || ((turn.oBoard&84) == 84)){
            return 'o';
        }
        return 'n';
    }


    public Turn nextTurn(Turn turn){
        Turn bestMove = null;
        Turn next = null;
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

                next = new Turn(newX,newO,0,turn.turn+1);

                if((turn.turn)%2 == 0){
                    if( (bestMove = nextTurn(next)).prio < max){
                        max = bestMove.prio;
                    }
                }
                else{
                    if( (bestMove = nextTurn(next)).prio > min){
                        min = bestMove.prio;
                    }
                }
            }
            counter<<=1;
        }
        return next;
    }

   
}
