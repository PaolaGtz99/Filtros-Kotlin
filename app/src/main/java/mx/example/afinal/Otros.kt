package mx.example.afinal

import android.graphics.Bitmap
import android.graphics.Color

class Otros(nombre: String, bmp: Bitmap) {
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

        if(tipo == "Flip Horizontal") flipH()
        if(tipo == "Flip Vertical") flipV()
        if(tipo == "Espejo Horizontal") espejoH()
        if(tipo == "Espejo Vertical") espejoV()
        if(tipo == "Pixeleado") pixeleado()
        if(tipo == "Sepia") sepia()
        if(tipo == "Opacidad") opacidad()
        return nuevo
    }

    //FILTRO "FLIP HORIZONTAL"---------------------------------------------------------
    fun flipH(){
        var k = ancho

        while(j < alto ){
            i = 0
            k = ancho - 1
            while(i < ancho){
                pixel = bitmap.getPixel(k,j)
                //a = Color.alpha(pixel)
                r = Color.red(pixel)
                g = Color.green(pixel)
                b = Color.blue(pixel)
                nuevo.setPixel(i , j , Color.rgb(r,g,b))
                i++
                k--
            }
            j++
        }
    }

    //FILTRO "FLIP VERTICAL"---------------------------------------------------------
    fun flipV(){
        var k = ancho
        while(j < ancho ){
            i = 0
            k = alto - 1
            while(i < alto){
                pixel = bitmap.getPixel(j,k)
                //a = Color.alpha(pixel)
                r = Color.red(pixel)
                g = Color.green(pixel)
                b = Color.blue(pixel)
                nuevo.setPixel(j , i ,Color.rgb(r,g,b))
                i++
                k--
            }
            j++
        }
    }

    //FILTRO "ESPEJO HORIZONTAL"---------------------------------------------------------
    fun espejoH(){
        var k = alto-1

        while(j < alto/2 ){
            i = 0
            while(i < ancho){
                pixel = bitmap.getPixel(i,j)
                //a = Color.alpha(pixel)
                r = Color.red(pixel)
                g = Color.green(pixel)
                b = Color.blue(pixel)
                nuevo.setPixel(i , j ,Color.rgb(r,g,b))
                nuevo.setPixel(i , k ,Color.rgb(r,g,b))
                i++

            }
            k--
            j++
        }
    }

    //FILTRO "ESPEJO VERTICAL"---------------------------------------------------------
    fun espejoV(){
        var k = ancho-1

        while(j < alto ){
            i = ancho/2
            k = ancho/2-1

            while(i < ancho){
                pixel = bitmap.getPixel(i,j)
                //a = Color.alpha(pixel)
                r = Color.red(pixel)
                g = Color.green(pixel)
                b = Color.blue(pixel)
                nuevo.setPixel(i , j ,Color.rgb(r,g,b))
                nuevo.setPixel(k , j ,Color.rgb(r,g,b))
                k--
                i++

            }
            j++
        }
    }

    //FILTRO "PIXELEADO"--------------------------------------------------------------
    fun pixeleado(){
        var k = ancho
        val tamPx = 70
        var contPx = 0

        while(j < alto ){
            i = 0
            while(i < ancho){

                if(contPx==tamPx || i == 0){
                    pixel = bitmap.getPixel(i,j)
                    //a = Color.alpha(pixel)
                    r = Color.red(pixel)
                    g = Color.green(pixel)
                    b = Color.blue(pixel)
                    contPx = 0
                }
                nuevo.setPixel(i , j ,Color.rgb(r,g,b))
                contPx++
                i++
            }
            j++
        }
    }

    //FILTRO "SEPIA"------------------------------------------------------------------
    fun sepia(){
        while(j < alto ){
            i = 0
            while(i < ancho){
                pixel = bitmap.getPixel(i,j)
                //a = Color.alpha(pixel).toFloat()
                r = ((0.393F * Color.red(pixel))+(0.769F * Color.green(pixel))+(0.189 * Color.blue(pixel))).toInt()
                g = ((0.349F * Color.red(pixel))+(0.686F * Color.green(pixel))+(0.168 * Color.blue(pixel))).toInt()
                b = ((0.272F * Color.red(pixel))+(0.534F * Color.green(pixel))+(0.131 * Color.blue(pixel))).toInt()
                if (r < 0) r = 0
                if (r > 255) r = 255
                if (g < 0) g = 0
                if (g > 255) g = 255
                if (b < 0) b = 0
                if (b > 255) b = 255
                nuevo.setPixel(i , j , Color.rgb(r,g,b))
                i++
            }
            j++
        }
    }

    //FILTRO "OPACIDAD"----------------------------------------------------------------
    fun opacidad(){
        while(j < alto ){
            i = 0
            while(i < ancho){
                pixel = bitmap.getPixel(i,j)
                r = ((.50F * Color.red(pixel)) + (1 - .50) * 255).toInt()
                g = ((.50F * Color.green(pixel)) + (1 - .50) * 255).toInt()
                b = ((.50F * Color.blue(pixel)) + (1 - .50) * 255).toInt()
                if (r < 0) r = 0
                if (r > 255) r = 255
                if (g < 0) g = 0
                if (g > 255) g = 255
                if (b < 0) b = 0
                if (b > 255) b = 255
                nuevo.setPixel(i , j ,Color.rgb(r,g,b))
                i++

            }
            j++
        }
    }
}