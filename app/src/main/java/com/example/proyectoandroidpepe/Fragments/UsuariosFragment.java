package com.example.proyectoandroidpepe.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectoandroidpepe.Datos.Usuarios;
import com.example.proyectoandroidpepe.MainActivity;
import com.example.proyectoandroidpepe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.proyectoandroidpepe.MainActivity.usuarioActual;

public class UsuariosFragment extends Fragment {

    TextView txtPersonName, txtPersonGivenName, txtPersonFamilyName, txtPersonEmail, txtPersonId, txtPersonUltimoPedido, txtPersonSaldo;
    EditText etPersonNombreUsuario, etPersonLugarEntrega, etPersonTelefono, etPersonObservaciones, etPersonFechaNacimiento, etPersonAux1, etPersonAux2;
    ImageView ivPersonPhoto;
    Button btnActualizar;
    Usuarios usuFireBase;

    DatabaseReference databaseReference;

    List<Usuarios> listaUsuarios = new ArrayList<>();

    public UsuariosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtPersonName = view.findViewById(R.id.txtPersonName);
        txtPersonGivenName = view.findViewById(R.id.txtPersonGivenName);
        txtPersonFamilyName = view.findViewById(R.id.txtPersonFamilyName);
        txtPersonEmail = view.findViewById(R.id.txtPersonEmail);
        txtPersonId = view.findViewById(R.id.txtPersonId);

        etPersonNombreUsuario = view.findViewById(R.id.etPersonNombreUsuario);
        etPersonLugarEntrega = view.findViewById(R.id.etPersonLugarEntrega);
        etPersonTelefono = view.findViewById(R.id.etPersonTelefono);
        etPersonObservaciones = view.findViewById(R.id.etPersonObservaciones);
        etPersonFechaNacimiento = view.findViewById(R.id.etPersonFechaNacimiento);
        etPersonAux1 = view.findViewById(R.id.etPersonAux1);
        etPersonAux2 = view.findViewById(R.id.etPersonAux2);

        txtPersonUltimoPedido = view.findViewById(R.id.txtPersonUltimoPedido);
        txtPersonSaldo = view.findViewById(R.id.txtPersonSaldo);

        ivPersonPhoto = view.findViewById(R.id.ivPersonPhoto);

        btnActualizar = view.findViewById(R.id.btnActualizar);

        setTextUsuario();

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarUsuario();
            }
        });

    }


    public void setTextUsuario(){

        FirebaseDatabase.getInstance().getReference().child("usuario").child(usuarioActual.getPersonId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               // for (DataSnapshot objeto : snapshot.getChildren()){
                    // Usuarios usu = (objeto.getValue(Usuarios.class));
                    // usuFireBase = usu;
               // }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
/*
        txtPersonName.setText(usuFireBase.getPersonName());
        txtPersonGivenName.setText(usuFireBase.getPersonGivenName());
        txtPersonFamilyName.setText(usuFireBase.getPersonFamilyName());
        txtPersonEmail.setText(usuFireBase.getPersonEmail());
        txtPersonId.setText(usuFireBase.getPersonId());

        etPersonNombreUsuario.setText(usuFireBase.getPersonNombreUsuario());
        etPersonLugarEntrega.setText(usuFireBase.getPersonLugarEntrega());
        etPersonTelefono.setText(usuFireBase.getPersonTelefono());
        etPersonObservaciones.setText(usuFireBase.getPersonObservaciones());
        etPersonFechaNacimiento.setText(usuFireBase.getPersonFechaNacimiento());
        etPersonAux1.setText(usuFireBase.getPersonAux1());
        etPersonAux2.setText(usuFireBase.getPersonAux2().toString());

        txtPersonUltimoPedido.setText(usuFireBase.getPersonUltimoPedido());
        txtPersonSaldo.setText(usuFireBase.getPersonSaldo().toString());

 */
    }


    public void editarUsuario(){
        listaUsuarios.clear();
        Usuarios usuario = new Usuarios(
                usuarioActual.getPersonName(),
                usuarioActual.getPersonGivenName(),
                usuarioActual.getPersonFamilyName(),
                usuarioActual.getPersonEmail(),
                usuarioActual.getPersonId(),
                usuarioActual.getPersonPhoto(),
                etPersonNombreUsuario.getText().toString(),
                etPersonLugarEntrega.getText().toString(),
                etPersonTelefono.getText().toString(),
                etPersonObservaciones.getText().toString(),
                etPersonFechaNacimiento.getText().toString(),
                etPersonAux1.getText().toString(),
                0.0,
                "",
                0.0,
                "No"
        );


        databaseReference.child("usuario").child(usuario.getPersonId()).setValue(usuario,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getContext(), "Usuario ....Añadido", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
