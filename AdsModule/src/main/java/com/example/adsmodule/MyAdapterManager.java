package com.example.adsmodule;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import java.util.List;

public class MyAdapterManager<Data, SampleViewHolder extends ViewHolder> extends RecyclerView.Adapter<SampleViewHolder> {


    public static final int TYPE_DATA = 0;
    public static final int TYPE_AD = 1;
    private Activity ac;
    private List<Data> values;
    private List<Data> ads_data;
    private SampleViewHolder viewHolder;
    private int layoutId;
    public static int mAdPosition;
    private AdapterView adapterView;

    public List<Data> getValues() {
        return values;
    }

    public void setValues(List<Data> values) {
        this.values = values;
    }

    public MyAdapterManager(Activity ac, List<Data> values, int layoutId, List<Data> ads_data, int ad_Pos) {
        this.ac = ac;
        this.values = values;
        this.layoutId = layoutId;
        this.ads_data = ads_data;
        this.mAdPosition = ad_Pos;
    }

    @Override
    public int getItemViewType(int position) {
        if (ads_data.size() > 0 && position > 0 && position % mAdPosition == 0 && position / mAdPosition < ads_data.size()) {
            return TYPE_AD;
        }
        return TYPE_DATA;
    }

    public MyAdapterManager setRowItemView(AdapterView adapterView) {
        this.adapterView = adapterView;
        return this;
    }

    public List<Data> getSelectedItemIds() {
        return values;
    }

    @NonNull
    @Override
    public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Object object = adapterView.setAdapterView(parent, viewType, this.layoutId);
        this.viewHolder = (SampleViewHolder) object;
        return this.viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull SampleViewHolder holder, int position) {
        Data data = values.get(position);
        adapterView.onBindView(holder, position, data, (List<Object>) this.values);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public interface AdapterView {

        Object setAdapterView(ViewGroup parent, int viewType, int layoutId);

        void onBindView(Object holder, int position, Object data, List<Object> dataList);

    }

    public void delete(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public int getSize() {
        return values.size();
    }

}
