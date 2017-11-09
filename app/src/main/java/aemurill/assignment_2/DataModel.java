package aemurill.assignment_2;

import android.provider.ContactsContract;

import java.util.Observable;

/**
 * Created by Beeooow on 11/9/2017.
 */

public class DataModel extends Observable{
    private static String ARG_LOAD;
    private int loadState;
    private int turn;
    private boolean win;
    private String def_state;
    private String state;
    private int position;

    public DataModel(){
        ARG_LOAD = "LoadState";
        loadState = 0;
        turn = 1;
        win = false;
        def_state = "000000000000000000000000000000000000000000";
        state = def_state;
        position = -1;
    }

    public String getArgLoad() {
        return ARG_LOAD;
    }

    public void setLoadState(int loadState){
        this.loadState = loadState;
    }

    public int getLoadState(){
        return loadState;
    }

    public void setTurn(int turn){
         this.turn = turn;
    }

    public int getTurn(){
        return turn;
    }

    public void setWin(boolean win){
        this.win = win;
    }

    public boolean isWin(){
        return win;
    }

    public String getDef_state() {
        return def_state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public boolean winDetect(String new_state){
        System.out.println(new_state);
        int[][] grid = new int[7][6];
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 6; j++){
                int k = i * 6 + j;
                grid[i][j] = Integer.parseInt(new_state.substring(k, k+1));
            }
        }
        //Vertical
        for (int i = 0; i < 7; i++){
            int count = 0;
            for(int j = 0; j < 6; j++) {
                if (grid[i][j] == turn) count++;
                else count = 0;
                if (count == 4)
                    return true;
            }
        }
        //Horizontal
        for (int j = 0; j < 6; j++){
            int count = 0;
            for(int i = 0; i < 7; i++) {
                if (grid[i][j] == turn) count++;
                else count = 0;
                if (count == 4)
                    return true;
            }
        }

        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++) {
                if(turn == grid[i][j] && turn == grid[i+1][j+1] &&
                        turn == grid[i+2][j+2] && turn == grid[i+3][j+3]){
                    return true;
                }
            }
            for(int j = 5; j > 2; j--) {
                if(turn == grid[i][j] && turn == grid[i+1][j-1] &&
                        turn == grid[i+2][j-2] && turn == grid[i+3][j-3]){
                    return true;
                }
            }
        }
        return false;
    }

    public String updateState(int Position){
        String new_string = "";
        int i = Position;
        new_string = state.substring(0, i*6);
        for (int j = 0; j < 6; j++) {
            int k = i * 6 + j;
            switch (state.charAt(k)) {
                case '0':
                    if(turn == 1){
                        new_string = new_string + "1";
                        for(int l = 0; l < (5 - j); l++){
                            new_string = new_string + "0";
                        }
                        j = 10;
                    }else{
                        new_string = new_string + "2";
                        for(int l = 0; l < (5 - j); l++){
                            new_string = new_string + "0";
                        }
                        j = 10;
                    }
                    break;
                case '1':
                    new_string = new_string + "1";
                    break;
                case '2':
                    new_string = new_string + "2";
                    break;
            }
        }


        new_string = new_string + state.substring(i*6+6, 42);
        //Toast.makeText(getContext(), new_string, Toast.LENGTH_SHORT).show();
        return new_string;
    }
}
