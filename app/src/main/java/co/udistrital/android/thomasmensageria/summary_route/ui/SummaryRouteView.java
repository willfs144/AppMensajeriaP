package co.udistrital.android.thomasmensageria.summary_route.ui;


public interface SummaryRouteView {

    void showElements();
    void hideElements();
    void showProgress();
    void hideProgress();


    void graphReports();
    void reportEffective(int num);
    void reportFailed(int num);
    void reportSlopes(int num);
    void reportRecaptures(float num);
    void reportService();
    void onMsgError(String error);

    void msg();
}
