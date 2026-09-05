package com.theabsolutecompany.stickercentral;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.theabsolutecompany.stickercentral.utils.DecodeBitmapTask;


public class DetailsActivity extends AddStickerPackActivity implements DecodeBitmapTask.Listener {

    static final String BUNDLE_IMAGE_ID = "BUNDLE_IMAGE_ID";

    private ImageView imageView;
    private DecodeBitmapTask decodeBitmapTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final int smallResId = getIntent().getIntExtra(BUNDLE_IMAGE_ID, -1);
        if (smallResId == -1) {
            finish();
            return;
        }
	    FrameLayout addbutton=findViewById(R.id.add_to_whatsapp_button);
        imageView = findViewById(R.id.image);
        imageView.setImageResource(smallResId);
        addbutton.setOnClickListener(v -> finish());


        imageView.setOnClickListener(view -> DetailsActivity.super.onBackPressed());

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            loadFullSizeBitmap(smallResId);
        } else {
            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {

                private boolean isClosing = false;

                @Override public void onTransitionPause(Transition transition) {}
                @Override public void onTransitionResume(Transition transition) {}
                @Override public void onTransitionCancel(Transition transition) {}

                @Override public void onTransitionStart(Transition transition) {
                    if (isClosing) {
                        addCardCorners();
                    }
                }

                @Override public void onTransitionEnd(Transition transition) {
                    if (!isClosing) {
                        isClosing = true;

                        removeCardCorners();
                        loadFullSizeBitmap(smallResId);
                    }
                }
            });
        }

    }


    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing() && decodeBitmapTask != null) {
            decodeBitmapTask.cancel(true);
        }
    }

    private void addCardCorners() {
        final CardView cardView = findViewById(R.id.card);
        cardView.setRadius(25f);
    }

    private void removeCardCorners() {
        final CardView cardView = findViewById(R.id.card);
        ObjectAnimator.ofFloat(cardView, "radius", 0f).setDuration(50).start();
    }

    private void loadFullSizeBitmap(int smallResId) {
        int bigResId;
        if (smallResId == R.drawable.val5) {
            bigResId = R.drawable.sl13;
        } else if (smallResId == R.drawable.porn) {
            bigResId = R.drawable.sl14;
        } else if (smallResId == R.drawable.spidey) {
            bigResId = R.drawable.sl0;
        } else if (smallResId == R.drawable.pubg) {
            bigResId = R.drawable.sl1;
        } else if (smallResId == R.drawable.ariana) {
            bigResId = R.drawable.sl2;
        } else if (smallResId == R.drawable.bby) {
            bigResId = R.drawable.sl3;
        } else if (smallResId == R.drawable.got) {
            bigResId = R.drawable.sl4;
        } else if (smallResId == R.drawable.dead25) {
            bigResId = R.drawable.sl5;
        } else if (smallResId == R.drawable.elon) {
            bigResId = R.drawable.sl6;
        } else if (smallResId == R.drawable.harley) {
            bigResId = R.drawable.sl7;
        } else if (smallResId == R.drawable.homer) {
            bigResId = R.drawable.sl8;
        } else if (smallResId == R.drawable.rage) {
            bigResId = R.drawable.sl9;
        } else if (smallResId == R.drawable.scream) {
            bigResId = R.drawable.sl10;
        } else if (smallResId == R.drawable.tuziki) {
            bigResId = R.drawable.sl11;
        } else if (smallResId == R.drawable.love) {
            bigResId = R.drawable.sl12;
        } else if (smallResId == R.drawable.wolv) {
            bigResId = R.drawable.sl15;
        } else {
            bigResId = R.drawable.webh;
        }

        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

        final int w = metrics.widthPixels;
        final int h = metrics.heightPixels;

        decodeBitmapTask = new DecodeBitmapTask(getResources(), bigResId, w, h, this);
        decodeBitmapTask.execute();
    }

    @Override
    public void onPostExecuted(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

}
