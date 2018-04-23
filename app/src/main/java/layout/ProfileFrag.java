package layout;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softmedialtda.softmediaphotoapp.MainActivity;
import com.softmedialtda.softmediaphotoapp.R;
import com.softmedialtda.softmediaphotoapp.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.jar.Manifest;

import static com.softmedialtda.softmediaphotoapp.util.Connection.*;
import static com.softmedialtda.softmediaphotoapp.util.Constants.DOMAIN;


public class ProfileFrag extends Fragment {
    User user = new User();
    String urlProfile = DOMAIN+"profile";
    TextView institution;
    TextView name;
    TextView documentNumber;
    TextView appointment;
    TextView birthdate;
    TextView headquarters;
    TextView dayTrip;
    TextView grade;
    TextView group;
    TextView relation;
    TextView textView25; //label Parentesco
    TextView textView10; //label Numero de documento
    TextView textView22; //label Sede
    TextView textView5; //label Cargo
    TextView textView7; //label fecha de nacimiento
    TextView textView11; //label jornada
    TextView textView15; //label grado
    TextView textView16; //label grupo
    ImageView profileImg;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    String urlUploadImage = DOMAIN+"img";
    ProgressDialog progressDialog;
    private final int CAMERA_RESULT = 101;


    public ProfileFrag() {
    }

