package com.ltrsoft.rajashtanuserapplication.fragments;
import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.ltrsoft.rajashtanuserapplication.LoginActivity;
import com.ltrsoft.rajashtanuserapplication.R;

public class NavigationDrawer extends Fragment {
public NavigationDrawer() {}
    public DrawerLayout drawerLayout;
    public Toolbar toolbar;
    public NavigationView navigationView;
    public ActionBarDrawerToggle toggle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        navigationView = v.findViewById(R.id.navigation);
        drawerLayout = v.findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getContext().getColor(R.color.white));

        DashBoard dashboardFragment = new DashBoard();
        getFragmentManager().beginTransaction().replace(R.id.containermain, dashboardFragment).addToBackStack(null).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home) {
                    item.setChecked(true);
                    DashBoard dashboardFragment = new DashBoard();
                    getFragmentManager().beginTransaction().replace(R.id.containermain, dashboardFragment).addToBackStack(null).commit();
                } else if (id == R.id.profile) {
                    item.setChecked(true);
                    getFragmentManager().beginTransaction().replace(R.id.containermain, new Profile()).addToBackStack(null).commit();
                }else if (id == R.id.setting) {
                    item.setChecked(true);
                    getFragmentManager().beginTransaction().replace(R.id.containermain, new Setting()).addToBackStack(null).commit();
                }
                else if (id==R.id.feedback){
                    item.setChecked(true);
                    getFragmentManager().beginTransaction().replace(R.id.containermain, new Feedback_Fragment()).addToBackStack(null).commit();
                }
                else if (id == R.id.logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Logout Dailoge");
                    builder.setMessage("Do You Want To Logout?");
                    builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences pref = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean("flag", false)
                                    .apply();
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
        return v;
    }
}