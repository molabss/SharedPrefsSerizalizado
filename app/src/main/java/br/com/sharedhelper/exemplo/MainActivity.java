package br.com.sharedhelper.exemplo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    EditText etNome, etSobreNome;
    Button btnSerializar, btnDesSerializar;

    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = getPreferences(MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        etNome = (EditText)findViewById(R.id.etNome);
        etSobreNome =(EditText)findViewById(R.id.etSobreNome);
        btnSerializar = (Button)findViewById(R.id.btnSerializar);
        btnSerializar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pessoa moises = new Pessoa(etNome.getText().toString(), etSobreNome.getText().toString());
                Gson gson = new Gson();
                String json = gson.toJson(moises);
                prefsEditor.putString("pessoa", json);
                prefsEditor.commit();
                etNome.setText("");
                etSobreNome.setText("");
                Toast.makeText(getBaseContext(),"Serializado com sucesso!\n"+json,Toast.LENGTH_SHORT).show();
            }
        });
        btnDesSerializar = (Button)findViewById(R.id.btnDesSerializar);
        btnDesSerializar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String json = mPrefs.getString("pessoa", "");
                Pessoa obj = gson.fromJson(json, Pessoa.class);
                etNome.setText(obj.getNome());
                etSobreNome.setText(obj.getSobrenome());
                Toast.makeText(getBaseContext(),"Des-Serializado com sucesso!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class Pessoa{
    private String nome;
    private String sobrenome;
    public Pessoa(String nome,String sobrenome) {
        this.sobrenome = sobrenome;
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSobrenome() {
        return sobrenome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
}
