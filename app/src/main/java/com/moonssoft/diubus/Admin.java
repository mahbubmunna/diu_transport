package com.moonssoft.diubus;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Admin extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private  FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    public static final int RC_SIGN_IN = 1;
    boolean status = false;

    private String adminEmailAddress = "mhmunna88@gmail.com";
    public static final String ADMIN_EMAIL_KEY = "admin_email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings remoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(remoteConfigSettings);

        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put(ADMIN_EMAIL_KEY, adminEmailAddress);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);
        fetchConfig();




        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){

                   if (!user.getEmail().equals(adminEmailAddress)){

                       AuthUI.getInstance().signOut(Admin.this);

                           Toast.makeText(getApplicationContext(),
                                   "Sorry, Dear " +user.getDisplayName()+ ". You are not Authorized. " +
                                           "Please Login Again or press back to return to home.",
                                   Toast.LENGTH_LONG).show();
                           startActivityForResult(
                                   AuthUI.getInstance()
                                           .createSignInIntentBuilder()
                                           .setIsSmartLockEnabled(false)
                                           .setProviders(Arrays.asList(
                                                   new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                           .build(), RC_SIGN_IN
                           );



                    } else {
                       ViewPager viewPager = (ViewPager) findViewById(R.id.admin_view_pager);
                       AdminCategoryAdapter adapter = new AdminCategoryAdapter(getApplicationContext(),
                               getSupportFragmentManager());
                       viewPager.setAdapter(adapter);

                       TabLayout tabLayout = (TabLayout) findViewById(R.id.admin_tabs);
                       tabLayout.setupWithViewPager(viewPager);
                       Toast.makeText(getApplicationContext(), "Welcome, " + user.getDisplayName(),
                               Toast.LENGTH_SHORT).show();

                    }

                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setTheme(R.style.AppThemeFirebaseAuth)
                            .setLogo(R.mipmap.ic_launcher_web)
                            .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                            .setProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                            .build(), RC_SIGN_IN
                    );
                }
            }
        };



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(), "Signed in", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED){
                mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
                Toast.makeText(getApplicationContext(), "Sign in canceled.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void fetchConfig(){
        long cacheExpiration = 3600;
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()){
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mFirebaseRemoteConfig.activateFetched();
                        applyRetrivedAdminEmail();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                applyRetrivedAdminEmail();
                Log.e("emailError", "Error happened", e);
            }
        });
    }

    private void applyRetrivedAdminEmail(){
        String admin_email = mFirebaseRemoteConfig.getString(ADMIN_EMAIL_KEY);
        adminEmailAddress = admin_email;
        Log.d("email", ADMIN_EMAIL_KEY + ":" + admin_email);

    }
}
