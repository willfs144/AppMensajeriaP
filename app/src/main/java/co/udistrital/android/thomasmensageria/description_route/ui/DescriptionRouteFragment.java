package co.udistrital.android.thomasmensageria.description_route.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.description_route.DescriptionRoutePresenter;
import co.udistrital.android.thomasmensageria.description_route.DescriptionRoutePresenterImpl;
import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Product;
import co.udistrital.android.thomasmensageria.entities.Route;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class DescriptionRouteFragment extends Fragment implements DescriptionRouteView {


    @BindView(R.id.llGuide)
    LinearLayout llGuide;
    @BindView(R.id.llProduct)
    LinearLayout llProduct;
    @BindView(R.id.llClient)
    LinearLayout llClient;
    @BindView(R.id.llOrigin)
    LinearLayout llOrigin;
    @BindView(R.id.llDestiny)
    LinearLayout llDestiny;
    @BindView(R.id.llObservation)
    LinearLayout llObservation;
    @BindView(R.id.llState)
    LinearLayout llState;
    @BindView(R.id.llDelivery)
    LinearLayout llDelivery;
    @BindView(R.id.llService)
    LinearLayout llService;
    @BindView(R.id.llTelephone)
    LinearLayout llTelephone;
    @BindView(R.id.llEmail)
    LinearLayout llEmail;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.editTxtGuia)
    EditText editTxtGuia;
    @BindView(R.id.editTxtProduct)
    EditText editTxtProduct;
    @BindView(R.id.editTxtCant)
    EditText editTxtCant;
    @BindView(R.id.editTxtClient)
    EditText editTxtClient;
    @BindView(R.id.editTxtDestiny)
    EditText editTxtDestiny;
    @BindView(R.id.editTxtObservation)
    EditText editTxtObservation;
    @BindView(R.id.editTxtOrigin)
    EditText editTxtOrigin;
    @BindView(R.id.editTxtTelephone)
    EditText editTxtTelephone;
    @BindView(R.id.editTxtEmain)
    EditText editTxtEmain;
    @BindView(R.id.container)
    ScrollView container;

    Unbinder unbinder;
    @BindView(R.id.editTxtDate)
    EditText editTxtDate;
    @BindView(R.id.editTxtState)
    EditText editTxtState;
    @BindView(R.id.editTxtDelivery)
    EditText editTxtDelivery;
    @BindView(R.id.editTxtService)
    EditText editTxtService;


    private Route route;
    private DescriptionRoutePresenter presenter;


    public DescriptionRouteFragment(Route route) {
        this.route = route;
        presenter = new DescriptionRoutePresenterImpl(this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_description_route, container, false);
        unbinder = ButterKnife.bind(this, view);
        setContentRoute();
        presenter.getDeltailRoute(route.getIdproducto());
        return view;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        presenter.onResume();
        super.onResume();

    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();

    }

    @Override
    public void showElements() {
        this.llGuide.setVisibility(View.VISIBLE);
        this.llClient.setVisibility(View.VISIBLE);
        this.llProduct.setVisibility(View.VISIBLE);
        this.llOrigin.setVisibility(View.VISIBLE);
        this.llDestiny.setVisibility(View.VISIBLE);
        this.llObservation.setVisibility(View.VISIBLE);
        this.llState.setVisibility(View.VISIBLE);
        this.llDelivery.setVisibility(View.VISIBLE);
        this.llService.setVisibility(View.VISIBLE);
        this.llEmail.setVisibility(View.VISIBLE);
        this.llTelephone.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideElements() {
        this.llGuide.setVisibility(View.GONE);
        this.llProduct.setVisibility(View.GONE);
        this.llClient.setVisibility(View.GONE);
        this.llOrigin.setVisibility(View.GONE);
        this.llDestiny.setVisibility(View.GONE);
        this.llObservation.setVisibility(View.GONE);
        this.llState.setVisibility(View.GONE);
        this.llDelivery.setVisibility(View.GONE);
        this.llService.setVisibility(View.GONE);
        this.llEmail.setVisibility(View.GONE);
        this.llTelephone.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setContentRoute() {
        this.editTxtGuia.setText("" + route.getId());
        this.editTxtDate.setText(route.getFecha_entrega());
        this.editTxtCant.setText(""+route.getCantidad());
        this.editTxtOrigin.setText(""+route.getOrigen());
        this.editTxtDestiny.setText(route.getDireccion() + "; "+route.getBarrio()+"; " + route.getCiudad());
        this.editTxtObservation.setText(route.getObservacion());
        this.editTxtState.setText(route.getEstado());
        this.editTxtDelivery.setText(route.getFecha_envio());
        this.editTxtService.setText(route.getServicio());
    }

    @Override
    public void setContentClient(Client client) {
        this.editTxtClient.setText(client.getNombre());
        this.editTxtTelephone.setText(client.getTelefono());
        this.editTxtEmain.setText(client.getCorreo());
        Linkify.addLinks(editTxtTelephone, Linkify.ALL);
        Linkify.addLinks(editTxtEmain, Linkify.ALL);
    }

    @Override
    public void setContentProduct(Product product) {
        this.editTxtProduct.setText(product.getNombre());
    }


}
