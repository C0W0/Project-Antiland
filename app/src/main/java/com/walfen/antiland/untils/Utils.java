package com.walfen.antiland.untils;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    public static String loadFileAsString(InputStream fileInputStream){

        StringBuilder builder = new StringBuilder();

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while((line = br.readLine()) != null){
                String newLine = line + "\n";
                if(!newLine.startsWith("//")){
                    builder.append(newLine);
                }
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();;
        }

        return builder.toString();
    }

    public static String loadFileAsString(String fileName){
        String str = "";
        try {
            str = loadFileAsString(Constants.WORLD_FILES.open(fileName, MODE_PRIVATE));
        }catch (IOException e){
            e.printStackTrace();
        }
        return str;
    }

    public static ArrayList<String> loadFileAsArrayList(InputStream fileInputStream){
        ArrayList<String> lines = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while((line = br.readLine()) != null){
                if(!line.startsWith("//")){
                    lines.add(line);
                }
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();;
        }

        return lines;
    }

    public static ArrayList<String> loadFileAsArrayList(String fileName){
        ArrayList<String> lines = new ArrayList<>();
        try {
            lines = loadFileAsArrayList(Constants.WORLD_FILES.open(fileName, MODE_PRIVATE));
        }catch (IOException e){
            e.printStackTrace();
        }
        return lines;
    }

    public static ArrayList<String> splitString(String string, int substringSize){
        StringBuilder builder = new StringBuilder();
        ArrayList<String> output = new ArrayList<>();
        int size = 0;
        for(String str: string.split("\\s+")){
            if(size >= substringSize){
                output.add(builder.toString());
                builder = new StringBuilder();
            }
            builder.append(str).append(" ");
            size = builder.length();
        }
        output.add(builder.toString());
        return output;
    }

    public static boolean deleteDirectory(File directory){
        File[] dirFiles = directory.listFiles();
        if(dirFiles != null)
            for(File f: dirFiles)
                deleteDirectory(f);
        return directory.delete();
    }

    public static void deleteDirectoryFiles(File directory){
        File[] dirFiles = directory.listFiles();
        if(dirFiles != null)
            for(File f: dirFiles)
                deleteDirectory(f);
    }

    public static void changeOrientation(Handler handler, int orientation){
        Activity mainActivity = (Activity)(handler.getGame().getContext());
        mainActivity.setRequestedOrientation(orientation);
    }

    public static int parseInt(String number){
        try {
            return Integer.parseInt(number);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }

    public static float getDistance(Entity eA, Entity eB){
        return (float)Math.sqrt(Math.pow(eA.getX() - eB.getX(), 2) + Math.pow(eA.getY() - eB.getY(), 2));
    }

    public static float getDistance(Entity e, float x2, float y2){
        return (float)Math.sqrt(Math.pow(e.getX() - x2, 2) + Math.pow(e.getY() - y2, 2));
    }

    public static float getDistance(Point pointA, Point pointB){
        return getDistance(pointA.x, pointA.y, pointB.x, pointB.y);
    }

    public static float getDistance(float x1, float y1, float x2, float y2){
        float a = x2-x1;
        float b = y2-y1;
        return Py.getC(a, b);
    }

    public static float pickNumber(float...nums){
        return nums[(int)(Math.random()*nums.length)];
    }

    public static int vecDir(float f){
        return (int)f/(int)Math.abs(f);
    }

    public static class Py{

        public static float getC(float a, float b){
            return (float) Math.sqrt(a*a+b*b);
        }

        public static float getB(float a, float c){
            return (float) Math.sqrt(c*c-a*a);
        }
    }

    public static RectF convertToRectF(Rect src){
        return new RectF(src.left, src.top, src.right, src.bottom);
    }

    public static class LinearFunction{

        private final float m, b;

        public LinearFunction(float m, float b){
            this.m = m;
            this.b = b;
        }

        public LinearFunction(float x1, float y1, float x2, float y2){
            m = (y1 - y2)/(x1 - x2);
            b = y1 - m*x1;
        }

        public LinearFunction(float x, float y, float m){
            this.m = m;
            b = y - m*x;
        }

        /** Gets the intersection point with another linear function
         *
         * @param otherFunction the other liner function
         * @return an array with size of 2, arr[0] is x and arr[1] is y
         */
        public float[] getIntersection(LinearFunction otherFunction){
            return new float[]{(otherFunction.getB()-b)/(m-otherFunction.getM()),
                    m*(otherFunction.getB()-b)/(m-otherFunction.getM())+b};
        }

        public float findDistance(float x, float y){
            LinearFunction l = new LinearFunction(x, y, -1/m);
            return Py.getC(getIntersection(l)[0] - x, getIntersection(l)[1] - y);
        }

        public float getM() {
            return m;
        }

        public float getB() {
            return b;
        }

        public float getY(float x) {
            return m*x+b;
        }

        public float getX(float y){
            return (y-b)/m;
        }
    }

}
