package com.epsi.gostyle;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.epsi.gostyle.model.Promotion;
import com.epsi.gostyle.utils.ItemClickSupport;
import com.epsi.gostyle.utils.JSONConversionUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Comparator;
import java.util.List;

public class ListePromos extends Activity {

    private Gson gson;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<Promotion> listePromotions = null;
    private String resultContents = null;

    public List<Promotion> getListPromo() {
        return listePromotions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Initialize AndroidNetworking and GSON object
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.enableLogging();
        gson = new Gson();

        // Récuperation Code in string
        resultContents = this.getIntent().getStringExtra("resultContents");

        // Call API Promotions
        callApiPromotion(resultContents);

        // Set view
        setContentView(R.layout.liste_promotions);
        recyclerView = findViewById(R.id.my_recycler_view);

        // Attach ItemClick on RecyclerView
        this.configureOnClickRecyclerView();

        // Init emppty recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(myAdapter);

    }

    // Call api to get promotions
    private void callApiPromotion(final String param) {

        AndroidNetworking.get("https://msprapi.herokuapp.com/api/promotions/{promoParam}")
            .addPathParameter("promoParam", param)
            .build()
            .getAsJSONArray(new JSONArrayRequestListener() {
                @Override
                public void onResponse(JSONArray response) {
                    // Init gestion erreurs
                    Boolean errorApi = false;
                    String errorMsg;
                    try {
                        listePromotions = new JSONConversionUtils().jsonConversion(response);
                        if (listePromotions!= null){
                            // tri par date
                            listePromotions.sort(Comparator.comparing(Promotion::getValidate_end_date).reversed());
                            listePromotions = new JSONConversionUtils().setValidePromotion(listePromotions);
                        }else {
                            errorApi = true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (errorApi == false) {
                        // Si pas d'erreur, set recycler view avec liste promos
                        myAdapter = new MyAdapter(listePromotions);
                        recyclerView.setAdapter(myAdapter);
                    }else {
                        // Si erreur détectée toast and redirect to home
                        try {
                            errorMsg = response.get(0).toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            errorMsg = e.toString();
                        }

                        errorMsg = new JSONConversionUtils().CleanUp(errorMsg);
                        printInfo(errorMsg);
                        redirectHome();
                    }
                }

                @Override
                public void onError(ANError error) {
                    // Gestion error
                    printInfo("Impossible d'accéder au serveur");
                    redirectHome();
                }
            });
    }

    // Redirection Home page Main Activity
    public void redirectHome() {
        Intent myIntent = new Intent(ListePromos.this, MainActivity.class);
        ListePromos.this.startActivity(myIntent);
    }

    // Print message on toast
    public void printInfo(String message) {
        Toast.makeText(ListePromos.this, message, Toast.LENGTH_LONG).show();
    }

    // Configure item click on RecyclerView
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.liste_promotions_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        try {
                            // Get promotion and Redirect to link of Promotion
                            Promotion p = myAdapter.getPromotionItem(position);
                            if (p.getIsValid() == true) {
                                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                                httpIntent.setData(Uri.parse(p.getLink()));
                                startActivity(httpIntent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            printInfo("Une erreur est survenue");
                        }
                    }
                });
    }
}
