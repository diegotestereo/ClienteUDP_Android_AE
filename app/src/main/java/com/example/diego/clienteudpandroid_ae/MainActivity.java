package com.example.diego.clienteudpandroid_ae;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class MainActivity extends ActionBarActivity {
    Button boton;
    EditText editMsn,editIpServer,editPort;
    TextView text_Servidor;
    private static final String LOGCAT ="API UDP";
    String mensaje ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
///////////////////////// importante!!!!
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
////////////////////////////  http://stackoverflow.com/questions/6976317/android-http-connection-exception/6986726#6986726
        Log.d(LOGCAT, "arranco");
        setContentView(R.layout.activity_main);
        LevantarXML();
        Botones();
    }

    private void LevantarXML() {
        boton=(Button)findViewById(R.id.button);
        editMsn=(EditText)findViewById(R.id.editMsn);
        editIpServer=(EditText)findViewById(R.id.editIpServer);
        editPort=(EditText)findViewById(R.id.editPort);
        text_Servidor = (TextView) findViewById(R.id.text_Servidor);

    }

     private void Botones(){
        boton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Log.d(LOGCAT,"seapreto boton");
        Cliente();

    }
});

     }

    private void Cliente(){
    ////////////  CODIGO AGREGADO //////////////////
    mensaje=editMsn.getText().toString();
    Log.d(LOGCAT,"mensaje: "+mensaje);
    int port = Integer.parseInt(editPort.getText().toString());
    String ipServer = editIpServer.getText().toString();

    try {

        ////////    envia datos //////////////////////7
        DatagramSocket socket = new DatagramSocket();
        InetAddress local  = InetAddress.getByName(ipServer);// ip servidor

        int msg  = mensaje.length();// longitud del mensaje
        byte []message=mensaje.getBytes();// extrae el mensaje como bytes
        DatagramPacket paquete = new DatagramPacket(message,msg,local,port);// envia mensaje , longitud del mensjae  , a que ip y puerto destino.
        socket.send(paquete);

         //////////////////////// recibe datos del servidor//////////////

        byte[] receiveData = new byte[1024];
        paquete = new DatagramPacket(receiveData,receiveData.length);//
        socket.receive(paquete);
        String msnIN = new String(paquete.getData());
        text_Servidor.append("- "+msnIN +"\n");
        socket.close();// cierro socket
    } catch (SocketException e) {
        e.printStackTrace();
       } catch (UnknownHostException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }


    editMsn.setText("");

    ///////////////////////////////////////////////




}

}


