package co.udistrital.android.thomasmensageria.services_deliver;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.add_photo.ui.AddPhotoFragment;
import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Product;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.services_deliver.ui.ServicesDeliverView;
import co.udistrital.android.thomasmensageria.services_route.ui.ServicesRouteFragment;

@SuppressLint("ValidFragment")
public class ServicesDeliverFragment extends Fragment implements ServicesDeliverView {

    @BindView(R.id.editTxtGuia)
    EditText editTxtGuia;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;
    @BindView(R.id.editTxtDate)
    EditText editTxtDate;
    @BindView(R.id.txtDate)
    TextInputLayout txtDate;
    @BindView(R.id.llGuide)
    LinearLayout llGuide;
    @BindView(R.id.editTxtProduct)
    EditText editTxtProduct;
    @BindView(R.id.txtNameProduct)
    TextInputLayout txtNameProduct;
    @BindView(R.id.editTxtCant)
    EditText editTxtCant;
    @BindView(R.id.txtQuantity)
    TextInputLayout txtQuantity;
    @BindView(R.id.llProduct)
    LinearLayout llProduct;
    @BindView(R.id.editTxtClient)
    EditText editTxtClient;
    @BindView(R.id.llClient)
    LinearLayout llClient;
    @BindView(R.id.editTxtService)
    EditText editTxtService;
    @BindView(R.id.llService)
    LinearLayout llService;
    @BindView(R.id.switchCedula)
    Switch switchCedula;
    @BindView(R.id.bAtras)
    Button bAtras;
    @BindView(R.id.bSiguiente)
    Button bSiguiente;
    @BindView(R.id.container_deliver)
    ScrollView container;
    @BindView(R.id.editTxtClientValidate)
    EditText editTxtClientValidate;

    @BindView(R.id.img_product)
    ImageView imgProduct;
    @BindView(R.id.imgQuantity)
    ImageView imgQuantity;
    @BindView(R.id.img_name)
    ImageView imgName;
    @BindView(R.id.textSwitch)
    TextView textSwitch;

    @BindView(R.id.img_typeService)
    ImageView imgTypeService;
    @BindView(R.id.img_name2)
    ImageView imgName2;
    @BindView(R.id.validateIVCC)
    ImageView validateIVCC;
    @BindView(R.id.llClient2)
    LinearLayout llClient2;
    @BindView(R.id.llDatosName)
    LinearLayout llDatosName;
    @BindView(R.id.lastName1)
    EditText lastName1;
    @BindView(R.id.lastName2)
    EditText lastName2;
    @BindView(R.id.bValidarCC)
    Button bValidarCC;
    @BindView(R.id.llFormbuttomCc)
    LinearLayout llFormbuttomCc;
    @BindView(R.id.llDatosLastName)
    LinearLayout llDatosLastName;
    @BindView(R.id.llbuttomCc)
    LinearLayout llbuttomCc;
    @BindView(R.id.name1)
    EditText name1;
    @BindView(R.id.name2)
    EditText name2;
    @BindView(R.id.llForms)
    LinearLayout llForms;
    @BindView(R.id.progressBarDeliver)
    ProgressBar progressBarDeliver;

    Unbinder unbinder;
    public Client client;
    public Route route;

    public ServicesDeliverPresenter presenter;
    @BindView(R.id.textInputLayout1)
    TextInputLayout textInputLayout1;
    @BindView(R.id.txtName)
    TextInputLayout txtName;
    @BindView(R.id.vRecuado)
    TextView vRecuado;
    @BindView(R.id.llRecaudo)
    LinearLayout llRecaudo;
    @BindView(R.id.switchRecaudo)
    Switch switchRecaudo;
    @BindView(R.id.bsRecaudo)
    Button bsRecaudo;
    @BindView(R.id.llFormbuttomRecaudo)
    LinearLayout llFormbuttomRecaudo;

    public ServicesDeliverFragment() {
    }

