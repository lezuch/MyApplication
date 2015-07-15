package pl.edu.wat.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;

    private RealmResults<User> itemsData;

    public MyAdapter( RealmResults<User> itemsData, Context context) {
        this.itemsData = itemsData;
        this.context = context;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_row, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Bitmap myBitmap = BitmapFactory.decodeFile(itemsData.get(position).getUrlAwatara());
        viewHolder.relativeLayout.setTag(itemsData.get(position));

        viewHolder.txtViewTitle.setText(itemsData.get(position).getName()
                        + " " + itemsData.get(position).getSurname()
                        + " (" + itemsData.get(position).getLogin()
                        + ")"
        );
        viewHolder.imgViewIcon.setImageBitmap(myBitmap);


    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout relativeLayout;
        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            relativeLayout = (RelativeLayout) itemLayoutView;
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}
