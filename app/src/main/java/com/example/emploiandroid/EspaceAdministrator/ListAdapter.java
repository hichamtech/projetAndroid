package com.example.emploiandroid.EspaceAdministrator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.emploiandroid.Models.Personne;
import com.example.emploiandroid.R;

import java.util.ArrayList;



public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Personne> dataModelArrayList;

    public ListAdapter(Context context, ArrayList<Personne> dataModelArrayList) {

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
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_client, null, true);

            holder.tvnom = (TextView) convertView.findViewById(R.id.txtNom);
            holder.tvEmail = (TextView) convertView.findViewById(R.id.txtEmail);
            holder.tvTele = (TextView) convertView.findViewById(R.id.txtTele);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvnom.setText("Nom: "+dataModelArrayList.get(position).getNom());
        holder.tvEmail.setText("Email: "+dataModelArrayList.get(position).getEmail());
        holder.tvTele.setText("Tel: "+dataModelArrayList.get(position).getNumTelephone());

        return convertView;
    }


    private class ViewHolder {

        protected TextView tvnom, tvEmail, tvTele;
    }

}
