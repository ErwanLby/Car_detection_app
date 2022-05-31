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

    private var buttonIp: Button? = null
    private var buttonPort: Button? = null
    private var buttonAvancer: Button? = null
    private var buttonReculer: Button? = null
    private var buttonGauche: Button? = null
    private var buttonDroite: Button? = null
    private var buttonOrienterG: Button? = null
    private var buttonOrienterD: Button? = null
    private var message_sortant: String? = null
    private var PORT: Int = 0
    private var HOST : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonIp = findViewById<View>(R.id.button_ip) as Button // reference to the send button
        buttonPort = findViewById<View>(R.id.button_port) as Button
        buttonAvancer = findViewById<View>(R.id.button_avancer) as Button 
        buttonReculer = findViewById<View>(R.id.button_reculer) as Button
        buttonGauche = findViewById<View>(R.id.button_gauche) as Button
        buttonDroite = findViewById<View>(R.id.button_droite) as Button
        buttonOrienterG = findViewById<View>(R.id.button_orienter_gauche) as Button
        buttonOrienterD = findViewById<View>(R.id.button_orienter_droite) as Button
        
        buttonIp!!.setOnClickListener {
            HOST = findViewById<EditText>(R.id.AdresseIP).text.toString()
        }
        buttonPort!!.setOnClickListener {
            var chainePort = findViewById<EditText>(R.id.Port).text.toString()
            PORT = Integer.parseInt(chainePort) //le port doit etre un int et non un string
        }
        buttonAvancer!!.setOnLongClickListener(View.OnLongClickListener { v -> //si le bouton est maintenu
            message_sortant = "Avancer" //on definit le message
            boutonMaintenu(v) //et on l'envoi en continu
            true
        })
        buttonReculer!!.setOnLongClickListener(View.OnLongClickListener { v ->
            message_sortant = "Reculer"
            boutonMaintenu(v)
            true
        })
        buttonGauche!!.setOnLongClickListener(View.OnLongClickListener { v ->
            message_sortant = "Gauche"
            boutonMaintenu(v)
            true
        })
        buttonDroite!!.setOnLongClickListener(View.OnLongClickListener { v ->
            message_sortant = "Droite"
            boutonMaintenu(v)
            true
        })
        buttonOrienterG!!.setOnLongClickListener(View.OnLongClickListener { v ->
            message_sortant = "Orienter Gauche"
            boutonMaintenu(v)
            true
        })
        buttonOrienterD!!.setOnLongClickListener(View.OnLongClickListener { v ->
            message_sortant = "Orienter Droite"
            boutonMaintenu(v)
            true
        })
        
        
        
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
    
    //Envoi le message_sortant toutes les 100ms tant que le bouton est maintenu
    private fun boutonMaintenu(v: View?) {
        var thread = Thread {
            // TODO Auto-generated method stub
            try {
                while (v!!.isPressed) {
                    val sendMessageTask = SendMessage()
                    sendMessageTask.execute()
                    Thread.sleep(100);
                }
            } catch (e : java.lang.Exception) {}
        }

        thread.start()
        true
    }
    
    //permet d'envoyer le message une fois
    private inner class SendMessage :
        AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            try {

                //Log.d("Connexion", "Demande de connexion");
                var client = Socket(HOST,PORT) //"192.168.20.181", 3456) // connect to the server

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
