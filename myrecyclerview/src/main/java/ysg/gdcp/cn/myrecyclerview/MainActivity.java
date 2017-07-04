package ysg.gdcp.cn.myrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        initData();
        MyAdapter myAdapter = new MyAdapter(datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickItem(new MyAdapter.onItemClickListener() {
            @Override
            public void onclickListener(View v, int position, String city) {
                Toast.makeText(MainActivity.this, "city"+city+"position"+position, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initData() {
        datas = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            datas.add("guangzhou" + i);
        }
    }
}