    public ServicesDeliverFragment(Client client, Route route) {
        this.client = client;
        this.route = route;
        presenter = new ServicesDeliverPresenterImpl(this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_services_deliver, container, false);
        unbinder = ButterKnife.bind(this, view);
        showRoute();
        showClient();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.getDeltailRoute(route.getIdproducto());
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showProgress() {
        progressBarDeliver.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarDeliver.setVisibility(View.GONE);
    }

    @Override
    public void hideElements() {
        llForms.setVisibility(View.GONE);
    }

    @Override
    public void showElements() {
        llForms.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bValidarCC)
    public void validateButtomCedula() {
        presenter.validateButtomCedula(client, editTxtClientValidate.getText().toString(), name1.getText().toString(),
                name2.getText().toString(), lastName1.getText().toString(), lastName2.getText().toString());
        inactivateForm();
    }

    @Override
    @OnClick(R.id.switchCedula)
    public void validateCedula() {
        if (switchCedula.isChecked())
            activeForm();
        else {
            inactivateForm();
            editTxtClientValidate.setText(R.string.deltails_body_route_description_cedula);
            validateIVCC.setClickable(true);
        }
    }

    private void activeForm() {
        llDatosName.setVisibility(View.VISIBLE);
        llDatosLastName.setVisibility(View.VISIBLE);
        bValidarCC.setVisibility(View.VISIBLE);
        llbuttomCc.setVisibility(View.GONE);
        validateIVCC.setClickable(false);
        editTxtClientValidate.setEnabled(true);
        editTxtClientValidate.setText("");
        editTxtClientValidate.setFocusable(true);
    }

    private void inactivateForm() {
        llDatosName.setVisibility(View.GONE);
        llDatosLastName.setVisibility(View.GONE);
        llbuttomCc.setVisibility(View.VISIBLE);
        bValidarCC.setVisibility(View.GONE);
        editTxtClientValidate.setEnabled(false);
        name1.setText("");
        name2.setText("");
        lastName1.setText("");
        lastName2.setText("");

    }

    @Override
    @OnClick(R.id.bSiguiente)
    public void bcontinue() {
        if (client.isValidate()) {
            if (switchRecaudo.isChecked()){
                if (!route.getCc_validada())
                    updateStatedCC();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content, new AddPhotoFragment(this.client, this.route), AddPhotoFragment.class.getName()).addToBackStack(null).commit();
            }else
                msgServices("Debe registrar recaudo");
        } else
            msgServices("Cedula aun no validada");
    }

    private void updateStatedCC() {
        presenter.updateStated(this.route.getId());
    }

    @OnClick(R.id.bAtras)
    public void back() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, new ServicesRouteFragment()).addToBackStack(null).commit();
    }

    @Override
    public void setContentProduct(Product product) {
        this.editTxtProduct.setText(product.getNombre());
    }

    @Override
    public void showClient() {
        this.editTxtClient.setText(this.client.getNombre() + "\nCC. " + client.getCedula());
    }

    @Override
    public void showRoute() {
        this.editTxtDate.setText(route.getFecha_entrega());
        this.editTxtGuia.setText("" + route.getId());
        this.editTxtCant.setText("" + route.getCantidad());
        this.editTxtService.setText(route.getServicio());
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        this.vRecuado.setText("$ "+formatea.format(Double.parseDouble(route.getValor_recaudo())));
    }


    @OnClick(R.id.validateIVCC)
    public void scanBar() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());

        Intent intent = integrator.createScanIntent();
        startActivityForResult(intent, IntentIntegrator.REQUEST_CODE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult.getContents() != null) {
            presenter.validateCedula(client, route, scanResult);
        }
    }


    public void msgServices(String msg) {
        Snackbar.make(container, "" + msg, Snackbar.LENGTH_LONG).show();
    }


    public void showValidateCC(String cc, String name1, String name2, String surname1, String surname2) {
        this.editTxtClientValidate.setText(name1 + " " + name2 + " " + surname1 + " " + surname2 + " " + "\nCC. " + cc);
        if (Double.parseDouble(route.getValor_recaudo()) > 0)
            showRecaudo();
        else{
            switchRecaudo.setChecked(true);
        }
    }

    private void showRecaudo() {
        llRecaudo.setVisibility(View.VISIBLE);
        llFormbuttomRecaudo.setVisibility(View.VISIBLE);
    }

    public void switchCedula() {
        switchCedula.setChecked(true);
        switchCedula.setEnabled(false);
        switchCedula.setFocusable(false);
        textSwitch.setText("Cedula validada");
        validateIVCC.setClickable(false);
    }
}


