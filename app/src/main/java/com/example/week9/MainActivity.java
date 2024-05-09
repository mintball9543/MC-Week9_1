package com.example.week9;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button btn[] = new Button[10];
    EditText ed;
    ImageView imageView;
    private final int MY_PERMISSION_REQUEST_STORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView);


        btn[0] = (Button) findViewById(R.id.button1);
        btn[1] = (Button) findViewById(R.id.button2);
        btn[2] = (Button) findViewById(R.id.button3);
        btn[3] = (Button) findViewById(R.id.button4);
        btn[4] = (Button) findViewById(R.id.button5);
        btn[5] = (Button) findViewById(R.id.button6);
        btn[6] = (Button) findViewById(R.id.button7);
        btn[7] = (Button) findViewById(R.id.button8);
        btn[8] = (Button) findViewById(R.id.button9);
        btn[9] = (Button) findViewById(R.id.button10);




        btn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesSaveClick(v);
            }
        });

        btn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesLoadClick(v);
            }

        });
        btn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.setText("");
            }
        });
        btn[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internalSaveClick(v);
            }
        });

        btn[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internalLoadClick(v);
            }
        });

        btn[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                externalSaveClick(v);
            }
        });

        btn[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                externalLoadClick(v);
            }
        });

        btn[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyClick(v);
            }
        });

        btn[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClick(v);
            }
        });

        btn[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showimageClick(v);
            }
        });


        checkPermission();

    }

    public void sharedPreferencesSaveClick(View v) {

        SharedPreferences settings = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", ed.getText().toString());
        editor.commit();
    }

    public void sharedPreferencesLoadClick(View v) {

        SharedPreferences settings = getSharedPreferences("shared", MODE_PRIVATE);
        String str = settings.getString("name", "");
        ed.setText(str);
    }

    public void internalSaveClick(View v) {
        try {
            FileOutputStream outFs = openFileOutput("internal.txt", MODE_PRIVATE);
            String str = ed.getText().toString();
            outFs.write(str.getBytes());
            outFs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void internalLoadClick(View v) {
        try {
            FileInputStream inFs = openFileInput("internal.txt");
            //   FileInputStream inFs=new  FileInputStream(new File(getFilesDir(), "internal.txt"));
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            ed.setText(new String(txt));
        } catch (IOException e) {
        }
    }

    public void externalSaveClick(View v) {


        File myDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/mydir");   //
        myDir.mkdir();

        Log.d("hwang", myDir.toString());

        try {
            FileOutputStream outFs = new FileOutputStream(new File(myDir, "external.txt"));
            String str = ed.getText().toString();
            outFs.write(str.getBytes());
            outFs.close();
            Log.d("hwang", "external save ok");
        } catch (IOException e) {
            Log.d("hwang", "external save error" + e.toString());
        }
    }

    public void externalLoadClick(View v) {
        try {

            File myDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/mydir");
            FileInputStream inFs = new FileInputStream(new File(myDir, "external.txt"));
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            ed.setText(new String(txt));
        } catch (IOException e) {
            Log.d("hwang", "external load error" + e.toString());
        }

    }

    public void copyClick(View v) {
        // internal to external //
/*
        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        try {
            FileInputStream inFs = openFileInput("internal.txt");
            FileOutputStream outFs = new FileOutputStream(new File(strSDpath + "/mydir", "internal.txt"));  //permission
            int c;
            while ((c = inFs.read()) != -1)
                outFs.write(c);

            outFs.close();
            inFs.close();
        } catch (IOException e) {
        }
        */
        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        final String strSDpath2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();

        Log.d("hwang", strSDpath + "   public path= " + strSDpath2);
        try {

            InputStream inFs = getResources().openRawResource(R.raw.hanbat);

            FileOutputStream outFs = new FileOutputStream(new File(strSDpath, "hanbatcopy.png"));
            FileOutputStream outFs2 = new FileOutputStream(new File(strSDpath2, "hanbatcopy.png"));
            int c;
            while ((c = inFs.read()) != -1) {
                outFs.write(c);
                outFs2.write(c);
            }

            outFs.close();
            outFs2.close();
            inFs.close();
        } catch (IOException e) {
            Log.d("hwang", "error" + e.toString());
        }

    }


    public void deleteClick(View v) {

        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(strSDpath, "hanbatcopy.png");

        file.delete();


    }

    public void showimageClick(View v) {

        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(strSDpath, "hanbatcopy.png");
        imageView.setImageURI(Uri.fromFile(file));
    }


    private void checkPermission() {

        //Log.d("hwang","check=" + checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_STORAGE);
            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

        } else {
            //Log.e(TAG, "permission deny");
            //writeFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,   permissions,   grantResults);

        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to write the permission.
                    Toast.makeText(this, "Read/Write external storage 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                }

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted,
                    Toast.makeText(this, "권한 승인", Toast.LENGTH_SHORT).show();
                    Log.d("hwang", "Permission granted");

                } else {
                    Log.d("hwang", "Permission deny");
                    // permission denied,
                }
                break;
        }
    }
}