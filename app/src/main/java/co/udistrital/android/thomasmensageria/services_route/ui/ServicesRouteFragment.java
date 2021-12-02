package co.udistrital.android.thomasmensageria.services_route.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.add_photo.ui.AddPhotoFragment;
import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.services_deliver.ServicesDeliverFragment;
import co.udistrital.android.thomasmensageria.services_returned.ui.ServicesReturnedFragmentDialog;
import co.udistrital.android.thomasmensageria.services_route.ServicesRoutePresenter;
import co.udistrital.android.thomasmensageria.services_route.ServicesRoutePresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesRouteFragment extends Fragment implements ServicesRouteView {


    @BindView(R.id.img_guide)
    ImageView imgGuide;
    @BindView(R.id.editTxtCode)
    EditText editTxtCode;
    @BindView(R.id.ivCodigo)
    ImageView ivCodigo;
    @BindView(R.id.img_name)
    ImageView imgName;
    @BindView(R.id.editTxtClient)
    EditText editTxtClient;
    @BindView(R.id.llClient)
    LinearLayout llClient;
    @BindView(R.id.img_destiny)
    ImageView imgDestiny;
    @BindView(R.id.editTxtDestiny)
    EditText editTxtDestiny;
    @BindView(R.id.llDestiny)
    LinearLayout llDestiny;
    @BindView(R.id.img_observation)
    ImageView imgObservation;
    @BindView(R.id.editTxtObservation)
    EditText editTxtObservation;
    @BindView(R.id.llObservation)
    LinearLayout llObservation;
    @BindView(R.id.img_telephone)
    ImageView imgTelephone;
    @BindView(R.id.editTxtTelephone)
    EditText editTxtTelephone;
    @BindView(R.id.llTelephone)
    LinearLayout llTelephone;
    @BindView(R.id.img_mail)
    ImageView imgMail;
    @BindView(R.id.editTxtEmain)
    EditText editTxtEmain;
    @BindView(R.id.llEmail)
    LinearLayout llEmail;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.container)
    ScrollView container;
    Unbinder unbinder;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.ivClear)
    ImageView ivClear;
    @BindView(R.id.llButtoms)
    LinearLayout llButtoms;
    @BindView(R.id.imageButtonDelete)
    Button imageButtonDelete;
    @BindView(R.id.imageButtonAdd)
    Button imageButtonAdd;
    @BindView(R.id.imgDate)
    ImageView imgDate;
    @BindView(R.id.editTxtDate)
    EditText editTxtDate;
    @BindView(R.id.txtDate)
    TextInputLayout txtDate;
    @BindView(R.id.llDate)
    LinearLayout llDate;

    private Client client;
    private Route route;

    private View view;
    private ServicesRoutePresenter presenter;

    public ServicesRouteFragment() {
        presenter = new ServicesRoutePresenterImpl(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_services_route, container, false);
        unbinder = ButterKnife.bind(this, view);


        changeTextToIcon();

        presenter.onCreate();
        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDestroy();
    }


    @Override
    public void showIconSearch() {
        ivSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIconSearch() {
        ivSearch.setVisibility(View.GONE);
    }

    @Override
    public void showIconBar() {
        ivCodigo.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIconBar() {
        ivCodigo.setVisibility(View.GONE);
    }

    @Override
    public void showIconClear() {
        ivClear.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIconClear() {
        ivClear.setVisibility(View.GONE);
    }

    @Override
    public void showForm() {
        llButtoms.setVisibility(View.VISIBLE);
        llClient.setVisibility(View.VISIBLE);
        llDestiny.setVisibility(View.VISIBLE);
        llEmail.setVisibility(View.VISIBLE);
        llObservation.setVisibility(View.VISIBLE);
        llDate.setVisibility(View.VISIBLE);
        llTelephone.setVisibility(View.VISIBLE);
        editTxtCode.setEnabled(false);
    }

    @Override
    public void hideForm() {
        llClient.setVisibility(View.GONE);
        llDestiny.setVisibility(View.GONE);
        llEmail.setVisibility(View.GONE);
        llObservation.setVisibility(View.GONE);
        llDate.setVisibility(View.GONE);
        llTelephone.setVisibility(View.GONE);
        llButtoms.setVisibility(View.GONE);
        editTxtCode.setEnabled(true);
    }

    @Override
    public void showProgress() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void changeTextToIcon() {

        editTxtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().equals("")) {
                    hideIconSearch();
                    showIconBar();
                } else {
                    hideIconClear();
                    hideIconBar();
                    showIconSearch();
                }
            }
        });

    }

    @OnClick(R.id.ivSearch)
    public void searchCodeBar() {
        findRoute(editTxtCode.getText().toString());
    }

    @OnClick(R.id.ivCodigo)
    public void clickScanBar() {
        scanBar();

    }

    @OnClick(R.id.ivClear)
    public void clearText() {
        hideIconClear();
        hideForm();
        showIconBar();
        editTxtCode.setText("");
    }

    @OnClick(R.id.imageButtonAdd)
    public void clickDeliver(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (route.getCc_validada())
            fragmentManager.beginTransaction().replace(R.id.content, new AddPhotoFragment(this.client, this.route),AddPhotoFragment.class.getName()).addToBackStack(null).commit();
        else
            fragmentManager.beginTransaction().replace(R.id.content, new ServicesDeliverFragment(client, route)).commit();
    }

    private void scanBar() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        Intent intent = integrator.createScanIntent();
        startActivityForResult(intent, IntentIntegrator.REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String result = scanResult.getContents();
        if (result != null) {
            editTxtCode.setText(result);
            findRoute(result);
            hideIconBar();
        }
    }

    private void findRoute(String result) {
        presenter.findRoute(result);
    }

    @Override
    public void onError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setContentRoute(Route route) {
        this.route = route;
        this.editTxtDate.setText(route.getFecha_entrega());
        this.editTxtDestiny.setText(route.getDireccion() + "; " + route.getBarrio() + "; " + route.getCiudad());
        this.editTxtObservation.setText(route.getObservacion());
    }

    @Override
    public void setContentClient(Client client) {
        this.client = client;
        this.editTxtClient.setText(client.getNombre() + "\nCC. " + client.getCedula());
        this.editTxtTelephone.setText(client.getTelefono());
        this.editTxtEmain.setText(client.getCorreo());
        Linkify.addLinks(editTxtTelephone, Linkify.ALL);
        Linkify.addLinks(editTxtEmain, Linkify.ALL);
    }

    @OnClick(R.id.imageButtonDelete)
    public void clickNoEntrega(){
        ServicesReturnedFragmentDialog dialog =new ServicesReturnedFragmentDialog(this);
        dialog.show(getFragmentManager(), getString(R.string.returned_route_message_title));
    }

    public Route getRoute() {
        return route;
    }
}
