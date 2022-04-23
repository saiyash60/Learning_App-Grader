package com.slpproject.slp_project;

import static com.slpproject.slp_project.DbQuery.ANSWERED;
import static com.slpproject.slp_project.DbQuery.NOT_VISITED;
import static com.slpproject.slp_project.DbQuery.REVIEW;
import static com.slpproject.slp_project.DbQuery.UNANSWERED;
import static com.slpproject.slp_project.DbQuery.g_catList;
import static com.slpproject.slp_project.DbQuery.g_quesList;
import static com.slpproject.slp_project.DbQuery.g_selected_cat_index;
import static com.slpproject.slp_project.DbQuery.g_selected_test_index;
import static com.slpproject.slp_project.DbQuery.g_testList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.slpproject.slp_project.Adapters.QuestionGridAdapter;
import com.slpproject.slp_project.Adapters.QuestionsAdapter;

import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity {

    private RecyclerView questionsView;
    private TextView tvQuesID,timerTV, catNameTV;
    private Button submitB, markB, clearSelB;
    private ImageButton prevQuesB, nextQuesB;
    private ImageView quesListB;
    private int quesID;
    QuestionsAdapter quesAdapter;
    private DrawerLayout drawer;
    private ImageButton drawerCloseB;
    private GridView quesListGV;
    private ImageView markImage;
    private QuestionGridAdapter gridAdapter;
    private CountDownTimer timer;
    private long timeLeft;
    private ImageView bookmarkB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list_layout);

        init();

        quesAdapter = new QuestionsAdapter(DbQuery.g_quesList);
        questionsView.setAdapter(quesAdapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionsView.setLayoutManager(layoutManager);

        gridAdapter= new QuestionGridAdapter(this,g_quesList.size());
        quesListGV.setAdapter(gridAdapter);

        setSnapHelper();

        setCLickListeners();

        startTimer();


    }

    private void init()
    {
        questionsView = findViewById(R.id.question_view);
        tvQuesID = findViewById(R.id.tv_quesID);
        timerTV = findViewById(R.id.tv_timer);
        catNameTV = findViewById(R.id.qa_catName);
        submitB = findViewById(R.id.SubmitB);
        markB = findViewById(R.id.markB);
        clearSelB = findViewById(R.id.clear_selB);
        prevQuesB = findViewById(R.id.prev_quesB);
        nextQuesB = findViewById(R.id.next_quesB);
        quesListB = findViewById(R.id.ques_list_gridB);
        quesListGV= findViewById(R.id.ques_list_gv);
        quesID =0;
        bookmarkB=findViewById(R.id.qa_bookmarkB);
        drawer = findViewById(R.id.drawer_layout);
        drawerCloseB=findViewById(R.id.drawerCloseB);
        markImage=findViewById(R.id.mark_image);
        tvQuesID.setText("1/" +String.valueOf(g_quesList.size()));
        catNameTV.setText(g_catList.get(g_selected_cat_index).getName());
//        g_quesList.get(0).setStatus(UNANSWERED);

        if (g_quesList.get(0).isBookmarked()){
            bookmarkB.setImageResource(R.drawable.bookmark_color_icon);

        }else{
            bookmarkB.setImageResource(R.drawable.bookmark_icon);
        }


    }

    private void setSnapHelper()
    {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionsView);

        questionsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                quesID = recyclerView.getLayoutManager().getPosition(view);

                if (g_quesList.get(quesID).getStatus() == NOT_VISITED)
                    g_quesList.get(quesID).setStatus(UNANSWERED);
                if (g_quesList.get(quesID).getStatus()==REVIEW){
                    markImage.setVisibility(View.VISIBLE);
                }else{
                    markImage.setVisibility(View.GONE);
                }

                tvQuesID.setText(String.valueOf(quesID + 1) + "/" + String.valueOf(g_quesList.size()));

                if (g_quesList.get(quesID).isBookmarked()){
                    bookmarkB.setImageResource(R.drawable.bookmark_color_icon);

                }else{
                    bookmarkB.setImageResource(R.drawable.bookmark_icon);
                }


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
    private void setCLickListeners()
    {
        prevQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quesID >0)
                {
                    questionsView.smoothScrollToPosition(quesID - 1);
                }
            }
        });

        nextQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quesID < g_quesList.size()- 1){
                    questionsView.smoothScrollToPosition(quesID + 1);
                }
            }
        });

        clearSelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g_quesList.get(quesID).setSelectedAns(-1);
                g_quesList.get(quesID).setStatus(UNANSWERED);
                markImage.setVisibility(View.GONE);

                quesAdapter.notifyDataSetChanged();

            }
        });
        quesListB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(! drawer.isDrawerOpen(GravityCompat.END))
                {
                    gridAdapter.notifyDataSetChanged();
                    drawer.openDrawer(GravityCompat.END);
                }

            }
        });
        drawerCloseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawer.isDrawerOpen(GravityCompat.END)){
                    drawer.closeDrawer(GravityCompat.END);
                }
            }
        });

        markB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (markImage.getVisibility() != view.VISIBLE){
                    markImage.setVisibility(view.VISIBLE);
                    g_quesList.get(quesID).setStatus(REVIEW);
                }else{
                    markImage.setVisibility(View.GONE);
                    if (g_quesList.get(quesID).getSelectedAns() != -1){
                        g_quesList.get(quesID).setStatus(ANSWERED);
                    }else{
                      g_quesList.get(quesID).setStatus(UNANSWERED);
                    }
                }
            }
        });
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumbitTest();


            }
        });

        bookmarkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToBookmark();
            }
        });

    }
    private void sumbitTest(){
        AlertDialog.Builder builder =new AlertDialog.Builder(QuestionsActivity.this);
        builder.setCancelable(true);

        View view1 = getLayoutInflater().inflate(R.layout.alart_dialog_layout, null);
        Button cancelB = view1.findViewById(R.id.cancelB);
        Button confirmB = view1.findViewById(R.id.confirmB);
        builder.setView(view1);
        AlertDialog alertDialog = builder.create();
        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                alertDialog.dismiss();

                Intent intent = new Intent(QuestionsActivity.this,ScoreActivity.class);
                long totalTime = g_testList.get(g_selected_test_index).getTime()*60*1000;
                intent.putExtra("TIME TAKEN", totalTime - timeLeft);
                startActivity(intent);
                QuestionsActivity.this.finish();
            }
        });
        alertDialog.show();
    }
    public void goToQuestion(int position){
        questionsView.smoothScrollToPosition(position);
        if (drawer.isDrawerOpen(GravityCompat.END))
            drawer.closeDrawer(GravityCompat.END);
    }

    private  void startTimer(){

        long totalTime = g_testList.get(g_selected_test_index).getTime()*60*1000;

        timer = new CountDownTimer(totalTime + 1000, 1000) {
            @Override
            public void onTick(long remainingTime) {

                timeLeft = remainingTime;

                String time = String.format("%02d:%02d min",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)));

                timerTV.setText(time);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(QuestionsActivity.this,ScoreActivity.class);
                long totalTime = g_testList.get(g_selected_test_index).getTime()*60*1000;
                intent.putExtra("TIME TAKEN", totalTime - timeLeft);
                startActivity(intent);
                QuestionsActivity.this.finish();

            }
        };

        timer.start();
    }

    private void addToBookmark(){
        if(g_quesList.get(quesID).isBookmarked()){

            g_quesList.get(quesID).setBookmarked(false);
            bookmarkB.setImageResource(R.drawable.bookmark_icon);

        }else{
            g_quesList.get(quesID).setBookmarked(true);
            bookmarkB.setImageResource(R.drawable.bookmark_color_icon);
        }
    }


}