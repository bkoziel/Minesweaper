package pl.sanders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Scoreboard {

    private static ScoreboardElement[] beginnerRanking = new ScoreboardElement[5] ;
    private static ScoreboardElement[] intermediateRanking = new ScoreboardElement[5];
    private static ScoreboardElement[] expertRanking = new ScoreboardElement[5];

    public Scoreboard() throws FileNotFoundException, NoSuchElementException {

        for(int i = 0 ; i < 5 ; i++ ){
            beginnerRanking[i] = new ScoreboardElement(" ",-1);
            intermediateRanking[i] = new ScoreboardElement(" ",-1);
            expertRanking[i] = new ScoreboardElement(" ", -1);
        }
        Scanner data = new Scanner(new File("src/pl/sanders/data.in"));
       String [] temp = null;
       String line;
        for(int i = 0 ; i < 5 ; i++ ){
            line = data.nextLine();
            temp= line.split("~");
            beginnerRanking[i] = new ScoreboardElement(temp[0],Integer.parseInt(temp[1]));
            line = data.nextLine();
            temp= line.split("~");
            intermediateRanking[i] = new ScoreboardElement(temp[0],Integer.parseInt(temp[1]));
            line = data.nextLine();
            temp= line.split("~");
            expertRanking[i] = new ScoreboardElement(temp[0],Integer.parseInt(temp[1]));
        }
        data.close();
    }

    public static int checkPosition(int time ,int gameMode) {
        switch (gameMode) {
            case 0: for (int i = 0 ; i < 5 ; i++){
                if(time<beginnerRanking[i].gainedTime || beginnerRanking[i].gainedTime == -1){ return i;}
            } break;
            case 1: for (int i = 0 ; i < 5 ; i++){
                if(time<intermediateRanking[i].gainedTime || intermediateRanking[i].gainedTime == -1){ return i;}
            }break;
            case 2: for (int i = 0 ; i < 5 ; i++){
                if(time<expertRanking[i].gainedTime || expertRanking[i].gainedTime == -1){ return i;}
            }break;
        }

        return 5;
    }
    public static void AddResult(ScoreboardElement player, int position, int gameMode){
            if(gameMode==0){
            for(int i=4 ; i > position; i--){
                beginnerRanking[i]=beginnerRanking[i-1];
            }
            beginnerRanking[position]=player;
        }else if(gameMode==1){
                for(int i=4 ; i > position; i--){
                    intermediateRanking[i]=intermediateRanking[i-1];
                }
                intermediateRanking[position]=player;
            }else if(gameMode==2){
            for(int i=4 ; i > position; i--){
                expertRanking[i]=expertRanking[i-1];
            }
            expertRanking[position]=player;
        }
    }
public static String getRanking(int gameMode) {
        String result="";
    if (gameMode == 0) {
        result="Beginner Ranking \n";
        for (int i = 0; i < 5; i++) {
            if (beginnerRanking[i].gainedTime == -1) {
                result += i + 1 + ". \n";
            } else {
                result += i + 1 + ". " + beginnerRanking[i] + "\n";
            }
        }
    } else if (gameMode == 1) {
        result="Intermediate Ranking \n";
        for (int i = 0; i < 5; i++) {
            if (intermediateRanking[i].gainedTime == -1) {
                result += i + 1 + ". \n";
            } else {
                result += i + 1 + ". " + intermediateRanking[i] + "\n";
            }
        }
    } else if (gameMode == 2) {
        result="Expert Ranking \n";
        for (int i = 0; i < 5; i++) {
            if (expertRanking[i].gainedTime == -1) {
                result += i + 1 + ". \n";
            } else {
                result += i + 1 + ". " + expertRanking[i] + "\n";
            }
        }
    }
    return result;
}
public static void saveData() throws FileNotFoundException {
    PrintWriter save = new PrintWriter("src/pl/sanders/data.in");

    for(int i = 0 ; i < 5 ; i++ ) {
        if(beginnerRanking[i].gainedTime==-1|| beginnerRanking[i]==null){
            save.println(" ~-1");
        }else {
            save.println(beginnerRanking[i].name+ "~"+beginnerRanking[i].gainedTime);
        }
        if(intermediateRanking[i].gainedTime==-1){
            save.println(" ~-1");
        }else {
            save.println(intermediateRanking[i].name+ "~"+intermediateRanking[i].gainedTime);
        }
        if(expertRanking[i].gainedTime==-1){
            save.println(" ~-1");
        }else {
            save.println(expertRanking[i].name+ "~"+expertRanking[i].gainedTime);
        }
    }


    save.close();

}
}
