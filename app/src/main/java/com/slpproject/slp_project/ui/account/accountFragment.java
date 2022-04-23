package com.slpproject.slp_project.ui.account;

import static com.slpproject.slp_project.DbQuery.g_usersCount;
import static com.slpproject.slp_project.DbQuery.g_usersList;
import static com.slpproject.slp_project.DbQuery.myPerformance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.slpproject.slp_project.BookmarksActivity;
import com.slpproject.slp_project.DbQuery;
import com.slpproject.slp_project.Login;
import com.slpproject.slp_project.MainActivity;
import com.slpproject.slp_project.MyCompleteListener;
import com.slpproject.slp_project.MyProfileActivity;
import com.slpproject.slp_project.R;
import com.slpproject.slp_project.create_test;
import com.slpproject.slp_project.ui.home.HomeFragment;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class accountFragment extends Fragment {

    private LinearLayout logoutB;
    private TextView profile_img_text, name,score, rank;
    private LinearLayout leaderB, profileB, bookmarksB;
    private BottomNavigationView bottomNavigationView;

    public accountFragment() {
        // Required empty public constructor
        Log.d("ddf","df");
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initViews(view);

//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//
//        ((create_test)getActivity()).getSupportActionBar().setTitle("My Account");

        String userName = DbQuery.myProfile.getName();
        profile_img_text.setText(userName.toUpperCase().substring(0,1));

        name.setText(userName);

        score.setText(String.valueOf(DbQuery.myPerformance.getScore()));

        if(DbQuery.g_usersList.size() == 0)
        {
            DbQuery.getTopUsers(new MyCompleteListener() {
                @Override
                public void onSuccess() {

                    if(myPerformance.getScore()!=0)
                    {
                        if(!DbQuery.isMeOnTopList)
                        {
                            calculateRank();
                        }

                        score.setText("Score :" + myPerformance.getScore());
                        rank.setText("Rank - " + myPerformance.getRank());
                    }
                }

                @Override
                public void onFailure() {
                    Toast.makeText(getContext(), "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
                }
            });

        }

        else
        {
            score.setText("Score :" + myPerformance.getScore());
            if(myPerformance.getScore() != 0)
                rank.setText("Rank - " + myPerformance.getRank());
        }



        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
//                getActivity().finish();
                startActivity(new Intent(getContext(),Login.class));

            }
        });
        bookmarksB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), BookmarksActivity.class);
                        startActivity(intent);

            }
        });
        profileB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyProfileActivity.class);
                startActivity(intent);

            }
        });
        leaderB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.setSelectedItemId(R.id.leaderboard);

            }
        });


        return view;
    }

    private void initViews(View view)
    {
        logoutB = view.findViewById(R.id.logoutB);
        profile_img_text=view.findViewById(R.id.profile_img_text);
        name=view.findViewById(R.id.name);
        score=view.findViewById(R.id.total_score);
        rank=view.findViewById(R.id.rank);
        leaderB=view.findViewById(R.id.leaderB);
        bookmarksB=view.findViewById(R.id.bookmarkB);
        profileB=view.findViewById(R.id.profileB);
        bottomNavigationView= getActivity().findViewById(R.id.bottomNavigationView);


    }
    private void calculateRank()
    {
        int lowTopScore = g_usersList.get(g_usersList.size() - 1).getScore();

        int  remaining_slots = g_usersCount - 20;

        int myslot = (myPerformance.getScore()*remaining_slots) / lowTopScore;

        int rank;

        if(lowTopScore != myPerformance.getScore())
        {
            rank = g_usersCount - myslot;
        }
        else
        {
            rank = 21;
        }

        myPerformance.setRank(rank);


    }

}