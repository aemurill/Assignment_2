package aemurill.assignment_2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import static junit.framework.Assert.fail;


/**
 * Created by aemurill on 11/4/2017.
 */

public class GameFragment extends Fragment{
    final static String ARG_LOAD = "LoadState";
    boolean loadState = false;
    int turn = 1;
    String state = "000000000000000000000000000000000000000000";

    int Position = -1;


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
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    int rPosition = (int)event.getX();
                    Position = convertPosition(v, rPosition);
                    String new_string = updateState(Position, turn);
                    drawView.update(new_string);
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    private int convertPosition(View v, int rPosition){
        return ((7 * rPosition) / v.getMeasuredWidth());

    }

    public String updateState(int Position, int turn){
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
        Toast.makeText(getContext(), new_string, Toast.LENGTH_SHORT).show();
        return new_string;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the updates the drawing?
        Bundle args = getArguments();
        loadState = args.getBoolean(ARG_LOAD);
//        if (loadState) {
//            StringBuilder text = new StringBuilder();
//            try {
//                // open the file for reading we have to surround it with a try
//                InputStream inStream = getContext().openFileInput("state.txt");//open the text file for reading
//                // if file the available for reading
//                if (inStream != null) {
//                    // prepare the file for reading
//                    InputStreamReader inputReader = new InputStreamReader(inStream);
//                    BufferedReader buffReader = new BufferedReader(inputReader);
//                    String line = "";
//                    while (( line = buffReader.readLine()) != null) {
//                        //buffered reader reads only one line at a time, hence we give a while loop to read all till the text is null
//                        text.append(line);
//                    }}}
//            //now we have to surround it with a catch statement for exceptions
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            if(text != null) {
//                updateDrawView(text.toString());
//                Toast.makeText(getContext(),
//                        "Load? - " + String.valueOf(loadState), Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(getContext(),
//                        "Load failed" + String.valueOf(loadState), Toast.LENGTH_SHORT).show();
//            }
//        //UPDATE DRAWING
//        }else Toast.makeText(getContext(),
//                "Load? - " + String.valueOf(loadState), Toast.LENGTH_SHORT).show();
    }

    public void updateDrawView(String new_state) {
//        try {
//            // open myfilename.txt for writing
//            OutputStreamWriter out=new OutputStreamWriter(getContext().openFileOutput("state.txt",MODE_PRIVATE));
//            // write the contents to the file
//            String text = state;
//            out.write(text);
//            // close the file
//            out.close();
//            Toast.makeText(getContext(), "Text Saved! " + text, Toast.LENGTH_LONG).show();
//        }
//        catch (java.io.IOException e) {
//            //do something if an IOException occurs.
//            Toast.makeText(getContext(), "Sorry Text could't be added", Toast.LENGTH_LONG).show();
//        }
//
//        DrawView view = getActivity().findViewById(R.id.drawView);
//        view.update(new_state);
    }
}
