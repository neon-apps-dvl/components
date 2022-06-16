package neon.pixel.components.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListView extends RecyclerView {
    List <Item> mItems;

    neon.pixel.components.listview.Adapter adapter;
    neon.pixel.components.listview.LayoutManager layoutManager;

    public ListView (@NonNull Context context, @Nullable AttributeSet attrs) {
        super (context, attrs);

        setBackgroundColor (getResources ().getColor (android.R.color.transparent, context.getTheme ()));

        mItems = new ArrayList <> ();

        adapter = new neon.pixel.components.listview.Adapter (mItems);
        layoutManager = new neon.pixel.components.listview.LayoutManager (context);

        adapter.notifyDataSetChanged ();
        layoutManager.setScrollable (true);

        adapter.notifyDataSetChanged ();

        setAdapter (adapter);
        setLayoutManager (layoutManager);
    }

    public void addItem (int position, View item) {
        Item listItem = new Item (item);

        mItems.add (position, listItem);
        adapter.notifyItemInserted (position);
    }

    public void addItem (View item) {
        addItem (mItems.size (), item);
    }

    public void addItems (int position, List <View> items) {
        for (View item : items) {
            Log.e ("index", "index: " + items.indexOf (item));

            Item listItem = new Item (item);
            mItems.add (position + items.indexOf (item), listItem);
        }
        adapter.notifyItemRangeInserted (position, items.size ());
    }

    public void addItems (List <View> items) {
        addItems (mItems.size (), items);
    }

    public void moveItem (int from, int to) {
        Item fromItem = mItems.get (from);
        Item toItem = mItems.get (to);

        mItems.set (from, toItem);
        mItems.set (to, fromItem);

        adapter.notifyItemMoved (from, to);
    }

    public void removeItem (int position) {
        mItems.remove (position);
        adapter.notifyItemRangeRemoved (position, 1);
    }

    public void removeItems (int position, int count) {
        for (int i = position; i < position + count; i = i + 1) {
            mItems.remove (position);
        }

        adapter.notifyItemRangeRemoved (position, count);
    }

    public void clearItems () {
        int c = mItems.size ();
        mItems.clear ();

        adapter.notifyItemRangeRemoved (0, c);
    }
}