package com.example.testgallery.fragments.mainFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.testgallery.R;
import com.example.testgallery.activities.mainActivities.ItemAlbumActivity;
import com.example.testgallery.utility.BCrypt;
import com.example.testgallery.utility.FileUtility;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class SecretFragment extends Fragment {

    private View view;
    Button btnCreatePass;
    Button btnEnterPass;
    EditText createPass;
    EditText confirmPass;
    EditText enterPass;
    EditText question;
    EditText answer;
    TextInputLayout enterField;
    TextInputLayout createField;
    TextInputLayout confirmField;
    TextInputLayout questionField;
    TextInputLayout answerField;
    String password;
    String info_question;
    String info_answer;
    SharedPreferences settings;
    boolean checked = false;
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
        info_question = settings.getString("question","");
        info_answer = settings.getString("answer","");
        view = inflater.inflate(R.layout.fragment_secret, container,false);
        mapping();
        File check_info = new File(secretPath+File.separator+"info.txt");
        if((check_info.exists()&&password.equals(""))||(check_info.exists()&&!checked)){
            try {
                BufferedReader br = new BufferedReader(new FileReader(check_info));
                password = br.readLine();
                info_question = br.readLine();
                info_answer = br.readLine();
                br.close();
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("password",password);
                editor.putString("question",info_question);
                editor.putString("answer",info_answer);
                editor.apply();
                checked=true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        toolbar_album.setTitle(getContext().getResources().getString(R.string.secret));
        toolbar_album.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menuChangePass:
                        getChangePassFrag();

                        break;
                    case R.id.menuForgotPass:
                        BottomSheetDialogFragment forgotDialog = new ForgotPassDialog();
                        forgotDialog.show(getChildFragmentManager(),forgotDialog.getTag());
                        break;
                    case R.id.menuDeleteSecret:
                        if(!password.equals("")){
                            deleteSecret();
                        }
                        else{
                            Toast.makeText(getActivity(),"Doesn't have secret", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return true;
            }
        });
    }
    public void deleteSecret(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final  EditText pass_box = new EditText(getContext());
        pass_box.setHint(getView().getResources().getString(R.string.enter_your_password));
        pass_box.setTransformationMethod(new PasswordTransformationMethod());
        final TextView question = new TextView(getContext());
        question.setText("Answer this question: "+settings.getString("question",""));
        final  EditText del_answer = new EditText(getContext());
        del_answer.setTransformationMethod(new PasswordTransformationMethod());
        linearLayout.addView(pass_box);
        linearLayout.addView(question);
        linearLayout.addView(del_answer);
        alert.setTitle("Confirm your action");
        alert.setView(linearLayout);

        alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String del_pass_value = pass_box.getText().toString();
                String del_answer_value = del_answer.getText().toString();
                if(!BCrypt.checkpw(del_pass_value, password)){
                    Toast.makeText(getActivity(),"Wrong password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!BCrypt.checkpw(del_answer_value, settings.getString("answer",""))){
                    Toast.makeText(getActivity(),"Wrong answer", Toast.LENGTH_SHORT).show();
                    return;
                }
                File scrDir = new File(secretPath);
                if(scrDir.exists()){
                    FileUtility fu = new FileUtility();
                    fu.deleteRecursive(scrDir);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("password","");
                    editor.apply();
                    enterPassView.setVisibility(View.INVISIBLE);
                    createPassView.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"Delete secret success", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(),"Doesn't have secret", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alert.show();


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
                String questionText = question.getText().toString();
                String answerText = answer.getText().toString();
                if(createText.equals("")||confirmText.equals("")){
                    createField.setError("Empty input");
                    confirmField.setError("Empty input");
                }
                if(questionText.equals("")||answerText.equals("")){
                    questionField.setError("Empty input");
                    answerField.setError("Empty input");
                }
                else{
                    if(createText.equals(confirmText)){
                        String hashedPass = BCrypt.hashpw(createText,BCrypt.gensalt());
                        String hashedAnswer = BCrypt.hashpw(answerText,BCrypt.gensalt());
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("password",hashedPass);
                        editor.putString("question",questionText);
                        editor.putString("answer",hashedAnswer);
                        editor.apply();

                        File mydir = new File(secretPath);
                        if (!mydir.exists()) {
                            mydir.mkdirs();
                            File nomedia = new File(Environment.getExternalStorageDirectory() + File.separator + ".secret" + File.separator + ".nomedia");
                            try {
                                nomedia.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            File info = new File(secretPath+ File.separator+"info.txt");

                            try {
                                info.createNewFile();
                                FileOutputStream fos = new FileOutputStream(info);
                                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                                bw.write(hashedPass);
                                bw.newLine();
                                bw.write(questionText);
                                bw.newLine();
                                bw.write(hashedAnswer);
                                bw.close();
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
                if(BCrypt.checkpw(enterText, password)){
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
        questionField = view.findViewById(R.id.question_field);
        answerField = view.findViewById(R.id.answer_field);
        question = view.findViewById(R.id.question);
        answer = view.findViewById(R.id.answer);

    }
    public void accessSecret(){
        Intent intent = new Intent(this.getContext(), ItemAlbumActivity.class);
        ArrayList<String> list = getListImg();

        intent.putStringArrayListExtra("data", list);
        intent.putExtra("name", "Secret");
        intent.putExtra("isSecret", 1);
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
                if(!file.getName().equals(".nomedia")&&!file.getName().equals("info.txt")) {
                    listPath.add(file.getPath());
                }
            }
            return listPath;
        }

    }
}
