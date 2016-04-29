package com.asm.wenhejiankang;
import com.github.mikephil.charting.charts.LineChart;
import android.app.Activity;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.charts.BarLineChartBase.BorderPosition;
import android.graphics.Typeface;
import android.graphics.Color;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.data.LineData;
import java.util.ArrayList;
import com.github.mikephil.charting.utils.YLabels;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import android.os.Bundle;
import com.xl.game.tool.DisplayUtil;
import android.widget.ListView;
import com.xl.view.MyAdapter3;
import android.widget.AdapterView;
import android.widget.Adapter;
import android.view.View;
import com.asm.wenhejiankang.net.Net_whjk_Listener;
import com.asm.wenhejiankang.model.User;

/*
血糖仪界面

*/

public class XuetangyiActivity extends StartActivity implements OnChartValueSelectedListener,AdapterView.OnItemClickListener,Net_whjk_Listener
	{

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
				// TODO: Implement this method
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
		public void onItemClick(AdapterView<?> p1, View p2, int pos, long id)
			{
				// 
		  
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
    MyAdapter3 adapter;
		@Override
		protected void onCreate(Bundle savedInstanceState)
			{
				//
				super.onCreate(savedInstanceState);

				setTitle(R.string.chart_xuetang);

			}

		@Override
		public void onContentView()
			{

				setContentView(R.layout.info_tiwen);
				adapter = new MyAdapter3(this);		
				listview = (ListView)findViewById(R.id.list_tiwen);

				listview.setOnItemClickListener(this);
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

		//添加一个温度信息
		//参数：时间 温度
		private void addData(ArrayList<Entry> entry, int time,float num)
			{
				entry.add(new Entry(num,time));
				adapter.add(""+time,""+num);

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
