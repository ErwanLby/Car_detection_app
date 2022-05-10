package com.example.monk_controle_scar
import android.annotation.SuppressLint;
import android.os.AsyncTask
import android.os.Bundle;
import android.util.Log
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



open class MainActivity : AppCompatActivity() {


    private var buttonAvancer: Button? = null
    private var buttonReculer: Button? = null
    private var buttonGauche: Button? = null
    private var buttonDroite: Button? = null
    private var buttonOrienterG: Button? = null
    private var buttonOrienterD: Button? = null
    private var message_sortant: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonAvancer = findViewById<View>(R.id.button_avancer) as Button // reference to the send button
        buttonReculer = findViewById<View>(R.id.button_reculer) as Button
        buttonGauche = findViewById<View>(R.id.button_gauche) as Button
        buttonDroite = findViewById<View>(R.id.button_droite) as Button
        buttonOrienterG = findViewById<View>(R.id.button_orienter_gauche) as Button
        buttonOrienterD = findViewById<View>(R.id.button_orienter_droite) as Button
        // Button press event listener
        buttonAvancer!!.setOnClickListener {
            message_sortant = "Avancer"
            val sendMessageTask = SendMessage()
            sendMessageTask.execute()
        }
        buttonReculer!!.setOnClickListener {
            message_sortant = "Reculer"
            val sendMessageTask = SendMessage()
            sendMessageTask.execute()
        }
        buttonGauche!!.setOnClickListener {
            message_sortant = "Gauche"
            val sendMessageTask = SendMessage()
            sendMessageTask.execute()
        }
        buttonDroite!!.setOnClickListener {
            message_sortant = "Droite"
            val sendMessageTask = SendMessage()
            sendMessageTask.execute()
        }
        buttonOrienterG!!.setOnClickListener {
            message_sortant = "Orienter Gauche"
            val sendMessageTask = SendMessage()
            sendMessageTask.execute()
        }
        buttonOrienterD!!.setOnClickListener {
            message_sortant = "Orienter Droite"
            val sendMessageTask = SendMessage()
            sendMessageTask.execute()
        }
    }

    private inner class SendMessage :
        AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            try {

                //Log.d("Connexion", "Demande de connexion");
                var client = Socket("192.168.20.181", 3456) // connect to the server

                //////  ENVOIE D'UN MESSAGE SORTANT ///////
                Log.d("Connexion", "Envoie de A")
                var printwriter = PrintWriter(client.getOutputStream(), true)
                printwriter.write(message_sortant) // write the message to output stream
                printwriter.close()
                printwriter.flush()
                var in_bufferedReader = BufferedReader(InputStreamReader(client.getInputStream()))
                var message_entrant = in_bufferedReader.readLine()
                Log.d("Connexion", message_entrant)
                //client.close()
            } catch (e: Exception) {
            }
            return null
        }
    }


}