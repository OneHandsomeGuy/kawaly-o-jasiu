package com.ntii.kawaly.o.jasiu;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReadJoke extends Activity implements OnClickListener {
	int the_id = -1;
	SQLiteDatabase database;
	TextView rj_tv_number;
	TextView rj_tv_content;
	Button rj_b_like, rj_b_hate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Pe³ny ekran
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Layout
		setContentView(R.layout.read_joke);
		// Inicjalizacja
		initialize();
		// Koszyk
		Bundle gotBasket = getIntent().getExtras();
		the_id = gotBasket.getInt("the_id");
		// Otwieranie bazy
		database = openOrCreateDatabase("jokes", 0, null);
		if (the_id >= 0) {
			Cursor cursor = database.rawQuery("SELECT _id,content FROM jokes_list WHERE _id=" + the_id + ";", null);
			while (cursor.moveToNext()) {
				rj_tv_number.setText("Kawal numer " + cursor.getString(0));
				rj_tv_content.setText(cursor.getString(1));
			}
			cursor.close();
		}
		int state=1;
		Cursor cursor = database.rawQuery("SELECT state FROM jokes_list WHERE _id=" + the_id + ";", null);
		while (cursor.moveToNext()) {
			state=cursor.getInt(0);
		}
		// ¯e przeczytany
		if (state!=2)
			database.execSQL("UPDATE jokes_list SET state = '1' WHERE _id=" + the_id + ";");
		// Klika ¿e lubi
	}
	
	private void initialize() {
		rj_tv_number = (TextView) findViewById(R.id.rj_tv_number);
		rj_tv_content = (TextView) findViewById(R.id.rj_tv_content);
		rj_b_like = (Button) findViewById(R.id.rj_b_like);
		rj_b_hate = (Button) findViewById(R.id.rj_b_hate);
		rj_b_like.setOnClickListener(this);
		rj_b_hate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.rj_b_like):
			database.execSQL("UPDATE jokes_list SET state = '2' WHERE _id=" + the_id + ";");
			Toast.makeText(this, "Lubisz ten kawa³!", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case (R.id.rj_b_hate):
			database.execSQL("UPDATE jokes_list SET state = '3' WHERE _id=" + the_id + ";");
			Toast.makeText(this, "Nie lubisz tego kawa³u...", Toast.LENGTH_SHORT).show();
			finish();
			break;
		}

	}
}
