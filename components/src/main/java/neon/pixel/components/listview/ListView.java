package neon.pixel.components.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListView extends RecyclerView {
    List <Item> items;

    com.pixel.components.listview.Adapter adapter;
    com.pixel.components.listview.LayoutManager layoutManager;

    public ListView (@NonNull Context context, @Nullable AttributeSet attrs) {
        super (context, attrs);

        setBackgroundColor (getResources ().getColor (android.R.color.transparent, context.getTheme ()));

        items = new ArrayList <> ();

        adapter = new com.pixel.components.listview.Adapter (items);
        layoutManager = new com.pixel.components.listview.LayoutManager (context);

        adapter.notifyDataSetChanged ();
        layoutManager.setScrollable (true);

        adapter.notifyDataSetChanged ();

        setAdapter (adapter);
        setLayoutManager (layoutManager);
    }

    public void addItem (int position, View item) {
        Item listItem = new Item (item);

        items.add (position, listItem);
        adapter.notifyItemInserted (position);
    }

    public void addItem (View item) {
        addItem (items.size (), item);
    }

    public void addItems (int position, List <View> items) {
        for (View item : items) {
            Item listItem = new Item (item);
            this.items.add (position + items.indexOf (item), listItem);
        }
        adapter.notifyItemRangeInserted (position, items.size ());
    }

    public void addItems (List <View> items) {
        addItems (items.size (), items);
    }

    public void moveItem (int from, int to) {
        Item fromItem = items.get (from);
        Item toItem = items.get (to);

        items.set (from, toItem);
        items.set (to, fromItem);

        adapter.notifyItemMoved (from, to);
    }

    public void removeItem (int position) {
        items.remove (position);
        adapter.notifyItemRangeRemoved (position, 1);
    }

    public void removeItems (int position, int count) {
        for (int i = position; i < position + count; i = i + 1) {
            items.remove (position);
        }

        adapter.notifyItemRangeRemoved (position, count);
    }

    public void clearItems () {
        int c = items.size ();
        items.clear ();

        adapter.notifyItemRangeRemoved (0, c);
    }
}