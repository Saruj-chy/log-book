package com.agamilabs.logbook.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agamilabs.logbook.Adapter.SettingsRecyclerAdapter;
import com.agamilabs.logbook.Constant.Config;
import com.agamilabs.logbook.Interfaces.SettingsInterface;
import com.agamilabs.logbook.MainActivity;
import com.agamilabs.logbook.Model.SettingsModels;
import com.agamilabs.logbook.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends Fragment implements SettingsInterface {

    private RecyclerView mFragmentRecyclerview;
    private ImageButton mAddBtn;
    private TextView mTextName, mTextDomain;
    private LinearLayout mLinearLayout01, mLinearLayout02, mLinearLayout03, mLinearLayout04, mLinearLayout05, mLinearLayout06;

    private List<SettingsModels> mSettingList = new ArrayList<>();
    private String mSettingName, mSettingDomain;

    private SharedPreferences mSharedPreferences1,  mSharedPreferences2;

    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mSharedPreferences1 = this.getActivity().getSharedPreferences("name", MODE_PRIVATE);
        mSharedPreferences2 = this.getActivity().getSharedPreferences("domain", MODE_PRIVATE);

        InitializeFields(view) ;
        loadProducts();


        return view ;
    }
    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.SETTING_JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray sectionArray = object.getJSONArray("section");

                            for(int i=0;i<sectionArray.length();i++){
                                JSONObject settingObject = sectionArray.getJSONObject(i);
                                SettingsModels aSettingModel = new SettingsModels() ;
                                Field[] fields =  aSettingModel.getAllFields() ;

                                for(int j=0; j<fields.length; j++ ){
                                    String fieldName = fields[j].getName() ;
                                    String fieldValueInJson =settingObject.has(fieldName)? settingObject.getString(fieldName) : "" ;
                                    fields[j].set(aSettingModel, fieldValueInJson) ;
                                }
                                mSettingList.add(aSettingModel);
                            }

                            SettingsRecyclerAdapter mAdapter = new SettingsRecyclerAdapter(getContext(), mSettingList, SettingsFragment.this);
                            mFragmentRecyclerview.setAdapter(mAdapter);
                            GridLayoutManager manager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                            mFragmentRecyclerview.setLayoutManager(manager);

                        } catch (JSONException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }



    private void InitializeFields(View view) {
        mFragmentRecyclerview = view.findViewById(R.id.firstFrament_RV) ;
        mAddBtn = view.findViewById(R.id.add_btn) ;
        mTextName = view.findViewById(R.id.textView_Name) ;
        mTextDomain = view.findViewById(R.id.textView_Domain) ;

        mLinearLayout01 = view.findViewById(R.id.linear_layout) ;
        mLinearLayout02 = view.findViewById(R.id.linearLayout02) ;
        mLinearLayout03 = view.findViewById(R.id.linearLayout03) ;
        mLinearLayout04 = view.findViewById(R.id.linearLayout04) ;
        mLinearLayout05 = view.findViewById(R.id.linearLayout05) ;
        mLinearLayout06 = view.findViewById(R.id.linearLayout06) ;
    }

    @Override
    public void onLogoName(String name, String domain) {
        mTextName.setText(name);
        mTextDomain.setText(domain);

        sharedSaved(mSharedPreferences1, "name", name) ;
        sharedSaved(mSharedPreferences2, "domain", domain) ;
    }

    public void Finish(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }, 100);
    }



    @Override
    public void onStart() {
        super.onStart();
        mSettingName = mSharedPreferences1.getString("name", "");
        mSettingDomain = mSharedPreferences2.getString("domain", "");
        onLogoName(mSettingName, mSettingDomain);

    }

    public void sharedSaved(SharedPreferences sharedPreferences, String state, String memberState){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(state, memberState);
        editor.apply();
    }
}