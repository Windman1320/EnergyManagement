package com.ym.charts;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tony.energymanagement.energymanagement.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by yangmiao on 2016/12/8.
 */

public class ShowPieCharts extends Activity {
    private PieChartView chart;
    private PieChartData data;
    private boolean hasLabels = true;
    private boolean hasLabelsOutside = true;
    private boolean hasCenterCircle = true;
    private boolean hasCenterText1 = true;//环形中间的文字1
    private boolean hasCenterText2 = false;
    private boolean isExploded = true;
    private boolean hasLabelForSelected = true;
    private Button btn;
    private EditText mEditText;
    private Button search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_piecharts);
        //设置一个Adapter,使用自定义的Adapter
//        setListAdapter(new TextShowAdapter(this));
        //加载数据和视图
        initViews();
        initDates();
        //时间显示
        mEditText = (EditText) findViewById(R.id.mEditText);
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });
    }
//    //元素的缓冲类,用于优化ListView
//    private static class ItemViewCache{
//        public TextView pieTextView;
//        public TextView pieValueView;
//    }
//    //展示的文字
//    private  String[] texts=new String[]{"所查询(时间)总能耗(kw.h)","单位(时间)平均能耗(kw.h)",
//            "动力(kw.h)","空调(kw.h)","特殊(kw.h)"};
//    //展示的数值
//    private int[] value=new int[]{83949,48,323,372,28};
//    //显示能源数据
//    private class TextShowAdapter extends BaseAdapter{
//        private Context mContext;
//        public TextShowAdapter(Context context){
//            this.mContext = context;
//        }
//        //元素的个数
//        public int getCount() {
//            return texts.length;
//        }
//        public Object getItem(int position) {
//            return null;
//        }
//        public long getItemId(int position) {
//            return 0;
//        }
//        //用以生成在ListView中展示的一个个元素View
//        public View getView(int position, View convertView, ViewGroup parent) {
//            //优化ListView
//            if(convertView==null){
//                convertView= LayoutInflater.from(mContext).inflate(R.layout.show_piecharts, null);
//               //缓冲类
//                ItemViewCache viewCache=new ItemViewCache();
//                viewCache.pieTextView=(TextView)convertView.findViewById(R.id.pie_text);
//                viewCache.pieValueView=(TextView)convertView.findViewById(R.id.pie_value);
//                convertView.setTag(viewCache);
//            }
//            ItemViewCache cache=(ItemViewCache)convertView.getTag();
//            //设置文本和值，然后返回这个View，用于ListView的文本的展示
//            cache.pieTextView.setText(texts[position]);
//            cache.pieValueView.setText(value[position]);
//            return convertView;
//        }
//    }

//加载视图
    private void initViews() {
        chart=(PieChartView)findViewById(R.id.pie_charts);
//        chart.setOnValueTouchListener(new ValueTouchListener());//添加点击事件
        chart.setCircleFillRatio(0.9f);//设置图所占整个view的比例  当有外面的数据时使用，防止数据显示不全
        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareDataAnimation();//更新数据，并添加动画
            }
        });
    }
    //数据初始化
    private void initDates() {
        generateData();
    }
    /**
     * 生成数据
     */
    private void generateData() {
        int numValues = 15;//分成的块数

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            //每一块的值和颜色，图标根据值自动进行比例分配
            SliceValue sliceValue = new SliceValue(20.0f, ChartUtils.pickColor());
            values.add(sliceValue);
        }
        data = new PieChartData(values);
        data.setHasLabels(true);//显示数据
        data.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
        data.setHasLabelsOutside(true);//占的百分比是否显示在饼图外面
        data.setHasCenterCircle(true);;//是否是环形显示
        data.setCenterCircleScale(0.5f);////设置环形的大小级别
        data.setValueLabelBackgroundColor(Color.TRANSPARENT);////设置值得背景透明
        data.setValueLabelBackgroundEnabled(false);//数据背景不显示
        data.setValueLabelsTextColor(Color.BLACK);

        //data.setValues(values);//填充数据
        if (isExploded) {
            data.setSlicesSpacing(1);//设置间隔为0
        }

        if (hasCenterText1) {
            data.setCenterText1("占比分析");

            // Get roboto-italic font.
            //      Typeface tf = Typeface.createFromAsset(MainActivity.this.getAssets(), "Roboto-Italic.ttf");//设置字体
            //   data.setCenterText1Typeface(tf);
            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.activity_horizontal_margin)));
            data.setCenterText1Color(Color.BLACK);////设置值颜色*/
        }
        chart.setPieChartData(data);
    }

    /**
     * To animate values you have to change targets values and then call}
     * method(don't confuse with View.animate()).
     */
    private void prepareDataAnimation() {
        for (SliceValue value : data.getValues()) {
            value.setTarget((float) Math.random() * 30 + 15);//更新数据
        }
        chart.startDataAnimation();
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {
        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
//            Toast.makeText(ShowLineCharts.this, "Selected: " + value, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onValueDeselected() {
        }
    }
    //显示时间对话框
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ShowPieCharts.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ShowPieCharts.this.mEditText.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
