package mx.example.afinal

import android.R.bool
import android.graphics.Bitmap
import android.graphics.Color


class Convolucion(nombre: String, bmp: Bitmap) {
    val bitmap = bmp
    val tipo = nombre
    val ancho = bmp.width
    val alto = bmp.height
    var i = 0
    var j = 1
    var nuevo = Bitmap.createBitmap(bmp.width,bmp.height,bmp.config)

    fun seleccion():Bitmap{
        if(tipo == "Smoothing") convertir(arrayOf(1,1,1,1,-6,1,1,1,1),2,0)
        if(tipo == "Gaussian Blur") convertir(arrayOf(1,2,1,2,4,2,1,2,1),16,0)
        if(tipo == "Sharpen") convertir(arrayOf(0,-2,0,-2,11,-2,0,-2,0),3,0)
        if(tipo == "Mean Removal") convertir(arrayOf(-1,-1,-1,-1,9,-1,-1,-1,-1),1,0)
        if(tipo == "Embossing") convertir(arrayOf(-1,0,-1,0,4,0,-1,0,-1),1,127)
        if(tipo == "Edge Detection") convertir(arrayOf(1,1,1,0,0,0,-1,-1,-1),1,127)
        return nuevo
    }

    fun convertir(matriz: Array<Int>, div: Int, suma: Int){

        while(j < alto - 1 ){
            i = 1
            while(i < ancho - 1){

                //TOMAMOS LA MATRIZ 3X3 DE PIXELES QUE SE USARA EN LOS CALCULOS
                var p1 =  bitmap.getPixel(i-1,j-1)
                var p2 =  bitmap.getPixel(i,j-1)
                var p3 =  bitmap.getPixel(i+1,j-1)
                var p4 =  bitmap.getPixel(i-1,j)
                var p5 =  bitmap.getPixel(i,j)
                var p6 =  bitmap.getPixel(i+1,j)
                var p7 =  bitmap.getPixel(i-1,j+1)
                var p8 =  bitmap.getPixel(i,j+1)
                var p9 =  bitmap.getPixel(i+1,j+1)

                //SE SUMA LA MULTIPLICACION DE LOS COLORES POR LA MATRIZ ENVIADA
                // Y SE DIVIDE Y SE LE SUMA LOS VALORES CORRESPONDIENTES SEGUN EL EFECTO
                //ROJO-----
                var sumaR =( Color.red(p1)*matriz[0])+( Color.red(p2)*matriz[1])+( Color.red(p3)*matriz[2])+( Color.red(p4)*matriz[3])
                +( Color.red(p5)*matriz[4])+( Color.red(p6)*matriz[5])+( Color.red(p7)*matriz[6])+( Color.red(p8)*matriz[7])+( Color.red(p9)*matriz[8])
                sumaR = ((sumaR/div )+suma)

                //VERDE----
                var sumaG =( Color.green(p1)*matriz[0])+( Color.green(p2)*matriz[1])+( Color.green(p3)*matriz[2])+( Color.green(p4)*matriz[3])
                +( Color.green(p5)*matriz[4])+( Color.green(p6)*matriz[5])+( Color.green(p7)*matriz[6])+( Color.green(p8)*matriz[7])+( Color.green(p9)*matriz[8])
                sumaG = ((sumaG/div )+suma)

                //AZUL----
                var sumaB =(Color.blue(p1)*matriz[0])+( Color.blue(p2)*matriz[1])+( Color.blue(p3)*matriz[2])+( Color.blue(p4)*matriz[3])
                +( Color.blue(p5)*matriz[4])+( Color.blue(p6)*matriz[5])+( Color.blue(p7)*matriz[6])+( Color.blue(p8)*matriz[7])+( Color.blue(p9)*matriz[8])
                sumaB = ((sumaB/div )+suma)

                if (sumaR < 0) sumaR = 0
                if (sumaR > 255) sumaR = 255
                if (sumaG< 0) sumaG = 0
                if (sumaG > 255) sumaG = 255
                if (sumaB < 0) sumaB = 0
                if (sumaB > 255) sumaB = 255

                //AÑADIMOS NUEVO COLOR
                nuevo.setPixel(i , j , Color.rgb(sumaR,sumaG,sumaB))
                i++

            }
            j++
        }
    }

}