package com.agamilabs.logbook.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agamilabs.logbook.Model.Uploads;
import com.agamilabs.logbook.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThirdFragmentRecyclerAdapter extends RecyclerView.Adapter<ThirdFragmentRecyclerAdapter.ViewHolder> {
    Context mContext;
    private ArrayList<Uploads> mList = new ArrayList<>();
    final static int MyAdapterViewType_text = 1;
    final static int MyAdapterViewType_image = 2;
    final static int MyAdapterViewType_image_text = 3;
    final static int MyAdapterViewType_vedio = 4;
    final static int MyAdapterViewType_vedio_text = 5;

    public ThirdFragmentRecyclerAdapter(Context mContext, ArrayList<Uploads> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }



    @Override
    public int getItemViewType(int position) {
        Uploads mUploads = this.mList.get(position);
        switch (mUploads.getType()){
            case "text":
                return MyAdapterViewType_text;
            case "image":
                return MyAdapterViewType_image;
            case "image_text":
                return MyAdapterViewType_image_text;
            case "vedio":
                return MyAdapterViewType_vedio;
            case "vedio_text":
                return MyAdapterViewType_vedio_text;
            default:
                return super.getItemViewType(position);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View view;
        switch (viewtype){
            case MyAdapterViewType_text:
                view = mLayoutInflater.inflate(R.layout.layout_text,viewGroup,false);
                break;
            case MyAdapterViewType_image:
                view = mLayoutInflater.inflate(R.layout.layout_image,viewGroup,false);
                break;
            case MyAdapterViewType_image_text:
                view = mLayoutInflater.inflate(R.layout.layout_imagetext,viewGroup,false);
                break;
            case MyAdapterViewType_vedio:
                view = mLayoutInflater.inflate(R.layout.layout_vedio,viewGroup,false);
                break;
            case MyAdapterViewType_vedio_text:
                view = mLayoutInflater.inflate(R.layout.layout_vediotext,viewGroup,false);
                break;
            default:
                view = mLayoutInflater.inflate(R.layout.layout_defult,viewGroup,false);
                break;
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Uploads mUploads = mList.get(position);
        switch (getItemViewType(position)){
            case MyAdapterViewType_text:
                ((ViewHolder)viewHolder).bind(mUploads);
                break;
            case MyAdapterViewType_image:
                ((ViewHolder)viewHolder).bind2(mUploads);
                break;
            case MyAdapterViewType_image_text:
                ((ViewHolder)viewHolder).bind3(mUploads);
                break;
            case MyAdapterViewType_vedio:
                ((ViewHolder)viewHolder).bind4(mUploads);
                break;
            case MyAdapterViewType_vedio_text:
                ((ViewHolder)viewHolder).bind5(mUploads);
                break;
            default:
                ((ViewHolder)viewHolder).bind6(mUploads);
                break;
        }

    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_name, text_time, text_message;
        private ImageView image_profile, image_post;
        private VideoView mVedioView;
        private Button btn_play, btn_push;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_name = itemView.findViewById(R.id.name);
            text_time = itemView.findViewById(R.id.time);
            text_message = itemView.findViewById(R.id.message);
            image_post = itemView.findViewById(R.id.image_view);
            image_profile = itemView.findViewById(R.id.image);
            mVedioView = itemView.findViewById(R.id.vedio);
            btn_play = itemView.findViewById(R.id.play);
            btn_push = itemView.findViewById(R.id.push);
        }

        public void bind(Uploads uploads) {
            text_name.setText(uploads.getName());
            text_time.setText(uploads.getTime());
            text_message.setText(uploads.getMessage());

            Picasso.get().load(uploads.getProfile_image()).into(image_profile);
        }

        public void bind2(Uploads uploads) {
            text_name.setText(uploads.getName());
            text_time.setText(uploads.getTime());
            Picasso.get().load(uploads.getProfile_image()).into(image_profile);
            Picasso.get().load(uploads.getAttachment_url()).into(image_post);
        }

        public void bind3(Uploads uploads) {
            text_name.setText(uploads.getName());
            text_time.setText(uploads.getTime());
            text_message.setText(uploads.getMessage());
            Picasso.get().load(uploads.getProfile_image()).into(image_profile);
            Picasso.get().load(uploads.getAttachment_url()).into(image_post);
        }
        public void bind4(Uploads uploads) {
            text_name.setText(uploads.getName());
            text_time.setText(uploads.getTime());

            Picasso.get().load(uploads.getProfile_image()).into(image_profile);

            mVedioView.setVideoURI(Uri.parse(uploads.getAttachment_url()));
            mVedioView.requestFocus();

            btn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVedioView.start();
                }
            });

            btn_push.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVedioView.pause();
                }
            });


        }
        public void bind5(Uploads uploads) {
            text_name.setText(uploads.getName());
            text_time.setText(uploads.getTime());
            text_message.setText(uploads.getMessage());

            Picasso.get().load(uploads.getProfile_image()).into(image_profile);

            mVedioView.setVideoURI(Uri.parse(uploads.getAttachment_url()));
            mVedioView.requestFocus();

            btn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVedioView.start();
                }
            });

            btn_push.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVedioView.pause();
                }
            });
        }

        public void bind6(Uploads uploads) {
            text_name.setText(uploads.getName());
            text_time.setText(uploads.getTime());

            //message_tv.setText(uploads.getMessage());
            Picasso.get().load(uploads.getProfile_image()).into(image_profile);
        }
    }
}
