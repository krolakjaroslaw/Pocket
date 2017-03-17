package com.example.hp.pocket;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.pocket.model.Link;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.LinksViewHolder>{
    private List<Link> mLinks;
    private ActionListener mActionListener;

    public LinksAdapter(List<Link> mLinks, ActionListener mActionListener) {
        this.mLinks = mLinks;
        this.mActionListener = mActionListener;
    }

    @Override
    public LinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.row_pocket_item, parent, false);
        return new LinksViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(LinksViewHolder holder, int position) {
        Link item = mLinks.get(position);

        holder.mCurrentLink = item;

        holder.mLinkTitle.setText(item.getName());
        holder.mLinkReference.setText(item.getReference());
        if (item.getType() == Link.TYPE_PHONE) {
            holder.mLinkImage.setImageResource(android.R.drawable.ic_menu_call);
        }
        else if (item.getType() == Link.TYPE_LINK) {
            holder.mLinkImage.setImageResource(android.R.drawable.ic_menu_share);
        }
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public class LinksViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.link_title)
        TextView mLinkTitle;
        @BindView(R.id.link_reference)
        TextView mLinkReference;
        @BindView(R.id.link_image)
        ImageView mLinkImage;

        Link mCurrentLink;

        public LinksViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void onRowClick() {
            if (mActionListener != null) {
                mActionListener.onRowClick(mCurrentLink);
            }
        }

        @OnLongClick
        boolean onRowLongClick() {
            if (mActionListener != null) {
                mActionListener.onRowLongClick(mCurrentLink);
            }
            return true;
        }

        @OnClick(R.id.link_image)
        void onSymbolClick(View clicked) {
            if (mActionListener != null) {
                mActionListener.onActionClick(clicked, mCurrentLink);
            }
        }
    }

    public interface ActionListener {
        void onRowLongClick(Link link);
        void onRowClick(Link link);
        void onActionClick(View anchor, Link link);
    }
}
