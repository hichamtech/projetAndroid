package com.example.emploiandroid.Models;

public class URLs

{


    public  final String BASE_URL="http://192.168.1.12:8000";
    public  final String URL_LOGIN=BASE_URL+"/api/login_check";
    public  final String URL_LISTECLIENT=BASE_URL+"/api/personnes?roles=ROLE_CLIENT";
    public  final String URL_LISTEMONITEUR=BASE_URL+"/api/personnes?roles=ROLE_MONITEUR";
    public  final String URL_LISTESEANCE=BASE_URL+"/api/seances";

    public  final String URL_BASE_PERSONNE=BASE_URL+"/api/personnes/";







}
