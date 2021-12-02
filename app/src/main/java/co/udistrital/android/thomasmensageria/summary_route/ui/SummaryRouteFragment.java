package co.udistrital.android.thomasmensageria.summary_route.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.udistrital.android.thomasmensageria.R;
import co.udistrital.android.thomasmensageria.summary_route.SummaryRoutePresenter;
import co.udistrital.android.thomasmensageria.summary_route.SummaryRoutePresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryRouteFragment extends Fragment implements SummaryRouteView {


    @BindView(R.id.graphBar)
    BarChart graphBar;
    @BindView(R.id.failed)
    TextView tVFailed;
    @BindView(R.id.slopes)
    TextView tVSlopes;
    @BindView(R.id.recaptures)
    TextView tVRecaptures;
    @BindView(R.id.services)
    TextView tVServices;

    @BindView(R.id.elements)
    LinearLayout elements;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tVEffective)
    TextView tVEffective;
    @BindView(R.id.summary)
    ScrollView summary;
    @BindView(R.id.msg)
    TextView msg;
    private SummaryRoutePresenter presenter;

    int effective;
    int failed;
    int slopes;

    Unbinder unbinder;

    public SummaryRouteFragment() {
        presenter = new SummaryRoutePresenterImpl(this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_summary_route, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onCreate();
        presenter.consultRoutes();
    }

    @Override
    public void showElements() {
        elements.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideElements() {
        elements.setVisibility(View.GONE);
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
    public void graphReports() {

        ArrayList<BarEntry> entradas = new ArrayList<>();

        entradas.add(new BarEntry(0, effective, "Efectivos"));
        entradas.add(new BarEntry(1, failed, "Fallidos"));
        entradas.add(new BarEntry(2, slopes, "Pendientes"));


        BarDataSet dataSet = new BarDataSet(entradas, "Grafica: Servicios de rutas.");
        BarData data = new BarData(dataSet);

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setFormLineWidth(0.9f);

        graphBar.setData(data);
        graphBar.setFitBars(true);
        graphBar.invalidate();

    }

    @Override
    public void reportEffective(int num) {
        this.effective = num;
        tVEffective.setText("   " + this.effective);
    }

    @Override
    public void reportFailed(int num) {
        this.failed = num;
        tVFailed.setText("   " + failed);
    }

    @Override
    public void reportSlopes(int num) {
        this.slopes = num;
        tVSlopes.setText("   " + slopes);
    }

    @Override
    public void reportRecaptures(float num) {
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        tVRecaptures.setText("$ " + formatea.format(num));

    }

    @Override
    public void reportService() {
        tVServices.setText("   " + (effective + failed + slopes));
    }


    @Override
    public void onMsgError(String error) {
        Snackbar.make(summary, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void msg() {
        if (slopes != 0){
            msg.setText("Pendiente cerrar rutas");
            msg.setGravity(Gravity.LEFT);
        }
        else {
            msg.setText("Felicidades jornada laboral terminada");
            presenter.closeRoutes();
        }

    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
