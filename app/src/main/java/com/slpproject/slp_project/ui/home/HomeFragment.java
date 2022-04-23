package com.slpproject.slp_project.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.slpproject.slp_project.Adapters.CategoryAdapter;
import com.slpproject.slp_project.DbQuery;
import com.slpproject.slp_project.R;
import com.slpproject.slp_project.create_test;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

//    private CategoryAdapter adapter;


    public HomeFragment() {
        // Required empty public constructor
    }

    private GridView catView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//
//        ((create_test)getActivity()).getSupportActionBar().setTitle("Subjects");



        catView = view.findViewById(R.id.cat_grid);
//        loadCategories();

//        DbQuery.loadCategories(new MyCompleteListener() {
//            @Override
//            public void onSuccess() {
//              adapter = new CategoryAdapter(DbQuery.g_catList);
//                catView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure() {
//
//            }
//        });

        CategoryAdapter adapter = new CategoryAdapter(DbQuery.g_catList);
        catView.setAdapter(adapter);


        return view;
    }

}
