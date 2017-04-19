package com.example.cecyt9.webservice;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MAinWeb extends AppCompatActivity {

    String resultado;
    String xx;

    TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_web);
        res =(TextView) findViewById(R.id.hola);
    }



    public void elBoton(View v){
        xx = "Elemento Android";
        llamaws ws = new llamaws();
        ws.execute();
    }

    private class llamaws extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            String NAMESPACE = "http://ws/";
            String URL="http://148.204.168.11:8080/WebApplication6/wsTest?WSDL";
            String METHOD_NAME = "hello";
            String SOAP_ACTION = "http://ws/hello";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("name", xx);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);


            HttpTransportSE ht = new HttpTransportSE(URL);
            try {
                ht.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
                resultado=response.toString();

                Log.i("Resultado: ",resultado);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success==false){
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(getApplicationContext(), "El resultado es: "+resultado, Toast.LENGTH_LONG).show();
                txt.setText(resultado);

            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }
}
