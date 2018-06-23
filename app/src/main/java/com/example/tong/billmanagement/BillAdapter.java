package com.example.tong.billmanagement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 15639 on 2018/5/25.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder>
{
    private List<Bill_Item> billList;
    Context context;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_id;
        private TextView tv_date;
        private TextView tv_coast;
        private TextView tv_type;
        private TextView tv_content;
        private Button btn_update;
        private Button btn_delete;

        public ViewHolder(View view)
        {
            super(view);
            tv_id = (TextView) view.findViewById(R.id.tv_id);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_coast = (TextView) view.findViewById(R.id.tv_coast);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            btn_update = (Button) view.findViewById(R.id.btn_update);
            btn_delete = (Button) view.findViewById(R.id.btn_delete);
        }
    }

    //构造函数
    public BillAdapter(List<Bill_Item> billList, Context context)
    {
        this.billList = billList;
        this.context = context;
    }

    //创建ViewHolder实例
    @NonNull
    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.btn_update.setOnClickListener
                (new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        int position = holder.getAdapterPosition();
                        callback.callBack(view, position);
                    }
                });
        holder.btn_delete.setOnClickListener
                (new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        int position = holder.getAdapterPosition();
                        callback.callBack(view, position);
                    }
                });
        return holder;
    }

    //设置recyclerview子项内容
    @Override
    public void onBindViewHolder(@NonNull BillAdapter.ViewHolder holder, int position)
    {
        Bill_Item bill_item = billList.get(position);
        holder.tv_id.setText(bill_item.getId());
        holder.tv_date.setText(bill_item.getDate());
        holder.tv_coast.setText(bill_item.getCoast());
        holder.tv_type.setText(bill_item.getType());
        holder.tv_content.setText(bill_item.getContent());
    }

    @Override
    public int getItemCount()
    {
        return billList.size();
    }

    //********************回掉接口***********************
    public interface AdapterCallback
    {
        public void callBack(View view, int position);
    }
    private AdapterCallback callback;
    public void setCallback(AdapterCallback callback)
    {
        this.callback = callback;
    }
}
