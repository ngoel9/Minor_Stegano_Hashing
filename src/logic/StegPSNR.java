/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import static java.lang.System.exit;
import javax.imageio.ImageIO;

/**
 *
 * @author Nitin
 */
public class StegPSNR {
    public double calculatepsnr(BufferedImage img1,BufferedImage img2){
        int i,j,k;
        double errR,errG,errB,errorP,errorT=0,mse=0,psnr=0;
        int w1=img1.getWidth();
        int h1=img1.getHeight();
        int w2=img2.getWidth();
        int h2=img2.getHeight();
        Color c1=null;
        Color c2=null;
        if(w1!=w2)
        {
            System.out.println("error width");
            exit(0);
        }
        if(h1!=h2)
        {
            System.out.println("error width");
            exit(0);
        }
        for(i=0;i<h1;i++){
            for(j=0;j<w1;j++){
                 c1=new Color(img1.getRGB(j,i));
                 c2=new Color(img2.getRGB(j,i));
                 errR=c1.getRed()-c2.getRed();
                 errG=c1.getGreen()-c2.getGreen();
                 errB=c1.getBlue()-c2.getBlue();
                 errorP=(errR*errR)+(errB*errB)+(errG*errG);
                 errorT+=errorP;

                 //System.out.println(errorP+" "+j);
            }
            //System.out.println(i+" "+errorT);
//            System.out.println("out of inner for"+i+" "+j);
        }
//        System.out.println("out of for loops");
//        System.out.println(errorT);
        mse=(errorT)/(w1*h1*3);
        psnr=10*Math.log10((255*255)/mse);
        return psnr;
    }
//    public static void main (String[] args)
//    {
//        try{
//            File f=new File("E:\\b.png");
//            File f1=new File("D:\\a.png");
//            BufferedImage im1=ImageIO.read(f);
//            BufferedImage im2=ImageIO.read(f1);
//            StegPSNR st=new StegPSNR();
//            double result=st.calculatepsnr(im1, im2);
//            //String res=Double.toString(result);
//            System.out.println(""+result);
//        }
//        catch(Exception ex){}
//    }
}
