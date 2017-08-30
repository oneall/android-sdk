package com.oneall.oneallsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Download image in background and put downloaded image into ImageView
 */
class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private final static String TAG = ImageDownloader.class.toString();

    private ImageView bmImage;

    ImageDownloader(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;

        Log.i(TAG, String.format("Downloading image from %s", urldisplay));

        HttpURLConnection conn = null;
        try {
            URL url = new URL(urldisplay);
            conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                mIcon11 = BitmapFactory.decodeStream(is);

                Log.i(TAG, String.format("Image downloaded from %s", urldisplay));
            } else {
                Log.i(TAG, String.format("Failed to download image from %s with status code %d",
                        urldisplay, conn.getResponseCode()));
            }

            Log.i(TAG, String.format("Image downloaded from %s", urldisplay));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
