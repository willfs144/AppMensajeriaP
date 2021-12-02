package co.udistrital.android.thomasmensageria.get_route.ui;


import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.add_route.ui.AddRouteFragmentDialog;
import co.udistrital.android.thomasmensageria.delete_route.ui.DeleteRouteFragmentDialog;
import co.udistrital.android.thomasmensageria.details_route.ui.DeltailsRouteFragment;
import co.udistrital.android.thomasmensageria.domain.FirebaseHelper;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.get_route.RouteListPresenter;
import co.udistrital.android.thomasmensageria.get_route.RouteListPresenterImpl;
import co.udistrital.android.thomasmensageria.get_route.adapters.OnItemClickListener;
import co.udistrital.android.thomasmensageria.get_route.adapters.RouteListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetRouteFragment extends Fragment implements RouteListView, OnItemClickListener {



    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerViewRoute)
    RecyclerView recyclerViewRoute;
    Unbinder unbinder;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.routeFragmentId)
    CoordinatorLayout routeFragmentId;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.treference)
    TextView treference;


    private RouteListAdapter adapter;
    private RouteListPresenter presenter;

    private int id_guia;
    private String id_Route;
    private boolean clickShort;

    private List<Route> routes;

    public GetRouteFragment() {
        presenter = new RouteListPresenterImpl(this);
        routes = new ArrayList<Route>();
        this.clickShort= true;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_route, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.onCreate();
        setupAdapter();
        setupRecyclerView();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return view;
    }

    private void setupAdapter() {
        adapter = new RouteListAdapter(new ArrayList<Route>(), this);
    }

    private void setupRecyclerView() {
        recyclerViewRoute.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewRoute.setAdapter(adapter);
    }


    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();

    }

    @Override
    public void showList() {
        recyclerViewRoute.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerViewRoute.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRouteAdd(Route route) {
        adapter.add(route);
    }

    @Override
    public void onRouteChanged(Route route) {
        adapter.update(route);
    }

    @Override
    public void onRouteRemoved(Route route) {
        adapter.remove(route);
        refresh();
    }

    @Override
    public void onRouteError(String error) {
        Snackbar.make(routeFragmentId, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void changedState() {
        Snackbar.make(routeFragmentId, getString(R.string.add_route_message_route_validate), Snackbar.LENGTH_SHORT).show();
    }


    @OnClick(R.id.fab)
    public void addRoute() {
        AddRouteFragmentDialog dialog =new AddRouteFragmentDialog(this);
        dialog.show(getFragmentManager(), getString(R.string.add_route_message_title));
    }


    @Override
    public void onItemClick(Route route) {
        if(clickShort){
            if(!route.getValidado())
                scanBar(route.getId());
            else{
                if(validarLista()){
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content, new DeltailsRouteFragment(route)).commit();
                }else
                    onRouteError("No se puede visualizar la ruta, ya que actualmente hacen falta servicios por verificar");

            }
        }

    }

    private boolean validarLista() {
        boolean result= true;
        List<Route> routeList = adapter.getRouteList();
        for (Route ruta : routeList) {
            Log.e("validar: ", ruta.getValidado().toString());
            if (ruta.getValidado()== false)
                return false;
        }
        return result;
    }

    @Override
    public void onItemLongClick(Route route) {

        if (!route.getValidado()){
            this.clickShort = false;
            DeleteRouteFragmentDialog dialog =new DeleteRouteFragmentDialog(this);
            dialog.setId_guia(route.getId());
            dialog.setPassRoute(route.getAutorizacion());
            dialog.show(getFragmentManager(), getString(R.string.delete_route_message_title));
            if (dialog.isCancelable())
                this.clickShort = true;
        }else{
            onRouteError("Ruta validada. No es posible eliminar");
        }
    }

    private void refresh() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, new GetRouteFragment()).commit();
    }


    private void scanBar(String idRoute) {
        this.id_Route = idRoute;

        IntentIntegrator integrator = new IntentIntegrator(getActivity());


        Intent intent = integrator.createScanIntent();
        intent.putExtra("SCAN_WIDTH", 1780);
        intent.putExtra("SCAN_HEIGHT", 1070);
        startActivityForResult(intent, IntentIntegrator.REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult.getContents() != null ) {
            presenter.validateGuia(this.id_Route, scanResult);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
