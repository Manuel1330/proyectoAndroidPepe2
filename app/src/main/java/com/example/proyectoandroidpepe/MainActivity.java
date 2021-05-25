package com.example.proyectoandroidpepe;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.proyectoandroidpepe.Datos.DatosApp;
import com.example.proyectoandroidpepe.Datos.Usuarios;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    DatabaseReference databaseReference;

    public static Usuarios usuarioActual = new Usuarios();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_usuarios)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            DatabaseReference userReference = FirebaseDatabase.getInstance()
                    .getReference("usuario")
                    .child(signInAccount.getId());
            final Usuarios[] user = new Usuarios[1];
            userReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        user[0] = task.getResult().getValue(Usuarios.class);
                        subirUsuario(user[0], signInAccount);
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

    public Usuarios getUsuarioactual() {
        return usuarioActual;
    }

    public void setUsuarioactual(Usuarios usuarioactual) {
        this.usuarioActual = usuarioactual;
    }

    private void subirUsuario(Usuarios user, GoogleSignInAccount signInAccount) {
        if (user == null) {
            usuarioActual.setPersonName(signInAccount.getDisplayName());
            usuarioActual.setPersonGivenName(signInAccount.getGivenName());
            usuarioActual.setPersonFamilyName(signInAccount.getFamilyName());
            usuarioActual.setPersonEmail(signInAccount.getEmail());
            usuarioActual.setPersonId(signInAccount.getId());
            usuarioActual.setPersonPhoto(signInAccount.getPhotoUrl().toString());

            Usuarios usuario = new Usuarios(
                    usuarioActual.getPersonName(),
                    usuarioActual.getPersonGivenName(),
                    usuarioActual.getPersonFamilyName(),
                    usuarioActual.getPersonEmail(),
                    usuarioActual.getPersonId(),
                    usuarioActual.getPersonPhoto(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    0.0,
                    "",
                    0.0,
                    "No"
            );

            databaseReference.child("usuario").child(usuario.getPersonId()).setValue(usuario,
                    new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Toast.makeText(MainActivity.this, "Usuario ....Añadido", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "El Usuario ya esta registrado", Toast.LENGTH_SHORT).show();
            DatosApp.currentUser = user;
        }
    }
}