package com.agamilabs.logbook.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.agamilabs.logbook.Fragments.SettingsFragment;
import com.agamilabs.logbook.Model.SettingsModels;
import com.agamilabs.logbook.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SettingsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mCtx;
    private List<SettingsModels> mSettingList;
    private String mLogName, mLogDomain;

    private SettingsFragment mFragment;
    private SharedPreferences mSharedPreferences;
    private String mPositionId;

    public SettingsRecyclerAdapter(Context mCtx, List<SettingsModels> settingList, SettingsFragment settingInterface) {
        this.mCtx = mCtx;
        this.mSettingList = settingList;
        this.mFragment = settingInterface;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_settings, null);
        mSharedPreferences = mCtx.getSharedPreferences("positionId", MODE_PRIVATE);

        return new SettingsRecyclerAdapter.PostViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final SettingsModels settings = mSettingList.get(position);
        ((PostViewHolder) holder).bind(settings, position);
    }

    @Override
    public int getItemCount() {
        return mSettingList.size();
    }


    class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewLogoName, mTextViewLogoText;
        private LinearLayout mLinearLayout;


        public PostViewHolder(View itemView) {
            super(itemView);

            mTextViewLogoName = itemView.findViewById(R.id.text_identifier);
            mTextViewLogoText = itemView.findViewById(R.id.text_name);
            mLinearLayout = itemView.findViewById(R.id.linear_layout);


        }

        public void bind(final SettingsModels settings, final int position){


            mTextViewLogoName.setText(settings.getSection_identifier());
            mTextViewLogoText.setText(settings.getSection_title());

//========    give position
            mPositionId = mSharedPreferences.getString("positionId", "");
            if(!mPositionId.equals("")){
                if(Integer.valueOf(mPositionId) == position){
                    mLinearLayout.setBackgroundResource(R.drawable.input);
                }
            }
            else{
                mTextViewLogoName.setBackgroundResource(R.drawable.background);
            }
            mTextViewLogoName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    mLogName = settings.getSection_title();
//                    logDomain = settings.getSection_identifier() ;
                    mLogDomain = "agamilabs.slack.com" ;

                    mFragment.sharedSaved(mSharedPreferences,"positionId", String.valueOf(position));
                    mFragment.Finish();
                    mFragment.onLogoName(mLogName, mLogDomain);
                }
            });

//            notifyDataSetChanged();

        }



    }

}