package com.example.nacho.proyectosdm;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.utils.CustomList;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;

import java.util.List;

import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class PlatosCercaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SeleccionExtrasDialog.OnSeleccionExtrasRealizada{//,
        //Callback {

    private ActionBarDrawerToggle mDrawerToggle;
    FragmentTransaction fragmentTransaction;

    private boolean[] extraSeleccionados = {true, true, true, true};

    DdbbDataSource datos;//acceso a datos bbdd
    private Usuario usuarioActual=null;
    private List<Comida> comidasUsuario;

    ListView list;//contiene la lista de comidas



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platoscerca);

        final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.drawer_open, R.string.drawer_close
        );

        drawerLayout.addDrawerListener(mDrawerToggle);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();//NO TOCAR

        datos = new DdbbDataSource(getApplicationContext());

        String emailUsuarioActual = getIntent().getExtras().getString("emailUsuario");
        usuarioActual = datos.getUserByEmail(emailUsuarioActual);
        comidasUsuario = datos.getComidasNoUsuario(emailUsuarioActual);

        actualizarHeader();
        inicializarListaComidas(comidasUsuario);

        //SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
        //        .findFragmentById(R.id.mapa);
        //mapFragment.getMapAsync(this);
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

        CustomList adapter = new CustomList(PlatosCercaActivity.this, titulos,raciones,precios,salado, dulce, celiaco, vegetariano,imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(PlatosCercaActivity.this, "You Clicked at " +position, Toast.LENGTH_SHORT).show();
                Log.i(null,"aaaaaaaaaaa entro en listener");
                if (position == 1) {
                    //code specific to first list item
                    Log.i(null,"entro en listener");
                }

            }
        });


    }


    private void actualizarHeader(){
        //actualizar campos header, nombre y foto de usuario
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nombreHeader = (TextView)hView.findViewById(R.id.nomUserHeader);
        TextView emailHeader = (TextView)hView.findViewById(R.id.emailUserHeader);
        ImageView imgHeader = (ImageView)hView.findViewById(R.id.imgUserHeader);

        nombreHeader.setText(usuarioActual.getNombre());
        emailHeader.setText(usuarioActual.getEmail());
        if(usuarioActual.getImagen()!=null)
            imgHeader.setImageBitmap(usuarioActual.getImagen().getDrawingCache());

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id) {
            case R.id.nav_subirplato:
                lanzarNavActivity(SubirPlatoActivity.class);
                break;
            case R.id.nav_mensajes:
                lanzarNavActivity(MensajesActivity.class);
                break;
            case R.id.nav_misPlatos:
                lanzarNavActivity(MisPlatosActivity.class);
                break;
            case R.id.nav_configuracion:
                lanzarNavActivity(ConfiguracionActivity.class);
                break;
            case R.id.nav_misReservas:
                lanzarNavActivity(MisReservasActivity.class);
                break;
            case R.id.nav_acerda_de:
                //startActivity(intento);
                Toast.makeText(PlatosCercaActivity.this, "Creadores: Ana, Laura y Nacho\n SDM 207/18 Uniovi", Toast.LENGTH_SHORT).show();
                break;

        }

        return true;
    }

    private void lanzarNavActivity(Class clase){
        Intent intento = new Intent(PlatosCercaActivity.this, clase);
        intento.putExtra("emailUsuario", usuarioActual.getEmail());
        startActivity(intento);
    }



    //metodos menu
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        //Inflate the menu
        getMenuInflater().inflate(R.menu.barramenu_platoscerca, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item)) {//opens the drawer
            return true;
        }
        else if (item.getItemId() == R.id.itemExtras) {
            DialogFragment dialogo = SeleccionExtrasDialog.crear(extraSeleccionados);
            dialogo.show(getSupportFragmentManager(), "dialogoExtras");
        }
        else {
            item.setChecked(!item.isChecked());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSeleccionExtraRealizada(boolean[] seleccion) {
        this.extraSeleccionados = seleccion;
    }

    /*
    @Override
    public void onMapReady(GoogleMap googleMap) {

        DdbbDataSource bd = new DdbbDataSource(this);

        List<Comida> comidas = bd.getComidasUsuario("a");

        LatLngBounds.Builder creadorRango = LatLngBounds.builder();
        for (Comida comida: comidas) {
            LatLng posicion = comida.crearPosicion();
            googleMap.addMarker(new MarkerOptions()
                    .position(posicion)
                    .title(comida.getDescripcion()));
            creadorRango.include(posicion);
        }

        googleMap.moveCamera(CameraUpdateFactory
            .newLatLngBounds(creadorRango.build(), 100));
    }
    */
}
