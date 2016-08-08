package com.example.linxj.internal;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linxj on 16/8/6.
 */

public class SchemeDispatcher {
    //缓存Map
    private Map<String, IScheme> handlersCache = new HashMap();
    private Map<String, Class> handlerClasses;
    private static SchemeDispatcher instance;

    public static synchronized SchemeDispatcher getInstance() {
        if(instance == null) {
            instance = new SchemeDispatcher();
        }

        return instance;
    }

    private SchemeDispatcher() {
        try {
            this.handlerClasses = SchemeDatabaseFactory.getDatabase().getSchemeActivityClasses();

        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public boolean dispatch(Context context, String url) {
        return url!=null && (new com.example.linxj.internal.Scheme.Builder(context, url)).dispatch();
    }


    public boolean dispatch(com.example.linxj.internal.Scheme scheme) {
        if(scheme == null) {
            return false;
        } else {
            boolean result = false;
            String key = scheme.getSchemeKey();
            IScheme handler = (IScheme)this.handlersCache.get(key);
            if(handler == null && this.handlerClasses != null) {
                Class e = (Class)this.handlerClasses.get(key);
                if(e != null) {
                    try {
                        handler = (IScheme)e.newInstance();
                        this.handlersCache.put(key, handler);
                    } catch (Exception var8) {
                        var8.printStackTrace();
                    }
                }
            }

            if(handler != null) {
                try {
                    handler.doWithScheme(scheme);
                    result = true;
                } catch (Exception var7) {
                    var7.printStackTrace();
                }
            }

            return result;
        }
    }
}
