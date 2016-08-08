package com.example.linxj.urlrouter;

import android.content.Intent;

import com.example.UrlScheme;
import com.example.linxj.internal.IScheme;
import com.example.linxj.internal.Scheme;

/**
 * Created by linxj on 16/8/8.
 */
@UrlScheme("linxj://jumpActivity")
public class JumpScheme implements IScheme{


    @Override
    public void doWithScheme(Scheme scheme) throws Exception {
        Intent i = new Intent(scheme.getContext(),UrlJumpActivity.class);
        if(scheme.hasParameter("name")) {
            i.putExtra("name", scheme.getParameter("name"));
        }
        scheme.getContext().startActivity(i);
    }
}
