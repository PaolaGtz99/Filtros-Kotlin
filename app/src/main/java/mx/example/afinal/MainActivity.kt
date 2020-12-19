package mx.example.afinal

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.jar.Manifest
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null

    var tipo: String = ""
    private lateinit var layIni: LinearLayout
    private lateinit var layEdi: LinearLayout
    private lateinit var elegImg: ElegirImg
    private lateinit var imageView: ImageView
    private lateinit var nombreFiltro: Filtros
    private lateinit var imgBitmap: Bitmap
    private lateinit var original: Bitmap
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layIni = findViewById(R.id.layInicio)
        layEdi = findViewById(R.id.layEditar)
        elegImg = findViewById(R.id.EleImg)
        imageView = findViewById(R.id.imgView)
        nombreFiltro = findViewById(R.id.EleFiltro)
        val btnGo: Button = findViewById(R.id.btnGo)
        val btnOriginal: Button = findViewById(R.id.btnOriginal)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)

        //BOTON PARA QUE APAREZCA EL MODO EDITAR
        btnGo.setOnClickListener {
            layIni.setVisibility(View.GONE);
            layEdi.setVisibility(View.VISIBLE);
        }

        //ZOOM
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        //VOLVER A IMAGEN ORIGINAL
        btnOriginal.setOnClickListener {
            imageView.setImageBitmap(original)
            imgBitmap = (imageView.drawable as BitmapDrawable).bitmap
        }

        //GUARDAR IMAGEN
        btnGuardar.setOnClickListener {
            it.isEnabled = false
            saveImage(imageView.drawable,"appFiltro")
            Toast.makeText(this, "Imagen Guardada" , Toast.LENGTH_SHORT).show()
        }

        //RETORNA SI LA IMAGEN SERA SELECCIONADA DE GALERIA O SE TOMARA CON LA CAMARA
        elegImg.setOnImageListener { eleccion ->
            if(eleccion == "galeria"){
                tipo = "galeria"
                pickImageFromGallery()
            }else{
                tipo = "camara"
                //if system os is Marshmallow or Above, we need to request runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(android.Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                        //permission was not enabled
                        val permission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        //show popup to request permission
                        requestPermissions(permission, PERMISSION_CODE)
                    }
                    else{ //permission already granted
                        openCamera() }
                } else{ //system os is < marshmallow
                    openCamera() }
            }
        }

        //RETORNA EL NOMBRE DEL FITRO QUE SE APLICARA Y EL TIPO (BASICO, CONVOLUCION O OTROS)
        nombreFiltro.setOnFiltroListener { nombre, tipo ->

            if(tipo == "basico" && nombre != "[Seleccion básicos]"){
                var bs = Basicos(nombre, imgBitmap)
                imageView.setImageBitmap(bs.seleccion())
                imgBitmap = (imageView.drawable as BitmapDrawable).bitmap
            }
            else if(tipo == "convolucion" && nombre != "[Seleccion convolucion]"){
                var cv = Convolucion(nombre,imgBitmap)
                imageView.setImageBitmap(cv.seleccion())
                imgBitmap = (imageView.drawable as BitmapDrawable).bitmap
            }
            else if(tipo == "otros" && nombre != "[Seleccion otros...]"){
                var ot = Otros(nombre,imgBitmap)
                imageView.setImageBitmap(ot.seleccion())
                imgBitmap = (imageView.drawable as BitmapDrawable).bitmap
            }
        }


    }

    //GUARDAR IMAGEN EN GALERIA
    private fun saveImage(drawable: Drawable, title:String){

        // Get the bitmap from drawable object
        val bitmap = (drawable as BitmapDrawable).bitmap

        // Save image to gallery
        val savedImageURL = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            title,
            "Image of $title"
        )
    }

    //ZOOM
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(motionEvent)
        return true
    }
    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = max(0.1f, min(scaleFactor, 10.0f))
            imageView.scaleX = scaleFactor
            imageView.scaleY = scaleFactor
            return true
        }
    }

    //TOMAR FOTO CON CAMARA
    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }


    //ELEGIR IMAGEN DE GALERIA
    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    if(tipo == "galeria"){
                        pickImageFromGallery()
                    }

                    else{
                        openCamera()
                    }

                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imageView.setImageURI(data?.data)
            imgBitmap = (imageView.drawable as BitmapDrawable).bitmap
            original = (imageView.drawable as BitmapDrawable).bitmap
        }else if (resultCode == Activity.RESULT_OK){
            //set image captured to image view
            imageView.setImageURI(image_uri)
            imgBitmap = (imageView.drawable as BitmapDrawable).bitmap
            original = (imageView.drawable as BitmapDrawable).bitmap
        }

    }


}