package com.anik.capstone.bookDetails;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anik.capstone.R;
import com.anik.capstone.databinding.ListItemDateViewBinding;
import com.anik.capstone.databinding.ListItemEditableTextViewBinding;
import com.anik.capstone.databinding.ListItemHeaderViewBinding;
import com.anik.capstone.databinding.ListItemOptionsViewBinding;
import com.anik.capstone.databinding.ListItemStarRatingViewBinding;
import com.anik.capstone.databinding.ListItemThumbnailViewBinding;
import com.anik.capstone.model.BookDetailsModel;


public class BookDetailAdapter extends ListAdapter<BookDetailsModel, RecyclerView.ViewHolder> {
    private static final DiffUtil.ItemCallback<BookDetailsModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<BookDetailsModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull BookDetailsModel oldItem, @NonNull BookDetailsModel newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull BookDetailsModel oldItem, @NonNull BookDetailsModel newItem) {
            return oldItem.getItemViewType().equals(newItem.getItemViewType()) && oldItem.isEditable() == newItem.isEditable();
        }
    };
    final OnBookDetailItemClickListener clickListener;

    public BookDetailAdapter(OnBookDetailItemClickListener clickListener) {
        super(DIFF_CALLBACK);
        this.clickListener = clickListener;

    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getItemViewType().ordinal();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (BookDetailsModel.ItemViewType.values()[viewType]) {
            case HEADER:
                ListItemHeaderViewBinding headerViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_header_view, parent, false);
                return new HeaderViewHolder(headerViewBinding);
            case EDITABLE_TEXT:
                ListItemEditableTextViewBinding editableTextViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_editable_text_view, parent, false);
                return new EditableTextViewHolder(editableTextViewBinding, clickListener);
            case DATE:
                ListItemDateViewBinding dateViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_date_view, parent, false);
                return new DateViewHolder(dateViewBinding, clickListener);
            case POP_UP:
                ListItemOptionsViewBinding spinnerViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_options_view, parent, false);
                return new OptionsViewHolder(spinnerViewBinding, clickListener);
            case STAR_RATING:
                ListItemStarRatingViewBinding starRatingViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_star_rating_view, parent, false);
                return new StarRatingViewHolder(starRatingViewBinding, clickListener);
            case THUMBNAIL:
                ListItemThumbnailViewBinding thumbnailViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_thumbnail_view, parent, false);
                return new ThumbnailViewHolder(thumbnailViewBinding);
            default:
                throw new IllegalArgumentException("Invalid view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookDetailsModel item = getItem(position);
        switch (item.getItemViewType()) {
            case HEADER:
                ((HeaderViewHolder) holder).bind(item);
                break;
            case EDITABLE_TEXT:
                ((EditableTextViewHolder) holder).bind(item);
                break;
            case DATE:
                ((DateViewHolder) holder).bind(item);
                break;
            case POP_UP:
                ((OptionsViewHolder) holder).bind(item);
                break;
            case STAR_RATING:
                ((StarRatingViewHolder) holder).bind(item);
                break;
            case THUMBNAIL:
                ((ThumbnailViewHolder) holder).bind(item);
                break;
        }
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            itemView.setOnClickListener(v -> clickListener.onItemClick(getAdapterPosition()));
        }

        public void bind(BookDetailsModel bookDetailsModel) {
        }
    }

    public class HeaderViewHolder extends BaseViewHolder {
        private final ListItemHeaderViewBinding binding;

        public HeaderViewHolder(@NonNull ListItemHeaderViewBinding binding) {
            super(binding);
            this.binding = binding;
        }

        @Override
        public void bind(BookDetailsModel bookDetailsModel) {
            if (binding != null) {
                binding.itemHeaderView.setHeaderName(bookDetailsModel.getTitle());
                binding.executePendingBindings();
            }
        }
    }

    public class EditableTextViewHolder extends BaseViewHolder {
        private final ListItemEditableTextViewBinding binding;

        public EditableTextViewHolder(@NonNull ListItemEditableTextViewBinding binding, OnBookDetailItemClickListener clickListener) {
            super(binding);
            this.binding = binding;
        }

        @Override
        public void bind(BookDetailsModel bookDetailsModel) {
            if (binding != null) {
                binding.itemEditableView.setIsEditable(bookDetailsModel.isEditable());
                binding.itemEditableView.setCenter(bookDetailsModel.isCenter());
                binding.itemEditableView.setText(bookDetailsModel.getValue());
                binding.itemEditableView.setListener(text -> clickListener.onTextChanged(text, getAdapterPosition()));
                binding.executePendingBindings();
            }
        }
    }

    public class DateViewHolder extends BaseViewHolder {
        private final ListItemDateViewBinding binding;

        public DateViewHolder(@NonNull ListItemDateViewBinding binding, OnBookDetailItemClickListener clickListener) {
            super(binding);
            this.binding = binding;
        }

        @Override
        public void bind(BookDetailsModel bookDetailsModel) {
            if (binding != null) {
                binding.itemDateView.setIsEditable(bookDetailsModel.isEditable());
                binding.itemDateView.setDate(bookDetailsModel.getDate());
                binding.itemDateView.setListener(date -> clickListener.onDateChanged(date, getAdapterPosition()));
                binding.executePendingBindings();
            }
        }
    }

    public class OptionsViewHolder extends BaseViewHolder {
        private final ListItemOptionsViewBinding binding;

        public OptionsViewHolder(@NonNull ListItemOptionsViewBinding binding, OnBookDetailItemClickListener clickListener) {
            super(binding);
            this.binding = binding;
        }

        public void bind(BookDetailsModel bookDetailsModel) {
            if (binding != null) {
                binding.itemOptionsView.setSelected(bookDetailsModel.getSelectedValue());
                binding.itemOptionsView.setOptions(bookDetailsModel.getSingleSelection());
                binding.itemOptionsView.setListener(selected -> clickListener.onOptionChanged(selected, getAdapterPosition()));
                binding.executePendingBindings();
            }
        }
    }

    public class StarRatingViewHolder extends BaseViewHolder {
        private final ListItemStarRatingViewBinding binding;

        public StarRatingViewHolder(@NonNull ListItemStarRatingViewBinding binding, OnBookDetailItemClickListener clickListener) {
            super(binding);
            this.binding = binding;
        }

        @Override
        public void bind(BookDetailsModel bookDetailsModel) {
            if (binding != null) {
                binding.itemStarRatingView.setIsEditable(bookDetailsModel.isEditable());
                binding.itemStarRatingView.setRating(bookDetailsModel.getRating());
                binding.itemStarRatingView.setRatingType(bookDetailsModel.getTitle());
                binding.itemStarRatingView.setListener(rating -> clickListener.onRatingChanged(rating, getAdapterPosition()));
                binding.executePendingBindings();
            }
        }
    }

    public class ThumbnailViewHolder extends BaseViewHolder {
        private final ListItemThumbnailViewBinding binding;

        public ThumbnailViewHolder(@NonNull ListItemThumbnailViewBinding binding) {
            super(binding);
            this.binding = binding;
        }

        @Override
        public void bind(BookDetailsModel bookDetailsModel) {
            if (binding != null) {
                binding.setThumbnailUrl(bookDetailsModel.getThumbnailUrl());
                binding.executePendingBindings();
            }
        }
    }
    public interface OnBookDetailItemClickListener {
        void onItemClick(int position);

        void onRatingChanged(float rating, int position);

        void onTextChanged(String newText, int position);

        void onDateChanged(String date, int position);

        void onOptionChanged(String selected, int position);
    }


}