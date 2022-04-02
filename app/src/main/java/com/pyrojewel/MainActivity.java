package com.pyrojewel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pyrojewel.EventDeal.InputActivity;

/**
 * @author Pyrojewel
 */
public class MainActivity extends AppCompatActivity {
    private FloatingActionMenu addButton;
    private FloatingActionButton camera;
    private FloatingActionButton gallery;


    public static String ADD_EVENT_SET_ACTION = "action.add.event.set";
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void onClickInput(View view){
        Intent intent=new Intent(this, InputActivity.class);
        startActivity(intent);
    }
    /**我的思考是，先调用接口
     * 然后处理完之后再跳转新页面？？？是不是还要一个NLP的处理？？
     * */
    public void onClickPictureInput(View view){

    }
    public void onClickVoiceInput(View view){

    }
}