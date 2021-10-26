package com.example.bouncingball.listas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bouncingball.R;
import com.example.bouncingball.clases.Usuario;

import java.util.ArrayList;

public class ListaUsuarioAdapter extends RecyclerView.Adapter<ListaUsuarioAdapter.UsuarioviewHolder> {

    ArrayList<Usuario> listaPlayers;
    public ListaUsuarioAdapter(ArrayList<Usuario> listaPlayers){
        this.listaPlayers = listaPlayers;
    }
    @NonNull

    @Override
    public UsuarioviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_lista_jugadores,null,false);
        return new UsuarioviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioviewHolder holder, int position) {
     holder.viewNombre.setText(listaPlayers.get(position).getUsuario());
     holder.viewPuntaje.setText("              "+listaPlayers.get(position).getPuntaje());
    }

    @Override
    public int getItemCount() {
        return listaPlayers.size();

    }

    public class UsuarioviewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre ,viewPuntaje ;

        public UsuarioviewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.textName);
            viewPuntaje = itemView.findViewById(R.id.textPuntaje);
        }
    }
}
