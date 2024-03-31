package com.anik.capstone.bookDetails;

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
import com.anik.capstone.databinding.DateViewBinding;
import com.anik.capstone.databinding.EditableTextViewBinding;
import com.anik.capstone.databinding.HeaderBinding;
import com.anik.capstone.databinding.SpinnerViewBinding;
import com.anik.capstone.databinding.StarRatingViewBinding;
import com.anik.capstone.model.BookDetailModel;


public class BookDetailAdapter extends ListAdapter<BookDetailModel, RecyclerView.ViewHolder> {

    private static final DiffUtil.ItemCallback<BookDetailModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<BookDetailModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull BookDetailModel oldItem, @NonNull BookDetailModel newItem) {
            return oldItem.getItemViewType().equals(newItem.getItemViewType());
        }

        @Override
        public boolean areContentsTheSame(@NonNull BookDetailModel oldItem, @NonNull BookDetailModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    public BookDetailAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getItemViewType().ordinal();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (BookDetailModel.ItemViewType.values()[viewType]) {
            case HEADER:
                return new BaseViewHolder(R.layout.header, parent);
            case EDITABLE_TEXT:
                return new BaseViewHolder(R.layout.editable_text_view, parent);
            case DATE:
                return new BaseViewHolder(R.layout.date_view, parent);
            case SPINNER:
                return new BaseViewHolder(R.layout.list_item_book_detail_spinner, parent);
            case STAR_RATING:
                return new BaseViewHolder(R.layout.star_rating_view, parent);
            case THUMBNAIL:
                return new ThumbnailViewHolder(R.layout.list_item_thumbnail_view, parent);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseViewHolder) {
            ((BaseViewHolder) holder).bind(getItem(position), position);
        }
    }


    class BaseViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public BaseViewHolder(@LayoutRes int layoutRes, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));

            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(BookDetailModel bookDetailModel, int position) {
            if (binding != null) {
                if (binding instanceof HeaderBinding) {
                    binding.setVariable(BR.headerName, bookDetailModel.getTitle());

                } else if (binding instanceof EditableTextViewBinding) {
                    binding.setVariable(BR.text, currentData);
                    binding.setVariable(BR.isEditable, bookDetailModel.isEditable());

                } else if (binding instanceof SpinnerViewBinding) {
                    binding.setVariable(BR.item, currentData.toString());
                    binding.setVariable(BR.isEditable, isEditable);

                } else if (binding instanceof DateViewBinding) {
                    binding.setVariable(BR.date, currentData);
                    binding.setVariable(BR.isEditable, isEditable);

                } else if (binding instanceof StarRatingViewBinding) {
                    binding.setVariable(BR.rating, currentData);
                    binding.setVariable(BR.ratingType, data.get(position+1));
                }
                binding.executePendingBindings();
            }
        }
    }

    class ThumbnailViewHolder extends BaseViewHolder {
        public ThumbnailViewHolder(int layoutRes, ViewGroup parent) {
            super(layoutRes, parent);
        }

        @Override
        public void bind(ItemType itemType, int position) {
            super.bind(itemType, position);
        }
    }
}