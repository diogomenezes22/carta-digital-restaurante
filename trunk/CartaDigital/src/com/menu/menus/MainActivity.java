package com.menu.menus;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

public class MainActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.video_introduccion);

		VideoView videoView = (VideoView) findViewById(R.id.videoView1);
		Uri path = Uri.parse("http://www.youtube.com/watch?v=fyMhvkC3A84");
		videoView.setVideoURI(path);
		videoView.start();
*/
		Intent intent=new Intent(MainActivity.this, ScreenSlideActivity.class);
		this.startActivity(intent);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
