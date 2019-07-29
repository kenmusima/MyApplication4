package com.example.ken.myapplication.Controllers.Main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.example.ken.myapplication.Api.FilmTask;
import com.example.ken.myapplication.Api.OnFilmAvailable;
import com.example.ken.myapplication.Controllers.Main.Main.DetailedActivity;
import com.example.ken.myapplication.Domain.Film;
import com.example.ken.myapplication.Util.FilmGridAdapter;
import com.example.ken.myapplication.R;

import java.util.ArrayList;



public class MainFragment extends Fragment implements OnFilmAvailable, AdapterView.OnItemClickListener, SearchView.OnQueryTextListener, View.OnClickListener {

    private ArrayList<Film> films;
    private ArrayList<Film> filteredFilms;
    private GridView gridview;
    private FilmGridAdapter filmGridAdapter;
    private boolean loaded;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Fragment is not yet loaded
        loaded = false;
        films = new ArrayList<>();
        filteredFilms = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Grid initialize
        gridview = getView().findViewById(R.id.filmGridView);
        gridview.setOnItemClickListener(this);

        //Adapter initialize
        filmGridAdapter = new FilmGridAdapter(getContext(), getLayoutInflater(), filteredFilms);
        gridview.setAdapter(filmGridAdapter);

        //Obtain films if this has not yet been done
        getFilmItems();
    }

    public void getFilmItems() {

        //Empty the film array and remove films from the film task
        films.clear();
        filteredFilms.clear();
        FilmTask task = new FilmTask(this);
        String[] urls = new String[]{FilmTask.filmQueries.popularUrl};
        task.execute(urls);
    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

        // Get the right movie
        Film film = filteredFilms.get(position);

        //Requests to go to a new window with the correct movie object
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailedActivity.class);
        intent.putExtra("FILM_OBJECT",  film);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_films_tab, container, false);
    }

    @Override
    public void onFilmAvailable(Film film) {

        //Film add
        films.add(film);
    }

    @Override
    public void onFilmsLoaded() {

        //1X grid update, better for performance
        filteredFilms.addAll(films);
        filmGridAdapter.notifyDataSetChanged();

        //Fragment loaded, fragment refresh once
        if (!loaded) {
            loaded = true;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    //Filtering
    @Override
    public boolean onQueryTextSubmit(String query) {

        //Toast
        Toast.makeText(getContext(), getString(R.string.action_search_results) + ": '" + query + "'", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        //Empty filtered films
        filteredFilms.clear();

        //View all movies
        for (Film film : films)

            //Does the movie contain the query?
            if (film.getName().toLowerCase().contains(newText.toLowerCase()))

                //Film add
                filteredFilms.add(film);

        //Update view
        filmGridAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

