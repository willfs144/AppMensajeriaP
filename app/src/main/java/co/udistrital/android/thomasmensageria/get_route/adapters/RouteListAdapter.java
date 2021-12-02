package co.udistrital.android.thomasmensageria.get_route.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.entities.Route;


public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.ViewHolder> {

    public final static String A_INACTIVO ="inactivo";
    public static final String A_NOENTREGADO = "no entregado";
    public static final String A_ENTREGADO = "entregado";

    private List<Route> routeList;
    private OnItemClickListener onItemClickListener;

    public RouteListAdapter(List<Route> routeList, OnItemClickListener onItemClickListener) {
        this.routeList = routeList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_route, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Route route = routeList.get(position);
        holder.setClickListener(route, onItemClickListener);


        holder.tvGuide.setText("" + (position + 1));
        holder.txtGuide.setText("" + route.getId());
        boolean validate = false|route.getValidado();
        int status = validate ? R.drawable.accept : R.drawable.bar;
        holder.itemIcon.setImageResource(status);
        holder.txtDate.setText(route.getFecha_entrega());
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    public void add(Route route) {
        if (!route.getEstado().toString().equals(A_INACTIVO))
            if (!route.getEstado().toString().equals(A_ENTREGADO))
                if (!route.getEstado().toString().equals(A_NOENTREGADO))
                    if (!routeList.contains(route)) {
                        routeList.add(route);
                        notifyDataSetChanged();
                    }
    }

    public void update(Route route) {
        if (routeList.contains(route)) {
            int index = routeList.indexOf(route);
            if (route.getEstado().toString().equals(A_INACTIVO)  )
                routeList.remove(index);
            else
                routeList.set(index, route);

            notifyDataSetChanged();
        }

    }

    public void remove(Route route) {
        if (!routeList.contains(route)) {
            routeList.remove(route);
            notifyDataSetChanged();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_icon)
        ImageView itemIcon;
        @BindView(R.id.tvGuide)
        TextView tvGuide;
        @BindView(R.id.txtGuide)
        TextView txtGuide;
        @BindView(R.id.txtDate)
        TextView txtDate;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        private void setClickListener(final Route route, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(route);
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(route);
                    return false;
                }
            });
        }
    }

    public List<Route> getRouteList() {
        return routeList;
    }
}
