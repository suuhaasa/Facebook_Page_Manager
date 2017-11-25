package com.suhas.pagemanager;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Suhas on 11/23/2017.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {

    // TODO make it a post dataset rather than a string.
    //private String[] names;
    //private String[] descriptions;
    private List<Post> mPostsToDisplay;
    //Listner to call when cardView is clicked
    private CardClickListener listener;
    public static interface CardClickListener{
        public void onClick(int position);
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // TODO make it a post structure
        // each data item is just a string in this case
        private CardView mCardView;
        public ViewHolder(CardView v) {
            super(v);
            mCardView = v;
        }
    }

    // TODO constructor for the page data set
    public PostRecyclerAdapter(List<Post> postsToDisplay) {
        this.mPostsToDisplay = postsToDisplay;

    }

    public void setListener(CardClickListener listener){
        this.listener = listener;
    }

    //TODO implement viewtype
    // Create new views (invoked by the layout manager)
    @Override
    public PostRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        CardView cardView = holder.mCardView;

        //set the page name
        TextView pageName = (TextView) cardView.findViewById(R.id.name_page);
        pageName.setText(mPostsToDisplay.get(position).getName());

        // set the page description
        TextView pageDescription = (TextView) cardView.findViewById(R.id.description_page);
        pageDescription.setText(mPostsToDisplay.get(position).getDescription());

        //set the listener
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(listener != null){
                    listener.onClick(position);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPostsToDisplay.size();
    }



}
