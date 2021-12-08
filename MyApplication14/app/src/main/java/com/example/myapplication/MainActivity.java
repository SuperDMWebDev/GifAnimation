package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                    .requestPermissions(
                            (Activity) MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            123);
        } else {
            Bitmap b= BitmapFactory.decodeResource(getResources(),R.drawable.a3);
            Bitmap c= BitmapFactory.decodeResource(getResources(),R.drawable.a2);
            bitmaps.add(b);
            bitmaps.add(c);
            convertGift(bitmaps,1);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
        convertGift(bitmaps,1);

    }
    public String convertGift(ArrayList<Bitmap> bitmaps, int duration)
    {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        File outputFile=new File(path,"test.gif");
        try {

            try{
                if(!path.isDirectory())
                {
                    path.mkdir();
                }
                outputFile.createNewFile();
            }catch (Exception u)
            {
                u.printStackTrace();
            }
            FileOutputStream outStream = new FileOutputStream(outputFile);
//This is storage path of gif
            outStream.write(generateGIF(bitmaps,duration));
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile.getAbsolutePath();
    }


    public byte[] generateGIF(ArrayList<Bitmap> bitmaps ,int duration ) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        // set thoi gian cho moi Gif
        encoder.setDelay(duration);
        //set frame cho Gif
        encoder.setSize(500,500);
        encoder.start(bos);
        // chinh chat luong anh
        encoder.setQuality(10);
        for (Bitmap bitmap : bitmaps) {
            encoder.addFrame(bitmap);
        }
        encoder.finish();
        return bos.toByteArray();
    }
}