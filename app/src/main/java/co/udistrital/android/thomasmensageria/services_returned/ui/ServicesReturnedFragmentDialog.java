package co.udistrital.android.thomasmensageria.services_returned.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.services_returned.ServicesReturnedPresenter;
import co.udistrital.android.thomasmensageria.services_returned.ServicesReturnedPresenterImpl;
import co.udistrital.android.thomasmensageria.services_route.ui.ServicesRouteFragment;


@SuppressLint("ValidFragment")
public class ServicesReturnedFragmentDialog extends DialogFragment implements ServicesReturnedView, DialogInterface.OnShowListener {

    String[] options = {"Destinatario desconocido", "Rehusado", "Cambio domicilio", "Cliente incumplió cita", "Dirección incompleta",
                        "Dirección errada", "Operador incumplió cita", "No hay quien reciba", "Otro"};
    int checkedItem = 0; // cow
    @BindView(R.id.editTxtCodeReturned)
    EditText editTxtCodeReturned;
    @BindView(R.id.progressBarReturned)
    ProgressBar progressBarReturned;
    Unbinder unbinder;
    @BindView(R.id.listViewNovedad)
    ListView listViewNovedad;
    @BindView(R.id.editTextObervation)
    EditText editTextObervation;
    Unbinder unbinder1;

    private ServicesRouteFragment servicesRouteFragment;

    private ServicesReturnedPresenter presenter;


    public ServicesReturnedFragmentDialog(ServicesRouteFragment servicesRouteFragment) {
        this.servicesRouteFragment = servicesRouteFragment;
        presenter = new ServicesReturnedPresenterImpl(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.returned_route_message_title)
                .setPositiveButton(R.string.add_route_message_add, null)
                .setNegativeButton(R.string.add_route_message_cancel, null);
        //.setSingleChoiceItems(options, checkedItem, null);

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_services_returned, null);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, options);



        unbinder = ButterKnife.bind(this, view);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        listViewNovedad.setAdapter(adapter);
        listViewNovedad.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        hideIcon();
        return dialog;

    }

    private void hideIcon() {
        editTxtCodeReturned.setEnabled(false);
        editTxtCodeReturned.setText(servicesRouteFragment.getRoute().getId());
    }


    @Override
    public void showInput() {

    }

    @Override
    public void hideInput() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


    @Override
    public void showMsg() {
        servicesRouteFragment.onError("Registro exitoso.Ruta fue inactivada");
    }

    @Override
    public void onShow(DialogInterface Interface) {
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
            final String[] item = new String[1];
            listViewNovedad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   item[0] = (String) parent.getItemAtPosition(position);
                }
            });
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (item[0] != null){
                        if (!editTextObervation.getText().toString().equals("")){
                            presenter.updateRoute(editTxtCodeReturned.getText().toString(),
                                    item[0]+": "+editTextObervation.getText().toString());
                            servicesRouteFragment.clearText();
                            dismiss();
                        }else
                            servicesRouteFragment.onError("Campo novedad no puede estar vacío");
                    }else
                        servicesRouteFragment.onError("Debe seleccionar un opción");
                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        presenter.onCreate();
    }


    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
    }

}
