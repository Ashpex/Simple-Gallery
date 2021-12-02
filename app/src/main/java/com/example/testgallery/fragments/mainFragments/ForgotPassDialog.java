package com.example.testgallery.fragments.mainFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testgallery.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ForgotPassDialog extends BottomSheetDialogFragment {
    private View view;
    private Button btnConfirm;
    EditText answerEdt;
    TextView questionText;
    TextView passwordText;
    String password;
    String question;
    String answer;
    SharedPreferences settings;
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
        event();
        return view;
    }

    public void event(){
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answerText = answerEdt.getText().toString();
                if(!answerText.equals(answer)){
                    Toast.makeText(getActivity(),"Wrong answer", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getActivity(),"Correct", Toast.LENGTH_SHORT).show();
                passwordText.setText("Your password is : "+password);
            }
        });
    }
    public void mapping(){
        questionText = view.findViewById(R.id.dialog_question);
        questionText.setText(question);
        answerEdt = view.findViewById(R.id.dialog_editAnswer);
        btnConfirm = view.findViewById(R.id.dialog_btnForgot);
        passwordText = view.findViewById(R.id.dialog_password);
    }
}