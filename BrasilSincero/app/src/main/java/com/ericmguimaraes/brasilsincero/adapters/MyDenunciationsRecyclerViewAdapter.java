package com.ericmguimaraes.brasilsincero.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ericmguimaraes.brasilsincero.R;
import com.ericmguimaraes.brasilsincero.fragments.DenunciationsFragment.OnListFragmentInteractionListener;
import com.ericmguimaraes.brasilsincero.fragments.dummy.DummyContent.DummyItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDenunciationsRecyclerViewAdapter extends RecyclerView.Adapter<MyDenunciationsRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final boolean displayDetails;

    public MyDenunciationsRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener, boolean showDetails) {
        mValues = items;
        mListener = listener;
        displayDetails = showDetails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (displayDetails) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_denunciation_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_convenio_denunciation_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public boolean isFirstClick = true;
        public boolean like = false;

        @Bind(R.id.like)
        ImageView likeImageView;

        @Bind(R.id.dislike)
        ImageView dislikeImageView;

        @Bind(R.id.likesValue)
        TextView likeValue;

        @Bind(R.id.dislikesValue)
        TextView dislikeValue;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);

            likeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isFirstClick || !like) {
                        likeImageView.setImageResource(android.R.color.transparent);
                        likeImageView.setBackgroundResource(R.drawable.ic_like_ativado);
                        dislikeImageView.setBackgroundResource(R.drawable.ic_dislike_desativado);
                        isFirstClick = false;
                        like = true;
                        updateCounter();
                    }
                }
            });

            dislikeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isFirstClick || like) {
                        dislikeImageView.setBackgroundResource(R.drawable.ic_dislike_ativado);
                        likeImageView.setBackgroundResource(R.drawable.ic_like_desativado);
                        isFirstClick = false;
                        like = false;
                        updateCounter();
                    }
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        private void updateCounter(){
            if(like){
                likeValue.setText("1");
                dislikeValue.setText("0");
            } else {
                likeValue.setText("0");
                dislikeValue.setText("1");
            }
        }

    }
}
