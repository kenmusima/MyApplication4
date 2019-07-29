package com.example.ken.myapplication.Tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ken.myapplication.Database.TicketDatabase;
import com.example.ken.myapplication.Domain.Ticket;
import com.example.ken.myapplication.R;
import com.example.ken.myapplication.Util.TicketsAdapter;

import java.util.ArrayList;
import java.util.Collections;


public class TicketOverviewFragment extends Fragment {

    private ListView listView;
    private ArrayList<Ticket> ticketArrayList;
    private TicketsAdapter ticketsAdapter;
    private TicketDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_tickets, container, false);
        this.listView = view.findViewById(R.id.ticketListView);

        database = new TicketDatabase(getContext());
        ticketArrayList = database.getAllTickets();
        Collections.reverse(ticketArrayList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("SelectedItem: ", i + "");

                Ticket ticket = ticketArrayList.get(i);

                Intent intent = new Intent(view.getContext(), TicketViewActivity.class);
                intent.putExtra("TICKET_OBJECT", ticket);
                startActivity(intent);
            }
        });

        this.ticketsAdapter = new TicketsAdapter(getContext(), ticketArrayList);
        this.listView.setAdapter(ticketsAdapter);

        // Inflate the layout for this fragment
        return view;
    }

}
