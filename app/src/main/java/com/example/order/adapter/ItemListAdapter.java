package com.example.order.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order.ItemDetailActivity;
import com.example.order.R;
import com.example.order.constant.Constant;
import com.example.order.entity.Item;
import com.example.order.util.ImgUtil;

import java.util.List;

/**
 * 商品列表Fragment适配器
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private List<Item> itemList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView itemNameTextView;
        private final TextView itemDescTextView;
        private final ImageView itemImgView;
        private final ItemListAdapter itemListAdapter;

        public ViewHolder(View view, ItemListAdapter itemListAdapter) {
            super(view);
            // Define click listener for the ViewHolder's View
            itemNameTextView = view.findViewById(R.id.item_name_text_view);
            itemDescTextView = view.findViewById(R.id.item_desc_text_view);
            itemImgView = view.findViewById(R.id.item_img);
            this.itemListAdapter = itemListAdapter;
            view.setOnClickListener(this);
        }

        public TextView getItemNameTextView() {
            return itemNameTextView;
        }

        public TextView getItemDescTextView() {
            return itemDescTextView;
        }

        public ImageView getItemImgView() {
            return itemImgView;
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            Item item = itemListAdapter.getItemList().get(mPosition);
            Intent intent = new Intent(view.getContext(), ItemDetailActivity.class)
                    .putExtra(Constant.ITEM_ID_KEY, item.getId());
            view.getContext().startActivity(intent);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public ItemListAdapter(List<Item> dataSet) {
        itemList = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row, viewGroup, false);
        return new ViewHolder(view, this);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Item item = itemList.get(position);
        viewHolder.getItemNameTextView().setText(item.getName());
        viewHolder.getItemDescTextView().setText(item.getDescription());
        if (item.getPic() != null && !item.getPic().isEmpty()) {
            viewHolder.getItemImgView().setImageBitmap(ImgUtil.getBitMap(item.getPic()));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> dataset) {
        this.itemList = dataset;
    }
}
