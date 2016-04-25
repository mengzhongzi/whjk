package com.asm.wenhejiankang;
import android.widget.LinearLayout;
import android.view.View;
import android.os.Bundle;

public class CtiwenActivity extends StartActivity
{
	
		LinearLayout layout_search;

		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				// TODO: Implement this method
				super.onCreate(savedInstanceState);
				setTitle(R.string.tiwenji);
				
			}
		
		
		
		@Override
		public void onContentView()
			{
				// TODO: Implement this method
				super.onContentView();
				setContentView(R.layout.facility_tiwenji);
				layout_search=(LinearLayout)findViewById(R.id.layout_search);
        searchStart();
			}



		//开始搜索
		void searchStart()
			{
				layout_search.setVisibility(View.VISIBLE);
			}

		//关闭搜索
		void searchClose()
			{
				layout_search.setVisibility(View.GONE);
			}
		
	
	
	
}
