package co.udistrital.android.thomasmensageria.generate_pdf.ui;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;





import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Messenger;
import co.udistrital.android.thomasmensageria.entities.Product;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.generate_pdf.PDFPresenter;
import co.udistrital.android.thomasmensageria.generate_pdf.PDFPresenterImpl;
import co.udistrital.android.thomasmensageria.help.TemplatePDF;
import co.udistrital.android.thomasmensageria.services_route.ui.ServicesRouteFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class PDFFragment extends Fragment implements PDF_View {


    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.imageFirm)
    ImageView imageFirm;
    @BindView(R.id.fabShares)
    FloatingActionButton fabShares;
    @BindView(R.id.btnFinish)
    Button btnFinish;
    @BindView(R.id.contairne)
    FrameLayout contairne;
    @BindView(R.id.progressBarDeliver)
    ProgressBar progressBarDeliver;

    private File file;
    private TemplatePDF templatePDF;

    private Client client;
    private Route route;
    private Messenger messenger;
    private Product product;
    private Bitmap bitmap;

    private String shortText = "Datos del cliente: ";
    private String timeStamp;

    private PDFPresenter presenter;

    Unbinder unbinder;


    public PDFFragment(Client client, Route route, Messenger messenger, Bitmap signature1) {
        this.client = client;
        this.route = route;
        this.messenger = messenger;
        this.bitmap = signature1;
        presenter = new PDFPresenterImpl(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pdf, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.onCreate();
        presenter.getDeltailRoute(route.getIdproducto());
        presenter.updateStated(route.getId());
        return view;
    }

    @Override
    public void generarPDF() {
        templatePDF = new TemplatePDF(getActivity().getApplicationContext());
        templatePDF.openDocument();


        templatePDF.addMetaData("Cliente: " + client.getCedula(), "Entrega: " + timeStamp, "Mensajero: " + messenger.getCedula());
        encabezado();

        tablaCliente();
        tablaRecibe();
        tablaProducto();
        firmaCliente();

        tablaMensajero();


        templatePDF.closeDocument();

        pdfView();
    }


    private void encabezado() {
        templatePDF.addParagraph("\n\n\n");
        timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        Drawable d = getResources().getDrawable(R.drawable.logo);
        BitmapDrawable bitDw = ((BitmapDrawable) d);
        Bitmap bmp = bitDw.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        templatePDF.addImg(stream);
        templatePDF.addTitles("Empresa de mensajeria ", "Pedido Número: "+route.getConsecutivo(), timeStamp.toString());
        templatePDF.addTitle("CERTIFICADO DE ENTREGA");

        templatePDF.addTitlesSingle("\nNo. Guia:  " + route.getId());
        templatePDF.addTitlesSingle("Estado ruta: Servicio efectivo o entregado.");
    }

    private void tablaCliente() {

        templatePDF.separator();
        String[] header = {"\n" + shortText, "\n"};
        ArrayList<String[]> rows = new ArrayList<>();

        rows.add(new String[]{" ", " "});
        rows.add(new String[]{"Nombre:  " + client.getNombre(), "Cedula: " + client.getCedula()});
        rows.add(new String[]{"Dirección:  " + route.getDireccion(), "Cita: " + route.getFecha_entrega()});

        templatePDF.createTable(header, rows);
        templatePDF.separator();
    }

    private void tablaRecibe() {
        String[] header = {"\nDatos de quien recibe:", "\nFecha:     " + timeStamp};
        ArrayList<String[]> rows = new ArrayList<>();

        rows.add(new String[]{" ", " "});
        rows.add(new String[]{"Nombre:  " + client.getNombre(), "Cedula: " + client.getCedula()});
        rows.add(new String[]{"Dirección:  " + route.getDireccion(), "Recibe titular: SI"});

        templatePDF.createTable(header, rows);
        templatePDF.separator();
    }

    private void tablaProducto() {
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        double precioProducto = Double.parseDouble(product.getPrecio());
        double precioEnvio = Double.parseDouble(route.getValor_recaudo());
        double total = precioProducto + precioEnvio;

        String[] header = {"\nDatos del producto:", " "};
        ArrayList<String[]> rows = new ArrayList<>();

        rows.add(new String[]{" ", " "});
        rows.add(new String[]{"Ref.                Descripción             ", "Cantidad         Precio Ext."});
        rows.add(new String[]{product.getId() + "                   " + product.getNombre(), "       " + route.getCantidad() + "               $ " + formatea.format(precioProducto)});
        rows.add(new String[]{"                                            ", "Subtotal:          $ " + formatea.format(precioProducto)});
        rows.add(new String[]{"                                            ", "    Envío:          $ " + formatea.format(precioEnvio)});
        rows.add(new String[]{"                                            ", "     Total:          $ " + formatea.format(total)});

        templatePDF.createTable(header, rows);
        templatePDF.addParagraph("\n");
        templatePDF.separator();

    }

    private void firmaCliente() {
        templatePDF.addParagraph("\n\n");
        templatePDF.addTitlesSingle("\nEl producto fue entregado satisfactoriamente.");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        this.bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        templatePDF.addImgFirm(stream);

        templatePDF.addParagraph("Firma del Cliente");

    }

    private void tablaMensajero() {

        templatePDF.separator();
        String[] header = {"\n" + "Registró Mensajero:", "\n"};
        ArrayList<String[]> rows = new ArrayList<>();

        rows.add(new String[]{" ", " "});
        rows.add(new String[]{"Nombre:  " + messenger.getNombre() + " " + messenger.getApellido(), "Cedula: " + messenger.getCedula()});
        rows.add(new String[]{"Cargo:  " + messenger.getCargo(), "Correo: " + messenger.getEmail()});

        templatePDF.createTable(header, rows);
    }


    public void pdfView() {
        file = new File(templatePDF.getRoutePDF());
        pdfView.fromFile(file).spacing(2).load();
    }


    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }



    @Override
    public void setContentProduct(Product product) {
        this.product = product;
    }

    @Override
    public void msgServices(String error) {
        Snackbar.make(this.contairne, "" + error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void hideElements() {
        btnFinish.setVisibility(View.GONE);
        imageFirm.setVisibility(View.GONE);
        fabShares.setVisibility(View.GONE);
        pdfView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBarDeliver.setVisibility(View.VISIBLE);

    }

    @Override
    public void showElements() {
        btnFinish.setVisibility(View.VISIBLE);
        imageFirm.setVisibility(View.VISIBLE);
        fabShares.setVisibility(View.VISIBLE);
        pdfView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBarDeliver.setVisibility(View.GONE);
    }

    @OnClick(R.id.fabShares)
    public void sharesPDF(){
        enviarPDF(this.client.getCorreo());
    }

    @OnClick(R.id.btnFinish)
    public void finish(){
        msgServices("Ruta entregada Exitosamente");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, new ServicesRouteFragment()).addToBackStack(null).commit();
    }

    public void enviarPDF(String email){
        verificar();
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {email, email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "CERTIFICADO DE ENTREGA MENSAJERIA");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email contiene el certificado de entrega del producto.");
        emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = Uri.fromFile(file);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(emailIntent, "Escoja un Proveedor de Email"));
    }

    private void verificar() {
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
