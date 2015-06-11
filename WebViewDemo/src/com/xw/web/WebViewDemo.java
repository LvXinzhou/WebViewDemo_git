package com.xw.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewDemo extends Activity {
	private WebView wv_shzc;
	private Button btn_toSpecifiedDir;
	private Handler mHandler = new Handler();
	private static final String STRING_URL = "file:///android_asset/social_support.html";
	private static final String FILE_URL = "social_support.html";
	private Document doc;
	private List<Map<String, String>> list;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		wv_shzc = (WebView) findViewById(R.id.wv_shzc);
		wv_shzc.loadUrl(STRING_URL);
//		WebSettings webSettings = wv_shzc.getSettings();
//		webSettings.setJavaScriptEnabled(true);
//		wv_shzc.addJavascriptInterface(new Object() {
//			@SuppressWarnings("unused")
//			public void clickOnAndroid() {
//				mHandler.post(new Runnable() {
//					public void run() {
//						wv_shzc.loadUrl("javascript:wave()");
//					}
//				});
//			}
//		}, "social_support");
//		wv_shzc.setWebViewClient(new WebViewClient() {
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				view.loadUrl(url);
//				return true;
//			}
//		});
		load();//
		btn_toSpecifiedDir = (Button) findViewById(R.id.btn_toSpecifiedDir);
		btn_toSpecifiedDir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = list.get(0).get("href");
				System.out.println("url:" + url);
				// wv_shzc.loadUrl("javascript:location.hash = " + url);
				// wv_shzc.loadUrl(STRING_URL);
				wv_shzc.loadUrl(STRING_URL + url);
			}
		});

	}

	/**
	 * 按键响应，在WebView中查看网页时，按返回键的时候按浏览历史退回,如果不做此项处理则整个WebView返回退出
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the key event was the Back button and if there's history
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wv_shzc.canGoBack()) {
			// 返回键退回
			wv_shzc.goBack();
			return true;
		}
		// If it wasn't the Back key or there's no web page history, bubble up
		// to the default
		// system behavior (probably exit the activity)
		return super.onKeyDown(keyCode, event);
	}

	protected void load() {
		try {
			InputStream in = getAssets().open(FILE_URL);
			doc = Jsoup.parse(in, "UTF-8", "http://www.baidu.com");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		list = new ArrayList<Map<String, String>>();
		Elements es = doc.getElementsByClass("MsoToc1");
		System.out.println("es:" + es.size());
		for (Element e : es) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", e.getElementsByTag("a").text());
			map.put("href", e.getElementsByTag("a").attr("href"));
			list.add(map);
		}

		// ListView listView = (ListView) findViewById(R.id.listView1);
		// listView.setAdapter(new SimpleAdapter(this, list,
		// android.R.layout.simple_list_item_2,
		// new String[] { "title","href" }, new int[] {
		// android.R.id.text1,android.R.id.text2
		// }));

	}

}