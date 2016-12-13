package com.quickstart.activities;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.quickstart.R;


/**
 * Created at 05.11.15 17:40
 *
 * @author Alexey_Ivanov
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {

    public void showToast(@StringRes int textResId) {

        showToast(this.getString(textResId));
    }

    public void showToast(String text) {


        final Toast toast = Toast.makeText(
                this,
                text,
                Toast.LENGTH_LONG
        );
        toast.show();
    }


    public void showSnackbar (String text){

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void showSnackbar (@StringRes int textResId){

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), textResId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    protected void placeProperFragment(String fragmentTag) {

        placeProperFragment(R.id.fragment_container, fragmentTag, null);
    }
    protected void placeProperFragment(String fragmentTag, Bundle args) {

        placeProperFragment(R.id.fragment_container, fragmentTag, args);
    }


    protected void placeProperFragment(@IdRes int fragmentResId, String fragmentTag, Bundle args) {
        if (isFinishing()){
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, fragmentTag);
            if (args != null) {
                fragment.setArguments(args);
            }
        }

        transaction.replace(fragmentResId, fragment, fragmentTag);
        transaction.commit();
    }
}
