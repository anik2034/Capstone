package com.anik.capstone.bookList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.anik.capstone.BR;
import com.anik.capstone.R;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BookRecyclerAdapter extends ListAdapter<BookListItem, RecyclerView.ViewHolder> {

    private static final DiffUtil.ItemCallback<BookListItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<BookListItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull BookListItem oldItem, @NonNull BookListItem newItem) {
            return oldItem.getBookModelId() == newItem.getBookModelId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull BookListItem oldItem, @NonNull BookListItem newItem) {
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
        void onItemClick(BookListItem bookListItem);
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public BaseViewHolder(@LayoutRes int layoutRes, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));

            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(BookListItem bookListItem) {
            if (binding != null) {
                binding.setVariable(BR.bookListItem, bookListItem);
                binding.executePendingBindings();
            }
        }
    }
}