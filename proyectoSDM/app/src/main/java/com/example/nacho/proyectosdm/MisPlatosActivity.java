package com.example.nacho.proyectosdm;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nacho.proyectosdm.R;
import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;
import com.example.nacho.proyectosdm.persistence.utils.MyDBHelper;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


public class MisPlatosActivity extends AppCompatActivity {

    SubirPlatoActivity id = new SubirPlatoActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_platos);
    }



    // OBTIENES LA IMAGEN DEL ID DE LA TABLA
    public Bitmap buscarImagen(Long id){

        MyDBHelper conn = new MyDBHelper(this, "chefya.db", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        String sql = String.format("SELECT * FROM" + Esquemas.TABLA_COMIDA+ " WHERE id = %d", id);
        Cursor cursor = db.rawQuery(sql, new String[] {});
        Bitmap bitmap = null;
        if(cursor.moveToFirst()){
            byte[] blob = cursor.getBlob(1);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            bitmap = BitmapFactory.decodeStream(bais);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return bitmap;
    }
}
