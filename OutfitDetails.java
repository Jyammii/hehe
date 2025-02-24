package com.example.closetifiy_finalproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;
public class OutfitDetails extends AppCompatActivity {

    private ConstraintLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outfitdetails);

        parentLayout = findViewById(R.id.outfitDetailsLayout);

        List<CanvasItem> canvasItems = getIntent().getParcelableArrayListExtra("canvasItems");

        if (canvasItems == null || canvasItems.isEmpty()) {
            Log.e("OutfitDetails", "No items received in intent");
            return;
        }

        Log.d("OutfitDetails", "Received items: " + canvasItems.size());

        for (CanvasItem item : canvasItems) {
            ImageView imageView = new ImageView(this);
            imageView.setImageURI(item.getUri());
            adjustImageViewSizeAndPosition(imageView, item);
            parentLayout.addView(imageView);
        }
    }

    private void adjustImageViewSizeAndPosition(ImageView imageView, CanvasItem item) {
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                (int) item.getWidth(),  // Use original width from CanvasItem
                (int) item.getHeight()   // Use original height from CanvasItem
        );

        // Positioning exactly as in Canvas
        params.leftMargin = (int) item.getLeft();
        params.topMargin = (int) item.getTop();

        // Ensure correct positioning
        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;

        // Set scale type to avoid stretching
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);

        // Apply the layout parameters
        imageView.setLayoutParams(params);
    }
}
