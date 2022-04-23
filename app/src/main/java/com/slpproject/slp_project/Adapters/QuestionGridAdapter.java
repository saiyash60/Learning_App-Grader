package com.slpproject.slp_project.Adapters;

import static com.slpproject.slp_project.DbQuery.ANSWERED;
import static com.slpproject.slp_project.DbQuery.NOT_VISITED;
import static com.slpproject.slp_project.DbQuery.REVIEW;
import static com.slpproject.slp_project.DbQuery.UNANSWERED;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.slpproject.slp_project.DbQuery;
import com.slpproject.slp_project.QuestionsActivity;
import com.slpproject.slp_project.R;

public class QuestionGridAdapter extends BaseAdapter {
    private int numOfQues;
    private Context context;

    public QuestionGridAdapter( Context context,int numOfQues) {
        this.numOfQues = numOfQues;
        this.context = context;
    }

    @Override
    public int getCount() {
        return numOfQues;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View myview;
        if(view == null){

            myview= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ques_grid_item,viewGroup,false);

        }else{
            myview=view;
        }
        myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof QuestionsActivity)
                    ((QuestionsActivity)context).goToQuestion(i);


            }
        });
        TextView quesTv = myview.findViewById(R.id.ques_num);
        quesTv.setText(String.valueOf(i+1));

        switch (DbQuery.g_quesList.get(i).getStatus()){
            case NOT_VISITED :
                quesTv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.blue)));
                break;

            case UNANSWERED :
                quesTv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.red)));
                break;

            case ANSWERED :
                quesTv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.green)));
                break;

            case REVIEW :
                quesTv.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(), com.google.android.material.R.color.design_default_color_secondary)));
                break;
            default:
                break;
        }

        return myview;
    }
}
