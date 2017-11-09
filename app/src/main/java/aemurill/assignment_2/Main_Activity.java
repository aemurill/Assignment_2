package aemurill.assignment_2;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main_Activity extends FragmentActivity implements MainFragment.ClickInterface{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout aka SMALL
        if (findViewById(R.id.fragment_container) != null) {
            //force screen orientation portrait
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            MainFragment currentFragment;
            currentFragment = new MainFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            currentFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, currentFragment).commit();


        }else{
            // the layout is the one without fragment_container aka LARGE
            //force screen orientation portrait
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }


    }

    private void swapViews(int loadState){
        // Capture the game fragment from the activity layout
        GameFragment gameFrag = (GameFragment)
                getSupportFragmentManager().findFragmentById(R.id.game_fragment);

        if (gameFrag != null) {
            // If game frag is available, we're in two-pane layout...

            // Call a method in the GameFragment to update its content
            gameFrag.startView(loadState);
            //Toast.makeText(this, "UPDATING VIEW", Toast.LENGTH_SHORT).show();

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...
            //Toast.makeText(this, "SWAPPING VIEW", Toast.LENGTH_SHORT).show();
            // Create fragment and give it an argument for the selected article
            GameFragment newFrag = new GameFragment();
            Bundle args = new Bundle();
            args.putInt(DataModel.ARG_LOAD, loadState);
            newFrag.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFrag);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void buttonClicked(int id){
        switch (id){
            //player selected New Game
            case R.id.restart:
                //Toast.makeText(this, "RESTART", Toast.LENGTH_SHORT).show();
                swapViews(2);
                break;
            //player selected Saved Game
            case R.id.load:
                //Toast.makeText(this, "LOAD", Toast.LENGTH_SHORT).show();
                swapViews(1);
                break;
        }
    }
}

