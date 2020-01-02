package pl.sanders;

import java.util.Random;

public class Manager {
    protected int width;
    protected int height;
    protected int bombs;
    protected short gameStatus;
        public final static short inProgress=0;
        public final static short youLose=1;
        public final static short youWin=2;
    protected int seconds;
    protected int bombsToDetect;
    protected Cell[][] board;
    public static int gameMode; //0-beginner 1-intermediate 2-expert 3-custom

    public Manager(int width, int height, int bombs){
        this.width=width;
        this.height=height;
        this.bombs=bombs;
        this.bombsToDetect=bombs;
        this.gameStatus=inProgress;
        this.seconds=0;

        this.board = new Cell[width][height];
        for(int i = 0 ; i < width ; i++){
            for(int j = 0 ; j < height;j++) {
                this.board[i][j]=new Cell();
            }
        }
        Random r = new Random();
        for(int i = 0 ; i < bombs ; i++){
           int randomPositionX = r.nextInt(width);
           int randomPositionY = r.nextInt(height);
           if(this.board[randomPositionX][randomPositionY].value >= 9){
               i--;
           }else{
               this.board[randomPositionX][randomPositionY].value = 9;

               for (int k =- 1 ; k <= 1 ; k++){
                   for (int l =- 1 ; l <= 1 ; l++){

                       try {
                           this.board[randomPositionX + k][randomPositionY + l].value++;
                       }catch (Exception e){}
                   }
               }
           }
        }
    }

public int checkDiscoveredFields(){
        int result=0;
    for(int i = 0 ; i < this.width ; i++){
        for(int j = 0 ; j < this.height;j++) {
            if(this.board[i][j].isDiscovered) result++;
        }
    }
        return result;
}

public Boolean checkIfWin(){
        if(checkDiscoveredFields()==(width*height)-bombs) {
            gameStatus=youWin;
            return true;
        }
        return false;
    }
}
