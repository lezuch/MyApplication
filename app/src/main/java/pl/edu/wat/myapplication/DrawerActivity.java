package pl.edu.wat.myapplication;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import static android.content.ClipData.*;


public class DrawerActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment;
        switch (position){
            case 0:
                fragment=MondayFragment.newInstance(null,null);
                break;
            case 1:
                fragment=TuesdayFragment.newInstance(null,null);
                break;

            case 2:
                fragment=WednesdayFragment.newInstance(null,null);
                break;
            case 3:
                fragment=ThursdayFragment.newInstance(null,null);
                break;
            case 4:
                fragment=FridayFragment.newInstance(null,null);
                break;

            default:
                fragment=MondayFragment.newInstance(null,null);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        onSectionAttached(position);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = "Poniedziałek";
                break;
            case 1:
                mTitle = "Wtorek";
                break;
            case 2:
                mTitle = "Środa";
                break;
            case 3:
                mTitle = "Czwartek";
                break;
            case 4:
                mTitle = "Piątek";
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.drawer, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_example) {
            //Intent intent2=new Intent(this, LogowanieActivity.class);
            //startActivity(intent2);
            return true;
        }
        if (id == R.id.About) {

            Toast.makeText(this, "Aplikacja", Toast.LENGTH_SHORT).show();
            return true;


        }
        if (id == R.id.action_logout) {
            Intent intent3=new Intent(this, MainActivity.class);
            startActivity(intent3);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onLogoutClick(MenuItem item) {
        Intent intent1=new Intent(this, MainActivity.class);
        startActivity(intent1);
    }



}
