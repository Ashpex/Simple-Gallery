package com.example.testgallery.fragments.mainFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testgallery.R;
import com.example.testgallery.utility.BCrypt;
import com.example.testgallery.utility.FileUtility;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.FileNotFoundException;

public class ForgotPassDialog extends BottomSheetDialogFragment {
    private View view;
    private Button btnConfirm;
    private Button btnChangePass;
    EditText answerEdt;
    EditText newPass;
    EditText rePass;
    TextView questionText;
    String password;
    String question;
    String answer;
    SharedPreferences settings;
    LinearLayout answerQuestionView;
    LinearLayout changePassVIew;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_secret_forgotpass, container,false);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
        settings = getActivity().getSharedPreferences("PREFS",0);
        password = settings.getString("password","");
        question = settings.getString("question","");
        answer = settings.getString("answer","");

        getParentFragmentManager().beginTransaction().add(this,"ForgotPass_frag");
        if(password.equals("")||question.equals("")||answer.equals("")){
            Toast.makeText(getActivity(),"Failed setting, can't get back password", Toast.LENGTH_SHORT).show();
            Fragment thisFrag = getParentFragmentManager().findFragmentByTag("ForgotPass_frag");
            getParentFragmentManager().beginTransaction()
                    .remove(thisFrag)
                    .commit();
        }
        mapping();
        changePassVIew.setVisibility(View.INVISIBLE);
        event();
        return view;
    }

    public void event(){
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answerText = answerEdt.getText().toString();
                if(!BCrypt.checkpw(answerText, answer)){
                    Toast.makeText(getActivity(),"Wrong answer", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getActivity(),"Correct", Toast.LENGTH_SHORT).show();
                answerQuestionView.setVisibility(View.INVISIBLE);
                changePassVIew.setVisibility(View.VISIBLE);
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassText = newPass.getText().toString();
                String rePassText = rePass.getText().toString();
                if(!newPassText.equals(rePassText)){
                    Toast.makeText(getActivity(),"Password doesn't match", Toast.LENGTH_SHORT).show();
                }
                else{
                    String hashed = BCrypt.hashpw(newPassText, BCrypt.gensalt());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("password",hashed);
                    editor.apply();
                    FileUtility fu = new FileUtility();
                    try {
                        fu.updateInfoFile(hashed);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(),"Change Password Success", Toast.LENGTH_SHORT).show();
                    Fragment thisFrag = getParentFragmentManager().findFragmentByTag("ForgotPass_frag");
                    getParentFragmentManager().beginTransaction()
                            .remove(thisFrag)
                            .commit();
                }
            }
        });
    }
    public void mapping(){
        answerQuestionView = view.findViewById(R.id.answer_view);
        changePassVIew = view.findViewById(R.id.changepass_view);
        questionText = view.findViewById(R.id.dialog_question);
        questionText.setText(question);
        answerEdt = view.findViewById(R.id.dialog_editAnswer);
        btnConfirm = view.findViewById(R.id.dialog_btnForgot);

        newPass = view.findViewById(R.id.dialog_editnewpass);
        rePass = view.findViewById(R.id.dialog_editrepass);
        btnChangePass = view.findViewById(R.id.dialog_btnChangepass);
    }
}