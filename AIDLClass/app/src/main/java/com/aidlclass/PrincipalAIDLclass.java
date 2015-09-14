package com.aidlclass;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PrincipalAIDLclass extends ActionBarActivity {

    public static final String CLASS_TAG=PrincipalAIDLclass.class.getName();

    IAddService servicio;
    AddServiceConnection conexion;
    Button resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_aidlclass);
        resultado=(Button)findViewById(R.id.buttonResult);
        iniciarservicio();
    }

    private void iniciarservicio() {
       Log.i(CLASS_TAG,"inicioservicio");
        conexion=new AddServiceConnection();
        Intent ir=new Intent();
        ir.setClassName("com.aidlclass", com.aidlclass.AddService.class.getName());
        boolean retotrno=bindService(ir,conexion, Context.BIND_AUTO_CREATE);
        Log.i(CLASS_TAG,"servicio iniciado con bound valor:"+retotrno);

        resultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iraresultado();
            }
        });

    }

    private void iraresultado() {

        EditText valor1=(EditText)findViewById(R.id.editTextValue1);
        EditText valor2=(EditText)findViewById(R.id.editTextValue2);
        EditText result=(EditText)findViewById(R.id.editTextResult);
        int n1=0;
        int n2=0;
        int res=-1;

        n1=Integer.parseInt(valor1.getText().toString());
        n2=Integer.parseInt(valor2.getText().toString());
        try {
            res=servicio.add(n1,n2);
        } catch (RemoteException e) {
            Log.i(CLASS_TAG, "Data fetch failed with: " + e);
            e.printStackTrace();
        }
        result.setText(new Integer(res).toString());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal_aidlclass, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //clase innerente para los servicios de conexion
    private class AddServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            servicio=IAddService.Stub.asInterface((IBinder) service);
            Log.i(CLASS_TAG,"onServiceConnected: Conectado"+name.toString());
            Toast.makeText(getApplicationContext(),"servicio conectado",Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            servicio=null;
            Log.i(CLASS_TAG,"onServiceConnected: Desconectado"+name.toString());
            Toast.makeText(getApplicationContext(),"servicio desconectado",Toast.LENGTH_SHORT).show();
        }
    }

    public void onDestroy(){
        super.onDestroy();
        relaseService();
    }

    private void relaseService() {

        unbindService(conexion);
        conexion=null;
        Log.d(CLASS_TAG, "releaseService(): unbound.");

    }
}
