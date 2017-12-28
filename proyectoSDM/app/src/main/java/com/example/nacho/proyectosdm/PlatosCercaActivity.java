package com.example.nacho.proyectosdm;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nacho.proyectosdm.modelo.Categoria;
import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.esquemas.Esquemas;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlatosCercaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SeleccionExtrasDialog.OnSeleccionExtrasRealizada {//,
    //Callback {

    private ActionBarDrawerToggle mDrawerToggle;
    FragmentTransaction fragmentTransaction;

    private boolean[] extraSeleccionados = {true, true, true, true};

    DdbbDataSource datos;//acceso a datos bbdd
    private Usuario usuarioActual = null;
    private List<Comida> comidasUsuario;

    ListView list;//contiene la lista de comidas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platoscerca);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

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
        int tam = datos.sizeTable(Esquemas.TABLA_COMIDA);
        comidasUsuario = datos.getComidasNoUsuario(emailUsuarioActual);

        actualizarHeader();
        inicializarListaComidas(comidasUsuario);

        //SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
        //        .findFragmentById(R.id.mapa);
        //mapFragment.getMapAsync(this);
    }


    private void inicializarListaComidas(List<Comida> comidas) {
        if (comidas.size() > 0) {
            String[] titulos = new String[comidas.size()];
            Integer[] raciones = new Integer[comidas.size()];
            Double[] precios = new Double[comidas.size()];
            String[] ciudades = new String[comidas.size()];
            Bitmap[] imagenes = new Bitmap[comidas.size()];

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            for (int i = 0; i < comidas.size(); i++) {
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(comidas.get(i).getLatitud(), comidas.get(i).getLongitud(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String cityName="", stateName="", countryName="";
                if (addresses != null && addresses.size()  != 0) {
                    cityName = addresses.get(0).getAddressLine(0);
                    stateName = addresses.get(0).getAddressLine(1);
                    countryName = addresses.get(0).getAddressLine(2);
                }

                titulos[i] = comidas.get(i).getTitulo();
                raciones[i] = comidas.get(i).getRaciones();
                precios[i] = comidas.get(i).getPrecio();
                ciudades[i] = cityName+", "+stateName+", "+countryName;
                imagenes[i] = comidas.get(i).getImagen();
            }

            CustomList adapter = new CustomList(PlatosCercaActivity.this, titulos, raciones, precios, ciudades, imagenes);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(PlatosCercaActivity.this, "You Clicked at " + position, Toast.LENGTH_SHORT).show();

                    if (position == 1) {
                        //code specific to first list item
                        Log.i(null, "entro en listener");
                    }

                }
            });
        }else{
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(null);
        }

    }


    private void actualizarHeader() {
        //actualizar campos header, nombre y foto de usuario
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView nombreHeader = (TextView) hView.findViewById(R.id.nomUserHeader);
        TextView emailHeader = (TextView) hView.findViewById(R.id.emailUserHeader);
        ImageView imgHeader = (ImageView) hView.findViewById(R.id.imgUserHeader);

        nombreHeader.setText(usuarioActual.getNombre());
        emailHeader.setText(usuarioActual.getEmail());
        if (usuarioActual.getImagen() != null)
            imgHeader.setImageBitmap(Bitmap.createScaledBitmap(usuarioActual.getImagen(), 200, 200, false));

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

        switch (id) {
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

    private void lanzarNavActivity(Class clase) {
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
        } else if (item.getItemId() == R.id.itemExtras) {
            DialogFragment dialogo = SeleccionExtrasDialog.crear(extraSeleccionados);
            dialogo.show(getSupportFragmentManager(), "dialogoExtras");
        } else if (item.getItemId() == R.id.itemDesayunos) {
            filtrarComidas(Categoria.DESAYUNO);
        } else if (item.getItemId() == R.id.itemComidas){
            filtrarComidas(Categoria.COMIDA);
        }else if(item.getItemId()==R.id.itemMeriendas){
            filtrarComidas(Categoria.MERIENDA);
        }else if (item.getItemId() == R.id.itemCenas) {
            filtrarComidas(Categoria.CENA);
        }else if (item.getItemId() == R.id.itemTodos) {
            filtrarComidas(null);
        }else {
            item.setChecked(!item.isChecked());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSeleccionExtraRealizada(boolean[] seleccion) {
        this.extraSeleccionados = seleccion;
    }

    private void filtrarComidas(Categoria categoria){
        if(categoria==null) {
            inicializarListaComidas(comidasUsuario);
        }else {
            List<Comida> comidasFiltradas = new ArrayList<Comida>();
            for (Comida c : comidasUsuario) {
                if (c.getCategoria() == categoria)
                    comidasFiltradas.add(c);
            }
            inicializarListaComidas(comidasFiltradas);
        }
        //resetItemsFiltrar(categoria);

    }

    /**
     * metodo para que deje seleccionado item seleccionado
     * EJ seleccion CENA deje seleccionado CENA
     */
    private void resetItemsFiltrar(Categoria categoria){
        MenuItem itemT = (MenuItem)findViewById(R.id.itemTodos);
        MenuItem itemD = (MenuItem)findViewById(R.id.itemDesayunos);
        MenuItem itemCo = (MenuItem)findViewById(R.id.itemComidas);
        MenuItem itemM = (MenuItem)findViewById(R.id.itemMeriendas);
        MenuItem itemCe = (MenuItem)findViewById(R.id.itemCenas);
        itemT.setChecked(false);
        itemD.setChecked(false);
        itemCo.setChecked(false);
        itemM.setChecked(false);
        itemCe.setChecked(false);


        if(categoria == Categoria.DESAYUNO) itemD.setChecked(true);
        else if(categoria == Categoria.COMIDA) itemCo.setChecked(true);
        else if(categoria == Categoria.MERIENDA) itemM.setChecked(true);
        else if(categoria == Categoria.CENA) itemCe.setChecked(true);
        else if(categoria == null) itemT.setChecked(true);

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
