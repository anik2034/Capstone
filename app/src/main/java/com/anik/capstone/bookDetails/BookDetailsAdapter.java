package com.anik.capstone.bookDetails;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.anik.capstone.R;
import com.anik.capstone.databinding.ListItemDateViewBinding;
import com.anik.capstone.databinding.ListItemEditableTextViewBinding;
import com.anik.capstone.databinding.ListItemHeaderViewBinding;
import com.anik.capstone.databinding.ListItemOptionsViewBinding;
import com.anik.capstone.databinding.ListItemStarRatingViewBinding;
import com.anik.capstone.databinding.ListItemThumbnailViewBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class BookDetailsAdapter extends ListAdapter<BookDetailsItem, RecyclerView.ViewHolder> {
    private static final DiffUtil.ItemCallback<BookDetailsItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<BookDetailsItem>() {

        @Override
        public boolean areItemsTheSame(@NonNull BookDetailsItem oldItem, @NonNull BookDetailsItem newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull BookDetailsItem oldItem, @NonNull BookDetailsItem newItem) {
            return oldItem.getItemViewType().equals(newItem.getItemViewType()) && oldItem.isEditable() == newItem.isEditable();
        }
    };
    final OnBookDetailItemClickListener clickListener;

    public BookDetailsAdapter(OnBookDetailItemClickListener clickListener) {
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
        switch (BookDetailsItem.ViewType.values()[viewType]) {
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
        BookDetailsItem item = getItem(position);
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

        public void bind(BookDetailsItem bookDetailsItem) {
        }
    }

    public class HeaderViewHolder extends BaseViewHolder {
        private final ListItemHeaderViewBinding binding;

        public HeaderViewHolder(@NonNull ListItemHeaderViewBinding binding) {
            super(binding);
            this.binding = binding;
        }

        @Override
        public void bind(BookDetailsItem bookDetailsItem) {
            if (binding != null) {
                binding.itemHeaderView.setHeaderName(bookDetailsItem.getTitle());
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
        public void bind(BookDetailsItem bookDetailsItem) {
            if (binding != null) {
                binding.itemEditableView.setIsEditable(bookDetailsItem.isEditable());
                binding.itemEditableView.setHint(bookDetailsItem.getHint());
                binding.itemEditableView.setText(bookDetailsItem.getValue());
                binding.itemEditableView.setListener((oldText, newText) ->
                        clickListener.onTextChanged(oldText, newText, getAdapterPosition())
                );
                if (bookDetailsItem.getItemType() == BookDetailsItem.ItemType.TITLE) {
                    binding.itemEditableView.setStyle(22);
                }
                if (bookDetailsItem.getItemType() == BookDetailsItem.ItemType.AUTHOR) {
                    binding.itemEditableView.setStyle(18);
                }
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
        public void bind(BookDetailsItem bookDetailsItem) {
            if (binding != null) {
                binding.itemDateView.setIsEditable(bookDetailsItem.isEditable());
                binding.itemDateView.setDate(bookDetailsItem.getDate());
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

        public void bind(BookDetailsItem bookDetailsItem) {
            if (binding != null) {
                binding.itemOptionsView.setSelected(bookDetailsItem.getSelectedValue());
                binding.itemOptionsView.setOptions(bookDetailsItem.getSingleSelection());
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
        public void bind(BookDetailsItem bookDetailsItem) {
            if (binding != null) {
                binding.itemStarRatingView.setIsEditable(bookDetailsItem.isEditable());
                binding.itemStarRatingView.setRating(bookDetailsItem.getRating());
                binding.itemStarRatingView.setRatingType(bookDetailsItem.getTitle());
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
        public void bind(BookDetailsItem bookDetailsItem) {
            if (binding != null) {
                binding.setThumbnailUrl(bookDetailsItem.getThumbnailUrl());
                binding.executePendingBindings();
            }
        }
    }

    public interface OnBookDetailItemClickListener {
        void onItemClick(int position);

        void onRatingChanged(float rating, int position);

        void onTextChanged(String oldText, String newText, int position);

        void onDateChanged(String date, int position);

        void onOptionChanged(String selected, int position);
    }


}