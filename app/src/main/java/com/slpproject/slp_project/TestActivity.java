package com.slpproject.slp_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.slpproject.slp_project.Adapters.TestAdapter;

public class TestActivity extends AppCompatActivity {

    private RecyclerView testView;
    private Toolbar toolbar;
    private TestAdapter adapter;
//    private List<TestModel> testlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        int cat_index = getIntent().getIntExtra("CAT_INDEX",0);
//        getSupportActionBar().setTitle(DbQuery.g_catList.get(cat_index).getName());

        getSupportActionBar().setTitle(DbQuery.g_catList.get(DbQuery.g_selected_cat_index).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        testView = findViewById(R.id.test_recycle_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        testView.setLayoutManager(layoutManager);

        //loadTestData();

        DbQuery.loadTestData(new MyCompleteListener() {
            @Override
            public void onSuccess() {

                DbQuery.loadMyScores(new MyCompleteListener() {
                    @Override
                    public void onSuccess() {
                        adapter = new TestAdapter(DbQuery.g_testList);
                        testView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(TestActivity.this, "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure() {
                Toast.makeText(TestActivity.this, "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

//        TestAdapter adapter = new TestAdapter(testlist);
//        testView.setAdapter(adapter);

    }

//        private void loadTestData()
//        {
//            testlist = new ArrayList<>();
//
//            testlist.add(new TestModel("1",30,40));
//            testlist.add(new TestModel("2",20,15));
//            testlist.add(new TestModel("3",50,60));
//            testlist.add(new TestModel("4",25,30));
//        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            TestActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}