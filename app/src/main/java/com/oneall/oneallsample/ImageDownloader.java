package com.oneall.oneallsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Download image in background and put downloaded image into ImageView
 */
public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private final static String TAG = ImageDownloader.class.toString();

    ImageView bmImage;

    public ImageDownloader(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;

        Log.i(TAG, String.format("Downloading image from %s", urldisplay));

        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            Log.i(TAG, String.format("Image downloaded from %s", urldisplay));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
