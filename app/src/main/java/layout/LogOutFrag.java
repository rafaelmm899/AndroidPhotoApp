package layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softmedialtda.softmediaphotoapp.LoginActivity;

/**
 * Created by Agustin on 15/4/2018.
 */

public class LogOutFrag extends Fragment {

    public LogOutFrag() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void close(){
        Intent myIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(myIntent);
    }
}
