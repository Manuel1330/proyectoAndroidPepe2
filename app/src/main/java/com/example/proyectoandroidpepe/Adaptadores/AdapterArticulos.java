package com.example.proyectoandroidpepe.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectoandroidpepe.Datos.Articulos;
import com.example.proyectoandroidpepe.R;

import java.util.ArrayList;

public class AdapterArticulos extends RecyclerView.Adapter<AdapterArticulos.ViewHolderArticulos> implements View.OnClickListener{

    ArrayList<Articulos> listaArticulos;
    private Context contexto;
    private View.OnClickListener listener;

    public AdapterArticulos(ArrayList<Articulos> listaArticulos, Context contexto) {
        this.listaArticulos = listaArticulos;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public AdapterArticulos.ViewHolderArticulos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_articulos, null, false);

        view.setOnClickListener(this);

        return new ViewHolderArticulos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterArticulos.ViewHolderArticulos holder, int position) {
        Articulos art = listaArticulos.get(position);

        holder.txtDescripcion.setText(art.getDescripcionArt());
        holder.txtPrecio.setText(art.getPrecioArt().toString());

    }

    @Override
    public int getItemCount() {
        return listaArticulos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderArticulos extends RecyclerView.ViewHolder{

        TextView txtDescripcion, txtPrecio;
        ImageView foto;
        LinearLayout item;


        public ViewHolderArticulos(@NonNull View itemView) {
            super(itemView);

            txtDescripcion= itemView.findViewById(R.id.idDescripcion);
            txtPrecio= itemView.findViewById(R.id.idPrecio);
            foto= itemView.findViewById(R.id.idImagen);
            item = itemView.findViewById(R.id.idItem);
        }

    }
}
