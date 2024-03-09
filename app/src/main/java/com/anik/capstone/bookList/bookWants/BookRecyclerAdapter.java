package com.anik.capstone.bookList.bookWants;

import static com.anik.capstone.bookList.ItemViewType.GRID;
import static com.anik.capstone.bookList.ItemViewType.ROW;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anik.capstone.bookList.ItemViewType;
import com.anik.capstone.databinding.BookGridItemBinding;
import com.anik.capstone.databinding.BookRowItemBinding;
import com.anik.capstone.model.BookModel;

public class BookRecyclerAdapter extends ListAdapter<BookModel, RecyclerView.ViewHolder> {
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
    private ItemViewType itemViewType;

    public BookRecyclerAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setItemView(ItemViewType itemViewType) {
        this.itemViewType = itemViewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (itemViewType == GRID) {
            BookGridItemBinding bookGridItemBinding = BookGridItemBinding.inflate(layoutInflater, parent, false);
            return new BookViewHolderGrid(bookGridItemBinding);
        } else if (itemViewType == ROW) {
            BookRowItemBinding bookRowItemBinding = BookRowItemBinding.inflate(layoutInflater, parent, false);
            return new BookViewHolderRow(bookRowItemBinding);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookModel book = getItem(position);
        if (holder instanceof BookViewHolderGrid) {
            ((BookViewHolderGrid) holder).bookGridItemBinding.setBookModel(book);
            ((BookViewHolderGrid) holder).bookGridItemBinding.executePendingBindings();
        } else if (holder instanceof BookViewHolderRow) {
            ((BookViewHolderRow) holder).bookRowItemBinding.setBookModel(book);
            ((BookViewHolderRow) holder).bookRowItemBinding.executePendingBindings();
        }

    }

    static class BookViewHolderRow extends RecyclerView.ViewHolder {
        BookRowItemBinding bookRowItemBinding;

        public BookViewHolderRow(@NonNull BookRowItemBinding bookRowItemBinding) {
            super(bookRowItemBinding.getRoot());
            this.bookRowItemBinding = bookRowItemBinding;
        }
    }

    static class BookViewHolderGrid extends RecyclerView.ViewHolder {
        BookGridItemBinding bookGridItemBinding;

        public BookViewHolderGrid(@NonNull BookGridItemBinding bookGridItemBinding) {
            super(bookGridItemBinding.getRoot());
            this.bookGridItemBinding = bookGridItemBinding;
        }
    }
}

