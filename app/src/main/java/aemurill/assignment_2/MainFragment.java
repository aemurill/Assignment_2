package aemurill.assignment_2;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by aemurill on 11/4/2017.
 */

public class MainFragment extends Fragment implements View.OnClickListener{
    ClickInterface Interface;

    // Container Activity must implement this interface
    public interface ClickInterface {
        public void buttonClicked(int id);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            Interface = (ClickInterface) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() +
                "must implement ClickInterface");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_view, container, false);
        Button loadButton = (Button) rootView.findViewById(R.id.load);
        loadButton.setOnClickListener(this);
        Button restartButton = (Button) rootView.findViewById(R.id.restart);
        restartButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.load:
                //Toast.makeText(getContext(), "LOAD", Toast.LENGTH_SHORT).show();
                Interface.buttonClicked(R.id.load);
                break;
            case R.id.restart:
                //Toast.makeText(getContext(), "RESTART", Toast.LENGTH_SHORT).show();
                Interface.buttonClicked(R.id.restart);
                break;
        }
    }
}
