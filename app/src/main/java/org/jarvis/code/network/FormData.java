package org.jarvis.code.network;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import org.jarvis.code.util.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by ki.kao on 12/16/2017.
 */

public class FormData {

    private Map<String, Object> map;

    public FormData() {
        this.map = new HashMap();
    }

    public FormData(@NonNull String param, @NonNull String value) {
        this();
        addFormDataPart(param, value);
    }

    public FormData(@NonNull String param, @NonNull File... files) {
        this();
        addFormDataPart(param, files);
    }

    public void addFormDataPart(@NonNull String param, @NonNull String value) {
        map.put(param, value);
    }

    public void addFormDataPart(@NonNull String param, @NonNull File... files) {
        if (map.containsKey(param)) {
            Object obj = map.get(param);
            if (obj instanceof File[]) {
                File[] tmp = (File[]) obj;
                for (int i = 0; i < files.length; i++)
                    tmp[tmp.length] = files[i];
            }
        } else
            map.put(param, files);
    }

    public void addFormDataPart(@NonNull String param, @NonNull Uri... fileUri) {
        for (Uri uri : fileUri)
            addFormDataPart(param, new File(uri.getPath()));
    }

    public void addFormDataPart(@NonNull String param, @NonNull Bitmap... bitmap) {
        for (Bitmap bit : bitmap)
            addFormDataPart(param, FileUtil.createFileFromBitmap(bit));
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public RequestBody getRequestBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object object = entry.getValue();
            if (object instanceof File[]) {
                File[] files = (File[]) object;
                for (File file : files) {
                    builder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MediaType.parse(FileUtil.getMimeType(file)), file));
                }
            } else {
                builder.addFormDataPart(entry.getKey(), object.toString());
            }
        }
        return builder.build();
    }
}
