package com.agamilabs.logbook.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agamilabs.logbook.Interfaces.ICallBack;
import com.agamilabs.logbook.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThumbnilAdapter extends RecyclerView.Adapter<ThumbnilAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Uri> mImagelist = new ArrayList<>();
    private ICallBack mCallBack;

    public ThumbnilAdapter(Context context, ArrayList<Uri> imagelist, ICallBack callBack) {
        this.mContext = context;
        this.mImagelist = imagelist;
        this.mCallBack =callBack;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_thumbnil_images,parent,false);
        return new ThumbnilAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Picasso.get().load(String.valueOf(mImagelist.get(position))).into(holder.mThumbnilImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.onClickOne(position);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mImagelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mThumbnilImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mThumbnilImage = itemView.findViewById(R.id.thumbnil_images);


        }
    }
}