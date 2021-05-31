package com.example.proyectoandroidpepe.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoandroidpepe.Adaptadores.AdapterArticulos;
import com.example.proyectoandroidpepe.Datos.Articulos;
import com.example.proyectoandroidpepe.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ArticulosFragment extends Fragment {

    ArrayList<Articulos> listaArticulos;

    RecyclerView recyclerArticulos;

    public ArticulosFragment() {
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
        View view = inflater.inflate(R.layout.fragment_articulos, container, false);
        recyclerArticulos = view.findViewById(R.id.recyclerId);

        listaArticulos = new ArrayList<>();
        recyclerArticulos.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterArticulos adapter = new AdapterArticulos(listaArticulos,getContext());

        recyclerArticulos.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void llenarUsuarios() {

        FirebaseDatabase.getInstance().getReference("articulo").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listaArticulos.clear();

                task.getResult().getChildren().forEach(child -> {
                    Articulos articulo = child.getValue(Articulos.class);
                    if (articulo != null) {
                        listaArticulos.add(articulo);
                    }
                });

                recyclerArticulos.getAdapter().notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        llenarUsuarios();
        recyclerArticulos.getAdapter().notifyDataSetChanged();
    }

}
