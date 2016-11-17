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

import como.isil.mynotes.rest.entity.ProgramaEntity;

/**
 * Created by Fabricio on 15/11/2016.
 */
public class ProgramaAdapter  extends BaseAdapter {

    private Context context;
    private List<ProgramaEntity> lsProgramaEntities;

    public ProgramaAdapter(Context context, List<ProgramaEntity> lsProgramaEntities) {
        this.context = context;
        this.lsProgramaEntities = lsProgramaEntities;
    }

    @Override
    public int getCount() {
        return lsProgramaEntities.size();
    }

    @Override
    public Object getItem(int i) {
        return lsProgramaEntities.get(i);
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
            v = inflater.inflate(R.layout.row_programa, null);
            ViewHolder holder = new ViewHolder();
            holder.tviNombrePrograma = (TextView)v.findViewById(R.id.tviNombrePrograma);
            v.setTag(holder);
        }
        ProgramaEntity entry = lsProgramaEntities.get(position);
        if(entry != null) {
            ViewHolder holder = (ViewHolder)v.getTag();
            holder.tviNombrePrograma.setText(entry.getNombreprograma());
        }

        return v;
    }

    static class ViewHolder
    {
        ImageView iviNote;
        TextView tviNombrePrograma;
    }
    
    
}
