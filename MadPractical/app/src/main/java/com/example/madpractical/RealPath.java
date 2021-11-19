package com.example.madpractical;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.util.Log;

import com.google.android.gms.common.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class RealPath {

    Context rContext;

    RealPath(){}

    RealPath(Context context){
        rContext = context;
    }

    @SuppressLint("NewApi")
    public String getRealPathFromURI(Context context, Uri uri) throws IOException {
        Boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) { //main if start

            // DocumentProvider
            if (isGoogleDriveUri(uri)){ return getDriveFilePath(context, uri); }

            else if (isExternalStorageDocument(uri)) {// ExternalStorageProvider

                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];             // This is for checking Main Memory
                if ("primary".equalsIgnoreCase(type)){
                    if (split.length > 1){
                        return context.getExternalFilesDir(null).toString() + "/" + split[1];
                    }
                    else{
                        return context.getExternalFilesDir(null).toString() + "/";
                    }
                // This is for checking SD Card
                }
                else {
                    return "storage" + "/" + docId.replace(":", "/");
                }
            } else if (isDownloadsDocument(uri)){
                // DownloadsProvider
                ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r", null);
                FileInputStream inputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                File file = new File(context.getCacheDir(),getFileName(uri));
                FileOutputStream outputStream = new FileOutputStream(file);
                IOUtils.copyStream(inputStream, outputStream);

                return file.getAbsolutePath();
            }
            else if (isMediaDocument(uri)){

                String docId =DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                return copyFileToInternalStorage(context,uri,"app name");
            }
        }//main if end
        else if ("content".equalsIgnoreCase(uri.getScheme())){
            // MediaStore (and general)
            if(isGooglePhotosUri(uri)) return uri.getLastPathSegment();

            return copyFileToInternalStorage(context,uri, "your app name");
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())){
            // File
            return uri.getPath();
        }
        return null;
    }

    public String getFileName(Uri uri) throws UnsupportedEncodingException {
        String name = "";
        Cursor returnCursor = rContext.getContentResolver().query(uri,null,null,null,null);
        if(returnCursor != null){
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            name = returnCursor.getString(nameIndex);
            returnCursor.close();
        }
        return URLEncoder.encode(name,"utf-8");
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public Boolean isExternalStorageDocument(Uri uri){
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    public Boolean isGoogleDriveUri(Uri uri){
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority()) || "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public Boolean isDownloadsDocument(Uri uri){
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public Boolean isMediaDocument(Uri uri){
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public Boolean isGooglePhotosUri(Uri uri){
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public String getDriveFilePath(Context context, Uri uri) throws UnsupportedEncodingException {

       Uri returnUri = uri;
       Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
       /*
        * Get the column indexes of the data in the Cursor,
        * move to the first row in the Cursor, get the data,
        * and display it.
        */
       int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
       int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);

       returnCursor.moveToFirst();
       String name = (returnCursor.getString(nameIndex));

       String size = Long.toString(returnCursor.getLong(sizeIndex));
       File file = new File(context.getCacheDir(), URLEncoder.encode(name,"utf-8"));

       try {
           InputStream inputStream = context.getContentResolver().openInputStream(uri);
           FileOutputStream outputStream = new FileOutputStream(file);
           int read = 0;
           int maxBufferSize = 1 * 1024 * 1024;
           int bytesAvailable = inputStream.available();      //int bufferSize = 1024;
           int bufferSize = Math.min(bytesAvailable, maxBufferSize);
           byte[] buffers = new byte[bufferSize];

           while (true){
               int length = inputStream.read(buffers);
               if(length<=0)break;
               outputStream.write(buffers,0,length);
           }
           outputStream.flush();
           outputStream.close();
           Log.e("File Size", "Size " + file.length());
           inputStream.close();
           Log.e("File Path", "Path " + file.getPath());
           Log.e("File Size", "Size " + file.length());
       } catch (Exception e){
           Log.e("Exception", e.getMessage());
       }
       return file.getAbsolutePath();
   }

    public String copyFileToInternalStorage(Context mContext,Uri uri,String newDirName) throws UnsupportedEncodingException {

        Cursor returnCursor = mContext.getContentResolver().query(uri,new String[]{OpenableColumns.DISPLAY_NAME,OpenableColumns.SIZE},
                null, null, null);

        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        String size = java.lang.Long.toString(returnCursor.getLong(sizeIndex));
        File output;

            if (newDirName != ""){
                File dir = new File(mContext.getFilesDir().toString() + "/" + newDirName);
                if (!dir.exists())dir.mkdir();
                output = new File(mContext.getFilesDir().toString() + "/" + newDirName + "/" + URLEncoder.encode(name,"utf-8"));
            }
            else{
                output = new File(mContext.getFilesDir().toString() + "/" + URLEncoder.encode(name, "utf-8"));
            }

            try{

                InputStream inputStream = mContext.getContentResolver().openInputStream(uri);
                FileOutputStream outputStream = new FileOutputStream(output);
                int read = 0;
                int bufferSize = 1024;
                byte[] buffers = new byte[bufferSize];

                while ((read =inputStream.read(buffers)) != -1){
                    outputStream.write(buffers, 0, read);
                }
                inputStream.close();
                outputStream.close();
            }
            catch (Exception e) {
                Log.e("Exception", e.getMessage());
            }
        return output.getAbsolutePath();
    }//function finish

}
