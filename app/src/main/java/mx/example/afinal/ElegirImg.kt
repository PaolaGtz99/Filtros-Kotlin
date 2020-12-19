package mx.example.afinal

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat

class ElegirImg: LinearLayout {
    constructor(ctx: Context): super (ctx){
        inicializar()
    }
    constructor(ctx: Context, attrs: AttributeSet): super (ctx, attrs){
        inicializar()
    }
    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int): super (ctx, attrs, defStyleAttr){
        inicializar()
    }


    fun inicializar(){
        //OBTENEMOS INTERFAZ
        LayoutInflater.from(context).inflate(R.layout.elegir_imagen,this,true)

        val camara: Button = findViewById(R.id.tomarFoto)
        val galeria: Button = findViewById(R.id.galeria)

        //BOTON PARA ELEGIR FOTO DE LA GALERIA
        galeria.setOnClickListener {
            listener?.onImage("galeria")
        }
        //BOTON PARA ELEGIR TOMAR FOTO
        camara.setOnClickListener {
            listener?.onImage("camara")
        }
    }

    interface OnImageListener {
        fun onImage(imagen: String)
    }
    var listener: OnImageListener? = null;

    fun setOnImageListener(eleccion: (String)-> Unit){
        listener = object: OnImageListener{
            override fun onImage(imagen: String) {
                eleccion(imagen)
            }
        }
    }


}