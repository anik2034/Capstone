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
import com.anik.capstone.databinding.ListItemDateViewBinding;
import com.anik.capstone.databinding.ListItemEditableTextViewBinding;
import com.anik.capstone.databinding.ListItemHeaderViewBinding;
import com.anik.capstone.databinding.ListItemSpinnerViewBinding;
import com.anik.capstone.databinding.ListItemStarRatingViewBinding;
import com.anik.capstone.databinding.ListItemThumbnailViewBinding;
import com.anik.capstone.databinding.SpinnerViewBinding;
import com.anik.capstone.databinding.StarRatingViewBinding;
import com.anik.capstone.model.BookDetailsModel;
import com.anik.capstone.widgets.HeaderView;


public class BookDetailAdapter extends ListAdapter<BookDetailsModel, RecyclerView.ViewHolder> {

    private static final DiffUtil.ItemCallback<BookDetailsModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<BookDetailsModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull BookDetailsModel oldItem, @NonNull BookDetailsModel newItem) {
            return oldItem.getItemViewType().equals(newItem.getItemViewType());
        }

        @Override
        public boolean areContentsTheSame(@NonNull BookDetailsModel oldItem, @NonNull BookDetailsModel newItem) {
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
        switch (BookDetailsModel.ItemViewType.values()[viewType]) {
            case HEADER:
                return new HeaderViewHolder(R.layout.list_item_header_view, parent);
            case EDITABLE_TEXT:
                return new EditableTextViewHolder(R.layout.list_item_editable_text_view, parent);
            case DATE:
                return new DateViewHolder(R.layout.list_item_date_view, parent);
            case SPINNER:
                return new SpinnerViewHolder(R.layout.list_item_spinner_view, parent);
            case STAR_RATING:
                return new StarRatingViewHolder(R.layout.list_item_star_rating_view, parent);
            case THUMBNAIL:
                return new ThumbnailViewHolder(R.layout.list_item_thumbnail_view, parent);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ThumbnailViewHolder) {
            ((ThumbnailViewHolder) holder).bind(getItem(position), position);
        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(getItem(position), position);
        }
        else if (holder instanceof EditableTextViewHolder) {
            ((EditableTextViewHolder) holder).bind(getItem(position), position);
        }
        else if (holder instanceof SpinnerViewHolder) {
            ((SpinnerViewHolder) holder).bind(getItem(position), position);
        }
        else if (holder instanceof DateViewHolder) {
            ((DateViewHolder) holder).bind(getItem(position), position);
        }
        else if (holder instanceof StarRatingViewHolder) {
            ((StarRatingViewHolder) holder).bind(getItem(position), position);
        }

    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final ListItemHeaderViewBinding binding;

        public HeaderViewHolder(@LayoutRes int layoutRes, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
            binding = ListItemHeaderViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }
        public void bind(BookDetailsModel bookDetailsModel, int position) {
            if (binding != null) {
                binding.itemHeaderView.setHeaderName(bookDetailsModel.getTitle());
            }
        }
    }

    class EditableTextViewHolder extends RecyclerView.ViewHolder {
        private final ListItemEditableTextViewBinding binding;

        public EditableTextViewHolder(@LayoutRes int layoutRes, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
            binding = ListItemEditableTextViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }

        public void bind(BookDetailsModel bookDetailsModel, int position) {
            if (binding != null) {
                binding.itemEditableView.setIsEditable(bookDetailsModel.getIsEditable());
                binding.itemEditableView.setText(bookDetailsModel.getValue());
            }
        }
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        private final ListItemDateViewBinding binding;

        public DateViewHolder(@LayoutRes int layoutRes, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
            binding = ListItemDateViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }

        public void bind(BookDetailsModel bookDetailsModel, int position) {
            if (binding != null) {
                binding.itemDateView.setIsEditable(bookDetailsModel.getIsEditable());
                binding.itemDateView.setDate(bookDetailsModel.getDate());
            }
        }
    }

    class SpinnerViewHolder extends RecyclerView.ViewHolder {
        private final ListItemSpinnerViewBinding binding;

        public SpinnerViewHolder(@LayoutRes int layoutRes, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
            binding = ListItemSpinnerViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }

        public void bind(BookDetailsModel bookDetailsModel, int position) {
            if (binding != null) {
                binding.itemSpinnerView.setIsEditable(bookDetailsModel.getIsEditable());
                binding.itemSpinnerView.setSelected(bookDetailsModel.getSelectedValue());
            }
        }
    }

    class StarRatingViewHolder extends RecyclerView.ViewHolder {
        private final ListItemStarRatingViewBinding binding;

        public StarRatingViewHolder(@LayoutRes int layoutRes, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
            binding = ListItemStarRatingViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        }

        public void bind(BookDetailsModel bookDetailsModel, int position) {
            if (binding != null) {
                binding.itemStarRatingView.setIsEditable(bookDetailsModel.getIsEditable());
                binding.itemStarRatingView.setRating(bookDetailsModel.getRating());
                binding.itemStarRatingView.setRatingType(bookDetailsModel.getTitle());
            }
        }
    }

    class ThumbnailViewHolder extends RecyclerView.ViewHolder {
        private final ListItemThumbnailViewBinding binding;
        public ThumbnailViewHolder(@LayoutRes int layoutRes, ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
            binding = ListItemThumbnailViewBinding.bind(itemView);
        }

        public void bind(BookDetailsModel bookDetailsModel, int position) {
            if (binding != null) {
                binding.setThumbnailUrl(bookDetailsModel.getThumbnailUrl());
            }
        }
    }
}