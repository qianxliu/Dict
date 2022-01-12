package com.qianxin.dict.ui.newproverb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.qianxin.dict.R;

public class NewProverbActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "EXTRA_REPLY";

    private EditText mEditWordView, mInterpretationView, mChinaProverbView, mSourceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_proverb);
        mEditWordView = findViewById(R.id.edit_word);
        mInterpretationView = findViewById(R.id.interpretation);
        mChinaProverbView = findViewById(R.id.chinaProverb);
        mSourceView = findViewById(R.id.source);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditWordView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String[] list = {
                        mEditWordView.getText().toString(),
                        mInterpretationView.getText().toString(),
                        mChinaProverbView.getText().toString(),
                        mSourceView.getText().toString(),
                };

                replyIntent.putExtra(EXTRA_REPLY, list);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        final Button clear = findViewById(R.id.button_clear);
        clear.setOnClickListener(
                view -> {
                    mEditWordView.setText("");
                    mEditWordView.clearComposingText();
                    mInterpretationView.setText("");
                    mInterpretationView.clearComposingText();
                    mChinaProverbView.setText("");
                    mChinaProverbView.clearComposingText();
                    mSourceView.setText("");
                    mSourceView.clearComposingText();
                }
        );
    }
}