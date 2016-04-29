package com.example.ok.speechtotext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;

public class text_to_speech extends Activity implements TextToSpeech.OnInitListener
{

    private TextToSpeech mTts;
    private static final int MY_DATA_CHECK_CODE = 1;
    Bundle b;
    String textResult;
    TextView showTextResult;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_to_speech);

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

        showTextResult=(TextView)findViewById(R.id.Resultado);
        b=getIntent().getExtras();
        textResult=b.getString("valueResult");
        tts=new TextToSpeech(text_to_speech.this,text_to_speech.this);

        showTextResult.setText(textResult);
    }

    public void onButtonImageClick(View v)
    {
        if(v.getId() == R.id.speak)
        {
            mTts.speak(showTextResult.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onInit(int status)
    {
        if(mTts!=null)
        mTts.setLanguage(new Locale(Locale.getDefault().getLanguage()));
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == MY_DATA_CHECK_CODE)
        {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
            {
                mTts = new TextToSpeech(this, this);
            }
            else
            {
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }
}
