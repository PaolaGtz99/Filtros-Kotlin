package mx.example.afinal

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*

class Filtros: LinearLayout{

    constructor(ctx: Context): super (ctx){
        inicializar()
    }
    constructor(ctx: Context, attrs: AttributeSet): super (ctx, attrs){
        inicializar()
    }
    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int): super (ctx, attrs, defStyleAttr){
        inicializar()
    }
    private lateinit var spinnerNom: Spinner
    private lateinit var btnBasico: Button
    private lateinit var btnConv: Button
    private lateinit var btnOtros: Button
    private var tipo = ""

    fun inicializar(){
        //OBTENEMOS INTERFAZ
        LayoutInflater.from(context).inflate(R.layout.filtros_lay,this,true)

        //Asignando los elementos a las variables
        spinnerNom = findViewById(R.id.spinner1)
        btnBasico = findViewById(R.id.basic)
        btnConv = findViewById(R.id.conv)
        btnOtros = findViewById(R.id.otros)

        eventos()
    }

    fun eventos(){
        var nombres: ArrayAdapter<CharSequence>
        btnBasico.setOnClickListener {
            //CAMBIO DE COLOR DE FONDO SEGUN BOTON SELECCIONADO
            btnBasico.setBackgroundColor(Color.parseColor("#CDDC39"))
            btnConv.setBackgroundColor(Color.parseColor("#E8000000"))
            btnOtros.setBackgroundColor(Color.parseColor("#E8000000"))
            //CAMBIO DE COLOR DE LETRA SEGUN BOTON SELECCIONADO
            btnBasico.setTextColor(Color.parseColor("#0E4C11"))
            btnConv.setTextColor(Color.parseColor("#FFFFFF"))
            btnOtros.setTextColor(Color.parseColor("#FFFFFF"))
            //CAMBIANDO VALORES AL SPINNER SEGUN BOTON SELECCIONADO
            tipo = "basico"
            nombres = ArrayAdapter.createFromResource(context, R.array.filtrosBasicos, android.R.layout.simple_spinner_item)
            nombres.setDropDownViewResource(android.R.layout.simple_spinner_item)

            spinnerNom.adapter = nombres
        }
        btnConv.setOnClickListener {
            //CAMBIO DE COLOR DE FONDO SEGUN BOTON SELECCIONADO
            btnConv.setBackgroundColor(Color.parseColor("#CDDC39"))
            btnBasico.setBackgroundColor(Color.parseColor("#E8000000"))
            btnOtros.setBackgroundColor(Color.parseColor("#E8000000"))
            //CAMBIO DE COLOR DE LETRA SEGUN BOTON SELECCIONADO
            btnConv.setTextColor(Color.parseColor("#0E4C11"))
            btnBasico.setTextColor(Color.parseColor("#FFFFFF"))
            btnOtros.setTextColor(Color.parseColor("#FFFFFF"))
            tipo = "convolucion"
            //CAMBIANDO VALORES AL SPINNER SEGUN BOTON SELECCIONADO
            nombres = ArrayAdapter.createFromResource(context, R.array.filtrosConv, android.R.layout.simple_spinner_item)
            nombres.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinnerNom.adapter = nombres
        }
        btnOtros.setOnClickListener {
            //CAMBIO DE COLOR DE FONDO SEGUN BOTON SELECCIONADO
            btnOtros.setBackgroundColor(Color.parseColor("#CDDC39"))
            btnConv.setBackgroundColor(Color.parseColor("#E8000000"))
            btnBasico.setBackgroundColor(Color.parseColor("#E8000000"))
            ///CAMBIO DE COLOR DE LETRA SEGUN BOTON SELECCIONADO
            btnOtros.setTextColor(Color.parseColor("#0E4C11"))
            btnBasico.setTextColor(Color.parseColor("#FFFFFF"))
            btnConv.setTextColor(Color.parseColor("#FFFFFF"))
            //CAMBIANDO VALORES AL SPINNER SEGUN BOTON SELECCIONADO
            tipo = "otros"
            nombres = ArrayAdapter.createFromResource(context, R.array.filtrosOtros, android.R.layout.simple_spinner_item)
            nombres.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinnerNom.adapter = nombres
        }

        spinnerNom.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //Valor seleccionado en el spinner
                val pos = (p0?.getItemAtPosition(p2)).toString()
                listener?.onFiltro(pos,tipo)

            }
        }

    }
    interface OnFiltroListener {
        fun onFiltro(nombre: String,tipo: String)
    }
    var listener: OnFiltroListener? = null;

    fun setOnFiltroListener(nomFiltro: (String,String)-> Unit){
        listener = object: OnFiltroListener{
            override fun onFiltro(nombre: String, tipo: String) {
                nomFiltro(nombre,tipo)
            }
        }
    }


}