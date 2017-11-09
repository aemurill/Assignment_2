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
    private DataModel model = new DataModel();
    //??????????
    public void startView(int load){
        model.setLoadState(load);
        model.setState(model.getDef_state());
        model.setTurn(1);
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
            model.setState(text1.toString());
            model.setTurn(Integer.valueOf(text2.toString()));
            updateDrawView(model.getState());
        } else {
            Log.e("LOAD_ERR2", "LOADED FILES INVALID");
        }
    }

    public void updateDrawView(String new_state) {
        try {
            OutputStreamWriter out1=new OutputStreamWriter(getContext().openFileOutput("state.txt",MODE_PRIVATE));
            out1.write(new_state);
            out1.close();
            OutputStreamWriter out2=new OutputStreamWriter(getContext().openFileOutput("turn.txt",MODE_PRIVATE));
            String text2 = Integer.toString(model.getTurn());
            out2.write(text2);
            out2.close();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }

        model.setWin(model.winDetect(new_state));
        System.out.println(model.isWin());
        DrawView view = getActivity().findViewById(R.id.drawView);
        view.update(new_state, model.getTurn(), model.isWin());
        if (model.getTurn() == 1) model.setTurn(2);
        else model.setTurn(1);
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
            model.setLoadState(
                    args.getInt(model.getArgLoad())
            );
        }
        if (model.getLoadState() == 1) {
            loadGame();
            //UPDATE DRAWING
        }else if (model.getLoadState() == 2) {
//            Toast.makeText(getContext(),
//                    "Load? - " + String.valueOf(loadState), Toast.LENGTH_SHORT).show();
            updateDrawView(model.getState());
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
                if(event.getAction() == MotionEvent.ACTION_UP
                        && model.getLoadState() != 0 && !model.isWin()) {
                    int rPosition = (int)event.getX();
                    model.setPosition(convertPosition(v, rPosition));
                    loadGame();
                    String new_string = model.updateState(model.getPosition());
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
