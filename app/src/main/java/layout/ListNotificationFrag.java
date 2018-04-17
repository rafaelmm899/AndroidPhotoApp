package layout;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.softmedialtda.softmediaphotoapp.NotificationActivity;
import com.softmedialtda.softmediaphotoapp.R;
import com.softmedialtda.softmediaphotoapp.models.Notification;
import com.softmedialtda.softmediaphotoapp.models.User;
import com.softmedialtda.softmediaphotoapp.util.NotificationListAdapter;
import com.softmedialtda.softmediaphotoapp.util.SectionsPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.softmedialtda.softmediaphotoapp.util.Common.getListNotification;
import static com.softmedialtda.softmediaphotoapp.util.Connection.sendPost;
import static com.softmedialtda.softmediaphotoapp.util.Constants.DOMAIN;


public class ListNotificationFrag extends Fragment {
    private ViewPager mViewPager;

    public ListNotificationFrag() {
    }

    public static ListNotificationFrag newInstance(int section){
        ListNotificationFrag fragment = new ListNotificationFrag();
        Bundle args = new Bundle();
        args.putInt("num", section);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_notification, container, false);
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
