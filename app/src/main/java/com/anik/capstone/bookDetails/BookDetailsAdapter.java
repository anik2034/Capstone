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
            return oldItem.getViewType().equals(newItem.getViewType()) && oldItem.isEditable() == newItem.isEditable();
        }
    };
    final OnBookDetailItemClickListener clickListener;

    public BookDetailsAdapter(OnBookDetailItemClickListener clickListener) {
        super(DIFF_CALLBACK);
        this.clickListener = clickListener;

    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType().ordinal();
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
                return new EditableTextViewHolder(editableTextViewBinding);
            case DATE:
                ListItemDateViewBinding dateViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_date_view, parent, false);
                return new DateViewHolder(dateViewBinding);
            case POP_UP:
                ListItemOptionsViewBinding spinnerViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_options_view, parent, false);
                return new OptionsViewHolder(spinnerViewBinding);
            case STAR_RATING:
                ListItemStarRatingViewBinding starRatingViewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_star_rating_view, parent, false);
                return new StarRatingViewHolder(starRatingViewBinding);
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
        switch (item.getViewType()) {
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
        }

    }

    public class HeaderViewHolder extends BaseViewHolder {
        private final ListItemHeaderViewBinding binding;

        public HeaderViewHolder(@NonNull ListItemHeaderViewBinding binding) {
            super(binding);
            this.binding = binding;
        }

        private void bind(BookDetailsItem bookDetailsItem) {
            if (binding != null) {
                binding.setHeaderName(bookDetailsItem.getTitle());
                binding.executePendingBindings();
            }
        }
    }

    public class EditableTextViewHolder extends BaseViewHolder {
        private final ListItemEditableTextViewBinding binding;

        public EditableTextViewHolder(@NonNull ListItemEditableTextViewBinding binding) {
            super(binding);
            this.binding = binding;
        }

        private void bind(BookDetailsItem bookDetailsItem) {
            if (binding != null) {
                binding.itemEditableView.setIsEditable(bookDetailsItem.isEditable());
                binding.itemEditableView.setHint(bookDetailsItem.getHint());
                binding.itemEditableView.setText(bookDetailsItem.getValue());
                binding.itemEditableView.setListener((oldText, newText) ->
                        clickListener.onTextChanged(newText, bookDetailsItem)
                );
                if (bookDetailsItem.getItemType() == BookDetailsItem.ItemType.TITLE) {
                    binding.itemEditableView.setEditTextViewBoldStyle();
                    binding.itemEditableView.setEditTextViewSize(22);
                } else if (bookDetailsItem.getItemType() == BookDetailsItem.ItemType.AUTHOR) {
                    binding.itemEditableView.setEditTextViewBoldStyle();
                    binding.itemEditableView.setEditTextViewSize(18);
                } else {
                    binding.itemEditableView.setEditTextViewSize(14);
                    binding.itemEditableView.setEditTextViewNormalStyle();
                }
                binding.executePendingBindings();
            }
        }
    }

    public class DateViewHolder extends BaseViewHolder {
        private final ListItemDateViewBinding binding;

        public DateViewHolder(@NonNull ListItemDateViewBinding binding) {
            super(binding);
            this.binding = binding;
        }

        private void bind(BookDetailsItem bookDetailsItem) {
            if (binding != null) {
                binding.itemDateView.setIsEditable(bookDetailsItem.isEditable());
                binding.itemDateView.setDate(bookDetailsItem.getDate());
                binding.itemDateView.setIsEditable(bookDetailsItem.isEditable());
                binding.itemDateView.setListener(date -> clickListener.onDateChanged(date, bookDetailsItem));
                binding.executePendingBindings();
            }
        }
    }

    public class OptionsViewHolder extends BaseViewHolder {
        private final ListItemOptionsViewBinding binding;

        public OptionsViewHolder(@NonNull ListItemOptionsViewBinding binding) {
            super(binding);
            this.binding = binding;
        }

        private void bind(BookDetailsItem bookDetailsItem) {
            if (binding != null) {
                binding.itemOptionsView.setSelected(bookDetailsItem.getSelectedValue());
                binding.itemOptionsView.setOptions(bookDetailsItem.getSingleSelection());
                binding.itemOptionsView.setIsEditable(bookDetailsItem.isEditable());
                binding.itemOptionsView.setListener(selected -> clickListener.onOptionChanged(selected, bookDetailsItem));
                binding.executePendingBindings();
            }
        }
    }

    public class StarRatingViewHolder extends BaseViewHolder {
        private final ListItemStarRatingViewBinding binding;

        public StarRatingViewHolder(@NonNull ListItemStarRatingViewBinding binding) {
            super(binding);
            this.binding = binding;
        }

        private void bind(BookDetailsItem bookDetailsItem) {
            if (binding != null) {
                binding.itemStarRatingView.setIsEditable(bookDetailsItem.isEditable());
                binding.itemStarRatingView.setRating(bookDetailsItem.getRating());
                binding.itemStarRatingView.setRatingType(bookDetailsItem.getTitle());
                binding.itemStarRatingView.setListener(rating -> clickListener.onRatingChanged(rating, bookDetailsItem));
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

        private void bind(BookDetailsItem bookDetailsItem) {
            if (binding != null) {
                binding.setThumbnailUrl(bookDetailsItem.getThumbnailUrl());
                binding.executePendingBindings();
            }
        }
    }

    public interface OnBookDetailItemClickListener {
        void onRatingChanged(float rating, BookDetailsItem bookDetailsItem);

        void onTextChanged(String newText, BookDetailsItem bookDetailsItem);

        void onDateChanged(String date, BookDetailsItem bookDetailsItem);

        void onOptionChanged(String selected, BookDetailsItem bookDetailsItem);
    }


}