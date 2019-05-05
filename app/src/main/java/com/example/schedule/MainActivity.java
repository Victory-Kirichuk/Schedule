package com.example.schedule;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.schedule.fragments.CatalogFragment;
import com.example.schedule.fragments.MySeriesFragment;
import com.example.schedule.fragments.NewSeriesFragment;
import com.example.schedule.fragments.ScheduleFragment;
import com.example.schedule.fragments.TopFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NewSeriesFragment newSeriesFragment;
    ScheduleFragment scheduleFragment;
    TopFragment topFragment;
    CatalogFragment catalogFragment;
    MySeriesFragment mySeriesFragment;
    // private ArrayList<String> AllSeries = new ArrayList<>();
    // ListView ListAllSeries;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore myRef;
    TextView emailProfile;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        newSeriesFragment = new NewSeriesFragment();
        scheduleFragment = new ScheduleFragment();
        topFragment = new TopFragment();
        catalogFragment = new CatalogFragment();
        mySeriesFragment = new MySeriesFragment();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();


        // ListAllSeries = (ListView) findViewById(R.id.allseries);
//      myRef= FirebaseFirestore.getInstance();
//
// myRef.collection("Сериалы").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//     @Override
//     public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//         List<String> strings=queryDocumentSnapshots.toObjects(String.class);
//         AllSeries.addAll(strings);
//
//     }
////           @Override
////           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
////               if(task.isSuccessful())
////           }
////       })
//
//    });
        //System.out.print(AllSeries);}
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.after, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            //todo
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.catalog) {
            setTitle("Каталог");
            fragmentTransaction.replace(R.id.container, catalogFragment);
        } else if (id == R.id.schedule) {
            setTitle("Расписание");
            fragmentTransaction.replace(R.id.container, scheduleFragment);

        } else if (id == R.id.top) {
            setTitle("Лучшие");
            fragmentTransaction.replace(R.id.container, topFragment);
        } else if (id == R.id.myseries) {
            setTitle("Мои сериалы");
            fragmentTransaction.replace(R.id.container, mySeriesFragment);
        } else if (id == R.id.newSeries) {
            setTitle("Новое");
            fragmentTransaction.replace(R.id.container, newSeriesFragment);
        }
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
