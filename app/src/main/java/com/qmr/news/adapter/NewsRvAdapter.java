package com.qmr.news.adapter;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qmr.news.R;
import com.qmr.news.model.entity.NewsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qmr on 2017/3/6.
 *
 * @author qmr777
 */

public class NewsRvAdapter extends RecyclerView.Adapter<NewsRvAdapter.NewsVH> {

    private static final String TAG = "NewsRvAdapter";

    private List<NewsEntity.ResultBean.ListBean> mContentList;

    private OnItemClickListener itemClickListener;

    private LayoutInflater inflater;

    public void setContentList(List<NewsEntity.ResultBean.ListBean> list){
        mContentList = list;
        notifyDataSetChanged();
    }

    public void insertContentList(List<NewsEntity.ResultBean.ListBean> list){
        int startPosition = mContentList.size();
        mContentList.addAll(list);
        int range = mContentList.size() - startPosition;
        notifyItemRangeInserted(startPosition,range);
    }

    @Override
    public NewsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if(inflater == null)
            inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_news,parent,false);
        Log.i(TAG, "onCreateViewHolder: ");
        return new NewsVH(v);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(final NewsVH holder, int position) {
        ViewCompat.setTransitionName(holder.iv_thumb,mContentList.get(position).getPic());
        holder.iv_thumb.setImageDrawable(null);
        if(itemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(holder,holder.getAdapterPosition());
                }
            });
        }

        holder.tv_title.setText(mContentList.get(position).getTitle());
        Glide.with(holder.iv_thumb.getContext())
                .load(mContentList.get(position).getPic())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.iv_thumb);

    }

    @Override
    public int getItemCount() {
        return mContentList == null?0:mContentList.size();
    }

    static class NewsVH extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_thumb)
        ImageView iv_thumb;
        @BindView(R.id.tv_title)
        TextView tv_title;

        public NewsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
