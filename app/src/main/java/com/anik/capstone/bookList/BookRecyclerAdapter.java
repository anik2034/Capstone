package com.anik.capstone.bookList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anik.capstone.databinding.BookRowItemBinding;
import com.anik.capstone.model.BookModel;


public class BookRecyclerAdapter extends ListAdapter<BookModel, BookRecyclerAdapter.BookViewHolder> {
    private static final DiffUtil.ItemCallback<BookModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<BookModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull BookModel oldItem, @NonNull BookModel newItem) {
            return oldItem.getISBN().equals(newItem.getISBN());
        }

        @Override
        public boolean areContentsTheSame(@NonNull BookModel oldItem, @NonNull BookModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    public BookRecyclerAdapter() {
        super(DIFF_CALLBACK);
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
        final BookModel book = getItem(position);
        holder.bookRowItemBinding.setBookModel(book);
        holder.bookRowItemBinding.executePendingBindings();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        BookRowItemBinding bookRowItemBinding;

        public BookViewHolder(@NonNull BookRowItemBinding bookRowItemBinding) {
            super(bookRowItemBinding.getRoot());
            this.bookRowItemBinding = bookRowItemBinding;
        }
    }
}

