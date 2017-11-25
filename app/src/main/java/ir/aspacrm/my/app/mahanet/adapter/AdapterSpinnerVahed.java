package ir.aspacrm.my.app.mahanet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ir.aspacrm.my.app.mahanet.G;
import ir.aspacrm.my.app.mahanet.R;
import ir.aspacrm.my.app.mahanet.model.Unit;

import java.util.List;

public class AdapterSpinnerVahed extends ArrayAdapter<Unit> {

    List<Unit> units;
    public AdapterSpinnerVahed(List<Unit> units) {
        super(G.context, R.layout.s_item_white,units);
        this.units = units;
    }
    @Override
    public int getCount() {
        return units.size();
    }
    @Override
    public Unit getItem(int position) {
        return units.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_white, parent, false);
        TextView txtUserSelect = (TextView) view.findViewById(R.id.txtName);
        txtUserSelect.setText("واحد ارجاع : " + units.get(position).name);
        return view;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(G.context).inflate(R.layout.s_item_black, parent, false);
        TextView txtChoice = (TextView) view.findViewById(R.id.txtName);
        txtChoice.setText(units.get(position).name);
        return view;
    }
    public void updateList(List<Unit> data){
        units = data;
        notifyDataSetChanged();
    }
}
