package com.agamilabs.logbook.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.agamilabs.logbook.Adapter.ThirdFragmentRecyclerAdapter;
import com.agamilabs.logbook.Adapter.ThumbnilAdapter;
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

public class FragmentActivity extends AppCompatActivity implements ICallBack {
    int length;
    ThirdFragmentRecyclerAdapter adapter;
    private RecyclerView recyclerView,recyclerView2;
    private EditText editText;
    private String type = "",store_string = "";
    private static final int REQUEST_CODE_FOR_IMAGE = 100;
    private static final int REQUEST_CODE_FOR_VEDIO = 200;
    private static final int REQUEST_CODE_FOR_PDF = 300;
    private static final int REQUEST_CODE_FOR_MS_WORD = 400;
    private String message_edit;

    private ImageView send_doc, send_info,expanded_imageview;

    private RequestQueue requestQueue;
    String url_image, url_vedio, url_pdf, url_ms_word;

    ArrayList<Uri> ImageList = new ArrayList<>();
    ArrayList<Uri> ImageList_for_thumnil = new ArrayList<>();
    private ArrayList<Uploads> arrayList_uploads = new ArrayList<>();
    private ArrayList<String> user_name = new ArrayList<>();
    private ArrayList<String> client_name = new ArrayList<>();

    ThumbnilAdapter thumbnilAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        requestQueue = Volley.newRequestQueue(FragmentActivity.this);

        expanded_imageview = findViewById(R.id.full_image);

        editText = findViewById(R.id.edit_text);
        send_doc = findViewById(R.id.send_document);
        send_info = findViewById(R.id.send_message_btn);

        send_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message_edit = editText.getText().toString().trim();
                if (type.equals("") && !editText.getText().toString().trim().isEmpty()) {
                    type = "text";
                }
                final Map<String, String> message = new HashMap<>();
                message.put("userid", "1");
                if(message_edit.isEmpty()){
                    if(type.equals("image")){
                        message.put("attachment_url", url_image);
                        message.put("msg", " ");
                    }
                    if(type.equals("vedio")){
                        message.put("attachment_url", url_vedio);
                        message.put("msg", " ");
                    }
                    if(type.equals("pdf")){
                        message.put("attachment_url", url_pdf);
                        message.put("msg", " ");

                    }
                    if(type.equals("ms_word")){
                        message.put("attachment_url", url_ms_word);
                        message.put("msg", " ");

                    }
                }
                else {
                    if(type.equals("image")){
                        type = "image_text";
                        message.put("attachment_url", url_image);
                        message.put("msg", message_edit);
                    }
                    if(type.equals("vedio")){
                        type = "vedio_text";
                        message.put("attachment_url", url_vedio);
                        message.put("msg", message_edit);
                    }
                    if(type.equals("text")){
                        message.put("msg", message_edit);
                    }
                }

                message.put("msgtype", type);
                message.put("group_item_id", "25");
                String post_info_url = "http://192.168.0.104/agamiLogbook/insert_msg_log.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, post_info_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("Java125","Response "+response);
                                editText.setText("");
                                ImageList_for_thumnil.clear();
                                loadMyRecyclerView2(ImageList_for_thumnil);
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
                requestQueue.add(stringRequest);
            }
        });

        send_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FragmentActivity.this);

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


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editText.getText().toString();

                HashMap<String, ArrayList<String>> map = new HashMap<>();
                map.put("@", user_name);

                if(str.length()==0){
                    length = 1;
                }
                else{
                    length = str.length();
                }

                if (map.containsKey(str.substring(length-1))) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(FragmentActivity.this);

                    final String[] names = new String[user_name.size()];

                    for (int i = 0; i < user_name.size(); i++) {
                        names[i] = user_name.get(i);
                    }

                    builder.setItems(names, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final SpannableStringBuilder builder_spann = new SpannableStringBuilder();
                            String selected_item = "<" + names[which] + "> ";
                            String previous_str = editText.getText().toString();

                            store_string =previous_str + selected_item;

                            String[] separated = store_string.split("@<");

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
                            editText.setText(builder_spann);

                            int position = editText.length();
                            Editable etext = editText.getText();
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
    }

    private void loadMyRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ThirdFragmentRecyclerAdapter(this, arrayList_uploads);
        recyclerView.setAdapter(adapter);

    }


    private void showData() {

        String myUrl = "http://192.168.0.104/agamiLogbook/view_msg_log.php";
        Log.e("Java12",myUrl);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, myUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("Java12","response");
                            arrayList_uploads.clear();
                            user_name.clear();
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject Json_data = jsonArray.getJSONObject(i);

                                user_name.add(Json_data.get("name").toString());

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

                                arrayList_uploads.add(model);
                            }
                            loadPost(arrayList_uploads);

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
        requestQueue.add(request);
    }


    private void loadPost(ArrayList<Uploads> arrayList_uploads) {
        adapter.notifyDataSetChanged();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_IMAGE) {
                type = "image";
                final Uri file_uri = data.getData();
                url_image = String.valueOf(file_uri);

                ImageList_for_thumnil.clear();
                ImageList_for_thumnil.add(file_uri);
                loadMyRecyclerView2(ImageList_for_thumnil);

            }
            else if (requestCode == REQUEST_CODE_FOR_VEDIO) {
                type = "vedio";
                final Uri file_uri = data.getData();
                url_vedio = String.valueOf(file_uri);


            }
            else if (requestCode == REQUEST_CODE_FOR_PDF) {
                type = "pdf";
                final Uri file_uri = data.getData();
                url_pdf = String.valueOf(file_uri);

            }
            else if (requestCode == REQUEST_CODE_FOR_MS_WORD) {
                type = "ms_word";
                final Uri file_uri = data.getData();
                url_ms_word = String.valueOf(file_uri);

            }
        }

    }
    private void loadMyRecyclerView2(ArrayList<Uri> ImageList) {
        recyclerView2 =findViewById(R.id.recycler_view_thumbnil);
        recyclerView2.setLayoutManager(new GridLayoutManager(FragmentActivity.this,5));
        thumbnilAdapter = new ThumbnilAdapter(FragmentActivity.this,ImageList,this);
        recyclerView2.setAdapter(thumbnilAdapter);

    }


    @Override
    public void onBackPressed() {
        if(expanded_imageview.getVisibility()!=View.VISIBLE){
            super.onBackPressed();
        }
        else{
            expanded_imageview.setVisibility(View.GONE);
            findViewById(R.id.feed_layout).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickOne(int position) {
        findViewById(R.id.feed_layout).setVisibility(View.GONE);
        Glide.with(FragmentActivity.this).load(String.valueOf(ImageList_for_thumnil.get(position))).into(expanded_imageview);
        expanded_imageview.setVisibility(View.VISIBLE);
    }
}