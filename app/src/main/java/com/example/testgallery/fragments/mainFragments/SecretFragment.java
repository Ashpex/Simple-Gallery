package com.example.testgallery.fragments.mainFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testgallery.R;
import com.example.testgallery.fragments.subFragments.ItemAlbumFragment;
import com.example.testgallery.models.Album;
import com.example.testgallery.models.Image;

import java.util.ArrayList;

public class SecretFragment extends Fragment {

    private View view;
    Button btnCreatePass;
    Button btnEnterPass;
    EditText createPass;
    EditText confirmPass;
    EditText enterPass;
    String password;
    SharedPreferences settings;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settings = getActivity().getSharedPreferences("PREFS",0);
        password = settings.getString("password","");

        if(password.equals("")){
            view = inflater.inflate(R.layout.fragment_secret_createpass, container,false);
            mappingCreatePass();
            eventCreatePass();
        }
        else{
            view = inflater.inflate(R.layout.fragment_secret_enterpass, container,false);
            mappingEnterPass();
            eventEnterPass();
        }


        return view;
    }
    public  void eventCreatePass(){
        btnCreatePass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String createText = createPass.getText().toString();
                String confirmText = confirmPass.getText().toString();
                if(createText.equals("")||confirmText.equals("")){
                    Toast.makeText(getActivity(),"Empty input", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(createText.equals(confirmText)){
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("password",createText);
                        editor.apply();
                        accessSecret(R.id.frag_createpass);
                    }
                    else{
                        Toast.makeText(getActivity(),"Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    public  void eventEnterPass(){
        btnEnterPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String enterText = enterPass.getText().toString();
                if(enterText.equals(password)){
                    Toast.makeText(getActivity(),"Password correct", Toast.LENGTH_SHORT).show();
                    accessSecret(R.id.frag_enterpass);
                }
                else{
                    Toast.makeText(getActivity(),"Wrong password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void mappingCreatePass(){
        createPass = view.findViewById(R.id.createpass);
        confirmPass = view.findViewById(R.id.confirmpass);
        btnCreatePass = view.findViewById(R.id.btnCreatePass);
    }
    public void mappingEnterPass(){
        enterPass = view.findViewById(R.id.enterPass);
        btnEnterPass = view.findViewById(R.id.btnEnterPass);
    }
    public void accessSecret(int container){
        Image img = new Image();
        ItemAlbumFragment fragment2 = new ItemAlbumFragment(new Album(img,"Secret"));
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
