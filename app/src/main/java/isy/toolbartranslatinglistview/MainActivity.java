package isy.toolbartranslatinglistview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements CustomScrollListener {

    private CustomListViewForActionBar listView;
    private CustomAdapter customAdapter;
    private Toolbar toolbar;
    private int tempY = 0;
    private ArrayList<String> stringArrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customAdapter = new CustomAdapter(stringArrayList);
        listView = (CustomListViewForActionBar) findViewById(R.id.listview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Disappearing listview");
        toolbar.setBackgroundColor(Color.GRAY);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        listView.setOnScrollListener(this);
        for (int i = 0; i < 100; i++) {
            stringArrayList.add("");
        }
        listView.setAdapter(customAdapter);
        customAdapter.notifyDataSetInvalidated();
    }

    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int bottom = c.getTop();

        int y = -bottom + firstVisiblePosition * c.getHeight();
        return y;
    }

    @Override
    public void onScrollChanged(int deltaY) {
        if (toolbar.getY() <= 0 && toolbar.getY() >= -getResources().getDimension(R.dimen.abc_action_bar_default_height_material)) {
            float y = (toolbar.getY() + deltaY);
            if (deltaY <= 0) {
                toolbar.setY(Math.max(y, -getResources().getDimension(R.dimen.abc_action_bar_default_height_material)));
            } else if (deltaY >= 0) {
                toolbar.setY(Math.min(y, 0));
            }
        }
    }


    private class CustomAdapter extends BaseAdapter {
        private ArrayList<String> stringArrayList = new ArrayList<String>();
        private LayoutInflater inflater;
        private TextView textView;

        private CustomAdapter(ArrayList<String> stringArrayList) {
            this.stringArrayList = stringArrayList;
            inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return stringArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.row_layout, null);
            textView = (TextView) convertView.findViewById(R.id.textview);
            return convertView;
        }
    }
}
