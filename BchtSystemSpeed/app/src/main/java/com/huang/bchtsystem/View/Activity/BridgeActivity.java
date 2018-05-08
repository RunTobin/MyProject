package com.huang.bchtsystem.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.huang.bchtsystem.R;
import com.huang.bchtsystem.Util.EventbusFinishMessage;
import com.huang.bchtsystem.View.LoginFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by admin on 2017/3/13.
 */

public class BridgeActivity  extends AppCompatActivity{
    public  static final  String TITLE = "title";
    public  static final  String CONTENT_FRAGMENT = "content_fragment";

    public static Intent generateIntent(Context context, String fragmentName) {
        Intent intent = new Intent(context, BridgeActivity.class);
        intent.putExtra(CONTENT_FRAGMENT, fragmentName);
        return intent;
    }

    public static void startActivity(Context context, String fragmentName) {
        Intent intent = generateIntent(context, fragmentName);
        context.startActivity(intent);
    }


    public static void startActivity(Context context, String fragmentName, String title) {
        Intent intent = generateIntent(context, fragmentName);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String fragmentName, String title, Bundle bundle) {
        Intent intent = generateIntent(context, fragmentName);
        intent.putExtra(TITLE, title);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bridge_activity);
        if (getIntent().hasExtra(TITLE)) {
            initActionBar();
        }
        if (getIntent().hasExtra(CONTENT_FRAGMENT)) {
            String fragmentName = getIntent().getStringExtra(CONTENT_FRAGMENT);
            replaceFragment(fragmentName);
        } else {
            replaceFragment("OtherUserSpaceFragment");
        }
        EventBus.getDefault().register(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setTitle(getIntent().getStringExtra(TITLE));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void replaceFragment(String fragmentName) {

        Fragment fragment = initFragmentByName(fragmentName);
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment).commit();
        }
    }
    private Fragment initFragmentByName(String fragmentName) {

        Fragment fragment = generateFragment(fragmentName);
        if (fragment != null) {
            fragment.setArguments(getIntent().getExtras());
        }
        return fragment;
    }
    private Fragment generateFragment(String fragmentName) {
        switch (fragmentName) {
            case "LoginFragment":
                return new LoginFragment();
            default:
                return null;
        }
    }
    @Subscribe
    public void onEventMainThread(EventbusFinishMessage event) {
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onStop() {
        super.onStop();
    }

}
