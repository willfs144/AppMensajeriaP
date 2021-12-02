package co.udistrital.android.thomasmensageria.details_route.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.description_route.ui.DescriptionRouteFragment;
import co.udistrital.android.thomasmensageria.details_route.adapters.DeltailsRoutePagerAdapter;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.map_route.MapRouteFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class DeltailsRouteFragment extends Fragment {


    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;
    View view;

    private Route route;

    public DeltailsRouteFragment(Route route) {
        this.route = route;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_deltails_route, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupAdapter();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.deltails_header_description_title);

        return view;
    }

    private void setupAdapter() {


        Fragment[] fragments = new Fragment[]{new DescriptionRouteFragment(route), new MapRouteFragment(route)};
        String[] titles = new String[]{getString(R.string.deltails_header_route_detail_title), getString(R.string.deltails_header_route_map_title)};
        DeltailsRoutePagerAdapter adapter = new DeltailsRoutePagerAdapter(getChildFragmentManager(), titles, fragments);
        viewpager.setAdapter(adapter);
        tabs.setupWithViewPager(viewpager);

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
