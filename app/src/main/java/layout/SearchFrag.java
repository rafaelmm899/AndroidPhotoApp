package layout;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import com.google.android.gms.vision.barcode.Barcode;
import com.softmedialtda.softmediaphotoapp.R;
import com.softmedialtda.softmediaphotoapp.models.User;
import com.softmedialtda.softmediaphotoapp.util.ScanActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.softmedialtda.softmediaphotoapp.util.Connection.sendPost;
import static com.softmedialtda.softmediaphotoapp.util.Constants.DOMAIN;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFrag extends Fragment implements OnClickListener {
    String typeSearch;
    String documentNumber;
    EditText editTextDocumentNumber;
    String urlSearch = DOMAIN+"search";
    User user = new User();
    public static final int PERMISSION_REQUEST = 200,  REQUEST_CODE = 100;
    ProgressDialog progressDialog;

    public SearchFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        typeSearch = getArguments().getString("typeSearch");
        editTextDocumentNumber = (EditText) rootView.findViewById(R.id.editTextDocumentNumber);
        Button buttonSearch = (Button) rootView.findViewById(R.id.buttonSearch);
        Button buttonQr = (Button) rootView.findViewById(R.id.buttonScan);
        user = (User) getActivity().getIntent().getSerializableExtra("user");
        buttonSearch.setOnClickListener(this);
        buttonQr.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSearch:
                documentNumber = editTextDocumentNumber.getText().toString();
                if (!documentNumber.equals("")){
                    new HttpAsyncTaskSearch().execute(urlSearch);
                }else{
                    final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setMessage(R.string.documentNumberEmpty).setPositiveButton(R.string.closeDialog, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }

                break;
            case R.id.buttonScan:
                Intent myIntent = new Intent(getActivity(), ScanActivity.class);
                myIntent.putExtra("user",user);
                startActivityForResult(myIntent, REQUEST_CODE);
                break;
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final Barcode barcode = data.getParcelableExtra("barcode");
        String code = barcode.displayValue.toString();
        editTextDocumentNumber.setText(code.split("-")[0]);
    }

    private class HttpAsyncTaskSearch extends AsyncTask<String, Void, String> {
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
            if (!s.equals("")) {
                if (checkData(s) == true){
                    Bundle bundle = new Bundle();
                    bundle.putString("response", s);
                    bundle.putString("typeSearch", typeSearch);
                    SearchProfileFrag searchFragement =  new SearchProfileFrag();
                    searchFragement.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.flContent, searchFragement).commit();
                    progressDialog.hide();
                }else{
                    progressDialog.hide();
                    final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setMessage(R.string.noDataFound).setPositiveButton(R.string.closeDialog, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
            }
        }

        @Override
        protected String doInBackground(String... params) {
            JSONObject paramaters = new JSONObject();
            try {
                paramaters.accumulate("DOCUMENT_NUMBER", documentNumber);
                paramaters.accumulate("TYPE", typeSearch);
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return sendPost(urlSearch,paramaters);
        }
    }

    public boolean checkData(String data){
        try{
            JSONArray jsonArray = new JSONArray(data);
            if (jsonArray.length() == 0){
                return false;
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

}
