package com.example.steamleecher69.ui.main.adapter;

import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.steamleecher69.R;
import com.example.steamleecher69.data.model.GameOverView;
import com.example.steamleecher69.databinding.GameItemBinding;
import com.example.steamleecher69.ui.main.callback.IMainActivityViewCallBack;
import com.example.steamleecher69.utils.Const;

import java.util.ArrayList;

/**
 * Created by macos on 26,July,2022
 */
public class GameListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<GameOverView> gameList;
    private LayoutInflater layoutInflater;
    private GameListListener listener;
    private IMainActivityViewCallBack iMainActivityViewCallBack;

    public GameListAdapter(ArrayList<GameOverView> gameList, GameListListener listener, IMainActivityViewCallBack iMainActivityViewCallBack) {
        this.gameList = gameList;
        this.listener = listener;
        this.iMainActivityViewCallBack = iMainActivityViewCallBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        if (viewType == Const.View.VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item_loading, parent, false);
            return new loadingViewHolder(view);
        }

        GameItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.game_item, parent, false);
        return new GameViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == Const.View.VIEW_TYPE_LOADING) {
            return;
        }

        GameOverView game = gameList.get(position);
        GameViewHolder gameViewHolder = (GameViewHolder) holder;

        GameItemBinding binding = gameViewHolder.gameItemBinding;
//        if (TextUtils.isEmpty(game.getFinalPriceString())) {
//            binding.container.setVisibility(View.GONE);
//        }
        // holder.gameItemBinding.se(game);
        binding.name.setText(game.getName());
        //holder.gameItemBinding.image
        binding.price.setText(game.getFinalPriceString());

        if (game.getDiscount() > 0) {
            binding.originalPrice.setVisibility(View.VISIBLE);
            binding.discountPercentage.setVisibility(View.VISIBLE);
            binding.originalPrice.setText(game.getOriginalPriceString());
            binding.originalPrice.setPaintFlags(binding.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.discountPercentage.setText(String.format("-%o%%", game.getDiscount()));
        } else {
            binding.originalPrice.setVisibility(View.GONE);
            binding.discountPercentage.setVisibility(View.GONE);
        }

        binding.windows.setVisibility(View.GONE);
        binding.linux.setVisibility(View.GONE);
        binding.macos.setVisibility(View.GONE);

        game.getPlatforms().forEach((p) -> {
            if (p.contains(Const.Platforms.WIN_PLATFORM)) {
                binding.windows.setVisibility(View.VISIBLE);
            }
            if (p.contains(Const.Platforms.LINUX_PLATFORM)) {
                binding.linux.setVisibility(View.VISIBLE);
            }
            if (p.contains(Const.Platforms.MAC_PLATFORM)) {
                binding.macos.setVisibility(View.VISIBLE);
            }
        });

        iMainActivityViewCallBack.setGameImage(binding.image, game.getImage2x());
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onGameClicked(game);
                }
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

    @Override
    public int getItemViewType(int position) {
        if (gameList.get(position) == null) {
            return Const.View.VIEW_TYPE_LOADING;
        }
        return Const.View.VIEW_TYPE_ITEM;
    }

    public void setGameList(ArrayList<GameOverView> gameList) {
        this.gameList = gameList;
        notifyDataSetChanged();
    }

    public void addGameList(ArrayList<GameOverView> gameList) {
        this.gameList.addAll(gameList);
        notifyDataSetChanged();
    }

    public interface GameListListener {
        void onGameClicked(GameOverView gameOverView);
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
        GameItemBinding gameItemBinding;

        public GameViewHolder(@NonNull GameItemBinding gameItemBinding) {
            super(gameItemBinding.getRoot());
            this.gameItemBinding = gameItemBinding;
        }
    }

    public class loadingViewHolder extends RecyclerView.ViewHolder {

        public loadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
