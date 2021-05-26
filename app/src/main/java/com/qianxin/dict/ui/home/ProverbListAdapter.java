package com.qianxin.dict.ui.home;

import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.qianxin.dict.db.entity.Proverb;

import org.jetbrains.annotations.NotNull;


public class ProverbListAdapter extends ListAdapter<Proverb, ProverbViewHolder> {
    public ProverbListAdapter(@NonNull DiffUtil.ItemCallback<Proverb> diffCallback) {
        super(diffCallback);
    }

    @NotNull
    @Override
    public ProverbViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return ProverbViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ProverbViewHolder holder, int position) {
        Proverb current = getItem(position);
        holder.bind(current.getProverb());

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(holder.itemView.getContext(), "释义：" + current.getInterpretation() +
                            "\n中谚：" + current.getChinaProverb() +
                            "\n出处：" + current.getSource()
                    , Toast.LENGTH_LONG).show();
            /*
            Intent intent = new Intent(holder.itemView.getContext().getApplicationContext(), ProverbItemActivity.class);
            //intent.putExtra("name", current);


            holder.itemView.getContext().startActivity(intent);
            */
        });
    }


     public static class ProverbDiff extends DiffUtil.ItemCallback<Proverb> {

        @Override
        public boolean areItemsTheSame(@NonNull Proverb oldItem, @NonNull Proverb newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Proverb oldItem, @NonNull Proverb newItem) {
            return oldItem.getProverb().equals(newItem.getProverb());
        }
    }
}
