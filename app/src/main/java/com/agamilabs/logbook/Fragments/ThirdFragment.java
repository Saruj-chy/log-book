package com.agamilabs.logbook.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agamilabs.logbook.Adapter.ThirdFragmentRecyclerAdapter;
import com.agamilabs.logbook.Adapter.ThumbnilAdapter;
import com.agamilabs.logbook.Constant.Config;
import com.agamilabs.logbook.Interfaces.ICallBack;
import com.agamilabs.logbook.Model.Uploads;
import com.agamilabs.logbook.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThirdFragment extends Fragment implements ICallBack {

    private  View mView;
    private int mLength;
    private ThirdFragmentRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView1, mRecyclerView2;
    private EditText mEditText;
    private String mType = "", mStoreString = "";
    public static final int RESULT_OK = -1;
    private static final int REQUEST_CODE_FOR_IMAGE = 100;
    private static final int REQUEST_CODE_FOR_VEDIO = 200;
    private static final int REQUEST_CODE_FOR_PDF = 300;
    private static final int REQUEST_CODE_FOR_MS_WORD = 400;
    private String mMessageEdit;

    private ImageView mSendDoc, mSendInfo, mExpandedImageview;

    private RequestQueue mRequestQueue;
    private String mUrlImage, mUrlVedio, mUrlPdf, mUrlMSWord;

    private ArrayList<Uri> mImageList = new ArrayList<>();
    private ArrayList<Uri> mImageListForThumnil = new ArrayList<>();
    private ArrayList<Uploads> mArrayListUploads = new ArrayList<>();
    private ArrayList<String> mUserName = new ArrayList<>();
//    private ArrayList<String> client_name = new ArrayList<>();

    ThumbnilAdapter mThumbnilAdapter;

    public ThirdFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_third, container, false);

        mRequestQueue = Volley.newRequestQueue(getContext());

        mExpandedImageview = mView.findViewById(R.id.full_image);
        mEditText = mView.findViewById(R.id.edit_text);
        mSendDoc = mView.findViewById(R.id.send_document);
        mSendInfo = mView.findViewById(R.id.send_message_btn);

        mSendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMessageEdit = mEditText.getText().toString().trim();
                if (mType.equals("") && !mEditText.getText().toString().trim().isEmpty()) {
                    mType = "text";
                }
                final Map<String, String> message = new HashMap<>();
                message.put("userid", "1");
                if(mMessageEdit.isEmpty()){
                    if(mType.equals("image")){
                        message.put("attachment_url", mUrlImage);
                        message.put("msg", " ");
                    }
                    if(mType.equals("vedio")){
                        message.put("attachment_url", mUrlVedio);
                        message.put("msg", " ");
                    }
                    if(mType.equals("pdf")){
                        message.put("attachment_url", mUrlPdf);
                        message.put("msg", " ");

                    }
                    if(mType.equals("ms_word")){
                        message.put("attachment_url", mUrlMSWord);
                        message.put("msg", " ");

                    }
                }
                else {
                    if(mType.equals("image")){
                        mType = "image_text";
                        message.put("attachment_url", mUrlImage);
                        message.put("msg", mMessageEdit);
                    }
                    if(mType.equals("vedio")){
                        mType = "vedio_text";
                        message.put("attachment_url", mUrlVedio);
                        message.put("msg", mMessageEdit);
                    }
                    if(mType.equals("text")){
                        message.put("msg", mMessageEdit);
                    }
                }

                message.put("msgtype", mType);
                message.put("group_item_id", "25");


                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.POST_INFO_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("Java125","Response "+response);
                                mEditText.setText("");
                                mImageListForThumnil.clear();
                                loadMyRecyclerView2(mImageListForThumnil);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Java125","Error "+error.toString());

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return message;
                    }
                };
                mRequestQueue.add(stringRequest);
            }
        });

        mSendDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                CharSequence[] options = new CharSequence[]
                        {
                                "Image",
                                "Vedio",
                                "PDF File",
                                "MS Word File"
                        };

                builder.setTitle("Select One");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent, REQUEST_CODE_FOR_IMAGE);
                        }
                        else if (i == 1) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("vedio/*");
                            startActivityForResult(intent, REQUEST_CODE_FOR_VEDIO);
                        }
                        else if (i == 2) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("application/pdf");
                            startActivityForResult(intent, REQUEST_CODE_FOR_PDF);
                        }
                        else if (i == 3) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("application/msword");
                            startActivityForResult(intent, REQUEST_CODE_FOR_MS_WORD);
                        }
                    }
                });

                builder.show();
            }
        });


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = mEditText.getText().toString();

                HashMap<String, ArrayList<String>> map = new HashMap<>();
                map.put("@", mUserName);

                if(str.length()==0){
                    mLength = 1;
                }
                else{
                    mLength = str.length();
                }

                if (map.containsKey(str.substring(mLength -1))) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    final String[] names = new String[mUserName.size()];

                    for (int i = 0; i < mUserName.size(); i++) {
                        names[i] = mUserName.get(i);
                    }

                    builder.setItems(names, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final SpannableStringBuilder builder_spann = new SpannableStringBuilder();
                            String selected_item = "<" + names[which] + "> ";
                            String previous_str = mEditText.getText().toString();

                            mStoreString =previous_str + selected_item;

                            String[] separated = mStoreString.split("@<");

                            for (int i = 0; i < separated.length; i++) {
                                int index = separated[i].indexOf(">");
                                if(index>=0){
                                    SpannableString megentaSpannable = new SpannableString("@<"+separated[i]);
                                    megentaSpannable.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, index+3, 0);
                                    builder_spann.append(megentaSpannable);
                                }
                                else {
                                    SpannableString blackSpannable = new SpannableString(separated[i]);
                                    blackSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, separated[i].length(), 0);
                                    builder_spann.append(blackSpannable);
                                }
                            }
                            mEditText.setText(builder_spann);

                            int position = mEditText.length();
                            Editable etext = mEditText.getText();
                            Selection.setSelection(etext, position);


                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });


        loadMyRecyclerView();

        showData();


        return mView;
    }


    private void loadMyRecyclerView() {
        mRecyclerView1 = mView.findViewById(R.id.recycler_view_main);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ThirdFragmentRecyclerAdapter(getContext(), mArrayListUploads);
        mRecyclerView1.setAdapter(mAdapter);

    }


    private void showData() {


        Log.e("Java12",Config.MY_URL);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Config.MY_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("Java12","response"+response);
                            mArrayListUploads.clear();
                            mUserName.clear();
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject Json_data = jsonArray.getJSONObject(i);

                                mUserName.add(Json_data.get("name").toString());

                                Uploads model = new Uploads();
                                Field[] fields = model.getClass().getDeclaredFields();

                                for(int j=0;j<fields.length;j++){
                                    String fieldName = fields[j].getName();
                                    String fieldValueInJson = Json_data.getString(fieldName);
                                    try {
                                        fields[j].set(model,fieldValueInJson);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }

                                mArrayListUploads.add(model);
                            }
                            loadPost(mArrayListUploads);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Java12",error.toString());
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }


    private void loadPost(ArrayList<Uploads> arrayList_uploads) {
        mAdapter.notifyDataSetChanged();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_IMAGE) {
                mType = "image";
                final Uri file_uri = data.getData();
                mUrlImage = String.valueOf(file_uri);

                mImageListForThumnil.clear();
                mImageListForThumnil.add(file_uri);
                loadMyRecyclerView2(mImageListForThumnil);

            }
            else if (requestCode == REQUEST_CODE_FOR_VEDIO) {
                mType = "vedio";
                final Uri file_uri = data.getData();
                mUrlVedio = String.valueOf(file_uri);


            }
            else if (requestCode == REQUEST_CODE_FOR_PDF) {
                mType = "pdf";
                final Uri file_uri = data.getData();
                mUrlPdf = String.valueOf(file_uri);

            }
            else if (requestCode == REQUEST_CODE_FOR_MS_WORD) {
                mType = "ms_word";
                final Uri file_uri = data.getData();
                mUrlMSWord = String.valueOf(file_uri);

            }
        }

    }
    private void loadMyRecyclerView2(ArrayList<Uri> ImageList) {
        mRecyclerView2 = mView.findViewById(R.id.recycler_view_thumbnil);
        mRecyclerView2.setLayoutManager(new GridLayoutManager(getContext(),5));
        mThumbnilAdapter = new ThumbnilAdapter(getContext(),ImageList,this);
        mRecyclerView2.setAdapter(mThumbnilAdapter);

    }




//    @Override
//    public void onBackPressed() {
//        if(expanded_imageview.getVisibility()!=View.VISIBLE){
//            super.onBackPressed();
//        }
//        else{
//            expanded_imageview.setVisibility(View.GONE);
//            findViewById(R.id.feed_layout).setVisibility(View.VISIBLE);
//        }
//    }


    @Override
    public void onClickOne(int position) {
        mView.findViewById(R.id.feed_layout).setVisibility(View.GONE);
        Glide.with(getContext()).load(String.valueOf(mImageListForThumnil.get(position))).into(mExpandedImageview);
        mExpandedImageview.setVisibility(View.VISIBLE);
    }
}