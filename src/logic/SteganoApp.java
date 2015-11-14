/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//i -> jaa rha h
//  ^
//j | jaa rha h

package logic;

import java.awt.image.BufferedImage;
/**
 *
 * @author Nitin
 */
public class SteganoApp {
    private int hashFun(int in,int mod){
        try {
            double i=in;
            i=(((i*i))%mod);
            in=(int)i;
        } catch (Exception e) {
            System.out.println(e);
        }
        return in;
    }
    private int collision(int in,int mod){
        try {
            double i=in;
            i=((i+11)% mod);
            in=(int)i;
        } catch (Exception e) {
            System.out.println(e);
        }
        return in;
    }
    private BufferedImage LSBRed(BufferedImage img){
        try {
            int i=0,j=0,pixel;
            for(i=0;i<img.getHeight();i++){
                for(j=0;j<img.getWidth();j++){
                    pixel = img.getRGB(j,i);
                    pixel=pixel & 0xFFFEFFFF;
                    img.setRGB(j, i, pixel);
                }
            }            
        } catch (Exception e) {
            System.out.println(e);
        } 
        return img;
    }//11111111000101100010100000000000
    private BufferedImage storeLength(String in, BufferedImage img){
        try {
            int len=in.length(),i,pixel,mask;
                System.out.println("len="+len);
//            System.out.println("get length:"+len);
            for(i=0;i<4;i++){
                pixel = img.getRGB(0,i);
                //System.out.println("get length:"+len);
//                System.out.println("get length:0 and" +i+"is pixel: "+pixel+"and rbg is:"+img.getRGB(0, i));
                pixel=pixel & 0xFFFFFF00;
//                System.out.println("Hell length:0 and" +i+"is pixel: "+pixel);
                mask=0;
                mask=len & 0x000000FF;
//                System.out.println("get length:0 and" +i+"is mask: "+mask);
//                System.out.println(len);
                len=len>>8;
                //System.out.println(len);
                pixel=pixel | mask;
                pixel=pixel | 0x00010000;
                
                img.setRGB(0, i, pixel);
               // System.out.println("after length:0 and" +i+"is pixel: "+pixel+"and rbg is:"+img.getRGB(0, i));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return img;
    }
    private BufferedImage textInImage(char in, BufferedImage image,int imgi,int imgj){
        try {
//                System.out.println("I'm Enter Here ");
                int blue=0,green=0,bytein,pixel,mask;
             //   System.out.println(len);
                bytein=(int)in;
                blue= bytein & 0x0000003F;
                green=bytein;
                green=green >>6;
                green= green & 0x0000003;
                mask=0;
                mask=(green<<8) | blue;
                pixel = image.getRGB(imgj,imgi);
                pixel=pixel & 0xFFFFFCC0;
                pixel=pixel | mask;
                pixel=pixel | 0x00010000;
                image.setRGB(imgj, imgi, pixel);
//                System.out.println("I'm Done Here ");
                return image;
        }
	catch(Exception ex) {
            System.out.println(ex);
        }
        return image;
    }
    public BufferedImage insertText(String in, BufferedImage img){
        try {
            img=LSBRed(img);
//            System.out.println("in insertText");
            img=storeLength(in, img);
//            System.out.println("length stored");
            int i,imgi=3,imgj=4,pixel;
            for(i=0;i<in.length();i++){
//                System.out.println("in for");
                imgi=hashFun(imgi, img.getHeight());
                imgj=hashFun(imgj, img.getWidth());
                
//                System.out.println(imgi+"j is"+imgj);
                pixel=img.getRGB(imgj, imgi);
                while((pixel & 0x00010000)!=0){
//                    System.out.println("in while");
                    imgi=collision(imgi, img.getHeight());
                    imgj=collision(imgj, img.getWidth());
                    pixel=img.getRGB(imgj, imgi);
//                    System.out.println("end of while"+imgi+"j is:"+imgj);
                }
//                System.out.println(in.charAt(i)+"is at i:"+imgi+"and j is"+imgj);
                img=textInImage(in.charAt(i), img, imgi, imgj);
//                System.out.println("after text in image");
            }
        } catch (Exception e) {
            System.out.println(e+"in insert text");
        }
        return img;
    }
    private int getLength(BufferedImage img){
        int len=0;
        try {
            int i=0,pixel;
            for(i=3;i>=0;i--){
                pixel = img.getRGB(0,i);
//                System.out.println("Before length:0 and" +i+"is pixel: "+pixel);
                len=len<<8;
                len=len | (pixel & 0x000000FF);
//                System.out.println("get length:0 and" +i+"is pixel: "+pixel);
            }
            return len;
        } catch (Exception e) {
            System.out.println(e);
        }
        return len;
    }
    private BufferedImage resetLength(BufferedImage img){
        try {
            int i,pixel;
            for(i=3;i>=0;i--){
                pixel = img.getRGB(0,i);
                pixel=pixel & 0xFFFEFFFF;
                img.setRGB(0, i, pixel);
            }
        } catch (Exception e) {
        }
        return img;
    }
    public String retrieveText(BufferedImage img){
        String str="";
        try {
            int len;
//            System.out.println("getting length");
            len=getLength(img);
            System.out.println("got length"+len);
            img=resetLength(img);
//            System.out.println("length reset");
            int i,imgi=3,imgj=4,pixel;
            for(i=0;i<len;i++){
//                System.out.println("in for");
                imgi=hashFun(imgi, img.getHeight());
                imgj=hashFun(imgj, img.getWidth());
                pixel=img.getRGB(imgj, imgi);
//                System.out.println("before while");
                while((pixel & 0x00010000)==0){
//                    System.out.println("in while");
                    imgi=collision(imgi, img.getHeight());
                    imgj=collision(imgj, img.getWidth());
                    pixel=img.getRGB(imgj, imgi);
//                    System.out.println("end while"+imgi+"j is:"+imgj);
                }
//                System.out.println(str+"is at i:"+imgi+"and j is"+imgj);
                str=str+textOutImage(img, imgi, imgj);
//                System.out.println("after textoutimage");
                resetPixel(img, imgi, imgj);
//                System.out.println("after reset");
            }
        } catch (Exception e) {
            System.out.println(e+"in ret text");
        }
        return str;
    }
    private BufferedImage resetPixel(BufferedImage img,int imgi,int imgj){
        try {
            int pixel=0;
            pixel=img.getRGB(imgj, imgi);
            pixel=pixel & 0xFFFEFFFF;
            img.setRGB(imgj, imgi, pixel);
        } catch (Exception e) {
            System.out.println(e+"in reset");
        }
        return img;
    }
    private char textOutImage(BufferedImage image,int imgi,int imgj){
        char ch=0;
        try {      
            int pixel,ascii=0,mask=0;
//            char ch;
        //System.out.println(len);
            pixel = image.getRGB(imgj,imgi);
            ascii = pixel;
            //System.out.println(ascii);
            ascii = ascii>>8;
            //System.out.println(ascii);
            ascii = ascii & 0x00000003;
            //System.out.println(ascii);
            ascii = ascii<<6;
            //System.out.println(ascii);
            mask= pixel & 0x0000003F;
            ascii = ascii | mask;
            //System.out.println(ascii);
            ch=(char)ascii;
            //System.out.println(ascii);
            //System.out.println("end of one loop");
//            System.out.println("I'm Here Decode Phase\n"+str);
            return ch;
        } catch (Exception e) {
            System.out.println(e+"text_out_image");
        }
        return ch;
    }
}
