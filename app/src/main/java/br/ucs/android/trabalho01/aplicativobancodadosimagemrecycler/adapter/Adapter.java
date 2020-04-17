package br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.R;
import br.ucs.android.trabalho01.aplicativobancodadosimagemrecycler.model.Imagem;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Imagem> imagens;

    public Adapter(List<Imagem> imagens) {
        this.imagens = imagens;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.setData(imagens.get(position));
    }

    @Override
    public int getItemCount() {
        return imagens.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//        private TextView txtFase;
        private TextView txtNome;
        private TextView txtDescricao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtDescricao = itemView.findViewById(R.id.txtDescricao);
//            txtTitulo = itemView.findViewById(R.id.txtTitulo);
        }

        private void setData(Imagem imagens) {
            txtNome.setText(imagens.getNome());
            txtDescricao.setText(imagens.getDescricao());
//            txtTitulo.setText(imagens.getTitulo());

        }

        public void onClick(View view) {
            // @TODO: COLOCAR AQUI A ABERTURA DE IMAGEM NOVA
            Toast.makeText(view.getContext(),"Você selecionou " + imagens.get(getLayoutPosition()).getNome(),Toast.LENGTH_LONG).show();
        }
    }
}
