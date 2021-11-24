package com.example.order.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order.R;
import com.example.order.entity.Item;

import java.util.List;

/**
 * 商品列表Fragment适配器
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private final List<Item> itemList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView itemNameTextView;
        private final TextView itemDescTextView;
        private final CustomAdapter customAdapter;

        public ViewHolder(View view, CustomAdapter customAdapter) {
            super(view);
            // Define click listener for the ViewHolder's View
            itemNameTextView = view.findViewById(R.id.item_name_text_view);
            itemDescTextView = view.findViewById(R.id.item_desc_text_view);
            this.customAdapter = customAdapter;
            view.setOnClickListener(this);
        }

        public TextView getItemNameTextView() {
            return itemNameTextView;
        }

        public TextView getItemDescTextView() {
            return itemDescTextView;
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            Item item = customAdapter.getItemList().get(mPosition);

            Toast.makeText(view.getContext(), "new page", Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(view.getContext(), DetailActivity.class);
//            intent.putExtra(DETAIL_KEY, element);
//            view.getContext().startActivity(intent);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public CustomAdapter(List<Item> dataSet) {
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
        viewHolder.getItemNameTextView().setText(itemList.get(position).getName());
        viewHolder.getItemDescTextView().setText(itemList.get(position).getDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<Item> getItemList() {
        return itemList;
    }
}
