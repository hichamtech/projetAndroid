package com.example.emploiandroid.EspaceAdministrator.Seance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.emploiandroid.EspaceAdministrator.ListAdapter;
import com.example.emploiandroid.Models.Seance;
import com.example.emploiandroid.R;

import java.util.ArrayList;

public class ListAdapterSeance  extends BaseAdapter {

    private Context context;
    private ArrayList<Seance> dataModelArrayList;

    public ListAdapterSeance(Context context, ArrayList<Seance> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListAdapterSeance.ViewHolder holder;

        if (convertView == null) {
            holder = new ListAdapterSeance.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_seances, null, true);

            holder.tvDateDebut = (TextView) convertView.findViewById(R.id.txtDateD);
            holder.tvDateFin = (TextView) convertView.findViewById(R.id.txtDateF);
            holder.tvNbrRep = (TextView) convertView.findViewById(R.id.txtNombreR);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ListAdapterSeance.ViewHolder)convertView.getTag();
        }

        holder.tvDateDebut.setText("Date debut: "+dataModelArrayList.get(position).getDateDebut());
        holder.tvDateFin.setText("Date Fin "+dataModelArrayList.get(position).getDateFin());
        holder.tvNbrRep.setText("NbrRep: "+dataModelArrayList.get(position).getNbrRep());

        return convertView;
    }


    private class ViewHolder {

        protected TextView tvDateDebut, tvDateFin, tvNbrRep;
    }

}
