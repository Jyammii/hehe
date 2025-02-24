package com.example.closetifiy_finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.net.Uri;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set the navigation bar color
        getWindow().setNavigationBarColor(getResources().getColor(android.R.color.white));

        BottomNavigationView bottomNavigationView = findViewById(R.id.homenavigation);
        bottomNavigationView.setBackgroundColor(getResources().getColor(android.R.color.white));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.HomeFragment, new HomeCloset())
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.addi) {
                showAddItemBottomSheet();
                return true;
            }

            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.closet) {
                selectedFragment = new HomeCloset();
            } else if (item.getItemId() == R.id.outfits) {
                selectedFragment = new Outfits();
            } else if (item.getItemId() == R.id.settings) {
                selectedFragment = new Settings();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.HomeFragment, selectedFragment)
                        .commit();
            }

            return true;
        });
    }

    private void showAddItemBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        bottomSheetDialog.setContentView(view);

        TextView addClothes = view.findViewById(R.id.add_items);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView create = view.findViewById(R.id.creat);

        addClothes.setOnClickListener(v -> {
            showAddOptionsBottomSheet();
            bottomSheetDialog.dismiss();
        });

        create.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Canvas.class);
            startActivity(intent);
            bottomSheetDialog.dismiss();
        });

        cancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
    }

    private void showAddOptionsBottomSheet() {
        BottomSheetDialog addOptionsDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.add_options_sheet, null);
        addOptionsDialog.setContentView(view);

        TextView takePhoto = view.findViewById(R.id.take_photo);
        TextView addFromGallery = view.findViewById(R.id.add_from_gallery);
        TextView cancel = view.findViewById(R.id.cancel);

        takePhoto.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
            addOptionsDialog.dismiss();
        });

        addFromGallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
            addOptionsDialog.dismiss();
        });

        cancel.setOnClickListener(v -> addOptionsDialog.dismiss());

        addOptionsDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Intent intent = new Intent(Home.this, NewItem.class);

            if (requestCode == 1) {
                if (data != null && data.getExtras() != null) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    intent.putExtra("imageBitmap", photo);
                }
            } else if (requestCode == 2) {
                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    intent.putExtra("imageUri", selectedImageUri.toString());
                }
            }

            startActivity(intent);
        }
    }
}