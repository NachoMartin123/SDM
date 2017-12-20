package com.example.nacho.proyectosdm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nacho.proyectosdm.modelo.Comida;
import com.example.nacho.proyectosdm.modelo.Usuario;
import com.example.nacho.proyectosdm.persistence.utils.DdbbDataSource;

import java.util.List;

public class PlatosCercaActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,
        SeleccionExtrasDialog.OnSeleccionExtrasRealizada{

    private ActionBarDrawerToggle mDrawerToggle;

    private boolean[] extraSeleccionados = {true, true, true, true};

    DdbbDataSource datos;//acceso a datos bbdd
    private Usuario usuarioActual=null;
    private List<Comida> comidasUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // para que no se gire
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platoscerca);

        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        );

        drawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);


        datos = new DdbbDataSource(getApplicationContext());

        String emailUsuarioActual = getIntent().getExtras().getString("emailUsuario");
        usuarioActual = datos.getUserByEmail(emailUsuarioActual);
        comidasUsuario = datos.getComidasUsuario(usuarioActual.getEmail());

        actualizarCampos();
    }

    private void actualizarCampos(){
        //actualizar campos nav
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nombreHeader = (TextView)hView.findViewById(R.id.nomUserHeader);
        ImageView imgHeader = (ImageView)hView.findViewById(R.id.imgUserHeader);
        nombreHeader.setText(usuarioActual.getNombre());
        imgHeader = (ImageView)hView.findViewById(R.id.imgUserHeader);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.subirplato) {
            final Intent intento = new Intent(PlatosCercaActivity.this, SubirPlatoActivity.class);
            startActivity(intento);
            return true;
        }
        else if(id == R.id.mensajes){
            final Intent intento = new Intent(PlatosCercaActivity.this, MensajesActivity.class);
            startActivity(intento);
            return true;
        }
        else if(id == R.id.misReservas){
            final Intent intento = new Intent(PlatosCercaActivity.this, MisReservasActivity.class);
            startActivity(intento);
            return true;
        }
        else if(id == R.id.misPlatos){
            final Intent intento = new Intent(PlatosCercaActivity.this, MisPlatosActivity.class);
            startActivity(intento);
            return true;
        }
        else if(id == R.id.configuracion){
            final Intent intento = new Intent(PlatosCercaActivity.this, ConfiguracionActivity.class);
            startActivity(intento);
            return true;
        }
        else if(id == R.id.ayuda){ //mejos un toast?
            //final Intent intento = new Intent(PlatosCercaActivity.this, activityNuevaVal.class);
            //startActivity(intento);
            return true;
        }

        return false;
    }

    //metodos menu
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        //Inflate the menu
        getMenuInflater().inflate(R.menu.barramenu_platoscerca, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
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
}
