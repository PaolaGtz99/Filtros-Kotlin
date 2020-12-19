package mx.example.afinal

import android.graphics.Bitmap
import android.graphics.Color

class Basicos(nombre: String, bmp: Bitmap) {
    val bitmap = bmp
    val tipo = nombre
    val ancho = bmp.width
    val alto = bmp.height
    var i = 0
    var j = 0
    var a: Int = 0
    var r : Int = 0
    var g : Int = 0
    var b : Int = 0
    var pixel: Int = 0
    var nuevo = Bitmap.createBitmap(bmp.width,bmp.height,bmp.config)

    //DEPENDIENDO DEL NOMBRE NOS LLEVA A LA FUNCION QUE CORRESPONDA A APLICAR EL FITRO
   fun seleccion():Bitmap{

        if(tipo == "Inversión o negativo") inversion()
        if(tipo == "Escala de grises") escalaGrises()
        if(tipo == "Brillo") brillo()
        if(tipo == "Contraste") contraste()
        if(tipo == "Gamma") gamma()
        if(tipo == "Separación de canales (rojo)") separacionCanales("rojo")
        if(tipo == "Separación de canales (verde)") separacionCanales("verde")
        if(tipo == "Separación de canales (azul)") separacionCanales("azul")
        return nuevo

   }

    // FILTRO "INVERSION" -------------------------------------------------
    fun inversion() {

        while(j < alto ){
            i = 0
            while(i < ancho){
                pixel = bitmap.getPixel(i,j)
                //a = Color.alpha(pixel)
                r = 255 - Color.red(pixel)
                g = 255 - Color.green(pixel)
                b = 255 - Color.blue(pixel)
                nuevo.setPixel(i , j , Color.rgb(r,g,b))
                i++
            }
            j++
        }
    }

    //FILTRO ESCALA DE GRISES -----------------------------------------------
    fun escalaGrises(){

        while(j < alto ){
            i = 0
            while(i < ancho){

                pixel = bitmap.getPixel(i,j)
                //a = Color.alpha(pixel).toFloat()
                r = (0.299 * Color.red(pixel)).toInt()
                g = ( 0.587 * Color.green(pixel)).toInt()
                b = (0.114 * Color.blue(pixel)).toInt()
                nuevo.setPixel(i , j , Color.rgb(r+g+b,r+g+b,r+g+b))
                i++
            }
            j++
        }
    }

    //FILTRO BRILLO ----------------------------------------------------------
    fun brillo(){

        while(j < alto ){
            i = 0
            while(i < ancho){
                pixel = bitmap.getPixel(i,j)
                // a = Color.alpha(pixel).toFloat()
                r = Color.red(pixel) +50
                g = Color.green(pixel)+50
                b = Color.blue(pixel)+50
                if(r<0) r = 0
                if(r>255) r = 255
                if(g<0) g = 0
                if(g>255) g = 255
                if(b<0) b = 0
                if(b>255) b = 255
                nuevo.setPixel(i , j , Color.rgb(r,g,b))
                i++
            }
            j++
        }
    }

    //FILTRO CONTRASTE -------------------------------------------------------

    fun contraste(){
        //nivContraste tiene que estar entre -100 y 100, si no, no se podria perder la imagen
        var nivContraste = 40
        var contraste: Float = (100.0F+nivContraste)/100.0F
        contraste = contraste* contraste

        while(j < alto ) {
            i = 0
            while (i < ancho) {
                pixel = bitmap.getPixel(i, j)
                //a = Color.alpha(pixel)
                r = (((((Color.red(pixel) / 255.0F) - .5F) * contraste) + .5F) * 255.0F).toInt()
                g = (((((Color.green(pixel) / 255.0F) - .5F) * contraste) + .5F) * 255.0F).toInt()
                b = (((((Color.blue(pixel) / 255.0F) - .5F) * contraste) + .5F) * 255.0F).toInt()

                if (r < 0) r = 0
                if (r > 255) r = 255
                if (g < 0) g = 0
                if (g > 255) g = 255
                if (b < 0) b = 0
                if (b > 255) b = 255

                nuevo.setPixel(i, j, Color.rgb(r, g, b))
                i++
            }
            j++
        }
    }

    //FILTRO GAMMA -----------------------------------------------------------
    fun gamma(){
        while(j < alto ){
            i = 0
            while(i < ancho) {
                pixel = bitmap.getPixel(i, j)

                r = ((Math.pow(Color.red(pixel)/255.0f.toDouble(),1/2.5F.toDouble()))*255F).toInt()
                g = ((Math.pow(Color.green(pixel)/255.0f.toDouble(),1/2.5F.toDouble()))*255f).toInt()
                b = ((Math.pow(Color.blue(pixel)/255.0f.toDouble(),1/2.5F.toDouble()))*255).toInt()

                if (r < 0) r = 0
                if (r > 255) r = 255
                if (g < 0) g = 0
                if (g > 255) g = 255
                if (b < 0) b = 0
                if (b > 255) b = 255

                nuevo.setPixel(i, j, Color.rgb(r, g,b))
                i++
            }
            j++
        }
    }

    //FILTRO SEPARACION DE CANALES --------------------------------------------------
    fun separacionCanales(color: String){

        while(j < alto ){
            i = 0
            while(i < ancho) {
                pixel = bitmap.getPixel(i, j)
                a = Color.alpha(pixel)
                r = Color.red(pixel)
                g = Color.green(pixel)
                b = Color.blue(pixel)

                if(color == "rojo")
                    nuevo.setPixel(i, j, Color.rgb(r, 0,0))
                if(color == "verde")
                    nuevo.setPixel(i, j, Color.rgb(0, g,0))
                if(color == "azul")
                    nuevo.setPixel(i, j, Color.rgb(0, 0,b))

                i++
            }
            j++
        }
    }
}