    public static ProfileFrag newInstance(int section){
        ProfileFrag fragment = new ProfileFrag();
        Bundle args = new Bundle();
        args.putInt("num", section);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        institution = (TextView) rootView.findViewById(R.id.institution);
        name = (TextView) rootView.findViewById(R.id.name);
        documentNumber = (TextView) rootView.findViewById(R.id.documentNumber);
        appointment = (TextView) rootView.findViewById(R.id.appointment);
        birthdate = (TextView) rootView.findViewById(R.id.birthdate);
        headquarters = (TextView) rootView.findViewById(R.id.headquarters);

        dayTrip = (TextView) rootView.findViewById(R.id.dayTrip);
        grade = (TextView) rootView.findViewById(R.id.grade);
        group = (TextView) rootView.findViewById(R.id.group);
        relation = (TextView) rootView.findViewById(R.id.relation);
        profileImg = (ImageView) rootView.findViewById(R.id.profileImg);

        textView25 = (TextView) rootView.findViewById(R.id.textView25);
        textView10 = (TextView) rootView.findViewById(R.id.textView10);
        textView22 = (TextView) rootView.findViewById(R.id.textView22);
        textView5 = (TextView) rootView.findViewById(R.id.textView5);
        textView7 = (TextView) rootView.findViewById(R.id.textView7);
        textView11 = (TextView) rootView.findViewById(R.id.textView11);
        textView15 = (TextView) rootView.findViewById(R.id.textView15);
        textView16 = (TextView) rootView.findViewById(R.id.textView16);

        FloatingActionButton captureImageFromCamera = (FloatingActionButton) rootView.findViewById(R.id.camButton);

        captureImageFromCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }else{
                    if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
                        Toast.makeText(getActivity().getApplicationContext(), R.string.permissionNeeded, Toast.LENGTH_LONG).show();
                    }
                    requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_RESULT);
                }

            }
        });

        user = (User) getActivity().getIntent().getSerializableExtra("user");
        showTextView(user, captureImageFromCamera);
        if (!user.getImageProfile().equals("")) {
            byte[] decodedString = Base64.decode(user.getImageProfile(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileImg.setImageBitmap(decodedByte);
        }

        if (user.getTypeUser().equals("SUPERADMIN")){
            captureImageFromCamera.hide();
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (user.getTypeUser().equals("SUPERADMIN")){
            name.setText(R.string.administrador);

        }else{
            new HttpAsyncTaskProfile().execute(urlProfile);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                Bitmap bitmapImg = (Bitmap) data.getExtras().get("data");
                profileImg.setImageBitmap(bitmapImg);
                MainActivity main   = (MainActivity) getActivity();
                main.img.setImageBitmap(bitmapImg);
                ImageUploadToServerFunction(bitmapImg);
            }
        }
    }

    public void showTextView(User user, FloatingActionButton floatingActionButton){
        switch (user.getTypeUser()){
            case "Functionary" :
                textView10.setVisibility(TextView.VISIBLE);
                textView5.setVisibility(TextView.VISIBLE);
                documentNumber.setVisibility(TextView.VISIBLE);
                appointment.setVisibility(TextView.VISIBLE);
                break;
            case "Student" :
                textView10.setVisibility(TextView.VISIBLE);
                textView7.setVisibility(TextView.VISIBLE);
                textView22.setVisibility(TextView.VISIBLE);
                textView11.setVisibility(TextView.VISIBLE);
                textView15.setVisibility(TextView.VISIBLE);
                textView16.setVisibility(TextView.VISIBLE);
                documentNumber.setVisibility(TextView.VISIBLE);
                birthdate.setVisibility(TextView.VISIBLE);
                headquarters.setVisibility(TextView.VISIBLE);
                dayTrip.setVisibility(TextView.VISIBLE);
                grade.setVisibility(TextView.VISIBLE);
                group.setVisibility(TextView.VISIBLE);
                floatingActionButton.hide();
                break;
            case "Attendant" :
                textView25.setVisibility(TextView.VISIBLE);
                textView10.setVisibility(TextView.VISIBLE);
                relation.setVisibility(TextView.VISIBLE);
                documentNumber.setVisibility(TextView.VISIBLE);
                floatingActionButton.hide();
                break;
        }
    }

    private class HttpAsyncTaskProfile extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            JSONObject paramaters = new JSONObject();
            try {
                paramaters.accumulate("ID_PER", user.getIdTypeUser());
                paramaters.accumulate("ID_INSTITUCION", user.getIdInstitution());
                paramaters.accumulate("TYPE", user.getTypeUser());
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return sendPost(urlProfile,paramaters);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getResources().getString(R.string.searching));
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("")){
                try{
                    JSONArray response = new JSONArray(s);
                    JSONObject data = response.getJSONObject(0);

                    switch (user.getTypeUser()){
                        case "Functionary" :
                            institution.setText(data.getString("NOMBRE").trim());
                            name.setText(data.getString("NOMBRE1").trim() +" "+data.getString("NOMBRE2").trim()+" "+data.getString("APELLIDO1").trim()+" "+data.getString("APELLIDO2").trim());
                            documentNumber.setText(data.getString("NO_DOCUMENTO").trim());
                            appointment.setText(data.getString("DOMINIO").trim());
                            break;

                        case "Student" :
                            institution.setText(data.getString("NOMBRE").trim());
                            name.setText(data.getString("NOMBRE1").trim() +" "+data.getString("NOMBRE2").trim()+" "+data.getString("APELLIDO1").trim()+" "+data.getString("APELLIDO2").trim());
                            documentNumber.setText(data.getString("NO_DOCUMENTO").trim());
                            birthdate.setText(data.getString("FECHA_NACIMIENTO").trim());
                            headquarters.setText(data.getString("SEDE").trim());
                            dayTrip.setText(data.getString("JORNADA").trim());
                            grade.setText(data.getString("NOM_GRADO").trim());
                            group.setText(data.getString("NOMBREGRUPO").trim());
                            break;

                        case "Attendant" :
                            institution.setText(data.getString("NOMBRE").trim());
                            name.setText(data.getString("NOMBRE1").trim() +" "+data.getString("NOMBRE2").trim()+" "+data.getString("APELLIDO1").trim()+" "+data.getString("APELLIDO2").trim());
                            documentNumber.setText(data.getString("NO_IDENTIFICACION").trim());
                            relation.setText(data.getString("PARENTESCO").trim());
                            break;
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            progressDialog.hide();
        }
    }

    public void ImageUploadToServerFunction(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStreamObject ;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put("idInstitucion", String.valueOf(user.getIdInstitution()));
                HashMapParams.put("typeUser", user.getTypeUser());
                HashMapParams.put("tittle", user.getnDocument());
                HashMapParams.put("path", ConvertImage);

                String FinalData = uploadImageToServer(urlUploadImage, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_RESULT){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
            else{
                Toast.makeText(getActivity().getApplicationContext(), R.string.permissionNeeded, Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
