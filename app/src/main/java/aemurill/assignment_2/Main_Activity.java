package aemurill.assignment_2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

public class Main_Activity extends FragmentActivity {

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

//    public void myClickMethod(View view){
//        // The user selected the headline of an article from the HeadlinesFragment
//
//        // Capture the article fragment from the activity layout
//        MainFragment mainFrag = (MainFragment)
//                getSupportFragmentManager().findFragmentById(R.id.main);
//
//        if (mainFrag != null) {
//            // If article frag is available, we're in two-pane layout...
//
//            // Call a method in the ArticleFragment to update its content
//            mainFrag.myClickMethod(view);
//
//        } else {
//            // If the frag is not available, we're in the one-pane layout and must swap frags...
//
//            // Create fragment and give it an argument for the selected article
//            GameFragment newFrag = new GameFragment();
//            Bundle args = new Bundle();
//            //args.putInt(MainFragment.ARG_POSITION, position);
//            newFrag.setArguments(args);
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//            // Replace whatever is in the fragment_container view with this fragment,
//            // and add the transaction to the back stack so the user can navigate back
//            transaction.replace(R.id.fragment_container, newFrag);
//            transaction.addToBackStack(null);
//
//            // Commit the transaction
//            transaction.commit();
//        }
//    }
}

