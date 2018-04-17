package com.softmedialtda.softmediaphotoapp.util;

import com.softmedialtda.softmediaphotoapp.models.Notification;
import com.softmedialtda.softmediaphotoapp.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Object;


/**
 * Created by Agustin on 4/4/2018.
 */

public class Common {

    public static User convertJsonObjectToUserObject(JSONObject json){
        User user = new User();
        try{
            switch (json.getString("TYPE").trim()){
                case "ADMIN" :
                    user = new User(json.getString("USERNAME").trim(),Integer.parseInt(json.getString("ID").trim()),json.getString("NAME").trim(),json.getString("LASTNAME").trim(),json.getString("TYPE").trim(),json.getString("IMAGE"));
                    break;
                case "Student":
                case "Functionary":
                    user = new User(json.getString("USERNAME").trim(),Integer.parseInt(json.getString("ID").trim()),json.getString("NOMBRE1").trim(),json.getString("NOMBRE2").trim(),json.getString("APELLIDO1").trim(),json.getString("APELLIDO2").trim(),json.getString("NO_DOCUMENTO").trim(),json.getString("TYPE").trim(),Integer.parseInt(json.getString("ID_INSTITUCION").trim()),Integer.parseInt(json.getString("ID_PER").trim()),json.getString("IMAGE"));
                    break;
                case "Attendant":
                    user = new User(json.getString("USERNAME").trim(),Integer.parseInt(json.getString("ID").trim()),json.getString("NOMBRE1").trim(),json.getString("NOMBRE2").trim(),json.getString("APELLIDO1").trim(),json.getString("APELLIDO2").trim(),json.getString("NO_DOCUMENTO").trim(),Integer.parseInt(json.getString("ID_ALUMNO").trim()),json.getString("NOMBRE1_ALUMNO").trim(),json.getString("NOMBRE2_ALUMNO").trim(),json.getString("APELLIDO1_ALUMNO").trim(),json.getString("APELLIDO2_ALUMNO").trim(),json.getString("NO_DOCUMENTO_ALUMNO").trim(),json.getString("TYPE").trim(),Integer.parseInt(json.getString("ID_INSTITUCION").trim()),Integer.parseInt(json.getString("ID_PER").trim()),json.getString("IMAGE"));
                    break;
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static ArrayList<Notification> getListNotification(JSONArray json, int position){
        ArrayList<Notification> list = new ArrayList<Notification>();
        try {
            for (int i = 0; i < json.length(); i++){
                JSONObject data = json.getJSONObject(i);
                switch (position){
                    case 0: //todos
                        list.add(new Notification(Integer.parseInt(data.getString("ID").trim()),data.getString("ASUNTO").trim(),data.getString("CONTENIDO").trim(),data.getString("FECHA").trim(),data.getString("HORA").trim(),Integer.parseInt(data.getString("VISTO").trim()),data.getString("EMAIL").trim()));
                        break;
                    case 1: //vistos
                        if (Integer.parseInt(data.getString("VISTO").trim()) == 1){
                            list.add(new Notification(Integer.parseInt(data.getString("ID").trim()),data.getString("ASUNTO").trim(),data.getString("CONTENIDO").trim(),data.getString("FECHA").trim(),data.getString("HORA").trim(),Integer.parseInt(data.getString("VISTO").trim()),data.getString("EMAIL").trim()));
                        }
                        break;
                    case 2: //sin leer
                        if (Integer.parseInt(data.getString("VISTO").trim()) == 0){
                            list.add(new Notification(Integer.parseInt(data.getString("ID").trim()),data.getString("ASUNTO").trim(),data.getString("CONTENIDO").trim(),data.getString("FECHA").trim(),data.getString("HORA").trim(),Integer.parseInt(data.getString("VISTO").trim()),data.getString("EMAIL").trim()));
                        }
                        break;
                }
            }

        }catch (JSONException ex){
            ex.printStackTrace();
        }
        return list;
    }
}
