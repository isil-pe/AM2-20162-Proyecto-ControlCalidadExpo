package como.isil.mynotes.rest.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isil.mynotes.rest.R;



import java.util.List;

import como.isil.mynotes.rest.entity.FundoEntity;

/**
 * Created by emedinaa on 15/09/15.
 */
public class FundoAdapter extends BaseAdapter {

    private Context context;
    private List<FundoEntity> lsFundoEntities;

    public FundoAdapter(Context context, List<FundoEntity> lsFundoEntities) {
        this.context = context;
        this.lsFundoEntities = lsFundoEntities;
    }

    @Override
    public int getCount() {
        return lsFundoEntities.size();
    }

    @Override
    public Object getItem(int i) {
        return lsFundoEntities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.row_fundo, null);
            ViewHolder holder = new ViewHolder();
            holder.tviName = (TextView)v.findViewById(R.id.tviName);
            holder.iviIconoEstado = (ImageView)v.findViewById(R.id.iviIconoEstado);
            v.setTag(holder);
        }
        FundoEntity entry = lsFundoEntities.get(position);
        if(entry != null) {
            ViewHolder holder = (ViewHolder)v.getTag();
            holder.tviName.setText(entry.getNombreproductor());


            if ( entry.getSincro().equals("NO")   ){

                holder.iviIconoEstado.setImageResource(R.drawable.notsync);

            }
        }

        return v;
    }

    static class ViewHolder
    {
        ImageView iviNote;
        TextView tviName;
        ImageView iviIconoEstado;
    }
}
