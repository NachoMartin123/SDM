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

    private boolean[] extraSeleccionados = {false, false, false, false};

    DdbbDataSource datos;//acceso a datos bbdd
    private Usuario usuarioActual = null;
    private List<Comida> comidasUsuario;
    private List<Integer> idsComidasFiltradas;
    private Categoria categoria;

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
        comidasUsuario = datos.getComidasNoUsuario(emailUsuarioActual);
        idsComidasFiltradas = new ArrayList<>();
        categoria = Categoria.TODAS;
        actualizarHeader();
        inicializarListaComidas(comidasUsuario);
    }

    private int tamRacionesMayorCero(List<Comida> comidas){
        int tam=0;
        for(int i=0;i<comidas.size();i++) {
            if(comidas.get(i).getRaciones()>0)
                tam++;
        }
        return tam;
    }

    private void inicializarListaComidas(List<Comida> comidas) {
        if (comidas.size() > 0) {
            int tam = tamRacionesMayorCero(comidas);
            String[] titulos = new String[tam];
            Integer[] raciones = new Integer[tam];
            Double[] precios = new Double[tam];
            String[] ciudades = new String[tam];
            Bitmap[] imagenes = new Bitmap[tam];

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            idsComidasFiltradas.clear();
            int position = 0;
            for (int i = 0; i < comidas.size(); i++) {
                if(comidas.get(i).getRaciones()>0) {
                    idsComidasFiltradas.add(comidas.get(i).getId());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(comidas.get(i).getLatitud(), comidas.get(i).getLongitud(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String cityName = "", stateName = "", countryName = "";
                    if (addresses != null && addresses.size() != 0) {
                        cityName = addresses.get(0).getAddressLine(0);
                        stateName = addresses.get(0).getAddressLine(1);
                        countryName = addresses.get(0).getAddressLine(2);
                    }

                    titulos[position] = comidas.get(i).getTitulo();
                    raciones[position] = comidas.get(i).getRaciones();
                    precios[position] = comidas.get(i).getPrecio();
                    ciudades[position] = cityName + ", " + stateName + ", " + countryName;
                    imagenes[position] = comidas.get(i).getImagen();
                    position++;
                }
            }

            CustomList adapter = new CustomList(PlatosCercaActivity.this, titulos, raciones, precios, ciudades, imagenes);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    //Toast.makeText(PlatosCercaActivity.this, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                    Intent  miIntent = new Intent(PlatosCercaActivity.this,ReservarComidaActivity.class);
                    miIntent.putExtra("idComidaActual", idsComidasFiltradas.get(position));
                    miIntent.putExtra("emailUsuarioComprador", usuarioActual.getEmail());
                    startActivity(miIntent);

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
            case R.id.nav_mapa:
                lanzarNavActivity(MapaActivity.class);
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
            item.setChecked(true);
            categoria = Categoria.DESAYUNO;
            filtrarComidas();
        } else if (item.getItemId() == R.id.itemComidas){
            item.setChecked(true);
            categoria = Categoria.COMIDA;
            filtrarComidas();
        }else if(item.getItemId()==R.id.itemMeriendas){
            item.setChecked(true);
            categoria = Categoria.MERIENDA;
            filtrarComidas();
        }else if (item.getItemId() == R.id.itemCenas) {
            item.setChecked(true);
            categoria = Categoria.CENA;
            filtrarComidas();
        }else if (item.getItemId() == R.id.itemTodos) {
            item.setChecked(true);
            categoria = Categoria.TODAS;
            filtrarComidas();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSeleccionExtraRealizada(boolean[] seleccion) {
        this.extraSeleccionados = seleccion;
        filtrarComidas();
    }

    private void filtrarComidas(){
        List<Comida> misComidasFiltradas = new ArrayList<Comida>();
        for (Comida c : comidasUsuario) {
            if ((categoria == Categoria.TODAS || c.getCategoria() == categoria)
                    && c.cumpleCaracteristicas(
                            extraSeleccionados[2],
                            extraSeleccionados[3],
                            extraSeleccionados[0],
                            extraSeleccionados[1])) {
                misComidasFiltradas.add(c);
            }
        }
        inicializarListaComidas(misComidasFiltradas);
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

}
