package co.udistrital.android.thomasmensageria.add_firm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.add_firm.ui.AddFirmView;
import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Messenger;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.generate_pdf.ui.PDFFragment;
import co.udistrital.android.thomasmensageria.help.CaptureSignatureView;



@SuppressLint("ValidFragment")
public class AddFirmFragment extends Fragment implements AddFirmView{


    @BindView(R.id.btnlimpiar)
    Button btnlimpiar;
    @BindView(R.id.btnguardar)
    Button btnguardar;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.ivfirma)
    ImageView ivfirma;
    @BindView(R.id.btnSiguente)
    Button btnSiguente;
    @BindView(R.id.container_firm)
    FrameLayout containerFirm;
    Unbinder unbinder;

    private Route route;
    private Client client;
    private Messenger messenger;
    private View view;
    private CaptureSignatureView mSig;
    private String photoFirmPath;

    private Bitmap signature1;

    private AddFirmPresenter presenter;

    @SuppressLint("ValidFragment")
    public AddFirmFragment(Client client, Route route, Messenger messenger) {
        this.client = client;
        this.route = route;
        this.messenger = messenger;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_add_firm, container, false);
        unbinder = ButterKnife.bind(this, view);


        // implementacion firma.
        mSig = new CaptureSignatureView(this, null);
        linear.addView(mSig, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        presenter = new AddFirmPresenterImpl(this);
        presenter.onCreate();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btnguardar)
    public void guardar() {
        signature1 = mSig.getBitmap();
        linear.setVisibility(View.GONE);
        ivfirma.setVisibility(view.VISIBLE);
        ivfirma.setImageBitmap(signature1);
        guardarFirmPhoto(signature1);
        btnguardar.setVisibility(View.GONE);
        btnSiguente.setVisibility(View.VISIBLE);


        msgServices("Firma Guardada");

    }

    private void guardarFirmPhoto(Bitmap bitmap) {
        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        Uri tempUri = getImageUri(getActivity().getApplicationContext(), bitmap);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        File finalFile = new File(getRealPathFromURI(tempUri));

        presenter.uploadPhoto(this.route.getId(), finalFile.getPath());
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    /*void quitarFondo(){

        String datoTempHex;
        int R, G, B;
        int altura = mSig.getHeight();
        int anchura = mSig.getWidth();

        Bitmap imagenFinal = Bitmap.createBitmap(anchura, altura, Bitmap.Config.ARGB_8888);

        for (int x = 0; x < anchura; x++) {
            for (int y = 0; y < altura; y++) {
                datoTempHex = Integer.toHexString(mSig.getBitmap().getPixel(x,y));
                while (datoTempHex.length() < 8) {
                    datoTempHex = "0" + datoTempHex;
                }

                //****************************************
                R = Integer.parseInt(datoTempHex.substring(2, 4), 16);
                G = Integer.parseInt(datoTempHex.substring(4, 6), 16);
                B = Integer.parseInt(datoTempHex.substring(6, 8), 16);
                //****************************************

                if (Color.RED == R && Color.GREEN == G && Color.BLUE == B) {
                    imagenFinal.setPixel(x, y, 256*256*256*256);
                }else{
                    imagenFinal.setPixel(x, y, 256*16777216-16777216+Integer.parseInt(datoTempHex.substring(2, 8), 16));
                }
            }
        }

        this.ivfirma.setImageBitmap(imagenFinal);

    }*/


    @OnClick(R.id.btnSiguente)
    public void siguiente() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, new PDFFragment(this.client, this.route, this.messenger, this.signature1)).addToBackStack(null).commit();
    }

    @OnClick(R.id.btnlimpiar)
    public void limpiar() {
        ivfirma.setVisibility(view.GONE);
        linear.setVisibility(View.VISIBLE);
        mSig.ClearCanvas();
        btnguardar.setVisibility(View.VISIBLE);
        btnSiguente.setVisibility(view.GONE);
    }

    public void msgServices(String msg) {
        Snackbar.make(containerFirm, "" + msg, Snackbar.LENGTH_LONG).show();
    }

    /*public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }*/

    @Override
    public void onUploadInit() {
        showSnackbar(R.string.addphoto_notice_upload_init);
    }

    @Override
    public void onUploadCompled() {
        showSnackbar(R.string.addphoto_notice_upload_complete);
    }

    @Override
    public void onUploadError(String error) {
        showSnackbar(error);
    }


    @Override
    public void refresh() {

    }

    public void showSnackbar(String msg) {
        Snackbar.make(containerFirm, msg, Snackbar.LENGTH_SHORT).show();
    }

    private void showSnackbar(int strResouce) {
        Snackbar.make(containerFirm, strResouce, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showBottoms() {

    }

    @Override
    public void hideBottoms() {

    }

    public Context getContext() {
        return getActivity().getBaseContext();
    }
}
