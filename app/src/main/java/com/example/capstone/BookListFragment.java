package com.example.capstone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstone.databinding.FragmentBookListBinding;


public class BookListFragment extends Fragment {

    private FragmentBookListBinding fragmentBookListBinding;
    private int textID;

    public BookListFragment(int textID) {
        this.textID = textID;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentBookListBinding = FragmentBookListBinding.inflate(inflater, container, false);
        return fragmentBookListBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setText(textID);
    }

    public void setText(int id) {
        if (fragmentBookListBinding != null) {
            TextView textView = fragmentBookListBinding.bookListTextView;
            textView.setText(id);
        }
    }
}