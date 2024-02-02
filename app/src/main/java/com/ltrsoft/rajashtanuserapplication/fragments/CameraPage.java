package com.ltrsoft.rajashtanuserapplication.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ltrsoft.rajashtanuserapplication.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.classes.Camera;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.CameraDao;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CameraPage extends Fragment {
    public CameraPage() {}
    private String encodeImage,statinId,useid;
    private ImageView image;
    private TextView camera,gallery;
    private Button submit;
    private EditText img_desc,imgage_adress;
    private RequestQueue requestQueue;
    private ArrayList liststation;
    ArrayAdapter adapter;

    StringBuilder output = new StringBuilder();


    private static final int REQUEST_IMAGE_GET = 102; // Request code for gallery image selection
    private static final int CAMERA_REQUEST = 101; // Updated request code for camera
    private static final String STATION_URL = "https://rj.ltr-soft.com/public/police_api/police_station/read_police_station.php";

    private Spinner station;
     private static final String IMAGE_UPLOAD_URL = "https://rj.ltr-soft.com/public/police_api/camera_uploading/insert_camera.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_page, container, false);
        camera = view.findViewById(R.id.camera);
        image = view.findViewById(R.id.show_img);
        img_desc = view.findViewById(R.id.img_desc);
        gallery = view.findViewById(R.id.camera_gallery);
        station = view.findViewById(R.id.station);
        imgage_adress = view.findViewById(R.id.img_adress);
        submit = view.findViewById(R.id.submit);


        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle("Upload Image ");
        }

        loadSattion();


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_GET);
            }
        });



        station.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statinId=String.valueOf(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                statinId = String.valueOf(0);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc =img_desc.getText().toString();
                String address =imgage_adress.getText().toString();
                if (encodeImage!=null){
                    sendData(desc,address,encodeImage,useid,statinId);
                }
                else {
                    Toast.makeText(getContext(), "select image first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void sendData(String desc, String address, String encodeImage, String useid, String statinId) {
        if (desc != null && address != null && encodeImage != null && useid != null ) {

            CameraDao cameraDao = new CameraDao(getContext());
            Camera camera = new Camera((new UserDataAccess().getUserId(getActivity()).toString()),encodeImage,statinId,desc,address);
            cameraDao.createCamera(camera, new UserCallBack() {
                @Override
                public void userSuccess(Object object) {
                    Toast.makeText(getContext(), "resposne = "+(String) object, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void userError(String error) {
                    Toast.makeText(getContext(), "error = "+error.toString(), Toast.LENGTH_SHORT).show();
                }
            });


            StringRequest request = new StringRequest(Request.Method.POST, IMAGE_UPLOAD_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    Toast.makeText(getContext(), "response = " + response.toString(), Toast.LENGTH_SHORT).show();
                    if (response.contains("success")){
//img_desc,imgage_adress;
                        image.setImageDrawable(null);
                        img_desc.setText("");
                        imgage_adress.setText("");

                        showPositiveDialogue();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "error " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("photo_path", encodeImage);
                    map.put("description", desc);
                    map.put("address", address);
                    map.put("user_id", output.toString());
                    map.put("station_id", "1");
                    return map;
                }
            };
//            requestQueue.add(request);
        }
        else {
            Log.d("values img",encodeImage);
            Log.d("values  desc",desc);
            Log.d("values adress",address);
            Log.d("values user",useid);
            Log.d("values stat","1");
            Toast.makeText(getContext(), "null values", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPositiveDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Success")
                .setMessage("Data Saved Succesfully")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the "OK" button click

                        dialog.dismiss();

                    }
                })
                .show();
    }

    private void encodeImg(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        encodeImage = Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST && data != null) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(bitmap);
                encodeImg(bitmap);
            } else if (requestCode == REQUEST_IMAGE_GET && data != null) {
                try {
                    Uri imageUri = data.getData();
                    InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    image.setImageBitmap(bitmap);
                    encodeImg(bitmap);
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    encodeImg(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void loadSattion() {
        StringRequest request = new StringRequest(Request.Method.POST, STATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getContext(), "response = "+response.toString(), Toast.LENGTH_SHORT).show();
                liststation = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String sta = jsonObject.getString("police_station_name");
                        liststation.add(sta);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "error json "+e.toString(), Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }
                adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,liststation);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                station.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}