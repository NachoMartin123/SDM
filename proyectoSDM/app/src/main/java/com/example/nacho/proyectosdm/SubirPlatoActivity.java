package com.example.nacho.proyectosdm;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nacho.proyectosdm.modelo.Categoria;
import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;
import com.example.nacho.proyectosdm.persistence.utils.MyDBHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SubirPlatoActivity extends AppCompatActivity {

    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 999;



    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen

    Button  mImageButton;
    ImageView  mImageFoto;
    File fileImagen;
    Bitmap bitmap;


    EditText mtitulo;
    EditText mdescripcion;
    EditText mprecio;
    Spinner mracion;
    EditText mlugar;


    RadioGroup mradioGroup;

    private RadioButton radioButtonDesayuno;
    private RadioButton radioButtonMerienda;
    private RadioButton radioButtonComida;
    private RadioButton radioButtonCena;

    CheckBox mvegetariano;
    CheckBox mceliaco;
    CheckBox msalado;
    CheckBox mdulce;

    private String emailUsuario;



    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_plato);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        //Inicializamos
        init();

        //rellenar spinner
        rellenarSpinner();
        //Permisos
        if(solicitaPermisosVersionesSuperiores()){
            mImageButton.setEnabled(true);
        }else{
            mImageButton.setEnabled(false);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        COD_FOTO);

            }
        }

        emailUsuario = getIntent().getStringExtra("emailUsuario");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void init() {

        radioButtonDesayuno = (RadioButton)findViewById(R.id.radioButtonDesayuno);
        radioButtonMerienda = (RadioButton)findViewById(R.id.radioButtonMerienda);
        radioButtonComida = (RadioButton)findViewById(R.id.radioButtonComida);
        radioButtonCena = (RadioButton)findViewById(R.id.radioButtonCena);


        mImageButton =(Button) findViewById(R.id.btonSubirFoto);
        mImageFoto=(ImageView) findViewById(R.id.imageFoto);

        mtitulo = (EditText) findViewById(R.id.textTitulo);
        mdescripcion = (EditText) findViewById(R.id.textDescripcion);
        mprecio = (EditText)  findViewById(R.id.textPrecio);

        mlugar = (EditText) findViewById(R.id.textLugar);

        mracion =(Spinner) findViewById(R.id.spinnerRaciones);

        mradioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        mvegetariano = (CheckBox) findViewById(R.id.checkBoxVegetariano);
        mceliaco = (CheckBox) findViewById(R.id.checkBoxCeliaco);
        msalado = (CheckBox) findViewById(R.id.checkBoxSalado);
        mdulce = (CheckBox) findViewById(R.id.checkBoxDulce);

    }

    private void rellenarSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerRaciones);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?>
                                               arg0,View arg1,int arg2,long arg3){
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        // Elementos en Spinner
        List<Integer> values = new ArrayList<Integer>();
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(5);
        values.add(6);
        values.add(7);
        values.add(8);
        values.add(9);
        values.add(10);

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
            }


   public void subirPlato (View view){
       try {
           escribirMyDB();
           //consultarMyBD();


       } catch(Exception e) {
           Toast.makeText(getApplicationContext(),"No se ha podido subir el plato" , Toast.LENGTH_SHORT).show();

       }

   }

    public String CategoriaMax() {
        MyDBHelper conn = new MyDBHelper(this, "chefya.db", null, 1);
        String sql = "select * from " + Esquemas.TABLA_COMIDA;
        SQLiteDatabase db = conn.getReadableDatabase();
        List lista1 = new ArrayList();
        Cursor cursor = db.rawQuery(sql, null);
        String id ="";
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(0);
                lista1.add(id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return String.valueOf(lista1);
        return id; //lleva el último valor
    }


    public void  escribirMyDB() {
        if (mtitulo.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Inserte un titulo", Toast.LENGTH_SHORT).show();
        }

        else if (mprecio.getText().toString().length() == 0 ) {
            Toast.makeText(getApplicationContext(), "Inserte un precio valido por racion", Toast.LENGTH_SHORT).show();

        }

        else if (mlugar.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Inserte un punto de venta", Toast.LENGTH_SHORT).show();
        }

        else{

            DdbbDataSource bd = new DdbbDataSource(this);

            Comida comida = new Comida();
            comida.setLatitud(0);
            comida.setLongitud(0);
            comida.setDescripcion(mdescripcion.getText().toString());
            comida.setSalado(msalado.isChecked());
            comida.setRaciones(mracion.getSelectedItemPosition() + 1);
            comida.setEmail_usuario(emailUsuario);
            comida.setDulce(mdulce.isChecked());
            comida.setCeliaco(mceliaco.isChecked());
            comida.setPrecio(Double.parseDouble(mprecio.getText().toString()));
            if (radioButtonDesayuno.isChecked())
                comida.setCategoria(Categoria.DESAYUNO);
            else if (radioButtonCena.isChecked())
                comida.setCategoria(Categoria.CENA);
            else if (radioButtonComida.isChecked())
                comida.setCategoria(Categoria.COMIDA);
            else
                comida.setCategoria(Categoria.MERIENDA);
            comida.setImagen(bitmap);

            bd.insertComida(comida);

            finish();
        }
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream(20480);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

//permisos
    ////////////////
    private void solicitarPermisosManual() {
    final CharSequence[] opciones={"si","no"};
    final android.support.v7.app.AlertDialog.Builder alertOpciones=new android.support.v7.app.AlertDialog.Builder(SubirPlatoActivity.this);//estamos en fragment
    alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
    alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (opciones[i].equals("si")){
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri=Uri.fromParts("package",SubirPlatoActivity.this.getPackageName(),null);
                intent.setData(uri);
                startActivity(intent);
            }else{
                Toast.makeText(SubirPlatoActivity.this,"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        }
    });
    alertOpciones.show();
}



    private boolean solicitaPermisosVersionesSuperiores() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){//validamos si estamos en android menor a 6 para no buscar los permisos
            return true;
        }     //validamos si los permisos ya fueron aceptados
        if((SubirPlatoActivity.this.checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&SubirPlatoActivity.this.checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED){
            return true;
        }
        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)||(shouldShowRequestPermissionRationale(CAMERA)))){
            cargarDialogoRecomendacion();
        }else{
            SubirPlatoActivity.this.requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MIS_PERMISOS);
        }

        return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui
    }

    private void cargarDialogoRecomendacion() {
        android.support.v7.app.AlertDialog.Builder dialogo=new android.support.v7.app.AlertDialog.Builder(SubirPlatoActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SubirPlatoActivity.this.requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MIS_PERMISOS){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){//el dos representa los 2 permisos
                Toast.makeText(SubirPlatoActivity.this,"Permisos aceptados",Toast.LENGTH_SHORT);
                mImageButton.setEnabled(true);
            }
        }else{
            solicitarPermisosManual();
        }
    }

 //OnClick
    public void subirFoto(View view){

        mostrarDialogOpciones();

    }


    private void mostrarDialogOpciones() {
        final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(SubirPlatoActivity.this);
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    abriCamara();
                }else{
                    if (opciones[i].equals("Elegir de Galeria")){
                        Intent intent=new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }


    private void abriCamara() {


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, COD_FOTO);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case COD_SELECCIONA:
                    Uri miPath = data.getData();
                    mImageFoto.setImageURI(miPath);

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(SubirPlatoActivity.this.getContentResolver(), miPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case COD_FOTO:
                    bitmap = (Bitmap)data.getExtras().get("data");
                    break;
            }
            bitmap = redimensionarImagen(bitmap, 600, 800);
            mImageFoto.setImageBitmap(bitmap);
        }
    }


    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto= altoNuevo/alto;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }


    }






}
