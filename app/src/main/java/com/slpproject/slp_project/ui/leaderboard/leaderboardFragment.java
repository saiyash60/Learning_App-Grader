package com.slpproject.slp_project.ui.leaderboard;

import static com.slpproject.slp_project.DbQuery.g_usersCount;
import static com.slpproject.slp_project.DbQuery.g_usersList;
import static com.slpproject.slp_project.DbQuery.myPerformance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.slpproject.slp_project.Adapters.RankAdapter;
import com.slpproject.slp_project.DbQuery;
import com.slpproject.slp_project.MainActivity;
import com.slpproject.slp_project.MyCompleteListener;
import com.slpproject.slp_project.R;
import com.slpproject.slp_project.TestActivity;


public class leaderboardFragment extends Fragment {

    private TextView totalUsersTV, myImgTextTV, myScoreTV, myRankTV;
    private RecyclerView usersView;
    private RankAdapter adapter;



    public leaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

//        ((MainActivity) getActivity()).getSupportActionBar().setTitle("LeaderBoard");

        initViews(view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        usersView.setLayoutManager(layoutManager);

        adapter = new RankAdapter(DbQuery.g_usersList);

        usersView.setAdapter(adapter);

        DbQuery.getTopUsers(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                adapter.notifyDataSetChanged();

                if(myPerformance.getScore()!=0)
                {
                    if(!DbQuery.isMeOnTopList)
                    {
                        calculateRank();
                    }

                    myScoreTV.setText("Score :" + myPerformance.getScore());
                    myRankTV.setText("Rank - " + myPerformance.getRank());
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        totalUsersTV.setText("Total Users : " + DbQuery.g_usersCount);
        myImgTextTV.setText(myPerformance.getName().toUpperCase().substring(0,1));

        return view;

    }

    private void initViews(View view)
    {
        totalUsersTV = view.findViewById(R.id.total_users);
        myImgTextTV = view.findViewById(R.id.img_text);
        myScoreTV  = view.findViewById(R.id.total_score);
        myRankTV = view.findViewById(R.id.rank);
        usersView = view.findViewById(R.id.users_view);
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