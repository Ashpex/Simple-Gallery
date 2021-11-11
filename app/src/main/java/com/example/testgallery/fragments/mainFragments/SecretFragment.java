package com.example.testgallery.fragments.mainFragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.testgallery.R;
import com.example.testgallery.activities.mainActivities.ItemAlbumActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.io.File;
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
    private androidx.appcompat.widget.Toolbar toolbar_album;
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
        toolbar_album = view.findViewById(R.id.toolbar_album);
        toolBarEvents();
        return view;
    }
    private void toolBarEvents() {
        toolbar_album.inflateMenu(R.menu.menu_album_secret);
        toolbar_album.setTitle("Secret");
        toolbar_album.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menuChangePass:
                        getChangePassFrag();
                        updatePassword();
                        break;
                    case R.id.menuDeleteSecret:
                        deleteSecret();
                        //updatePassword();
                        break;
                }
                return true;
            }
        });
    }
    public void deleteSecret(){

    }
    public void getChangePassFrag(){
        BottomSheetDialogFragment changePassFrag = new ChangePassFragment();
        changePassFrag.show(getChildFragmentManager(),changePassFrag.getTag());
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
                        File mydir = getContext().getDir("secret", getContext().MODE_PRIVATE);
                        if (!mydir.exists())
                        {
                            mydir.mkdirs();
                        }
                        reload();
                        updatePassword();
                        accessSecret();
                    }
                    else{
                        Toast.makeText(getActivity(),"Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    public void updatePassword(){
        password = settings.getString("password","");
    }
    public void reload(){
        getParentFragmentManager().beginTransaction()
                .detach(this)
                .attach(new SecretFragment())
                .commit();
    }

    public  void eventEnterPass(){
        btnEnterPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String enterText = enterPass.getText().toString();
                if(enterText.equals(password)){
                    Toast.makeText(getActivity(),"Password correct", Toast.LENGTH_SHORT).show();
                    accessSecret();
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
    public void accessSecret(){
        Intent intent = new Intent(this.getContext(), ItemAlbumActivity.class);
        ArrayList<String> list = getListImg();

        intent.putStringArrayListExtra("data", list);
        intent.putExtra("name", "Secret");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.getContext().startActivity(intent);
    }
    public ArrayList<String> getListImg(){
        File mydir = getContext().getDir("secret", getContext().MODE_PRIVATE);
        if (!mydir.exists())
        {
            Toast.makeText(getActivity(),"Secret doesn't exist", Toast.LENGTH_SHORT).show();
            return null;
        }
        else{
            ArrayList<String> listPath = new ArrayList<>();
            File list[] = mydir.listFiles();
            for(File file:list){
                listPath.add(file.getPath());
            }
            return listPath;
        }

    }
}
