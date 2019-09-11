package com.example.notset;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.midi.MidiOutputPort;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LatestFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DatabaseReference databaseReports;
    private DatabaseReference mDatabaseRefwi;
    public ProgressBar progressBar;
    private ImageAdapter mAdapter;
    private List<Upload> mUploads;

    public LatestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest, container, false);

        progressBar = view.findViewById(R.id.progressbar1);
        databaseReports = FirebaseDatabase.getInstance().getReference("reports");
        databaseReports.keepSynced(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.myrecycleview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mUploads = new ArrayList<>();

        mDatabaseRefwi = FirebaseDatabase.getInstance().getReference("uploads");

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab_1);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), report.class);
                startActivity(in);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);

        /*
        mDatabaseRefwi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }

                mAdapter = new ImageAdapter(getActivity(), mUploads);

                mRecyclerView.setAdapter(mAdapter);

                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });  */

        FirebaseRecyclerAdapter<allreports, LatestFragment.allreportsHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<allreports, LatestFragment.allreportsHolder>
                (allreports.class, R.layout.list_reports, LatestFragment.allreportsHolder.class, databaseReports) {
            @Override
            protected void populateViewHolder(LatestFragment.allreportsHolder viewHolder, final allreports model, int position) {
                viewHolder.setTitle(model.getReportTitle());
                viewHolder.setDescription(model.getReportDescription());

                viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String titlevalue = ""+model.getReportTitle();
                        String descriptionvalue = ""+model.getReportDescription();

                        Intent intent = new Intent(getActivity(), FullViewDescription.class);
                        intent.putExtra("TITLE",titlevalue);
                        intent.putExtra("DESCRIPTION",descriptionvalue);
                        startActivity(intent);
                    }
                });
            progressBar.setVisibility(View.INVISIBLE);
            }

        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class allreportsHolder extends RecyclerView.ViewHolder {
        View mView;
        public LinearLayout linearLayout;
        public ImageButton imageButton;
        public allreportsHolder(View itemView) {
            super(itemView);
            mView = itemView;
            linearLayout = (LinearLayout) mView.findViewById(R.id.line1);
            imageButton = (ImageButton) mView.findViewById(R.id.imageButtonz);
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setDescription(String description) {
            TextView post_description = (TextView) mView.findViewById(R.id.post_description);
            post_description.setText(description);
        }

    }
}

