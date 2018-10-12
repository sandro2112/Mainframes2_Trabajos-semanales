/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regresion;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
/**
 *
 * @author user
 */
public class Regresion {
    static double[]x;
    static double[]y;
    static double promx,promy,cv,devx,devy,r;//medias, covarianza, desviaciones y coeficiente de correlación
    static int lmax;
    static double A0, A1, A2;
    static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    public static void lectura()throws IOException{
    
        x=new double[lmax];
        y=new double[lmax];
        for (int i=0 ; i<lmax; i++){
            System.out.print("Ingrese x");
            x[i]=Integer.parseInt(br.readLine());
            System.out.print("Ingrese y");
            y[i]=Integer.parseInt(br.readLine());
        }
    }
    public static void visualizacion()throws IOException{
        for (int i=0; i<lmax; i++){
            System.out.println(x[i]+""+y[i]);
        }
    }
    public static double calculaR(){
        double sx=0,sy=0;
        double sxy=0,sx2=0,sy2=0;
        double[]xy= new double[lmax];
        double[]x2= new double[lmax];
        double[]y2= new double[lmax];
        
        //Generacion de matriz
        for(int i=0; i<lmax; i++){
            xy[i]=x[i]*y[i];
            x2[i]=Math.pow(x[i], 2);
            y2[i]=Math.pow(y[i], 2);
        }
        
        //Calculo de sumatorias
        for(int i=0; i<lmax; i++){
            sx=sx+x[i];
            sy=sy+y[i];
            sxy=sxy+xy[i];
            sx2=sx2+x2[i];
            sy2=sy2+y2[i];
        }
        
        //Calculo de medias
        promx=sx/lmax;
        promy=sy/lmax;
        System.out.println("Media x: "+promx+" Media y: "+promy);
        //Calculo de la Covarianza
        cv=(sxy/lmax)-(promx*promy);
        System.out.println("Covarianza: "+cv);
        //Calculo de desviaciones
        devx=Math.sqrt((sx2/lmax)- (Math.pow(promx,2)));
        devy=Math.sqrt((sy2/lmax)- (Math.pow(promy,2)));
        //Calculo de Coeficiente de Correlacion lineal
        r=cv/(devx*devy);
        return r;
        
    }
       
    public static void poblar(){
        double[]x1={14,7,13,12,16,14,18,13,12,16,13};
        x=x1;
        double[]y1={16,12,13,14,15,12,16,11,13,18,17};
        y=y1;
        lmax=11;
    }
    public static double predecirNX(double ny){
        double nx;
        nx=(cv/Math.pow(devy,2))*(ny-promy)+promx;
        return nx;
    }
    public static double predecirNY(double nx){
        double ny;
        ny=(cv/Math.pow(devx,2))*(nx-promx)+promy;
        return ny;
    }
    
    public static void leerArchivo(String ruta)throws IOException{
        String cadena;
        String []campos;
        short c=0;
        BufferedReader br1;
        br1=new BufferedReader(new FileReader(new File(ruta)));
        while((cadena=br1.readLine())!=null){
            c++;
        }
        lmax=c;
        x = new double[c];
        y = new double[c];
        System.out.println(c);
        c=0;
        br1=new BufferedReader(new FileReader(new File(ruta)));
        while((cadena=br1.readLine())!=null){
            campos=cadena.split(";");
            x[c]=Integer.parseInt(campos[0]);
            y[c]=Integer.parseInt(campos[1]);
            System.out.println(x[c]+" "+ y[c]);
            c++;
        }
    }
    public static double calcularDeterminante(double a1,double b1,double c1,double a2,double b2,double c2,double a3,double b3,double c3){
        double deter;
        deter=(a1*b2*c3+b1*c2*a3+a2*b3*c1)-(c1*b2*a3+b1*a2*c3+c2*b3*a1);
        return deter;
    }
    public static double calculaR_MinCuad(){
        double RD, RDx ,RDy ,RDz;
        double sx=0,sy=0;
        double sxy=0,sx2=0,sx3=0,sx4=0,sx2y=0;
        double[]xy= new double[lmax];
        double[]x2= new double[lmax];
        double[]x2y= new double[lmax];
        double[]x3= new double[lmax];
        double[]x4= new double[lmax];
        
        //Generacion de matriz
        for(int i=0; i<lmax; i++){
            xy[i]=x[i]*y[i];
            x2[i]=Math.pow(x[i], 2);
            x2y[i]=x2[i]*y[i];
            x3[i]=Math.pow(y[i], 3);
            x4[i]=Math.pow(y[i], 4);
        }
        
        //Calculo de sumatorias
        for(int i=0; i<lmax; i++){
            sx=sx+x[i];
            sy=sy+y[i];
            sxy=sxy+xy[i];
            sx2=sx2+x2[i];
            sx3=sx3+x3[i];
            sx4=sx4+x4[i];
            sx2y=sx2y+x2y[i];
        }
        
        //Calculo de determinante
        RD=calcularDeterminante(lmax,sx,sx2,sx,sx2,sx3,sx2,sx3,sx4);
        RDx=calcularDeterminante(sy,sx,sx2,sxy,sx2,sx3,sx2y,sx3,sx4);
        RDy=calcularDeterminante(lmax,sy,sx2,sx,sxy,sx3,sx2,sx2y,sx4);
        RDz=calcularDeterminante(lmax,sx,sy,sx,sx2,sxy,sx2,sx3,sx2y);
        
       //Calculo de a0,a1,a2
       A0=RDx/RD;
       A1=RDy/RD;
       A2=RDz/RD;
       
       return RD;
        
    }
    public static double predecirMinCuad(double mx){
        double my;
        my=A0+A1*mx+A2*Math.pow(mx, 2);;
        return my;
    }
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
        
        //lectura();
        //poblar();
        leerArchivo("D:\\Sandro\\ejemplo.csv");
        //visualizacion();
        System.out.print("El coeficiente de correlación lineal es :"+calculaR());
        System.out.print("El determinante es :"+calculaR_MinCuad());
        System.out.print("Valor de Y predecido con MInCuad es:"+predecirMinCuad(10));
        /*double probarX=13;
        double nuevoY;
        nuevoY=predecirNY(probarX);
        System.out.print("Valor Y que se predice es :"+nuevoY);
        double probarY=17;
        double nuevoX;
        nuevoX=predecirNX(probarY);
        System.out.print("Valor X que se predice es :"+nuevoX);*/
    }
    
}
