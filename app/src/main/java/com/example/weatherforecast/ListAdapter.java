package com.example.weatherforecast;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<MyViewHolder> {
    ArrayList<String>places=new ArrayList<>();
    public ListAdapter() {
        places.add("Warszawa");
        places.add("Kraków");
        places.add("Poznań");
        places.add("Gdańsk");
        places.add("Szczecin");
        places.add("Wrocław");
        places.add("Katowice");
        places.add("Gdynia");
        places.add("Białystok");
        places.add("Lublin");
        places.add("Bydgoszcz");
        places.add("Lublin");
        places.add("Łódź");
        places.add("Rzeszów");
        places.add("Olsztyn");
        places.add("Toruń");
        places.add("Kielce");
        places.add("Zielona Góra");
        places.add("Gliwice");
        places.add("Rybnik");
        places.add("Sosnowiec");
        places.add("Bielsko-Biała");
        places.add("Bytom");
        places.add("Zabrze");
        places.add("Dąbrowa Górnicza");
        places.add("Gorzów Wielkopolski");
        places.add("Chorzów");
        places.add("Tychy");
        places.add("Koszalin");
        places.add("Opole");
        places.add("Ruda Śląska");
        places.add("Tarnów");
        places.add("Wałbrzych");
        places.add("Włocławek");
        places.add("Elbląg");
        places.add("Jelenia Góra");
        places.add("Kalisz");
        places.add("Konin");
        places.add("Legnica");
        places.add("Leszno");
        places.add("Mysłowice");
        places.add("Ostrów Wielkopolski");
        places.add("Piotrków Trybunalski");
        places.add("Płock");
        places.add("Pabianice");
        places.add("Piła");
        places.add("Piekary Śląskie");
        places.add("Poznań");
        places.add("Przemyśl");
        places.add("Pruszków");
        places.add("Radom");
        places.add("Rybnik");
        places.add("Ruda Śląska");
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(parent.getContext(),R.layout.fragment_ulubione,null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        for (int i=0;i<places.size();i++){
            holder.getmTextView().setText(places.get(position));
        }

    }


    @Override
    public int getItemCount() {
        return places.size();
    }
}
