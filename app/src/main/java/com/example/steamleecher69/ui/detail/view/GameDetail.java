package com.example.steamleecher69.ui.detail.view;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.steamleecher69.R;
import com.example.steamleecher69.data.local.db.Database;
import com.example.steamleecher69.data.model.api.GameOverView;
import com.example.steamleecher69.data.model.db.Game;
import com.example.steamleecher69.databinding.FragmentGameDetailBinding;
import com.example.steamleecher69.ui.detail.viewmodel.GameDetailViewModel;
import com.example.steamleecher69.utils.Const;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.util.ArrayList;

public class GameDetail extends AppCompatActivity {
    private GameDetailViewModel gameDetailViewModel;
    private FragmentGameDetailBinding binding;
    private StyledPlayerView playerView;
    private ExoPlayer player;
    private ProgressBar progressBar;

    public void starter(Context context, Game gameOverView) {
        Intent intent = new Intent(context, GameDetail.class);
        intent.putExtra(Const.Game.GAME_NAME, gameOverView.getName());
        intent.putExtra(Const.Game.GAME_FINAL_PRICE, gameOverView.getFinalPriceString());
        intent.putExtra(Const.Game.GAME_ID, gameOverView.getAppid());
        intent.putExtra(Const.Game.GAME_IMG, gameOverView.getImage2x());
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_game_detail);
        initActionBar();

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_game_detail);
        binding.setLifecycleOwner(this);
        gameDetailViewModel = new GameDetailViewModel(Database.getInstance(getBaseContext()));

        Intent intent = getIntent();
        if (intent == null) return;
        String appId = intent.getStringExtra(Const.Game.GAME_ID);
        if (appId == null) return;
        gameDetailViewModel.getGameInfo(appId);
        gameDetailViewModel.checkIsFavorite(appId);

        player = new ExoPlayer.Builder(this).build();
        playerView = binding.playerView;
        progressBar = binding.progressBar;
        playerView.setPlayer(player);

        bind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        gameDetailViewModel.getCurrentIsFavorite().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer isFavorite) {
                if (isFavorite > 0) {
                    menu.findItem(R.id.action_save).setIcon(R.drawable.ic_baseline_star_24);
                    Log.d(TAG, "onChanged: " + isFavorite);
                } else {
                    menu.findItem(R.id.action_save).setIcon(R.drawable.ic_baseline_star_24_unselected);
                }
            }
        });

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_save:
                Intent intent = getIntent();
                if (intent == null) break;
                String appId = intent.getStringExtra(Const.Game.GAME_ID);
                if (appId == null) break;
                gameDetailViewModel.saveGameToFavoriteList();
                gameDetailViewModel.checkIsFavorite(appId);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onPause() {
        player.setPlayWhenReady(false);
        player.stop();
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        player.release();
        super.onDestroy();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();
        if (intent == null) return;
        String title = intent.getStringExtra(Const.Game.GAME_NAME);
        if (title == null) return;
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void bind() {
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                    player.setPlayWhenReady(true);
                } else if (state == Player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);
                    playerView.setKeepScreenOn(true);
                } else {
                    progressBar.setVisibility(View.GONE);
                    player.setPlayWhenReady(true);
                }
            }
        });

        gameDetailViewModel.getCurrentGame().observe(this, new Observer<GameOverView>() {
            @Override
            public void onChanged(GameOverView gameOverView) {
                if (gameOverView == null) {
                    return;
                }
                updateGameInfo(gameOverView);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateGameInfo(GameOverView game) {
        new Handler(Looper.getMainLooper()).post(() -> {
            binding.name.setText(game.getName());
            if (game.getDiscount() > 0) {
                binding.originalPrice.setVisibility(View.VISIBLE);
                binding.originalPrice.setText(game.getOriginalPriceString());
                binding.originalPrice.setPaintFlags(binding.originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            binding.finalPrice.setText(game.getFinalPriceString());
            binding.developer.setText(game.getDeveloper());
            binding.publisher.setText(game.getPublisher());
            binding.releaseDate.setText(game.getReleaseDate());
            binding.desc.setText(game.getDescription());
            if (TextUtils.isEmpty(game.getFinalPriceString())) {
                binding.finalPrice.setText("Free to play");
            }

            ArrayList<MediaItem> mediaItems = new ArrayList<>();
            game.getVideoList().forEach(s -> {
                mediaItems.add(MediaItem.fromUri(s));
            });
            player.setMediaItems(mediaItems);
            player.prepare();
            player.setPlayWhenReady(true);
            
            binding.buy.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(game.getHref()));
                startActivity(browserIntent);
            });

        });


    }

}