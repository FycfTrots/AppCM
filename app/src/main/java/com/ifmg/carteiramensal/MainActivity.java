package com.ifmg.carteiramensal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

import ferramentas.EventosDB;

public class MainActivity extends AppCompatActivity {

    private TextView titulo;
    private TextView entrada;
    private TextView saida;
    private TextView saldo;
    private ImageButton entradaBtn;
    private ImageButton saidaBtn;
    private Button anteriorBtn;
    private Button proximoBtn;
    private Button novoBtn;
    private Calendar hoje;
    private Calendar dataApp;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //criando o link entre os componentes JAVA X XML

        titulo = (TextView) findViewById(R.id.tituloMain);
        entrada = (TextView) findViewById(R.id.entradaTxt);
        saida = (TextView) findViewById(R.id.saidaTxt);
        saldo = (TextView) findViewById(R.id.saldoTxt);

        entradaBtn = (ImageButton) findViewById(R.id.entradaBtn);
        saidaBtn = (ImageButton) findViewById(R.id.saidaBtn);

        anteriorBtn = (Button) findViewById(R.id.anteriorBtn);
        proximoBtn = (Button) findViewById(R.id.proximoBtn);
        novoBtn = (Button) findViewById(R.id.novoBtn);

        //responsável por implementar todos os eventos de botões

        cadastroEventos();

        //recupero a data e hora atual
        dataApp = Calendar.getInstance();
        hoje = Calendar.getInstance();

        mostraDataApp();

    }

    private void cadastroEventos(){
        anteriorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaMes(-1);
            }
        });

        proximoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaMes(1);
            }
        });

        novoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventosDB db = new EventosDB(MainActivity.this);
                db.insereEvento();

                Toast.makeText(MainActivity.this, db.getDatabaseName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostraDataApp(){
        //0.janeiro 1.fevereiro ... 11.dezembro

        String nomeMes[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        int mes = dataApp.get(Calendar.MONTH);
        int ano = dataApp.get(Calendar.YEAR);

        titulo.setText(nomeMes[mes] + "/" +ano);
    }

    private void atualizaMes(int ajuste) {

        dataApp.add(Calendar.MONTH, ajuste);

        //proximo mes (nao pode passar do mes atual)
        if(ajuste>0){
            if(dataApp.after(hoje)){
                dataApp.add(Calendar.MONTH, -1);
                Toast.makeText(MainActivity.this, "Você não pode ir para o mês futuro", Toast.LENGTH_SHORT ).show();
            }
        }else{
            //realizar a busca no banco de dados (avaliar se existem meses anteriores cadastrados)

        }

        mostraDataApp();
    }

}