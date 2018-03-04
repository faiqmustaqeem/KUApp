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

public class AllFAQSActivity extends AppCompatActivity {

    ProgressDialog dialog;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<AnnouncementModel> announcementModelList;
    private RecyclerView recyclerView;
    private AnnouncementAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_faqs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Wait...");
        dialog.show();

        toolbar.setTitle("FAQs");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("faqs");

        announcementModelList=new ArrayList<>();
       // final List<AnnouncementModel> temp=new ArrayList<AnnouncementModel>();

        recyclerView = (RecyclerView) findViewById(R.id.rvAnnouncements);
        mAdapter = new AnnouncementAdapter(announcementModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                 //   Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
                    String ans= childDataSnapshot.child("answer").getValue()+"";   //gives the value for given keyname
                    Log.e("ans" , ans);
                    String query=childDataSnapshot.child("query").getValue()+"";

                    AnnouncementModel model=new AnnouncementModel();
                    model.setAnswer(ans);
                    model.setQuestion(query);

                    announcementModelList.add(model);
                }
                //temp.clear();
                //temp.add(announcementModelList.get(announcementModelList.size()-1));
                mAdapter.notifyDataSetChanged();

                dialog.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
           //     Log.w(TAG, "Failed to read value.", error.toException());
            }
        });




    }
}
