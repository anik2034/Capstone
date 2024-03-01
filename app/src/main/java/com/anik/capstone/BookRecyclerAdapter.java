package com.anik.capstone;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anik.capstone.databinding.BookRowItemBinding;
import com.anik.capstone.model.BookModel;

import java.util.ArrayList;
import java.util.List;

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder> {
    private List<BookModel> bookList = new ArrayList<>();

    public void setBooks(List<BookModel> books) {
        this.bookList = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BookRowItemBinding bookRowItemBinding = BookRowItemBinding.inflate(layoutInflater, parent, false);
        return new BookViewHolder(bookRowItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        final BookModel book = bookList.get(position);
        holder.bookRowItemBinding.setBookModel(book);
        holder.bookRowItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {

        BookRowItemBinding bookRowItemBinding;

        public BookViewHolder(@NonNull BookRowItemBinding bookRowItemBinding) {
            super(bookRowItemBinding.getRoot());
            this.bookRowItemBinding = bookRowItemBinding;

        }
    }
}