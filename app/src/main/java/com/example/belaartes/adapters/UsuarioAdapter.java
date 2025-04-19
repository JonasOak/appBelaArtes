package com.example.belaartes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belaartes.R;
import com.example.belaartes.data.model.entities.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> usuarios;
    private UsuarioClickListener listener;

    public interface UsuarioClickListener {
        void onUsuarioClick(Usuario usuario);
        void onEditClick(Usuario usuario);
    }

    public UsuarioAdapter(List<Usuario> usuarios, UsuarioClickListener listener) {
        this.usuarios = usuarios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.bind(usuario);
    }

    class UsuarioViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEmail, tvCargo;
        private ImageButton btnEdit;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvCargo = itemView.findViewById(R.id.tvCargo);
            btnEdit = itemView.findViewById(R.id.btnEdit);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUsuarioClick(usuarios.get(getAdapterPosition()));
                }
            });

            btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditClick(usuarios.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Usuario usuario) {
            tvEmail.setText(usuario.getEmail());
            tvCargo.setText(usuario.getCargo());
        }
    }
}
