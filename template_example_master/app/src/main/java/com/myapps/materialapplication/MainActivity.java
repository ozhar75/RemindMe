package com.myapps.materialapplication;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.util.StringTokenizer;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        Firebase mRef = new Firebase(Constants.FIREBASE_HOME_URL);
        AuthData authData = mRef.getAuth();
        String mail = authData.getProviderData().get("email").toString();
        StringTokenizer token = new StringTokenizer(mail, "@");
        String uname = token.nextToken();
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        mNavigationDrawerFragment.closeDrawer();
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData(uname, mail, BitmapFactory.decodeResource(getResources(), R.drawable.avatar));


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = getFragmentManager().findFragmentByTag(HomeFragment.TAG);
                if (fragment == null) {
                    fragment = new HomeFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, HomeFragment.TAG).commit();
                break;
            case 1:
                fragment = getFragmentManager().findFragmentByTag(DoctorFragment.TAG);
                if (fragment == null) {
                    fragment = new DoctorFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, DoctorFragment.TAG).commit();
                break;
            case 2:
                fragment = getFragmentManager().findFragmentByTag(Updates.TAG);
                if (fragment == null) {
                    fragment = new Updates();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, Updates.TAG).commit();
                break;
            case 3:
                fragment = getFragmentManager().findFragmentByTag(MeasurementFragment.TAG);
                if (fragment == null) {
                    fragment = new MeasurementFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, MeasurementFragment.TAG).commit();
                break;
            case 5:
                fragment = getFragmentManager().findFragmentByTag(Profile.TAG);
                if (fragment == null) {
                    fragment = new Profile();
                }
                getFragmentManager().beginTransaction().replace(R.id.container, fragment, Profile.TAG).commit();

        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
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

        switch (id){
            case R.id.action_logout:
                Firebase firebase = new Firebase(Constants.FIREBASE_HOME_URL);
                firebase.unauth();
                finish();
                startActivity(new Intent(this, LoginRegisterActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }


}
