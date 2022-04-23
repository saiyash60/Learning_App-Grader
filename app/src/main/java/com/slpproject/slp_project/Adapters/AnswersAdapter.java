package com.slpproject.slp_project.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.SetOptions;
import com.slpproject.slp_project.Models.QuestionModel;
import com.slpproject.slp_project.R;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private List<QuestionModel>queslist;

    public AnswersAdapter(List<QuestionModel> queslist) {
        this.queslist = queslist;
    }

    @NonNull
    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item_layout,parent,false);

        return new AnswersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersAdapter.ViewHolder holder, int position) {

        String ques = queslist.get(position).getQuestion();
        String a = queslist.get(position).getOptionA();
        String b = queslist.get(position).getOptionB();
        String c = queslist.get(position).getOptionC();
        String d = queslist.get(position).getOptionD();
        int selected = queslist.get(position).getSelectedAns();
        int ans = queslist.get(position).getCorrectAns();

        holder.setData(position, ques, a, b, c, d,selected,ans);

    }

    @Override
    public int getItemCount() {
        return queslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView quesNo, question, optionA, optionB,optionC, optionD, result;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            quesNo=itemView.findViewById(R.id.quesNo);
            question=itemView.findViewById(R.id.question);
            optionA=itemView.findViewById(R.id.optionA);
            optionB=itemView.findViewById(R.id.optionB);
            optionC=itemView.findViewById(R.id.optionC);
            optionD=itemView.findViewById(R.id.optionD);
            result=itemView.findViewById(R.id.result);

        }
        private void setData(int pos, String ques,String a, String b, String c, String d, int selected, int correctAns)
        {
            quesNo.setText("Question No."+ String.valueOf(pos+1));
            question.setText(ques);
            optionA.setText("A " +a);
            optionB.setText("B " +b);
            optionC.setText("C " +c);
            optionD.setText("D " +d);

            if (selected == -1){
                result.setText("UN-ANSWERED");
                result.setTextColor(itemView.getContext().getResources().getColor(R.color.black));

                setOptionColor(selected, R.color.text_normal);
            }else{
                if (selected==correctAns){
                    result.setText("Correct");
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.green));

                    setOptionColor(selected, R.color.green);
                }else{
                    result.setText("WRONG");
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.red));

                    setOptionColor(selected, R.color.red);

                }
            }


        }

        private void setOptionColor(int selected,int color){
            if (selected==1){
                optionA.setTextColor(itemView.getContext().getResources().getColor(color));
            }
            else{
                optionA.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }

            if (selected==2){
                optionB.setTextColor(itemView.getContext().getResources().getColor(color));
            }
            else{
                optionB.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }

            if (selected==3){
                optionC.setTextColor(itemView.getContext().getResources().getColor(color));
            }
            else{
                optionC.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }

            if (selected==4){
                optionD.setTextColor(itemView.getContext().getResources().getColor(color));
            }
            else{
                optionD.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }


        }
    }
}
