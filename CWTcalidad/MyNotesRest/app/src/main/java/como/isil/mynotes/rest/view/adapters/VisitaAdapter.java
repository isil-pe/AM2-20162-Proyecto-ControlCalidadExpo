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

import como.isil.mynotes.rest.entity.VisitaEntity;

/**
 * Created by Fabricio on 28/11/2016.
 */
public class VisitaAdapter   extends BaseAdapter {

    private Context context;
    private List<VisitaEntity> lsVisitaEntities;

    public VisitaAdapter(android.content.Context context, List<VisitaEntity> lsVisitaEntities) {
        this.context = context;
        this.lsVisitaEntities = lsVisitaEntities;
    }

    @Override
    public int getCount() {
        return lsVisitaEntities.size();
    }

    @Override
    public Object getItem(int i) {
        return lsVisitaEntities.get(i);
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
            v = inflater.inflate(R.layout.row_visita, null);
            ViewHolder holder = new ViewHolder();
            holder.tviName = (TextView)v.findViewById(R.id.tviName);
            holder.iviIconoEstado = (ImageView)v.findViewById(R.id.iviIconoEstadoVisita);
            v.setTag(holder);
        }
        VisitaEntity entry = lsVisitaEntities.get(position);
        if(entry != null) {
            ViewHolder holder = (ViewHolder)v.getTag();
            holder.tviName.setText(entry.getContenedor());

            if(entry.getObjectId()!=null){
                if ( entry.getObjectId()==""   ){

                    holder.iviIconoEstado.setImageResource(R.drawable.notsync);

                }

            }else{

                holder.iviIconoEstado.setImageResource(R.drawable.notsync);
            }

        }

        return v;
    }

    static class ViewHolder
    {
        ImageView iviVisita;
        TextView tviName;
        ImageView iviIconoEstado;
    }
}
