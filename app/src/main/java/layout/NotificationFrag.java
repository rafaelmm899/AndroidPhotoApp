package layout;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.softmedialtda.softmediaphotoapp.R;
import com.softmedialtda.softmediaphotoapp.models.Notification;
import com.softmedialtda.softmediaphotoapp.models.User;
import com.softmedialtda.softmediaphotoapp.util.NotificationListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.softmedialtda.softmediaphotoapp.util.Common.getListNotification;
import static com.softmedialtda.softmediaphotoapp.util.Connection.sendPost;
import static com.softmedialtda.softmediaphotoapp.util.Constants.DOMAIN;

/**
 * Created by Agustin on 12/4/2018.
 */

public class NotificationFrag extends Fragment {
    ListView notificationListView;
    String urlNotifications = DOMAIN+"notifications";
    User user = new User();
    int selectedPosition;
    LinearLayout emptyNotifications;

    public static NotificationFrag newInstance(int section){
        NotificationFrag fragment = new NotificationFrag();
        Bundle args = new Bundle();
        args.putInt("num", section);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        emptyNotifications = (LinearLayout) getView();
        notificationListView = (ListView) rootView.findViewById(R.id.notificationListViewFragment);
        notificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = notificationListView.getItemAtPosition(position);
                Notification notSelected = (Notification) o;

                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(notSelected.getSubject());
                alert.setMessage(notSelected.getContent()).setPositiveButton(R.string.closeDialogNotification, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
        ColorDrawable devidrColor = new ColorDrawable(
                this.getResources().getColor(R.color.backgroundListView));
        notificationListView.setDivider(devidrColor);
        notificationListView.setDividerHeight(5);
        user = (User) getActivity().getIntent().getSerializableExtra("user");
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedPosition = getArguments() != null ? getArguments().getInt("num") : 1;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new HttpAsyncTask().execute(urlNotifications);
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            JSONObject paramaters = new JSONObject();
            try {
                paramaters.accumulate("ID_PER", user.getIdTypeUser());
                paramaters.accumulate("ID_INSTITUCION", user.getIdInstitution());
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return sendPost(urlNotifications,paramaters);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("")){
                try{
                    if (getActivity() != null) {
                        JSONArray response = new JSONArray(s);
                        ArrayList<Notification> list = getListNotification(response, selectedPosition);
                        if (list.size() == 0){
                            emptyNotifications.setVisibility(View.VISIBLE);
                            notificationListView.setVisibility(View.INVISIBLE);
                        }else{
                            emptyNotifications.setVisibility(View.INVISIBLE);
                            NotificationListAdapter nla = new NotificationListAdapter(getActivity(), list);
                            notificationListView.setAdapter(nla);
                        }

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
