package aemurill.assignment_2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;
import static junit.framework.Assert.fail;


/**
 * Created by aemurill on 11/4/2017.
 */

public class GameFragment extends Fragment{
    final static String ARG_LOAD = "LoadState";
    int loadState = 0;
    int turn = 1;
    boolean win = false;
    final String def_state = "000000000000000000000000000000000000000000";
    String state = def_state;
    int Position = -1;

    //??????????
    public void startView(int load){
        loadState = load;
        state = def_state;
        turn = 1;
        onStart();
    }

    private int convertPosition(View v, int rPosition){
        return ((7 * rPosition) / v.getMeasuredWidth());

    }

    private void loadGame() {
        StringBuilder text1 = new StringBuilder();
        StringBuilder text2 = new StringBuilder();
        try {
            // open the file for reading we have to surround it with a try
            InputStream inStream = getContext().openFileInput("state.txt");
            //open the text file for reading
            // if file the available for reading
            if (inStream != null) {
                // prepare the file for reading
                InputStreamReader inputReader = new InputStreamReader(inStream);
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = "";
                while ((line = buffReader.readLine()) != null) {
                    //buffered reader reads only one line at a time,
                    // hence we give a while loop to read all till the text is null
                    text1.append(line);
                }
            }
            inStream = getContext().openFileInput("turn.txt");
            //open the text file for reading
            // if file the available for reading
            if (inStream != null) {
                // prepare the file for reading
                InputStreamReader inputReader = new InputStreamReader(inStream);
                BufferedReader buffReader = new BufferedReader(inputReader);
                String line = "";
                while ((line = buffReader.readLine()) != null) {
                    //buffered reader reads only one line at a time,
                    // hence we give a while loop to read all till the text is null
                    text2.append(line);
                    System.out.print(line);
                }
            }
        }
        //now we have to surround it with a catch statement for exceptions
        catch (IOException e) {
            Log.e("LOAD_ERR", "FAILED TO LOAD STRING!");
            e.printStackTrace();
        }

        if (text1 != null && text2 != null) {
//                Toast.makeText(getContext(),
//                        "Load? - " + String.valueOf(loadState), Toast.LENGTH_SHORT).show();
            state = text1.toString();
            turn = Integer.valueOf(text2.toString());
            updateDrawView(state);
        } else {
            Log.e("LOAD_ERR2", "LOADED FILES INVALID");
        }
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
        loadGame();
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

    public void updateDrawView(String new_state) {
        try {
            OutputStreamWriter out1=new OutputStreamWriter(getContext().openFileOutput("state.txt",MODE_PRIVATE));
            out1.write(new_state);
            out1.close();
            OutputStreamWriter out2=new OutputStreamWriter(getContext().openFileOutput("turn.txt",MODE_PRIVATE));
            String text2 = Integer.toString(turn);
            out2.write(text2);
            out2.close();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }

        win = winDetect(new_state);
        System.out.println(win);
        DrawView view = getActivity().findViewById(R.id.drawView);
        view.update(new_state, turn, win);
        if (turn == 1) turn = 2;
        else turn = 1;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the updates the drawing?
        Bundle args = getArguments();
        if (args != null) {
            loadState = args.getInt(ARG_LOAD);
        }
        if (loadState == 1) {
            loadGame();
            //UPDATE DRAWING
        }else if (loadState == 2) {
//            Toast.makeText(getContext(),
//                    "Load? - " + String.valueOf(loadState), Toast.LENGTH_SHORT).show();
            updateDrawView(state);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.game_view, container, false);
        FrameLayout screen = (FrameLayout) view.findViewById(R.id.game);
        final DrawView drawView = (DrawView) view.findViewById(R.id.drawView);
        screen.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN) return true;
                if(event.getAction() == MotionEvent.ACTION_UP && loadState != 0 && !win) {
                    int rPosition = (int)event.getX();
                    Position = convertPosition(v, rPosition);
                    String new_string = updateState(Position);
                    //drawView.update(new_string);
                    updateDrawView(new_string);
                    return true;
                }
                return false;
            }
        });
        return view;
    }
}
