package net.justdave.mcstatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutDialog extends Dialog {
    private static Context mContext = null;
    private static final String TAG = AboutDialog.class.getSimpleName();

    public AboutDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.about);

        PackageInfo p;
        String version = "???";
        int vCode = 0;
        TextView tv = (TextView) findViewById(R.id.info_text);
        tv.setText(Html.fromHtml(readRawTextFile(R.raw.about_info)));
        tv.setLinkTextColor(Color.BLUE);
        Linkify.addLinks(tv, Linkify.ALL);
        TextView ver = (TextView) findViewById(R.id.version_string);
        Context aContext = mContext.getApplicationContext();
        try {
            p = aContext.getPackageManager().getPackageInfo(aContext.getPackageName(), 0);
            version = p.versionName;
            vCode = p.versionCode;
        } catch (NameNotFoundException e) {
            Log.i(TAG, "Failed to retrieve package name");
            e.printStackTrace();
        }
        ver.setText(aContext.getResources().getString(R.string.about_version, version, vCode));
        Button button = (Button) findViewById(R.id.about_ok_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
 
    }

    public static String readRawTextFile(int id) {

        InputStream inputStream = mContext.getResources().openRawResource(id);

        InputStreamReader in = new InputStreamReader(inputStream);
        BufferedReader buf = new BufferedReader(in);

        String line;

        StringBuilder text = new StringBuilder();
        try {
            while ((line = buf.readLine()) != null)
                text.append(line);
        } catch (IOException e) {
            return null;
        }

        return text.toString();
    }

}
