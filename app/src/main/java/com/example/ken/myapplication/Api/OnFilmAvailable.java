package com.example.ken.myapplication.Api;


import com.example.ken.myapplication.Domain.Film;



public interface OnFilmAvailable {
    void onFilmAvailable(Film film);
    void onFilmsLoaded();
}