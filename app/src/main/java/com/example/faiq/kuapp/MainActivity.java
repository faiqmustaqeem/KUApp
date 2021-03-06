package com.example.faiq.kuapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String TAG="MainActivity";
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myRef1;

    List<AnnouncementModel> announcementModelList;
    private RecyclerView recyclerView;
    private AnnouncementAdapter mAdapter;

    List<PostsModel> postsModelList;
    private RecyclerView recyclerView1;
    private PostsAdapter mAdapter1;

    ProgressDialog dialog;
    TextView tvViewAllAnnouncement , tvViewAllAnnouncement1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Wait...");
        dialog.show();

        tvViewAllAnnouncement=(TextView)findViewById(R.id.tvViewAllAnnouncements);
        tvViewAllAnnouncement1=(TextView)findViewById(R.id.tvViewAllAnnouncements1);

        tvViewAllAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(MainActivity.this , AllFAQSActivity.class);
                startActivity(i1);
            }
        });

        tvViewAllAnnouncement1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(MainActivity.this , AllPostsActivity.class);
                startActivity(i1);
            }
        });

        toolbar.setTitle("UBIT App");

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
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("faqs");
        myRef1 = database.getReference("posts");

        announcementModelList=new ArrayList<>();
        final List<AnnouncementModel> temp=new ArrayList<AnnouncementModel>();

        recyclerView = (RecyclerView) findViewById(R.id.rvAnnouncements);
        mAdapter = new AnnouncementAdapter(temp);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
                    String ans= childDataSnapshot.child("answer").getValue()+"";   //gives the value for given keyname
                    Log.e("ans" , ans);
                    String query=childDataSnapshot.child("query").getValue()+"";

                    AnnouncementModel model=new AnnouncementModel();
                    model.setAnswer(ans);
                    model.setQuestion(query);

                    announcementModelList.add(model);
                }
                temp.clear();
                temp.add(announcementModelList.get(announcementModelList.size()-1));
                mAdapter.notifyDataSetChanged();

                dialog.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        postsModelList=new ArrayList<>();
        final List<PostsModel> temp1=new ArrayList<PostsModel>();

        recyclerView1 = (RecyclerView) findViewById(R.id.rvAnnouncements1);
        mAdapter1 = new PostsAdapter(temp1);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView1.setLayoutManager(mLayoutManager1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(mAdapter1);


        // Read from the database
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
                    String post= childDataSnapshot.child("post").getValue()+"";   //gives the value for given keyname
                    //Log.e("ans" , ans);
                    DataSnapshot tag=childDataSnapshot.child("tags");
                    List<String> listTags=new ArrayList<String>();
                    int i=0;
                    for(DataSnapshot tagchild : tag.getChildren() )
                    {
                        String x=tagchild.getValue()+"";
                        Log.e("x" , x);
                        listTags.add(x);
                        i++;
                    }

                    PostsModel model=new PostsModel();
                    model.setPost(post);
                    model.setTags(listTags);

                    postsModelList.add(model);
                }
                temp1.clear();
                temp1.add(postsModelList.get(postsModelList.size()-1));
                mAdapter1.notifyDataSetChanged();

                dialog.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_faqs) {
            Intent i1=new Intent(MainActivity.this , AllFAQSActivity.class);
            startActivity(i1);
            // Handle the camera action
        } else if (id == R.id.nav_posts) {
            Intent i1=new Intent(MainActivity.this , AllPostsActivity.class);
            startActivity(i1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
