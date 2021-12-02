package co.udistrital.android.thomasmensageria.add_photo.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.add_firm.AddFirmFragment;
import co.udistrital.android.thomasmensageria.add_photo.AddPhotoPresenter;
import co.udistrital.android.thomasmensageria.add_photo.AddPhotoPresenterImpl;
import co.udistrital.android.thomasmensageria.entities.Client;
import co.udistrital.android.thomasmensageria.entities.Messenger;
import co.udistrital.android.thomasmensageria.entities.Route;
import co.udistrital.android.thomasmensageria.generate_pdf.ui.PDFFragment;
import co.udistrital.android.thomasmensageria.show_photo.ui.ShowPhoto;

import static android.app.Activity.RESULT_OK;



@SuppressLint("ValidFragment")
public class AddPhotoFragment extends Fragment implements AddPhotoView {

    private static final int REQUEST_PICTURE = 1;
    @BindView(R.id.recyclerViewPhoto)
    RecyclerView recyclerViewPhoto;
    @BindView(R.id.progressBarPhoto)
    ProgressBar progressBarPhoto;
    @BindView(R.id.fabPhoto)
    FloatingActionButton fabPhoto;
    @BindView(R.id.btnSiguente)
    Button btnSiguente;
    @BindView(R.id.containerphoto)
    CoordinatorLayout containerphoto;

    private String photoPath;

    private Route route;
    private Client client;
    private View view;
    private AddPhotoPresenter presenter;
    private ShowPhoto showPhoto;

    Unbinder unbinder;


    @SuppressLint("ValidFragment")
    public AddPhotoFragment(Client client, Route route) {
        this.client = client;
        this.route = route;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_photo,  container, false);
        unbinder = ButterKnife.bind(this, view);
        showPhoto = new ShowPhoto(this, recyclerViewPhoto, progressBarPhoto);
        presenter = new AddPhotoPresenterImpl(this);
        presenter.onCreate();
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICTURE) {
            if (resultCode == RESULT_OK) {
                boolean fromCamera = (data == null || data.getData() == null);
                if (fromCamera) {
                    addToGallery();
                } else {
                    photoPath = getRealPathFromURI(data.getData());
                }
                presenter.uploadPhoto(this.route.getId(), photoPath);
            }
        }
    }

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

    @OnClick(R.id.btnSiguente)
    public void btnSiguente(){
        if ( showPhoto.getAdapter().getItemCount()> 0) {
            Messenger mensajero = showPhoto.getMensajero();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content, new AddFirmFragment(this.client, this.route, mensajero)).addToBackStack(null).commit();
            //fragmentManager.beginTransaction().replace(R.id.content, new PDFFragment(this.client, this.route, mensajero)).addToBackStack(null).commit();
        }else{
            showSnackbar("Aun no se ha registrado soporte fotografico");
        }
    }

    @OnClick(R.id.fabPhoto)
    public void takePicture() {

        Intent chooserIntent = null;
        List<Intent> intentList = new ArrayList<>();
        Intent pickIntect = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("return-data", true);

        File photoFile = getFile();
        if (photoFile != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                intentList = addIntentsToList(intentList, cameraIntent);
            }
        }
        if (pickIntect.resolveActivity(getActivity().getPackageManager()) != null) {
            intentList = addIntentsToList(intentList, pickIntect);
        }

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    getString(R.string.addphoto_message_picture_source));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }
        if (chooserIntent != null) {
            startActivityForResult(chooserIntent, REQUEST_PICTURE);
        }
    }

    private File getFile() {
        File photoFile = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        photoFile = new File(storageDir, imageFileName);

        photoPath = photoFile.getAbsolutePath();

        return photoFile;
    }

    private List<Intent> addIntentsToList(List<Intent> list, Intent intent) {
        List<ResolveInfo> resolveInfos = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetIntent = new Intent(intent);
            targetIntent.setPackage(packageName);
            list.add(targetIntent);
        }
        return list;
    }


    private void addToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(photoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result = null;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();// captura la ruta directa
        } else {
            if (contentURI.toString().contains("mediakey")) {
                cursor.close();
                try {
                    //photo remote
                    File file = File.createTempFile("tempImg", ".jpg", getActivity().getCacheDir());
                    InputStream input = getActivity().getContentResolver().openInputStream(contentURI);
                    OutputStream output = new FileOutputStream(file);

                    try {
                        byte[] buffer = new byte[4 * 1024];
                        int read;

                        while ((read = input.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }
                        output.flush();
                        result = file.getAbsolutePath();
                    } finally {
                        output.close();
                        input.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                cursor.moveToFirst();
                int dataColum = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(dataColum);
                cursor.close();
            }
        }
        return result;
    }


    public void showSnackbar(String msg) {
        Snackbar.make(containerphoto, msg, Snackbar.LENGTH_SHORT).show();
    }

    private void showSnackbar(int strResouce) {
        Snackbar.make(containerphoto, strResouce, Snackbar.LENGTH_SHORT).show();
    }

    public void refresh() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.containerphoto, new AddPhotoFragment(this.client, this.route)).commit();
    }

    public void hideBottoms(){
        fabPhoto.setVisibility(View.GONE);
        btnSiguente.setVisibility(View.GONE);
    }

    public void showBottoms(){
        fabPhoto.setVisibility(View.VISIBLE);
        btnSiguente.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        showPhoto.onDestroy();
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        showPhoto.onDestroy();
        super.onDestroyView();
    }

    public Route getRoute() {
        return route;
    }
}
