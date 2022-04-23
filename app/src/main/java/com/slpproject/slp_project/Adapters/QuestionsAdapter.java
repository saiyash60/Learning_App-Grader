package com.slpproject.slp_project.Adapters;

import static com.slpproject.slp_project.DbQuery.ANSWERED;
import static com.slpproject.slp_project.DbQuery.REVIEW;
import static com.slpproject.slp_project.DbQuery.UNANSWERED;
import static com.slpproject.slp_project.DbQuery.g_quesList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slpproject.slp_project.DbQuery;
import com.slpproject.slp_project.Models.QuestionModel;
import com.slpproject.slp_project.R;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private final List<QuestionModel> questionList;

    public QuestionsAdapter(List<QuestionModel> questionList) {
        this.questionList = questionList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ques;
        private Button optionA, optionB, optionC, optionD, prevSelectedB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ques = itemView.findViewById(R.id.tv_quesion);

            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);

            prevSelectedB = null;
        }

        private void setData(final int pos) {
            ques.setText(questionList.get(pos).getQuestion());
            optionA.setText(questionList.get(pos).getOptionA());
            optionB.setText(questionList.get(pos).getOptionB());
            optionC.setText(questionList.get(pos).getOptionC());
            optionD.setText(questionList.get(pos).getOptionD());

            setOption(optionA, 1, pos);
            setOption(optionB, 2, pos);
            setOption(optionC, 3, pos);
            setOption(optionD, 4, pos);

            optionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectOption(optionA, 1, pos);
                }
            });
            optionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectOption(optionB, 2, pos);
                }
            });
            optionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectOption(optionC, 3, pos);
                }
            });
            optionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectOption(optionD, 4, pos);
                }
            });
        }


        private void selectOption(Button btn, int option_num, int quesID) {
            if (prevSelectedB == null) {
                btn.setBackgroundResource(R.drawable.selected_btn);
                DbQuery.g_quesList.get(quesID).setSelectedAns(option_num);

                changeStatus(quesID, ANSWERED);

                prevSelectedB = btn;
            } else {
                if (prevSelectedB.getId() == btn.getId()) {
                    btn.setBackgroundResource(R.drawable.unselected_btn);
                    DbQuery.g_quesList.get(quesID).setSelectedAns(-1);

                    changeStatus(quesID, UNANSWERED);

                    prevSelectedB = null;
                } else {
                    prevSelectedB.setBackgroundResource(R.drawable.unselected_btn);
                    btn.setBackgroundResource(R.drawable.selected_btn);

                    DbQuery.g_quesList.get(quesID).setSelectedAns(option_num);

                    changeStatus(quesID,ANSWERED);

                    prevSelectedB = btn;
                }
            }
        }
        private void changeStatus(int id, int stauts){
            if (g_quesList.get(id).getStatus() != REVIEW){
                g_quesList.get(id).setStatus(stauts);
            }
        }


        private void setOption(Button btn, int option_num, int quesID) {
            if (DbQuery.g_quesList.get(quesID).getSelectedAns() == option_num) {
                btn.setBackgroundResource(R.drawable.selected_btn);
            } else {
                btn.setBackgroundResource(R.drawable.unselected_btn);
            }
        }

    }
}