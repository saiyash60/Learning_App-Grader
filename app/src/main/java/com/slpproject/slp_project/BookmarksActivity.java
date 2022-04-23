package com.slpproject.slp_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.slpproject.slp_project.Adapters.AnswersAdapter;
import com.slpproject.slp_project.Adapters.BookmarksAdapter;

public class BookmarksActivity extends AppCompatActivity {

    private RecyclerView questionsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        Toolbar toolbar = findViewById(R.id.ba_toolbar);
        questionsView= findViewById(R.id.ba_recycle_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle("Saved Questions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        questionsView.setLayoutManager(layoutManager);

        DbQuery.loadBookmarks(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                BookmarksAdapter adapter = new BookmarksAdapter(DbQuery.g_bookmarksList);
                questionsView.setAdapter(adapter);
            }

            @Override
            public void onFailure() {

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            BookmarksActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}


