package com.xiazhao.learning.mymirror.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiazhao.learning.mymirror.R;

public class PhotoFrameActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private GridView gridView;
    private TextView textView;
    private int[] photo_styles;
    private String[] photo_name;
    private Bitmap[] bitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_frame);

        initDate();

        textView = this.findViewById(R.id.back_to_main);
        gridView = this.findViewById(R.id.photo_frame_list);

        textView.setOnClickListener(this);

        PhotoFrameAdapter adapter = new PhotoFrameAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    private void initDate() {

        photo_styles = new int[] {R.drawable.mag_0001, R.drawable.mag_0003, R.drawable.mag_0005,
                R.drawable.mag_0006, R.drawable.mag_0007, R.drawable.mag_0008, R.drawable.mag_0009,
                R.drawable.mag_0011, R.drawable.mag_0012, R.drawable.mag_0014};

        photo_name = new String[] {"Beautiful", "Special", "Forever", "Journey", "Love", "River",
                "Wonderful", "Birthday", "Nice"};

        bitmaps = new Bitmap[photo_styles.length];
        for (int i = 0; i < photo_styles.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), photo_styles[i]);
            bitmaps[i] = bitmap;
        }

    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("POSITION", position);
        setResult(RESULT_OK, intent);
        finish();
    }

    class PhotoFrameAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return photo_name.length;
        }

        @Override
        public Object getItem(int position) {
            return photo_name[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.item_gridview, null);
                holder.image = (ImageView) convertView.findViewById(R.id.item_pic);
                holder.txt = (TextView) convertView.findViewById(R.id.item_txt);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            setData(holder, position);
            return convertView;
        }

        private void setData(ViewHolder holder, int position) {
            holder.image.setImageBitmap(bitmaps[position]);
            holder.txt.setText(photo_name[position]);
        }

        class ViewHolder {
            ImageView image;
            TextView txt;
        }

    }
}