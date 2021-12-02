package co.udistrital.android.thomasmensageria.add_route.ui;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.add_route.AddRoutePresenter;
import co.udistrital.android.thomasmensageria.add_route.AddRoutePresenterImpl;
import co.udistrital.android.thomasmensageria.get_route.ui.GetRouteFragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


/**
 * A simple {@link Fragment} subclass.
 */

@SuppressLint("ValidFragment")
public class AddRouteFragmentDialog extends DialogFragment implements AddRouteView, DialogInterface.OnShowListener {

    private static final String BS_PACKAGE = "com.google.zxing.client.android";
    private static final int REQUEST_CODE = 24;

    @BindView(R.id.editTxtCode)
    EditText editTxtCode;
    @BindView(R.id.ivCodigo)
    ImageView ivCodigo;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;
    @BindView(R.id.editTextAdmin)
    EditText editTextAdmin;
    @BindView(R.id.editTextObervation)
    EditText editTextObervation;


    private AddRoutePresenter presenter;
    private GetRouteFragment getRouteFragment;



    @SuppressLint("ValidFragment")
    public AddRouteFragmentDialog(GetRouteFragment getRouteFragment) {
        presenter = new AddRoutePresenterImpl(this);
        this.getRouteFragment = getRouteFragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.add_route_message_title)
                .setPositiveButton(R.string.add_route_message_add, null)
                .setNegativeButton(R.string.add_route_message_cancel, null);

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_route, null);
        unbinder = ButterKnife.bind(this, view);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void showInput() {
        this.editTxtCode.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInput() {
        this.editTxtCode.setVisibility(View.GONE);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ivCodigo)
    public void addCodeBar() {

        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        //integrator.initiateScan();

        Intent intent = integrator.createScanIntent();
        intent.putExtra("SCAN_WIDTH", 1780);
        intent.putExtra("SCAN_HEIGHT", 1070);
        startActivityForResult(intent, IntentIntegrator.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult.getContents() != null ) {

            if (scanResult.getFormatName().equals(IntentIntegrator.PDF_417)){

                String r =scanResult.getContents();

                long  cedula =  Long.parseLong(r.substring(48,58));
                String nombre1 = r.substring(92, 124).trim();
                String nombre2 = r.substring(125,140).trim();
                String apellido1 = r.substring(58,74).trim();
                String apellido2 = r.substring(75,91).trim();
                String sexo = r.substring(151,152);
                String anual = r.substring(152,156);
                String mes = r.substring(156,158);
                String dia = r.substring(158,160);
                String rh = r.substring(166,168);


                editTxtCode.setText("CC. "+cedula+"    Nombre1: "+nombre1+" Nombre2: "+nombre2
                        +" Apellido1: "+apellido1+" Apellido2: "+apellido2
                        +"     Sexo: "+sexo+"   Fecha Nacimiento: "+dia+"-"+mes+"-"+anual
                        +" RH: "+rh);
            }
            else
                editTxtCode.setText(scanResult.getContents());
        }

    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String idRoute= editTxtCode.getText().toString().trim();
                    if (idRoute == null || idRoute.equals(""))
                        showMsg("No. Guia no puede ser vacio");
                    else
                        presenter.addRoute(idRoute, editTextObervation.getText().toString(),
                            editTextAdmin.getText().toString());
                }
            });
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        presenter.onShow();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showMsg(String msg){
        getRouteFragment.onRouteError(msg);
    }

    public void closeDialog(){
        dismiss();
    }
}
