package com.example.steamleecher69.ui.saved.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.steamleecher69.R;
import com.example.steamleecher69.data.model.db.Game;
import com.example.steamleecher69.databinding.SavedGameItemBinding;

import java.util.ArrayList;

/**
 * Created by macos on 08,August,2022
 */
public class SavedListAdapter extends RecyclerView.Adapter<SavedListAdapter.GameViewHolder> {
    private ArrayList<Game> gameList;
    private LayoutInflater layoutInflater;
    private GameListListener listener;

    public SavedListAdapter(ArrayList<Game> gameList, GameListListener listener) {
        this.gameList = gameList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        SavedGameItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.saved_game_item, parent, false);
        return new GameViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gameList.get(position);
        GameViewHolder gameViewHolder = holder;
        SavedGameItemBinding binding = gameViewHolder.gameItemBinding;
        binding.name.setText(game.getName());
        binding.price.setText(game.getFinalPriceString());
        binding.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onGameClicked(game);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (gameList == null) {
            return 0;
        }
        return gameList.size();
    }

    public void setGameList(ArrayList<Game> gameList) {
        this.gameList = gameList;
        notifyDataSetChanged();
    }

    public interface GameListListener {
        void onGameClicked(Game game);
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
        SavedGameItemBinding gameItemBinding;

        public GameViewHolder(@NonNull SavedGameItemBinding gameItemBinding) {
            super(gameItemBinding.getRoot());
            this.gameItemBinding = gameItemBinding;
        }
    }

}
