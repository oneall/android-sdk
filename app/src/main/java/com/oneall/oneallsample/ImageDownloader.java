package com.oneall.oneallsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.URI;

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
            HttpGet httpRequest = new HttpGet(URI.create(urldisplay));
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpRequest);
            HttpEntity entity = response.getEntity();
            BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
            mIcon11 = BitmapFactory.decodeStream(bufHttpEntity.getContent());
            httpRequest.abort();

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
