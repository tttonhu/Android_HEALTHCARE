package com.example.miniproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.miniproject.R;

public class blog_fragment extends Fragment {
    public blog_fragment(){

    }

    public static blog_fragment newInstance(){
        blog_fragment fragment = new blog_fragment();
        Bundle args = new Bundle();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog,container,false);

        return view;
    }
}
