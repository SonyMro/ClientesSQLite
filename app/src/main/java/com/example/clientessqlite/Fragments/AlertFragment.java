package com.example.clientessqlite.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientessqlite.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlertFragment extends Fragment {

private TextView textView;
private  DataListerner callback;

    public AlertFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callback= (DataListerner) context;

        }catch (Exception ex){
            Toast.makeText(getContext(),"Error: "+ex.getMessage(),Toast.LENGTH_SHORT).show();
            throw  new ClassCastException(context.toString()+"Error en la implementacion");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_alert, container, false);
        textView=(TextView) view.findViewById(R.id.lbl);
        Toast.makeText(getContext(),""+textView.getText().toString(),Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        return view;
    }
    public  interface DataListerner{
        void senData(String texto);
    }

}
