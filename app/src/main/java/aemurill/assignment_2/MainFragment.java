package aemurill.assignment_2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Beeooow on 11/4/2017.
 */

public class MainFragment extends Fragment implements View.OnClickListener{
//    OnButtonClickListener buttonCallback;
//
//    // Container Activity must implement this interface
//    public interface OnButtonClickListener {
//        public void myClickMethod();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*// Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_view, container, false);
        */
        View rootView = inflater.inflate(R.layout.main_view, container, false);
        Button playButton = (Button) rootView.findViewById(R.id.play);
        playButton.setOnClickListener(this);
        Button loadButton = (Button) rootView.findViewById(R.id.load);
        loadButton.setOnClickListener(this);
        Button restartButton = (Button) rootView.findViewById(R.id.restart);
        restartButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.play:
                Toast.makeText(getContext(), "PLAY", Toast.LENGTH_SHORT).show();
                break;
            case R.id.load:
                Toast.makeText(getContext(), "LOAD", Toast.LENGTH_SHORT).show();
                break;
            case R.id.restart:
                Toast.makeText(getContext(), "RESTART", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
