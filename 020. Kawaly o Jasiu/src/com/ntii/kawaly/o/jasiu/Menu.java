package com.ntii.kawaly.o.jasiu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Menu extends Activity implements OnClickListener{
	Button m_b_start,m_b_more_apps,m_b_rate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Pe³ny ekran
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//
		setContentView(R.layout.menu);
		//
		initialize();
	}

	private void initialize() {
		m_b_rate=(Button)findViewById(R.id.m_b_rate);
		m_b_more_apps=(Button)findViewById(R.id.m_b_more_apps);
		m_b_start=(Button)findViewById(R.id.m_b_start);
		m_b_rate.setOnClickListener(this);
		m_b_more_apps.setOnClickListener(this);
		m_b_start.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case(R.id.m_b_start):
			Intent start = new Intent(this,JokesList.class);
			startActivity(start);
			finish();
			break;
		case(R.id.m_b_more_apps):
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=NTIInnovations")));
			break;
		case(R.id.m_b_rate):
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.ntii.kawaly.o.jasiu")));
			break;
		}
	}
}
