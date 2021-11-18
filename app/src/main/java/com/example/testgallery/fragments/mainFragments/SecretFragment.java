package com.example.testgallery.fragments.mainFragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.testgallery.R;
import com.example.testgallery.activities.mainActivities.ItemAlbumActivity;
import com.example.testgallery.utility.FileUtility;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SecretFragment extends Fragment {

    private View view;
    Button btnCreatePass;
    Button btnEnterPass;
    EditText createPass;
    EditText confirmPass;
    EditText enterPass;
    TextInputLayout enterField;
    TextInputLayout createField;
    TextInputLayout confirmField;
    String password;
    SharedPreferences settings;
    private androidx.appcompat.widget.Toolbar toolbar_album;
    private LinearLayout createPassView;
    private LinearLayout enterPassView;
    private  String secretPath;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        secretPath = Environment.getExternalStorageDirectory()+File.separator+".secret";
        settings = getActivity().getSharedPreferences("PREFS",0);
        password = settings.getString("password","");
        view = inflater.inflate(R.layout.fragment_secret, container,false);
        mapping();
        if(!password.equals("")){
            createPassView.setVisibility(View.INVISIBLE);
        }
        else{
            enterPassView.setVisibility(View.INVISIBLE);
        }
        eventEnterPass();
        eventCreatePass();
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

                        break;
                    case R.id.menuDeleteSecret:
                        deleteSecret();
                        break;
                }
                return true;
            }
        });
    }
    public void deleteSecret(){
        File scrDir = new File(secretPath);
        if(scrDir.exists()){
            FileUtility fu = new FileUtility();
            fu.deleteRecursive(scrDir);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("password","");
            editor.apply();
            enterPassView.setVisibility(View.INVISIBLE);
            createPassView.setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(getActivity(),"Doesn't have secret", Toast.LENGTH_SHORT).show();
        }

    }

    public void getChangePassFrag(){
        updatePassword();
        if(!password.equals("")){
            BottomSheetDialogFragment changePassFrag = new ChangePassFragment();
            changePassFrag.show(getChildFragmentManager(),changePassFrag.getTag());
        }
        else{
            Toast.makeText(getActivity(),"Don't have password", Toast.LENGTH_SHORT).show();
        }
    }
    public  void eventCreatePass(){
        btnCreatePass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String createText = createPass.getText().toString();
                String confirmText = confirmPass.getText().toString();
                if(createText.equals("")||confirmText.equals("")){
                    createField.setError("Empty input");
                    confirmField.setError("Empty input");
                }
                else{
                    if(createText.equals(confirmText)){
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("password",createText);
                        editor.apply();

                        File mydir = new File(secretPath);
                        if (!mydir.exists()) {
                            mydir.mkdirs();
                            File nonmedia = new File(Environment.getExternalStorageDirectory() + File.separator + ".secret" + File.separator + ".nonmedia");
                            try {
                                nonmedia.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        createPassView.setVisibility(View.INVISIBLE);
                        enterPassView.setVisibility(View.VISIBLE);
                        updatePassword();
                        accessSecret();
                    }
                    else{
                        confirmField.setError("Password doesn't match");
                    }
                }
            }
        });

    }

    public  void eventEnterPass(){
        btnEnterPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                updatePassword();
                String enterText = enterPass.getText().toString();
                if(enterText.equals(password)){
                    Toast.makeText(getActivity(),"Password correct", Toast.LENGTH_SHORT).show();
                    accessSecret();
                    enterPass.setText("");
                }
                else{
                    enterField.setError("Wrong password");
                }
            }
        });
    }
    public void updatePassword(){
        password = settings.getString("password","");
    }
    public void mapping(){
        enterPass = view.findViewById(R.id.enterPass);
        btnEnterPass = view.findViewById(R.id.btnEnterPass);
        createPass = view.findViewById(R.id.createpass);
        confirmPass = view.findViewById(R.id.confirmpass);
        btnCreatePass = view.findViewById(R.id.btnCreatePass);
        createPassView = view.findViewById(R.id.frag_createpass);
        enterPassView = view.findViewById(R.id.frag_enterpass);
        enterField = view.findViewById(R.id.enterField);
        createField = view.findViewById(R.id.createField);
        confirmField = view.findViewById(R.id.confirmField);

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
        File mydir = new File(secretPath);
        if (!mydir.exists())
        {
            Toast.makeText(getActivity(),"Secret doesn't exist", Toast.LENGTH_SHORT).show();
            return null;
        }
        else{
            ArrayList<String> listPath = new ArrayList<>();
            File list[] = mydir.listFiles();
            for(File file:list){
                if(!file.getName().equals(".nonmedia")) {
                    listPath.add(file.getPath());
                }
            }
            return listPath;
        }

    }
}
