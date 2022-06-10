

package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;

    //pass context and list of tweets

    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }

    public void Add(List<Tweet> list){
        tweets.addAll(list);
        notifyDataSetChanged();
    }
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //inflate layout for each tweet/row
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }


    //Get data and bind view to it
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    //Defining a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage;
        TextView  tvBody;
        TextView  tvScreeName;
        ImageView ivMediaImage;
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            tvBody       = itemView.findViewById(R.id.tvBody);
            tvScreeName       = itemView.findViewById(R.id.tvScreeName);
            ivMediaImage = itemView.findViewById(R.id.ivMediaImage);
            tvTime = itemView.findViewById(R.id.tvTime);
        }


        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreeName.setText(tweet.user.screeName);
            tvTime.setText(tweet.timeStamp);
            Glide.with(context).load(tweet.user.profileImageUrl).into(profileImage);

            if(!tweet.imageUrl.equals("")){
                ivMediaImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(tweet.imageUrl).into(ivMediaImage);
            }
            else{
                ivMediaImage.setVisibility(View.GONE);
            }


        }
    }
}