package com.example.linxj.internal;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by linxj on 16/8/6.
 */

public class Scheme {
        public static final String UTF_8 = "utf-8";
        private Context context;
        private String scheme;
        private String host;
        private Map<String, String> params;

        private Scheme(Scheme.Builder builder) {
            this.context = builder.context;
            this.scheme = builder.scheme;
            this.host = builder.host;
            this.params = builder.params;
        }

        private static String toParamsString(Map<String, String> params) {
            StringBuilder paramsString = new StringBuilder();
            Iterator iterator = params.entrySet().iterator();

            while(iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                String key = (String)entry.getKey();
                String value = (String)entry.getValue();
                paramsString.append(key + "=" + value + "&");
            }

            paramsString.deleteCharAt(paramsString.length() - 1);
            return paramsString.toString();
        }

        public static Map<String, String> toParamsMap(String paramsString) throws UnsupportedEncodingException {
            HashMap paramsMap = new HashMap();
            String[] pairs = paramsString.split("&");
            String[] arr$ = pairs;
            int len$ = pairs.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String pair = arr$[i$];
                int idx = pair.indexOf("=");
                paramsMap.put(pair.substring(0, idx), pair.substring(idx + 1));
            }

            return paramsMap;
        }

        public static String encodeUrl(String url) throws UnsupportedEncodingException {
            return URLEncoder.encode(url, "utf-8");
        }

        public static String getSchemeKey(String scheme, String host) {
            String separator = TextUtils.isEmpty(host)?":":"://";
            host = TextUtils.isEmpty(host)?"":host;
            return scheme + separator + host;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder(this.getSchemeKey());
            if(this.params.size() > 0) {
                builder.append("?");
                builder.append(toParamsString(this.params));
            }

            return builder.toString();
        }

        public Uri toUri() {
            return Uri.parse(this.toString());
        }

        public String getSchemeKey() {
            return getSchemeKey(this.scheme, this.host);
        }

        public String getParameter(String key) {
            return (String)this.params.get(key);
        }

        public boolean hasParameter(String key) {
            return !TextUtils.isEmpty((CharSequence)this.params.get(key));
        }

        public Context getContext() {
            return this.context;
        }

        public static class ActivityBuilder {
            public static final String SCHEME = "linxj";
            public static final String HOST = "activity";
            public static final String PARAM = "param";
            public static final String NAME = "name";
            private Context context;
            private String name;

            private Map<String, String> params = new HashMap();

            public ActivityBuilder(Context context, String name) {
                this.context = context;
                this.name = name;
            }

            public ActivityBuilder(Activity activity) {
                this.context = activity;
                Uri uri = activity.getIntent().getData();
                this.name = uri.getQueryParameter("name");

                try {
                    this.params = Scheme.toParamsMap(uri.getQueryParameter("param"));
                } catch (UnsupportedEncodingException var4) {
                    var4.printStackTrace();
                }

            }

            public Scheme.ActivityBuilder addParam(String key, String value) {
                this.params.put(key, value);
                return this;
            }

            public Scheme.ActivityBuilder addParams(Map<String, String> params) {
                if(params != null && params.size() > 0) {
                    this.params.putAll(params);
                }

                return this;
            }

            public Scheme build() {
                return new Scheme(this.toBuilder());
            }

            public boolean dispatch() {
                return com.example.linxj.internal.SchemeDispatcher.getInstance().dispatch(this.build());
            }

            private Scheme.Builder toBuilder() {
                Scheme.Builder builder = new Scheme.Builder(this.context);

                try {
                    builder.scheme("linxj").host("activity").addParam("name", this.name);
                    if(this.params != null && !this.params.isEmpty()) {
                        builder.addParam("param", Scheme.encodeUrl(Scheme.toParamsString(this.params)));
                    }

                } catch (UnsupportedEncodingException var3) {
                    var3.printStackTrace();
                }

                return builder;
            }
        }

        public static class Builder {
            private Context context;
            private String scheme;
            private String host;
            private Map<String, String> params;


            public Builder(Context context) {
                this.params = new HashMap();
                this.context = context;
            }

            public Builder(Context context, String uriString) {
                this(context, Uri.parse(uriString));
            }

            public Builder(Context context, Uri uri) {
                this.params = new HashMap();
                this.context = context;
                this.scheme = uri.getScheme();
                this.host = uri.getHost();

                try {
                    Iterator e = uri.getQueryParameterNames().iterator();

                    while(e.hasNext()) {
                        String key = (String)e.next();
                        this.params.put(key, uri.getQueryParameter(key));
                    }
                } catch (Exception var5) {

                }

            }

            public Scheme.Builder scheme(String scheme) {
                this.scheme = scheme;
                return this;
            }

            public Scheme.Builder host(String host) {
                this.host = host;
                return this;
            }

            public Scheme.Builder addParam(String key, String value) {
                this.params.put(key, value);
                return this;
            }

            public Scheme.Builder addParams(Map<String, String> params) {
                if(params != null && params.size() > 0) {
                    this.params.putAll(params);
                }

                return this;
            }

            public Scheme build() {
                return new Scheme(this);
            }

            public boolean dispatch() {
                return com.example.linxj.internal.SchemeDispatcher.getInstance().dispatch(this.build());
            }
        }
    }

