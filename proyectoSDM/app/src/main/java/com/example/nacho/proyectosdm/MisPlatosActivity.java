package com.example.nacho.proyectosdm;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nacho.proyectosdm.R;
import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;
import com.example.nacho.proyectosdm.persistence.utils.CustomList;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;
import com.example.nacho.proyectosdm.persistence.utils.MyDBHelper;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


public class MisPlatosActivity extends AppCompatActivity {


    DdbbDataSource datos;//acceso a datos bbdd
    private Usuario usuarioActual=null;
    private List<Comida> comidasUsuario;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_platos);

        datos = new DdbbDataSource(getApplicationContext());

        String emailUsuarioActual = getIntent().getExtras().getString("emailUsuario");
        usuarioActual = datos.getUserByEmail(emailUsuarioActual);
        comidasUsuario = datos.getComidasUsuario(emailUsuarioActual);

        inicializarListaComidas(comidasUsuario);

    }


    private void inicializarListaComidas(List<Comida> comidas){

        String[] titulos = new String[comidas.size()];
        Integer[] imageId = new Integer[comidas.size()];
        Integer[] raciones = new Integer[comidas.size()];
        Double[] precios = new Double[comidas.size()];
        Boolean[] salado = new Boolean[comidas.size()];
        Boolean[] dulce = new Boolean[comidas.size()];
        Boolean[] celiaco = new Boolean[comidas.size()];
        Boolean[] vegetariano = new Boolean[comidas.size()];

        for(int i=0;i<comidas.size();i++){
            titulos[i]=comidas.get(i).getTitulo();
            imageId[i]=i;//comidas.get(i).getId()
            raciones[i]=comidas.get(i).getRaciones();
            precios[i]=comidas.get(i).getPrecio();
            salado[i] = comidas.get(i).isSalado();
            dulce[i] = comidas.get(i).isDulce();
            celiaco[i] = comidas.get(i).isCeliaco();
            vegetariano[i] = comidas.get(i).isVegetariano();
        }

        CustomList adapter = new CustomList(MisPlatosActivity.this, titulos,raciones,precios,salado, dulce, celiaco, vegetariano,imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MisPlatosActivity.this, "You Clicked at " +position, Toast.LENGTH_SHORT).show();

            }
        });

    }



    // OBTIENES LA IMAGEN DEL ID DE LA TABLA
    public Bitmap buscarImagen(Long id){

        MyDBHelper conn = new MyDBHelper(this, "chefya.db", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        String sql = String.format("SELECT * FROM" + Esquemas.TABLA_COMIDA+ " WHERE id = %d", id);
        Cursor cursor = db.rawQuery(sql, new String[] {});
        /*
        cursor = db.query(
                Esquemas.TABLA_COMIDA,
                new String [] {
                    "*"
                },
                "id = ?",
                new String [] {
                   id.toString()
                },
                null, null, null);
        */
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
