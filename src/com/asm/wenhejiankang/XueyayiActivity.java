package com.asm.wenhejiankang;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import com.asm.wenhejiankang.net.*;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.*;
import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View.OnClickListener;
import com.asm.wenhejiankang.model.User;
import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.xl.game.tool.DisplayUtil;
import com.xl.view.MyAdapter3;
import com.xl.view.ItemAdapter;
import com.xl.game.tool.Log;

/*
血压统计图界面


*/


public class XueyayiActivity extends StartActivity implements OnChartValueSelectedListener,Net_whjk_Listener,OnClickListener
	{

		@Override
		public void onClick(View p1)
			{
				// TODO: Implement this method
			}
		

		@Override
		public void onEnter(User user)
			{
				// TODO: Implement this method
			}

		@Override
		public void onError(String text)
			{
				// TODO: Implement this method
			}

		@Override
		public void onTiWen(ArrayList<String> list)
			{
				// TODO: Implement this method
			}

		@Override
		public void onTiwenError()
			{
				// TODO: Implement this method
			}

		@Override
		public void onXieya(ArrayList<String> list)
			{
				if(list!=null)
				for(String text:list)
				addData(text);
			}

		@Override
		public void onXieyaError()
			{
				// TODO: Implement this method
			}

		@Override
		public void onXietang(ArrayList<String> list)
			{
				// TODO: Implement this method
			}

		@Override
		public void onXietangError()
			{
				// TODO: Implement this method
			}

		@Override
		public void onXieyang(ArrayList<String> list)
			{
				// TODO: Implement this method
			}

		@Override
		public void onXieyangError()
			{
				// TODO: Implement this method
			}

		@Override
		public void onUpError()
			{
				// TODO: Implement this method
			}


		@Override
		public void onValueSelected(Entry p1, int p2)
			{
				// TODO: Implement this method
			}

		@Override
		public void onNothingSelected()
			{
				// TODO: Implement this method
			}


		private LineChart mChart;
		ArrayList<Entry> entry;
		ListView listview;
		Net_whjk net;
		XlApplication application;
		Button btn_prior15,btn_next15;
		TextView text_time;
		Date update,nextdate;
		
    ItemAdapter adapter;
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				//
				super.onCreate(savedInstanceState);

				setTitle(R.string.chart_xueya);

			}

		@Override
		public void onContentView()
			{

				setContentView(R.layout.info_tiwen);
				adapter = new ItemAdapter(this);		
				listview = (ListView)findViewById(R.id.list_tiwen);
				btn_prior15=(Button)findViewById(R.id.prior15);
				text_time=(TextView)findViewById(R.id.time);
				btn_next15=(Button)findViewById(R.id.next15);
				btn_prior15.setOnClickListener(this);
				btn_next15.setOnClickListener(this);
				text_time.setOnClickListener(this);
				application=(XlApplication)getApplication();
				net=application.getNetContext();
				net.setListener(this);
				nextdate=new Date();
				update=getNextDay(nextdate);
				getXieya(update,nextdate);
				onSetChart();
			}




		private void onSetChart()
			{
				mChart = (LineChart) findViewById(R.id.chart1);
				mChart.setOnChartValueSelectedListener(this);

				mChart.setUnit("℃");
				//mChart.setDrawUnitsInChart(true);

				// if enabled, the chart will always start at zero on the y-axis
				//mChart.setStartAtZero(false);

				// disable the drawing of values into the chart
				mChart.setDrawYValues(false);

				mChart.setDrawBorder(true);
				mChart.setBorderPositions(new BorderPosition[] {
						BorderPosition.BOTTOM
					});

				// no description text
				mChart.setDescription("");
				mChart.setNoDataTextDescription("You need to provide data for the chart.");

				// enable value highlighting
				mChart.setHighlightEnabled(true);

				// enable touch gestures
				mChart.setTouchEnabled(true);

				// enable scaling and dragging
				mChart.setDragEnabled(true);
				mChart.setScaleEnabled(true);
				mChart.setDrawGridBackground(false);
				mChart.setDrawVerticalGrid(true);
				mChart.setDrawHorizontalGrid(true);

				// if disabled, scaling can be done on x- and y-axis separately
				mChart.setPinchZoom(true);

				// set an alternative background color
				mChart.setBackgroundColor(getResources().getColor( R.color.background));

				// add data 设置数据
				entry = new ArrayList<Entry>();
				addData(entry, 1,37.3f);
				addData(entry,2,37);
				setData(entry,15, 100);

				mChart.animateX(2500);

				Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

				// get the legend (only possible after setting data)
				Legend l = mChart.getLegend();

				// modify the legend ...
				// l.setPosition(LegendPosition.LEFT_OF_CHART);
				l.setForm(LegendForm.LINE);
				l.setTypeface(tf);
				l.setTextColor(getResources().getColor(R.color.chart_legend));

				XLabels xl = mChart.getXLabels();
				//xl.setCenterXLabelText(true);
				xl.setPosition(XLabels. XLabelPosition.BOTTOM);
				xl.setTypeface(tf);
				xl.setTextColor(getResources().getColor(R.color.chart_xlable));

				YLabels yl = mChart.getYLabels();
				yl.setTypeface(tf);
				yl.setTextColor(getResources().getColor(R.color.chart_ylable));

				listview.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
			
			void getXieya(Date update,Date nextdate)
			{
				net.getXieya(update,nextdate);
					text_time.setText(""+update.getMonth()+"."+update.getDay()+"-"+nextdate.getMonth()+"."+nextdate.getDay());
			}
			
		//获取前15天时间
		public static Date getNextDay(Date date) {
				long time=date.getTime();
				time=time-15*1000*60*60*24;
				/*
				 Calendar calendar = Calendar.getInstance();
				 calendar.setTime(date);
				 calendar.add(Calendar.DATE, -15);
				 date = calendar.getTime();
				 */
				date=new Date(time);
				return date;
			}

		//添加一个温度信息
		//参数：时间 温度
		private void addData(ArrayList<Entry> entry, int time,float num)
			{
				entry.add(new Entry(num,time));
				adapter.add(""+adapter.getCount()+" "+ time+" "+num);

			}

		private void addData(String text)
			{
				String items[]=text.split(" ");
				if(items.length>=2)
					adapter.add(""+(adapter.getCount()+1)+" "+ text);
				adapter.notifyDataSetChanged();
				Log.e("添加血压数据",text);
			}

		private void setData(ArrayList<Entry> entry, int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
					}

        ArrayList<Entry> yVals = new ArrayList<Entry>();
				/*
				 for (int i = 0; i < count; i++) {
				 float mult = (range + 1);
				 float val = (float) (Math.random() * mult) + 3;// + (float)
				 // ((mult *
				 // 0.1) / 10);
				 yVals.add(new Entry(val, i));
				 }
				 */

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(entry, "体温");
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(0xff60a0f0);
        set1.setLineWidth(DisplayUtil.dip2px(this,1));
        set1.setCircleSize(DisplayUtil.dip2px(this,1));
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
			}
		
	}
