package com.example.faiq.kuapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllPostsActivity extends AppCompatActivity {

    List<PostsModel> postsModelList;
    private RecyclerView recyclerView1;
    private PostsAdapter mAdapter1;
    FirebaseDatabase database;
    ProgressDialog dialog;
    DatabaseReference myRef1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_posts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Wait...");
        dialog.show();

        toolbar.setTitle("Posts");
        database = FirebaseDatabase.getInstance();
        myRef1 = database.getReference("posts");




        postsModelList=new ArrayList<>();
        final List<PostsModel> temp1=new ArrayList<PostsModel>();

        recyclerView1 = (RecyclerView) findViewById(R.id.rvAnnouncements1);
        mAdapter1 = new PostsAdapter(postsModelList);
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
                   // Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
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
               // temp1.clear();
               // temp1.add(postsModelList.get(postsModelList.size()-1));
                mAdapter1.notifyDataSetChanged();

                dialog.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
               // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
