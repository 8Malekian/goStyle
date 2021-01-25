package com.epsi.gostyle;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.epsi.gostyle.model.Promotion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Promotion> listePromotions;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView discount, description, datedebutvalidite, datefinvalidite, expired;
        public ImageView image_promotion;
        private boolean promoValide;
        public View view;

        @SuppressLint("ResourceType")
        public MyViewHolder(View v) {
            super(v);
            view = v;
            discount = (TextView) v.findViewById(R.id.discount);
            description = (TextView)v.findViewById(R.id.description);
            datedebutvalidite = (TextView)v.findViewById(R.id.datedebutvalidite);
            datefinvalidite = (TextView)v.findViewById(R.id.datefinvalidite);
            expired = (TextView) v.findViewById(R.id.expired);
            image_promotion = (ImageView) v.findViewById(R.id.image_promotion);
        }

        void display(Promotion promo) throws IOException {

            SimpleDateFormat sdf= new SimpleDateFormat("dd MMM yyyy");
            String startDate =sdf.format(promo.getValidate_start_date());
            String endDate = sdf.format(promo.getValidate_end_date());

            discount.setText(promo.getDiscount());
            description.setText(promo.getDescription());
            datedebutvalidite.setText("du "+ startDate);
            datefinvalidite.setText("au "+ endDate);

            promoValide = promo.getIsValid();
            if(promoValide == false){
                discount.setTextColor(Color.BLACK);
                expired.setText("Expirée");
                datedebutvalidite.setTextColor(0xFFff5555);
                datefinvalidite.setTextColor(0xFFff5555);
                view.setBackgroundColor(0xFFbebebe);
            }
            String image_path = "http://msprapi.herokuapp.com/images/" + promo.getImage_path();
            Glide.with(itemView).load(image_path).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).placeholder(R.drawable.placeholder).into(image_promotion);

        }
    }

    public MyAdapter(List<Promotion> listePromotions)
    {
        this.listePromotions = listePromotions;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View tv = layoutInflater.inflate(R.layout.liste_promotions_item, parent, false);

        MyViewHolder vh = new MyViewHolder(tv);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            holder.display(listePromotions.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listePromotions.size();
    }

    public Promotion getPromotionItem(int position) {
        return this.listePromotions.get(position);
    }
}
