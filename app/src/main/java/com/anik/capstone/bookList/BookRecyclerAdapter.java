package com.anik.capstone.bookList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anik.capstone.BR;
import com.anik.capstone.R;
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
    private final OnItemClickListener itemClickListener;
    private LayoutViewType layoutViewType;

    public BookRecyclerAdapter(OnItemClickListener itemClickListener) {
        super(DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;

    }

    public void setLayoutViewType(LayoutViewType layoutViewType) {
        this.layoutViewType = layoutViewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (layoutViewType) {
            case ROW:
                return new BaseViewHolder(R.layout.book_row_item, parent);
            case GRID:
                return new BaseViewHolder(R.layout.book_grid_item, parent);
        }
        throw new IllegalArgumentException("Invalid view type: " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseViewHolder) {
            ((BaseViewHolder) holder).bind(getItem(position));
            holder.itemView.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(BookModel bookModel);
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public BaseViewHolder(@LayoutRes int layoutRes, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));

            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(BookModel bookModel) {
            if (binding != null) {
                binding.setVariable(BR.bookModel, bookModel);
                binding.executePendingBindings();
            }
        }
    }
}