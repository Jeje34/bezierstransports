package com.bezierstransports.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bezierstransports.beans.BeziersTransports;
import com.bezierstransports.beans.City;
import com.bezierstransports.beans.Line;
import com.bezierstransports.beans.LineStation;
import com.bezierstransports.beans.Period;
import com.bezierstransports.beans.Schedule;
import com.bezierstransports.beans.Station;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Servlet extends HttpServlet {

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		BeziersTransports b = new BeziersTransports();
		
		/* CITIES */
		City beziers = new City(1, "Béziers");
		City vlb = new City(2, "Villeneuve-lès-Béziers");
		City cers = new City(3, "Cers");
		
		/* PERIODS */
		Period ls = new Period(1, "LS", "Hiver");
		Period d = new Period(2, "D", "Hiver");
		Period lv = new Period(3, "LV", "Hiver");
		
		/* LINES */
		Line l1 = new Line("1","De Gaulle - Les Grands Hommes");
		Line l2 = new Line("2","De Gaulle - La Courondelle");
		Line l2E = new Line("2E","De Gaulle - Boujan/Libron");
		Line l3 = new Line("3","Gare SNCF - Pôle Commercial Ginieisse");
		Line l4 = new Line("4","De Gaulle - Mousquetaires");
		Line l5 = new Line("5","De Gaulle - CC Montimaran");
		Line l6 = new Line("6","De Gaulle - Capiscol Ouest");
		Line l7 = new Line("7","De Gaulle - Lycée Jean Moulin");
		Line l8 = new Line("8","De Gaulle - Avenue de la Devèze");
		Line l9 = new Line("9","De Gaulle - Les 9 Ecluses");
		Line l10 = new Line("10","De Gaulle - Capiscol Est");
		Line l11 = new Line("11","De Gaulle - Marcel Pagnol");
		Line l12 = new Line("12","De Gaulle - Les Oiseaux");
		Line l13 = new Line("13","De Gaulle - Gasquinoy");
		Line l14A = new Line("14A","De Gaulle - Bonaval Haut");
		Line l14B = new Line("14B","De Gaulle - Bonaval");
		Line l15 = new Line("15","De Gaulle - Villeneuve - Cers");
		Line l16 = new Line("16","De Gaulle - Sauvian - Valras");
		Line l17 = new Line("17","De Gaulle - PA La Baume");
		Line l18 = new Line("18","De Gaulle - SNCF - Capiscol Est");
		Line l19 = new Line("19","De Gaulle - Lignan/Orb - Corneilhan");
		Line l20 = new Line("20","De Gaulle - Lignan/Orb - Lieuran - Espondeilhan");
		Line l21 = new Line("21","De Gaulle - Bassan - Servian");
		Line l23 = new Line("23","De Gaulle - Zatopek");
		
		/* STATIONS */		
		Station deGaulle = new Station(1, "De Gaulle", 43.345759f, 3.217365f, beziers);
		Station clemenceau = new Station(2, "Clémenceau", 43.348474f, 3.220128f, beziers);
		Station jussieu = new Station(3, "Jussieu", 43.352514f, 3.220945f, beziers);
		Station magnelli = new Station(4, "Magnelli", 43.359852f, 3.225895f, beziers);
		Station dahlias = new Station(5, "Dahlias", 43.355705f, 3.226299f, beziers);
		Station morisot = new Station(6, "Morisot", 43.355378f, 3.226813f, beziers);
		Station laurencin = new Station(7, "Laurencin", 43.359355f, 3.229490f, beziers);
		Station renoir = new Station(8, "Renoir", 43.359365f, 3.234486f, beziers);
		Station grandsHommes = new Station(9, "Grands Hommes", 43.362602f, 3.231993f, beziers);
		Station pompiers = new Station(10, "Pompiers", 43.351317f, 3.219821f, beziers);
		Station gareDuNord = new Station(11, "Gare du Nord", 0.0f, 0.0f, beziers);
		Station bassins = new Station(12, "Bassins", 0.0f, 0.0f, beziers);
		Station seurat = new Station(13, "Seurat", 0.0f, 0.0f, beziers);
		Station citeMunicipale = new Station(14, "Cité Municipale", 0.0f, 0.0f, beziers);
		Station hemingway = new Station(15, "Hemingway", 0.0f, 0.0f, beziers);
		Station courondelle = new Station(16, "Courondelle", 0.0f, 0.0f, beziers);
		Station avenueDoc = new Station(17, "Avenue d'Oc", 0.0f, 0.0f, beziers);
		Station cooperativeBeziers = new Station(18, "Cave Coopérative", 0.0f, 0.0f, beziers);
		Station hotelDuDepartement = new Station(19, "Hôtel du Département", 0.0f, 0.0f, beziers);
		Station robert = new Station(20, "Robert", 0.0f, 0.0f, beziers);
		Station cohl = new Station(21, "Cohl", 0.0f, 0.0f, beziers);
		Station laDaubinelle = new Station(22, "La Daubinelle", 0.0f, 0.0f, beziers);
		Station myosotis = new Station(23, "Myosotis", 0.0f, 0.0f, beziers);
		Station cureDArs = new Station(24, "Curé d'Ars", 0.0f, 0.0f, beziers);
		Station utrillo = new Station(25, "Utrillo", 0.0f, 0.0f, beziers);		
		Station puel = new Station(26, "Puel", 0.0f, 0.0f, beziers);
		Station liberte = new Station(27, "Liberté", 0.0f, 0.0f, beziers);
		Station victorHugo = new Station(28, "Victor Hugo", 0.0f, 0.0f, beziers);
		Station gagarine = new Station(29, "Gagarine", 0.0f, 0.0f, beziers);
		Station manet = new Station(30, "Manet", 0.0f, 0.0f, beziers);
		Station croixDePoumeyrac = new Station(31, "Croix de Poumeyrac", 0.0f, 0.0f, beziers);		
		Station lautrec = new Station(32, "T.Lautrec", 0.0f, 0.0f, beziers);
		Station picasso = new Station(33, "Picasso", 0.0f, 0.0f, beziers);
		Station rocagel = new Station(34, "Rocagel", 0.0f, 0.0f, beziers);
		Station puivert = new Station(35, "Puivert", 0.0f, 0.0f, beziers);
		Station gareSNCF = new Station(36, "Gare SNCF", 0.0f, 0.0f, beziers);
		Station pontNoir = new Station(37, "Pont Noir", 0.0f, 0.0f, beziers);
		Station quaiOuest2 = new Station(38, "Quai Ouest 2", 0.0f, 0.0f, beziers);
		Station lesPoetes = new Station(39, "Les Poètes", 0.0f, 0.0f, beziers);
		Station musset = new Station(40, "Musset", 0.0f, 0.0f, beziers);
		Station alleesPaulRiquet = new Station(41, "Allées Paul Riquet", 0.0f, 0.0f, beziers);
		Station grandsMagasins = new Station(42, "Grands Magasins", 0.0f, 0.0f, beziers);
		Station gareRoutiere = new Station(43, "Gare Routière", 0.0f, 0.0f, beziers);
		Station trinite = new Station(44, "Trinité", 0.0f, 0.0f, beziers);
		Station mediatheque = new Station(45, "Médiathèque - 14 juillet", 0.0f, 0.0f, beziers);
		Station universite  = new Station(46, "Université", 0.0f, 0.0f, beziers);
		Station maffre  = new Station(47, "A. Maffre", 0.0f, 0.0f, beziers);
		Station mai45 = new Station(48, "8 Mai 45", 0.0f, 0.0f, beziers);
		Station lesseps  = new Station(49, "Lesseps", 0.0f, 0.0f, beziers);
		Station fabre = new Station(50, "J. Fabre", 0.0f, 0.0f, beziers);
		Station beausejour  = new Station(51, "	Beauséjour", 0.0f, 0.0f, beziers);
		Station ladoux = new Station(52, "J. Ladoux", 0.0f, 0.0f, beziers);
		Station orangeraie = new Station(53, "Orangeraie", 0.0f, 0.0f, beziers);
		Station laLorraine = new Station(54, "La Lorraine", 0.0f, 0.0f, beziers);
		Station limousin = new Station(55, "Limousin", 0.0f, 0.0f, beziers);
		Station supermarche = new Station(56, "Supermarché", 0.0f, 0.0f, beziers);
		Station cortot = new Station(57, "Cortot", 0.0f, 0.0f, beziers);
		Station milhaud = new Station(58, "Milhaud", 0.0f, 0.0f, beziers);
		Station delibes = new Station(59, "Léo Delibes", 0.0f, 0.0f, beziers);
		Station lesOliviers = new Station(60, "Les Oliviers", 0.0f, 0.0f, beziers);
		Station badones  = new Station(61, "Badones", 0.0f, 0.0f, beziers);
		Station laCrouzette = new Station(62, "La Crouzette", 0.0f, 0.0f, beziers);
		Station laDomitienne = new Station(63, "La Domitienne", 0.0f, 0.0f, beziers);
		Station pomarede = new Station(64, "Pomarède", 0.0f, 0.0f, beziers);
		Station gramme = new Station(65, "Gramme", 0.0f, 0.0f, beziers);
		Station ginieisse = new Station(66, "Ginieisse", 0.0f, 0.0f, beziers);
		Station poleCommercialGinieisse = new Station(67, "Pôle Commercial Ginieisse", 0.0f, 0.0f, beziers);
		Station laRoseraie = new Station(68, "La Roseraie", 0.0f, 0.0f, beziers);
		Station quaiOuest1 = new Station(69, "Quai Ouest 1", 0.0f, 0.0f, beziers);
		
		Station pontAutoroute = new Station(201, "Pont Autoroute", 0.0f, 0.0f, vlb);
		Station cimetiere = new Station(202, "Cimetière", 0.0f, 0.0f, vlb);
		Station centre = new Station(203, "Centre", 0.0f, 0.0f, vlb);
		Station poleMediterranee = new Station(204, "Pôle Méditerranée", 0.0f, 0.0f, vlb);
		
		Station saintPrivat = new Station(251, "Saint Privat", 0.0f, 0.0f, cers);
		Station cooperativeCers = new Station(252, "Cave Coopérative", 0.0f, 0.0f, cers);

		
		// LIGNE 1 - ALLER		
		LineStation ls1a1 = new LineStation(l1, deGaulle, "A", 1);
		LineStation ls1a2 = new LineStation(l1, clemenceau, "A", 2);
		LineStation ls1a3 = new LineStation(l1, pompiers, "A", 3);
		LineStation ls1a4 = new LineStation(l1, jussieu, "A", 4);
		LineStation ls1a5 = new LineStation(l1, hotelDuDepartement, "A", 5);
		LineStation ls1a6 = new LineStation(l1, robert, "A", 6);
		LineStation ls1a7 = new LineStation(l1, cohl, "A", 7);
		LineStation ls1a8 = new LineStation(l1, magnelli, "A", 8);		
		LineStation ls1a9 = new LineStation(l1, laDaubinelle, "A", 9);
		LineStation ls1a10 = new LineStation(l1, myosotis, "A", 10);
		LineStation ls1a11 = new LineStation(l1, dahlias, "A", 11);		
		LineStation ls1a12 = new LineStation(l1, morisot, "A", 12);		
		LineStation ls1a13 = new LineStation(l1, cureDArs, "A", 13);
		LineStation ls1a14 = new LineStation(l1, utrillo, "A", 14);		
		LineStation ls1a15 = new LineStation(l1, laurencin, "A", 15);		
		LineStation ls1a16 = new LineStation(l1, manet, "A", 16);
		LineStation ls1a17 = new LineStation(l1, croixDePoumeyrac, "A", 17);		
		LineStation ls1a18 = new LineStation(l1, renoir, "A", 18);
		LineStation ls1a19 = new LineStation(l1, hemingway, "A", 19);
		LineStation ls1a20 = new LineStation(l1, grandsHommes, "A", 20);
		
		// LIGNE 1 - RETOUR
		LineStation ls1r1 = new LineStation(l1, grandsHommes, "R", 1);
		LineStation ls1r2 = new LineStation(l1, hemingway, "R", 2);
		LineStation ls1r3 = new LineStation(l1, renoir, "R", 3);
		LineStation ls1r4 = new LineStation(l1, croixDePoumeyrac, "R", 4);
		LineStation ls1r5 = new LineStation(l1, manet, "R", 5);
		LineStation ls1r6 = new LineStation(l1, laurencin, "R", 6);		
		LineStation ls1r7 = new LineStation(l1, utrillo, "R", 7);
		LineStation ls1r8 = new LineStation(l1, cureDArs, "R", 8);
		LineStation ls1r9 = new LineStation(l1, dahlias, "R", 9);
		LineStation ls1r10 = new LineStation(l1, myosotis, "R", 10);
		LineStation ls1r11 = new LineStation(l1, laDaubinelle, "R", 11);		
		LineStation ls1r12 = new LineStation(l1, magnelli, "R", 12);
		LineStation ls1r13 = new LineStation(l1, robert, "R", 13);		
		LineStation ls1r14 = new LineStation(l1, hotelDuDepartement, "R", 14);
		LineStation ls1r15 = new LineStation(l1, jussieu, "R", 15);
		LineStation ls1r16 = new LineStation(l1, pompiers, "R", 16);
		LineStation ls1r17 = new LineStation(l1, clemenceau, "R", 17);
		LineStation ls1r18 = new LineStation(l1, deGaulle, "R", 18);
		
		// LIGNE 2 - ALLER
		LineStation ls2a1 = new LineStation(l2, deGaulle, "A", 1);
		LineStation ls2a2 = new LineStation(l2, clemenceau, "A", 2);
		LineStation ls2a3 = new LineStation(l2, gareDuNord, "A", 3);
		LineStation ls2a4 = new LineStation(l2, bassins, "A", 4);
		LineStation ls2a5 = new LineStation(l2, seurat, "A", 5);
		LineStation ls2a6 = new LineStation(l2, lautrec, "A", 6);
		LineStation ls2a7 = new LineStation(l2, picasso, "A", 7);		
		LineStation ls2a8 = new LineStation(l2, citeMunicipale, "A", 8);		
		LineStation ls2a9 = new LineStation(l2, rocagel, "A", 9);
		LineStation ls2a10 = new LineStation(l2, puivert, "A", 10);		
		LineStation ls2a11 = new LineStation(l2, hemingway, "A", 11);
		LineStation ls2a12 = new LineStation(l2, grandsHommes, "A", 12);
		LineStation ls2a13 = new LineStation(l2, courondelle, "A", 13);
		
		// LIGNE 2 - RETOUR
		LineStation ls2r1 = new LineStation(l2, courondelle, "R", 1);
		LineStation ls2r2 = new LineStation(l2, grandsHommes, "R", 2);
		LineStation ls2r3 = new LineStation(l2, hemingway, "R", 3);
		LineStation ls2r4 = new LineStation(l2, puivert, "R", 4);
		LineStation ls2r5 = new LineStation(l2, rocagel, "R", 5);		
		LineStation ls2r6 = new LineStation(l2, citeMunicipale, "R", 6);
		LineStation ls2r7 = new LineStation(l2, picasso, "R", 7);
		LineStation ls2r8 = new LineStation(l2, lautrec, "R", 8);		
		LineStation ls2r9 = new LineStation(l2, seurat, "R", 9);		
		LineStation ls2r10 = new LineStation(l2, avenueDoc, "R", 10);
		LineStation ls2r11 = new LineStation(l2, cooperativeBeziers, "R", 11);
		LineStation ls2r12 = new LineStation(l2, gareDuNord, "R", 12);
		LineStation ls2r13 = new LineStation(l2, clemenceau, "R", 13);
		LineStation ls2r14 = new LineStation(l2, deGaulle, "R", 14);
		
		// LIGNE 3 - ALLER
		LineStation ls3a1 = new LineStation(l3, gareSNCF, "A", 1);
		LineStation ls3a2 = new LineStation(l3, pontNoir, "A", 2);
		LineStation ls3a3 = new LineStation(l3, quaiOuest2, "A", 3);
		LineStation ls3a4 = new LineStation(l3, lesPoetes, "A", 4);
		LineStation ls3a5 = new LineStation(l3, musset, "A", 5);
		LineStation ls3a6 = new LineStation(l3, alleesPaulRiquet, "A", 6);
		LineStation ls3a7 = new LineStation(l3, grandsMagasins, "A", 7);
		LineStation ls3a8 = new LineStation(l3, gareRoutiere, "A", 8);		
		LineStation ls3a9 = new LineStation(l3, trinite, "A", 9);
		LineStation ls3a10 = new LineStation(l3, mediatheque, "A", 10);
		LineStation ls3a11 = new LineStation(l3, universite , "A", 11);		
		LineStation ls3a12 = new LineStation(l3, maffre, "A", 12);		
		LineStation ls3a13 = new LineStation(l3, mai45 , "A", 13);
		LineStation ls3a14 = new LineStation(l3, lesseps, "A", 14);		
		LineStation ls3a15 = new LineStation(l3, fabre, "A", 15);		
		LineStation ls3a16 = new LineStation(l3, beausejour, "A", 16);
		LineStation ls3a17 = new LineStation(l3, ladoux, "A", 17);		
		LineStation ls3a18 = new LineStation(l3, orangeraie, "A", 18);
		LineStation ls3a19 = new LineStation(l3, laLorraine, "A", 19);
		LineStation ls3a20 = new LineStation(l3, limousin, "A", 20);
		LineStation ls3a21 = new LineStation(l3, supermarche, "A", 21);
		LineStation ls3a22 = new LineStation(l3, cortot, "A", 22);
		LineStation ls3a23 = new LineStation(l3, milhaud, "A", 23);
		LineStation ls3a24 = new LineStation(l3, delibes, "A", 24);
		LineStation ls3a25 = new LineStation(l3, lesOliviers, "A", 25);
		LineStation ls3a26 = new LineStation(l3, badones, "A", 26);
		LineStation ls3a27 = new LineStation(l3, laCrouzette, "A", 27);
		LineStation ls3a28 = new LineStation(l3, laDomitienne , "A", 28);
		LineStation ls3a29 = new LineStation(l3, pomarede, "A", 29);
		LineStation ls3a30 = new LineStation(l3, gramme, "A", 30);
		LineStation ls3a31 = new LineStation(l3, ginieisse , "A", 31);
		LineStation ls3a32 = new LineStation(l3, poleCommercialGinieisse, "A", 32);
		
		// LIGNE 3 - RETOUR
		LineStation ls3r1 = new LineStation(l3, poleCommercialGinieisse, "R", 1);
		LineStation ls3r2 = new LineStation(l3, pomarede, "R", 2);
		LineStation ls3r3 = new LineStation(l3, laDomitienne, "R", 3);
		LineStation ls3r4 = new LineStation(l3, laCrouzette, "R", 4);
		LineStation ls3r5 = new LineStation(l3, badones, "R", 5);
		LineStation ls3r6 = new LineStation(l3, lesOliviers, "R", 6);
		LineStation ls3r7 = new LineStation(l3, laRoseraie, "R", 7);
		LineStation ls3r8 = new LineStation(l3, cortot, "R", 8);
		LineStation ls3r9 = new LineStation(l3, supermarche, "R", 9);
		LineStation ls3r10 = new LineStation(l3, limousin, "R", 10);		
		LineStation ls3r11 = new LineStation(l3, laLorraine , "R", 11);		
		LineStation ls3r12 = new LineStation(l3, ladoux, "R", 12);
		LineStation ls3r13 = new LineStation(l3, beausejour , "R", 13);
		LineStation ls3r14 = new LineStation(l3, fabre, "R", 14);		
		LineStation ls3r15 = new LineStation(l3, lesseps, "R", 15);	
		LineStation ls3r16 = new LineStation(l3, mai45, "R", 16);		
		LineStation ls3r17 = new LineStation(l3, maffre, "R", 17);		
		LineStation ls3r18 = new LineStation(l3, universite, "R", 18);
		LineStation ls3r19 = new LineStation(l3, trinite, "R", 19);
		LineStation ls3r20 = new LineStation(l3, gareRoutiere, "R", 20);
		LineStation ls3r21 = new LineStation(l3, grandsMagasins, "R", 21);
		LineStation ls3r22 = new LineStation(l3, alleesPaulRiquet, "R", 22);
		LineStation ls3r23 = new LineStation(l3, musset, "R", 23);
		LineStation ls3r24 = new LineStation(l3, lesPoetes, "R", 24);
		LineStation ls3r25 = new LineStation(l3, quaiOuest1, "R", 25);
		LineStation ls3r26 = new LineStation(l3, gareSNCF, "R", 26);
				
		// LIGNE 15 - ALLER
		LineStation ls15a1 = new LineStation(l15, deGaulle, "A", 1);
		LineStation ls15a2 = new LineStation(l15, puel, "A", 2);
		LineStation ls15a3 = new LineStation(l15, liberte, "A", 3);
		LineStation ls15a4 = new LineStation(l15, victorHugo, "A", 4);
		LineStation ls15a5 = new LineStation(l15, gagarine, "A", 5);
		LineStation ls15a6 = new LineStation(l15, pontAutoroute, "A", 6);
		LineStation ls15a7 = new LineStation(l15, cimetiere, "A", 7);
		LineStation ls15a8 = new LineStation(l15, centre, "A", 8);
		LineStation ls15a9 = new LineStation(l15, cimetiere, "A", 9);
		LineStation ls15a10 = new LineStation(l15, poleMediterranee, "A", 10);
		LineStation ls15a11 = new LineStation(l15, saintPrivat, "A", 11);
		LineStation ls15a12 = new LineStation(l15, cooperativeCers, "A", 12);
		
		// LIGNE 15 - RETOUR
		LineStation ls15r1 = new LineStation(l15, cooperativeCers, "R", 1);
		LineStation ls15r2 = new LineStation(l15, saintPrivat, "R", 2);
		LineStation ls15r3 = new LineStation(l15, poleMediterranee, "R", 3);
		LineStation ls15r4 = new LineStation(l15, cimetiere, "R", 4);
		LineStation ls15r5 = new LineStation(l15, centre, "R", 5);
		LineStation ls15r6 = new LineStation(l15, cimetiere, "R", 6);
		LineStation ls15r7 = new LineStation(l15, pontAutoroute, "R", 7);
		LineStation ls15r8 = new LineStation(l15, gagarine, "R", 8);
		LineStation ls15r9 = new LineStation(l15, victorHugo, "R", 9);
		LineStation ls15r10 = new LineStation(l15, liberte, "R", 10);
		LineStation ls15r11 = new LineStation(l15, puel, "R", 11);
		LineStation ls15r12 = new LineStation(l15, deGaulle, "R", 12);
		

		
		Schedule[] array = new Schedule[] {
				
				
			/******************************************************************************************************
			/******************************************LIGNE 3*****************************************************
			/*****************************************************************************************************/
				
			new Schedule(ls3a1, ls, new String[]{"06:30", "06:45", "07:00", "07:15", "07:30", "07:45", "08:00", "08:15",
				"08:30", "08:45", "09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", "11:00",
				"11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45",
				 "14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15",
				 "16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00", "18:15", "18:30", "18:45",
				 "19:00", "19:15", "19:30", "19:45", "20:00"}),

			new Schedule(ls3a2, ls, new String[]{"06:31", "06:46", "07:01", "07:16", "07:31", "07:46", "08:01", "08:16",
				"08:31", "08:46", "09:01", "09:16", "09:31", "09:46", "10:01", "10:16", "10:31", "10:46", "11:01",
				"11:16", "11:31", "11:46", "12:01", "12:16", "12:31", "12:46", "13:01", "13:16", "13:31", "13:46",
				 "14:01", "14:16", "14:31", "14:46", "15:01", "15:16", "15:31", "15:46", "16:01", "16:16",
				 "16:31", "16:46", "17:01", "17:16", "17:31", "17:46", "18:01", "18:16", "18:31", "18:46",
				 "19:01", "19:16", "19:31", "19:46", "20:01"}),
					 
			new Schedule(ls3a3, ls, new String[]{"06:32", "06:47", "07:02", "07:17", "07:32", "07:47", "08:02", "08:17",
				"08:32", "08:47", "09:02", "09:17", "09:32", "09:47", "10:02", "10:17", "10:32", "10:47", "11:02",
				"11:17", "11:32", "11:47", "12:02", "12:17", "12:32", "12:47", "13:02", "13:17", "13:32", "13:47",
				 "14:02", "14:17", "14:32", "14:47", "15:02", "15:17", "15:32", "15:47", "16:02", "16:17",
				 "16:32", "16:47", "17:02", "17:17", "17:32", "17:47", "18:02", "18:17", "18:32", "18:47",
				 "19:02", "19:17", "19:32", "19:47", "20:02"}),
			 
			new Schedule(ls3a4, ls, new String[]{"06:34", "06:49", "07:04", "07:19", "07:34", "07:49", "08:04", "08:19",
				"08:34", "08:49", "09:04", "09:19", "09:34", "09:49", "10:04", "10:19", "10:34", "10:49", "11:04",
				"11:19", "11:34", "11:49", "12:04", "12:19", "12:34", "12:49", "13:04", "13:19", "13:34", "13:49",
				 "14:04", "14:19", "14:34", "14:49", "15:04", "15:19", "15:34", "15:49", "16:04", "16:19",
				 "16:34", "16:49", "17:04", "17:19", "17:34", "17:49", "18:04", "18:19", "18:34", "18:49",
				 "19:04", "19:19", "19:34", "19:49", "20:04"}),
				 
			new Schedule(ls3a5, ls, new String[]{"06:36", "06:51", "07:06", "07:21", "07:36", "07:51", "08:06", "08:21",
				"08:36", "08:51", "09:06", "09:21", "09:36", "09:51", "10:06", "10:21", "10:36", "10:51", "11:06",
				"11:21", "11:36", "11:51", "12:06", "12:21", "12:36", "12:51", "13:06", "13:21", "13:36", "13:51",
				 "14:06", "14:21", "14:36", "14:51", "15:06", "15:21", "15:36", "15:51", "16:06", "16:21",
				 "16:36", "16:51", "17:06", "17:21", "17:36", "17:51", "18:06", "18:21", "18:36", "18:51",
				 "19:06", "19:21", "19:36", "19:51", "20:06"}),
				 
			 new Schedule(ls3a6, ls, new String[]{"06:37", "06:52", "07:07", "07:22", "07:37", "07:52", "08:07", "08:22",
					"08:37", "08:52", "09:07", "09:22", "09:37", "09:52", "10:07", "10:22", "10:37", "10:52", "11:07",
					"11:22", "11:37", "11:52", "12:07", "12:22", "12:37", "12:52", "13:07", "13:22", "13:37", "13:52",
					 "14:07", "14:22", "14:37", "14:52", "15:07", "15:22", "15:37", "15:52", "16:07", "16:22",
					 "16:37", "16:52", "17:07", "17:22", "17:37", "17:52", "18:07", "18:22", "18:37", "18:52",
					 "19:07", "19:22", "19:37", "19:52", "20:07"}),
					 
			 new Schedule(ls3a7, ls, new String[]{"06:38", "06:53", "07:08", "07:23", "07:38", "07:53", "08:08", "08:23",
				"08:38", "08:53", "09:08", "09:23", "09:38", "09:53", "10:08", "10:23", "10:38", "10:53", "11:08",
				"11:23", "11:38", "11:53", "12:08", "12:23", "12:38", "12:53", "13:08", "13:23", "13:38", "13:53",
				 "14:08", "14:23", "14:38", "14:53", "15:08", "15:23", "15:38", "15:53", "16:08", "16:23",
				 "16:38", "16:53", "17:08", "17:23", "17:38", "17:53", "18:08", "18:23", "18:38", "18:53",
				 "19:08", "19:23", "19:38", "19:53", "20:08"}),
				 
			 
					 
			/******************************************************************************************************
			/******************************************LIGNE 1*****************************************************
			/*****************************************************************************************************/
					
			new Schedule(ls1a1, ls, new String[]{"06:55", "07:30", "08:10", "08:50", "09:30", "10:10", "10:50", "11:30",
				"12:10", "12:50", "13:30", "14:10",  "14:50", "15:30", "16:10", "16:50", "17:30", "18:10", "18:50",
				"19:30"}),
			new Schedule(ls1a2, ls, new String[]{"06:56", "07:31", "08:11", "08:51", "09:31", "10:11",
				"10:51", "11:31", "12:11", "12:51", "13:31", "14:11", "14:51", "15:31", "16:11",
				"16:51", "17:31", "18:11", "18:51", "19:31"}),
			new Schedule(ls1a3, ls, new String[]{ "06:57", "07:32", "08:12", "08:52", "09:32",
						"10:12", "10:52", "11:32", "12:12", "12:52", "13:32", "14:12", "14:52", "15:32",
						"16:12", "16:52", "17:32", "18:12", "18:52", "19:32"}),
			new Schedule(ls1a4, ls, new String[]{ "06:58", "07:33", "08:13", "08:53", "09:33",
				"10:13", "10:53", "11:33", "12:13", "12:53", "13:33", "14:13", "14:53", "15:33",
				"16:13", "16:53", "17:33", "18:13", "18:53", "19:33"}),
			new Schedule(ls1a5, ls, new String[]{ "06:58", "07:33", "08:13", "08:53", "09:33",
					"10:13", "10:53", "11:33", "12:13", "12:53", "13:33", "14:13", "14:53", "15:33",
					"16:13", "16:53", "17:33", "18:13", "18:53", "19:33"}),
			new Schedule(ls1a6, ls, new String[]{ "06:59", "07:34", "08:14", "08:54", "09:34",
					"10:14", "10:54", "11:34", "12:14", "12:54", "13:34", "14:14", "14:54", "15:34",
					"16:14", "16:54", "17:34", "18:14", "18:54", "19:34"}),
			new Schedule(ls1a7, ls, new String[]{ "07:00", "07:35" , "08:15", "08:55", "09:35", "10:15",
				"10:52", "11:35", "12:15", "12:55", "13:35", "14:15", "14:55", "15:35", "16:15", "16:55",
				"17:35", "18:15", "18:55", "19:35"}),
			new Schedule(ls1a8, ls, new String[]{ "07:01", "07:36" , "08:16", "08:56", "09:36", "10:16",
				"10:56", "11:36", "12:16", "12:56", "13:36", "14:16", "14:56", "15:36", "16:16", "16:56",
				"17:36", "18:16", "18:56", "19:36"}),
			new Schedule(ls1a9, ls, new String[]{ "07:01", "07:36" , "08:16", "08:56", "09:36", "10:16",
					"10:56", "11:36", "12:16", "12:56", "13:36", "14:16", "14:56", "15:36", "16:16", "16:56",
					"17:36", "18:16", "18:56", "19:36"}),
			new Schedule(ls1a10, ls, new String[]{ "07:02", "07:37" , "08:17", "08:57", "09:37", "10:17",
					"10:57", "11:37", "12:17", "12:57", "13:37", "14:17", "14:57", "15:37", "16:17", "16:57",
					"17:37", "18:17", "18:57", "19:37"}),					
			new Schedule(ls1a11, ls, new String[]{"07:03", "07:38", "08:18", "08:58", "08:38", "10:18", "10:58",
				"11:38", "12:18", "12:58", "13:38", "14:18", "14:58", "15:38", "16:18", "16:58", "17:38",
				"18:18", "18:58", "19:38"}),
			new Schedule(ls1a12, ls, new String[]{"07:03", "07:38", "08:18", "08:58", "08:38", "10:18", "10:58",
					"11:38", "12:18", "12:58", "13:38", "14:18", "14:58", "15:38", "16:18", "16:58", "17:38",
					"18:18", "18:58", "19:38"}),
			new Schedule(ls1a13, ls, new String[]{ "07:04", "07:39", "08:19", "08:59", "09:39", "10:19", "10:59",
				"11:39", "12:19", "12:59", "13:39", "14:19", "14:59", "15:39", "16:19", "16:59", "17:39", "18:19",
				"18:59", "19:39"}),
			new Schedule(ls1a14, ls, new String[]{ "07:05", "07:40", "08:20", "09:00", "09:40", "10:20", "11:00",
					"11:40", "12:20", "13:00", "13:40", "14:20", "15:00", "15:40", "16:20", "17:00", "17:40", "18:20",
					"19:00", "19:40"}),
			new Schedule(ls1a15, ls, new String[]{"07:06", "07:41", "08:21", "09:01", "09:41", "10:21", "11:01",
				"11:41", "12:21", "13:01", "13:41", "14:21", "15:01", "15:41", "16:21", "17:01", "17:41",
				"18:21", "19:01", "19:41"}),
			new Schedule(ls1a16, ls, new String[]{"07:06", "07:41", "08:21", "09:01", "09:41", "10:21", "11:01",
					"11:41", "12:21", "13:01", "13:41", "14:21", "15:01", "15:41", "16:21", "17:01", "17:41",
					"18:21", "19:01", "19:41"}),
			new Schedule(ls1a17, ls, new String[]{"07:07", "07:42", "08:22", "09:02", "09:42", "10:22", "11:02",
					"11:42", "12:22", "13:02", "13:42", "14:22", "15:02", "15:42", "16:22", "17:02", "17:42",
					"18:22", "19:02", "19:42"}),
			new Schedule(ls1a18, ls, new String[]{"07:08", "07:43", "08:23", "09:03", "09:43", "10:23", "11:03",
				"11:43", "12:23", "13:03", "13:43", "14:23", "15:03", "15:43", "16:23", "17:03", "17:43",
				"18:23", "19:03", "19:43"}),
			new Schedule(ls1a19, ls, new String[]{"07:09", "07:44", "08:24", "09:04", "09:44", "10:24", "11:04",
					"11:44", "12:24", "13:04", "13:44", "14:24", "15:04", "15:44", "16:24", "17:04", "17:44",
					"18:24", "19:04", "19:44"}),
			new Schedule(ls1a20, ls, new String[]{"07:11", "07:46", "08:26", "09:06", "09:46", "10:26",
				"11:06", "11:46", "12:26", "13:06", "13:46", "14:26", "15:06", "15:46", "16:26",
				"17:06", "17:46", "18:26", "19:06", "19:46"}),

			
			new Schedule(ls1r1, ls, new String[]{"07:11", "07:46", "08:26", "09:06", "09:46", "10:26",
				"11:06", "11:46", "12:26", "13:06", "13:46", "14:26", "15:06", "15:46", "16:26", "17:06",
				"17:46", "18:26", "19:06"}),
			new Schedule(ls1r2, ls, new String[]{"07:12", "07:47", "08:27", "09:07", "09:47", "10:27",
				"11:07", "11:47", "12:27", "13:07", "13:47", "14:27", "15:07", "15:47", "16:27", "17:07",
				"17:47", "18:27", "19:07"}),
			new Schedule(ls1r3, ls, new String[]{"07:12", "07:47", "08:27", "09:07", "09:47", "10:27",
				"11:07", "11:47", "12:27", "13:07", "13:47", "14:27", "15:07", "15:47", "16:27", "17:07",
				"17:47", "18:27", "19:07"}),
			new Schedule(ls1r4, ls, new String[]{"07:13", "07:48", "08:28", "09:08", "09:48", "10:28",
				"11:08", "11:48", "12:28", "13:08", "13:48", "14:28", "15:08", "15:48", "16:28", "17:08",
				"17:48", "18:28", "19:08"}),
			new Schedule(ls1r5, ls, new String[]{"07:14", "07:49", "08:29", "09:09", "09:49", "10:29",
				"11:09", "11:49", "12:29", "13:09", "13:49", "14:29", "15:09", "15:49", "16:29", "17:09",
				"17:49", "18:29", "19:09"}),
			new Schedule(ls1r6, ls, new String[]{"07:15", "07:50", "08:30", "09:10", "09:50", "10:30",
				"11:10", "11:50", "12:30", "13:10", "13:50", "14:30", "15:10", "15:50", "16:30", "17:10",
				"17:50", "18:30", "19:10"}),
			new Schedule(ls1r7, ls, new String[]{"07:15", "07:50", "08:30", "09:10", "09:50", "10:30",
					"11:10", "11:50", "12:30", "13:10", "13:50", "14:30", "15:10", "15:50", "16:30", "17:10",
					"17:50", "18:30", "19:10"}),						
			new Schedule(ls1r8, ls, new String[]{"07:16", "07:51", "08:31", "09:11", "09:51", "10:31",
					"11:11", "11:51", "12:31", "13:11", "13:51", "14:31", "15:11", "15:51", "16:31", "17:11",
					"17:51", "18:31", "19:11"}),
			new Schedule(ls1r9, ls, new String[]{"07:17", "07:52", "08:32", "09:12", "09:52", "10:32",
				"11:12", "11:52", "12:32", "13:12", "13:52", "14:32", "15:12", "15:52", "16:32",
				"17:12", "17:52", "18:32", "19:12"}),
			new Schedule(ls1r10, ls, new String[]{"07:17", "07:52", "08:32", "09:12", "09:52", "10:32",
					"11:12", "11:52", "12:32", "13:12", "13:52", "14:32", "15:12", "15:52", "16:32",
					"17:12", "17:52", "18:32", "19:12"}),
			new Schedule(ls1r11, ls, new String[]{"07:18", "07:53", "08:33", "09:13", "09:53", "10:33",
					"11:13", "11:53", "12:33", "13:13", "13:53", "14:33", "15:13", "15:53", "16:33",
					"17:13", "17:53", "18:33", "19:13"}),
			new Schedule(ls1r12, ls, new String[]{"07:19", "07:54", "08:34", "09:14", "09:54", "10:34", "11:14",
				"11:54", "12:34", "13:14", "13:54", "14:34", "15:14", "15:54", "16:34", "17:14", "17:54",
				"18:34", "19:14"}),
			new Schedule(ls1r13, ls, new String[]{"07:20", "07:55", "08:35", "09:15", "09:55", "10:35", "11:15",
					"11:55", "12:35", "13:15", "13:55", "14:35", "15:15", "15:55", "16:35", "17:15", "17:55",
					"18:35", "19:15"}),
			new Schedule(ls1r14, ls, new String[]{"07:22", "07:57", "08:37", "09:17", "09:57", "10:37", "11:17",
					"11:57", "12:37", "13:17", "13:57", "14:37", "15:17", "15:57", "16:37", "17:17", "17:57",
					"18:37", "19:17"}),
			new Schedule(ls1r15, ls, new String[]{"07:23", "07:58", "08:38", "09:18", "09:58", "10:38", "11:18",
					"11:58", "12:38", "13:18", "13:58", "14:38", "15:18", "15:58", "16:38", "17:18", "17:58",
					"18:38", "19:18"}),
			new Schedule(ls1r16, ls, new String[]{"07:23", "07:58", "08:38", "09:18", "09:58", "10:38", "11:18",
						"11:58", "12:38", "13:18", "13:58", "14:38", "15:18", "15:58", "16:38", "17:18", "17:58",
						"18:38", "19:18"}),
			new Schedule(ls1r17, ls, new String[]{"07:25", "08:00", "08:40", "09:20", "10:00", "10:40", "11:20",
					"12:00", "12:40", "13:20", "14:00", "14:40", "15:20", "16:00", "16:40", "17:20", "18:00",
					"18:40", "19:20"}),				
			new Schedule(ls1r18, ls, new String[]{"07:30", "08:02", "08:42", "09:22", "10:02", "10:42", "11:22", "12:02",
				"12:42", "13:22", "14:02", "14:42", "15:22", "16:02", "16:42", "17:22", "18:02", "18:42", "19:22"}),

			new Schedule(ls1a1, d, new String[]{"09:25", "10:50", "13:30", "14:55", "16:20", "17:45"}),
			new Schedule(ls1a2, d, new String[]{"09:25", "10:50", "13:30", "14:55", "16:20", "17:45"}),
			new Schedule(ls1a3, d,  new String[]{"09:26", "10:51", "13:31", "14:56", "16:21", "17:46"}),
			new Schedule(ls1a4, d, new String[]{"09:27", "10:52", "13:32", "14:57", "16:22", "17:47"}),
			new Schedule(ls1a5, d, new String[]{"09:27", "10:52", "13:32", "14:57", "16:22", "17:47"}),
			new Schedule(ls1a6, d, new String[]{"09:28", "10:53", "13:33", "14:58", "16:23", "17:48"}),
			new Schedule(ls1a7, d, new String[]{"09:29", "10:54", "13:34", "14:59", "16:24", "17:49"}),
			new Schedule(ls1a8, d, new String[]{"09:30", "10:55", "13:35", "15:00", "16:25", "17:50"}),
			new Schedule(ls1a9, d, new String[]{"09:30", "10:55", "13:35", "15:00", "16:25", "17:50"}),
			new Schedule(ls1a10, d, new String[]{"09:30", "10:55", "13:35", "15:00", "16:25", "17:50"}),
			new Schedule(ls1a11, d, new String[]{"09:31", "10:56", "13:36", "15:01", "16:26", "17:51"}),
			new Schedule(ls1a12, d, new String[]{"09:31", "10:56", "13:36", "15:01", "16:26", "17:51"}),
			new Schedule(ls1a13, d, new String[]{ "09:32", "10:57", "13:37", "15:02", "16:27", "17:52"}),
			new Schedule(ls1a14, d, new String[]{ "09:32", "10:57", "13:37", "15:02", "16:27", "17:52"}),
			new Schedule(ls1a15, d, new String[]{"09:33", "10:58", "13:38", "15:03", "16:28", "17:53"}),
			new Schedule(ls1a16, d, new String[]{"09:33", "10:58", "13:38", "15:03", "16:28", "17:53"}),
			new Schedule(ls1a17, d, new String[]{"09:34", "10:59", "13:39", "15:04", "16:29", "17:54"}),
			new Schedule(ls1a18, d, new String[]{"09:35", "11:00", "13:40", "15:05", "16:30", "17:55"}),
			new Schedule(ls1a19, d, new String[]{"09:35", "11:00", "13:40", "15:05", "16:30", "17:55"}),
			new Schedule(ls1a20, d, new String[]{"09:37", "11:02", "13:42", "15:07", "16:32", "17:57"}),
				
			new Schedule(ls1r1, d, new String[]{"09:37", "11:02", "13:42", "15:07", "16:32", "17:57"}),
			new Schedule(ls1r2, d, new String[]{"09:37", "11:02", "13:42", "15:07", "16:32", "17:57"}),
			new Schedule(ls1r3, d, new String[]{"09:38", "11:03", "13:43", "15:08", "16:33", "17:58"}),
			new Schedule(ls1r4, d, new String[]{"09:38", "11:03", "13:43", "15:08", "16:33", "17:58"}),
			new Schedule(ls1r5, d, new String[]{"09:38", "11:03", "13:43", "15:08", "16:33", "17:58"}),
			new Schedule(ls1r6, d, new String[]{"09:39", "11:04", "13:44", "15:09", "16:34", "17:59"}),
			new Schedule(ls1r7, d, new String[]{"09:39", "11:04", "13:44", "15:09", "16:34", "17:59"}),
			new Schedule(ls1r8, d, new String[]{"09:39", "11:04", "13:44", "15:09", "16:34", "17:59"}),
			new Schedule(ls1r9, d, new String[]{"09:40", "11:05", "13:45", "15:10", "16:35", "18:00"}),
			new Schedule(ls1r10, d, new String[]{"09:41", "11:06", "13:46", "15:11", "16:36", "18:01"}),
			new Schedule(ls1r11, d, new String[]{"09:41", "11:06", "13:46", "15:11", "16:36", "18:01"}),
			new Schedule(ls1r12, d, new String[]{"09:42", "11:07", "13:47", "15:12", "16:37", "18:02"}),
			new Schedule(ls1r13, d, new String[]{"09:43", "11:08", "13:48", "15:13", "16:38", "18:03"}),
			new Schedule(ls1r14, d, new String[]{"09:44", "11:09", "13:49", "15:14", "16:39", "18:04"}),
			new Schedule(ls1r15, d, new String[]{"09:45", "11:10", "13:50", "15:15", "16:40", "18:05"}),
			new Schedule(ls1r16, d, new String[]{"09:45", "11:10", "13:50", "15:15", "16:40", "18:05"}),
			new Schedule(ls1r17, d, new String[]{"09:47", "11:12", "13:52", "15:17", "16:42", "18:07"}),
			new Schedule(ls1r18, d, new String[]{"09:48", "11:13", "13:53", "15:18", "16:43", "18:08"}),
			
			
		/******************************************************************************************************
		/******************************************LIGNE 2*****************************************************
		/*****************************************************************************************************/
			
			new Schedule(ls2a1, ls, new String[]{"07:10", "07:50", "08:30", "09:10", "09:50", "10:30", "11:10", "11:50",
				"12:30", "13:10", "13:50", "14:30", "15:10", "15:50", "16:30", "17:10", "17:50", "18:30", "19:10", "19:45"}),
			new Schedule(ls2a2, ls, new String[]{"07:11", "07:51", "08:31", "09:11", "09:51", "10:31", "11:11", "11:51",
				"12:31", "13:11", "13:51", "14:31", "15:11", "15:51", "16:31", "17:11", "17:51", "18:31", "19:11", "19:46"}),
			new Schedule(ls2a3, ls, new String[]{"07:12", "07:52", "08:32", "09:12", "09:52", "10:32", "11:12", "11:52",
				"12:32", "13:12", "13:52", "14:32", "15:12", "15:52", "16:32", "17:12", "17:52", "18:32", "19:12", "19:47"}),			
			new Schedule(ls2a4, ls, new String[]{"07:13", "07:53", "08:33", "09:13", "09:53", "10:33", "11:13", "11:53",
				"12:33", "13:13", "13:53", "14:33", "15:13", "15:53", "16:33", "17:13", "17:53", "18:33", "19:13", "19:48"}),
			new Schedule(ls2a5, ls, new String[]{"07:15", "07:55", "08:35", "09:15", "09:55", "10:35", "11:15", "11:55",
				"12:35", "13:15", "13:55", "14:35", "15:15", "15:55", "16:35", "17:15", "17:55", "18:35", "19:15", "19:50"}),
			new Schedule(ls2a6, ls, new String[]{"07:16", "07:56", "08:36", "09:16", "09:56", "10:36", "11:16", "11:56",
				"12:36", "13:16", "13:56", "14:36", "15:16", "15:56", "16:36", "17:16", "17:56", "18:36", "19:16", "19:51"}),
			new Schedule(ls2a7, ls, new String[]{"07:17", "07:57", "08:37", "09:17", "09:57", "10:37", "11:17", "11:57",
				"12:37", "13:17", "13:57", "14:37", "15:17", "15:57", "16:37", "17:17", "17:57", "18:37", "19:17", "19:52"}),				
			new Schedule(ls2a8, ls, new String[]{"07:19", "07:59", "08:39", "09:19", "09:59", "10:39", "11:19", "11:59",
				"12:39", "13:19", "13:59", "14:39", "15:19", "15:59", "16:39", "17:19", "17:59", "18:39", "19:19", "19:54"}),			
			new Schedule(ls2a9, ls, new String[]{"07:20", "08:00", "08:40", "09:20", "10:00", "10:40", "11:20", "12:00",
				"12:40", "13:20", "14:00", "14:40", "15:20", "16:00", "16:40", "17:20", "18:00", "18:40", "19:20", "19:55"}),
			new Schedule(ls2a10, ls, new String[]{"07:20", "08:00", "08:40", "09:20", "10:00", "10:40", "11:20", "12:00",
				"12:40", "13:20", "14:00", "14:40", "15:20", "16:00", "16:40", "17:20", "18:00", "18:40", "19:20", "19:55"}),
			new Schedule(ls2a11, ls, new String[]{"07:21", "08:01", "08:41", "09:21", "10:01", "10:41", "11:21", "12:01",
				"12:41", "13:21", "14:01", "14:41", "15:21", "16:01", "16:41", "17:21", "18:01", "18:41", "19:21", "19:56"}),			
			new Schedule(ls2a12, ls, new String[]{"07:23", "08:03", "08:43", "09:23", "10:03", "10:43", "11:23", "12:03",
				"12:43", "13:23", "14:03", "14:43", "15:23", "16:03", "16:43", "17:23", "18:03", "18:43", "19:23", "19:58"}),			
			new Schedule(ls2a13, ls, new String[]{"07:26", "08:06", "08:46", "09:26", "10:06", "10:46", "11:26", "12:06",
				"12:46", "13:26", "14:06", "14:46", "15:26", "16:06", "16:46", "17:26", "18:06", "18:46", "19:26", "20:01"}),
			

			new Schedule(ls2r1, ls, new String[]{"07:27", "08:07", "08:47", "09:27", "10:07", "10:47", "11:27", "12:07",
				"12:47", "13:27", "14:07", "14:47", "15:27", "16:07", "16:47", "17:27", "18:07", "18:47", "19:27", "20:02"}),
			new Schedule(ls2r2, ls, new String[]{"07:29", "08:09", "08:49", "09:29", "10:09", "10:49", "11:29", "12:09",
				"12:49", "13:29", "14:09", "14:49", "15:29", "16:09", "16:49", "17:29", "18:09", "18:49", "19:29", "20:04"}),
			new Schedule(ls2r3, ls, new String[]{"07:30", "08:10", "08:50", "09:30", "10:10", "10:50", "11:30", "12:10",
				"12:50", "13:30", "14:10", "14:50", "15:30", "16:10", "16:50", "17:30", "18:10", "18:50", "19:30", "20:05"}),	
			new Schedule(ls2r4, ls, new String[]{"07:31", "08:11", "08:51", "09:31", "10:11", "10:51", "11:31", "12:11",
				"12:51", "13:31", "14:11", "14:51", "15:31", "16:11", "16:51", "17:31", "18:11", "18:51", "19:31", "20:06"}),
			new Schedule(ls2r5, ls, new String[]{"07:32", "08:12", "08:52", "09:32", "10:12", "10:52", "11:32", "12:12",
				"12:52", "13:32", "14:12", "14:52", "15:32", "16:12", "16:52", "17:32", "18:12", "18:52", "19:32", "20:07"}),
			new Schedule(ls2r6, ls, new String[]{"07:33", "08:13", "08:53", "09:33", "10:13", "10:53", "11:33", "12:13",
				"12:53", "13:33", "14:13", "14:53", "15:33", "16:13", "16:53", "17:33", "18:13", "18:53", "19:33", "20:08"}),
			new Schedule(ls2r7, ls, new String[]{"07:34", "08:14", "08:54", "09:34", "10:14", "10:54", "11:34", "12:14",
					"12:54", "13:34", "14:14", "14:54", "15:34", "16:14", "16:54", "17:34", "18:14", "18:54", "19:34", "20:09"}),
			new Schedule(ls2r8, ls, new String[]{"07:34", "08:14", "08:54", "09:34", "10:14", "10:54", "11:34", "12:14",
					"12:54", "13:34", "14:14", "14:54", "15:34", "16:14", "16:54", "17:34", "18:14", "18:54", "19:34", "20:09"}),
			new Schedule(ls2r9, ls, new String[]{"07:36", "08:16", "08:56", "09:36", "10:16", "10:56", "11:36", "12:16",
				"12:56", "13:36", "14:16", "14:56", "15:36", "16:16", "16:56", "17:36", "18:16", "18:56", "19:36", "20:11"}),
			new Schedule(ls2r10, ls, new String[]{"07:36", "08:16", "08:56", "09:36", "10:16", "10:56", "11:36", "12:16",
					"12:56", "13:36", "14:16", "14:56", "15:36", "16:16", "16:56", "17:36", "18:16", "18:56", "19:36", "20:11"}),
			new Schedule(ls2r11, ls, new String[]{"07:37", "08:17", "08:57", "09:37", "10:17", "10:57", "11:37", "12:17", "12:57",
				"13:37", "14:17", "14:57", "15:37", "16:17", "16:57", "17:37", "18:17", "18:57", "19:37", "20:12"}),
			new Schedule(ls2r12, ls, new String[]{"07:39", "08:19", "08:59", "09:39", "10:19", "10:59", "11:39", "12:19",
				"12:59", "13:39", "14:19", "14:59", "15:39", "16:19", "16:59", "17:39", "18:19", "18:59", "19:39", "20:14"}),
			new Schedule(ls2r13, ls, new String[]{"07:40", "08:20", "09:00", "09:40", "10:20", "11:00", "11:40", "12:20", "13:00",
				"13:40", "14:20", "15:00", "15:40", "16:20", "17:00", "17:40", "18:20", "19:00", "19:40", "20:15"}),
			new Schedule(ls2r14, ls, new String[]{"07:42", "08:22", "09:02", "09:42", "10:22", "11:02", "11:42", "12:22", "13:02",
				"13:42", "14:22", "15:02", "15:42", "16:22", "17:02", "17:42", "18:22", "19:02", "19:42", "20:17"}),
			
				
			/******************************************************************************************************
			/******************************************LIGNE 15****************************************************
			/*****************************************************************************************************/
				
			new Schedule(ls15a1, ls, new String[]{"09:00", "10:00", "11:00", "13:30", "14:30", "15:30", "16:30", "17:30",
					"18:30", "19:30"}),
			new Schedule(ls15a2, ls, new String[]{"09:00", "10:00", "11:00", "13:30", "14:30", "15:30", "16:30", "17:30",
					"18:30", "19:30"}),
			new Schedule(ls15a3, ls, new String[]{"09:01", "10:01", "11:01", "13:31", "14:31", "15:31", "16:31", "17:31",
					"18:31", "19:31"}),
			new Schedule(ls15a4, ls, new String[]{"09:02", "10:02", "11:02", "13:32", "14:32", "15:32", "16:32", "17:32", 
					"18:32", "19:32"}),
			new Schedule(ls15a5, ls, new String[]{"09:03", "10:03", "11:03", "13:33", "14:33", "15:33", "16:33", "17:33",
					"18:33", "19:33"}),
			new Schedule(ls15a6, ls, new String[]{"09:10", "10:10", "11:10", "13:40", "14:40", "15:40", "16:40", "17:40",
					"18:40", "19:40"}),
			new Schedule(ls15a7, ls, new String[]{"09:12", "10:12", "11:12", "13:42", "14:42", "15:42", "16:42", "17:42",
					"18:42", "19:42"}),
			new Schedule(ls15a8, ls, new String[]{"09:14", "10:14", "11:14", "13:44", "14:44", "15:44", "16:44", "17:44",
					"18:44","19:44"}),				
			new Schedule(ls15a9, ls, new String[]{"09:15", "10:15", "11:15", "13:45", "14:45", "15:45", "16:45", "17:45",
					"18:45", "19:45"}),
			new Schedule(ls15a10, ls, new String[]{"09:18", "10:18", "11:18", "13:48", "14:48", "15:48", "16:48", "17:48",
					"18:48", "19:48"}),
			new Schedule(ls15a11, ls, new String[]{"09:22", "10:22", "11:22", "13:52", "14:52", "15:52", "16:52", "17:52",
					"18:52", "19:52"}),
			new Schedule(ls15a12, ls, new String[]{"09:24", "10:24", "11:24", "13:54", "14:54", "15:54", "16:54", "17:54",
					"18:54", "19:54"}),
					
			new Schedule(ls15r1, ls, new String[]{"09:24", "10:24", "11:24", "13:54", "14:54", "15:54", "16:54", "17:54",
					"18:54", "19:54"}),
			new Schedule(ls15r2, ls, new String[]{"09:25", "10:25", "11:25", "13:55", "14:55", "15:55", "16:55", "17:55", 
					"18:55", "19:55"}),
			new Schedule(ls15r3, ls, new String[]{"09:29", "10:29", "11:29", "13:59", "14:59", "15:59", "16:59", "17:59",
					"18:59", "19:59"}),
			new Schedule(ls15r4, ls, new String[]{"09:31", "10:31", "11:31", "14:01", "15:01", "16:01", "17:01", "18:01",
					"19:01", "20:01"}),
			new Schedule(ls15r5, ls, new String[]{"09:33", "10:33", "11:33", "14:03", "15:03", "16:03", "17:03", "18:03",
					"19:03", "20:03"}),
			new Schedule(ls15r6, ls, new String[]{"09:34", "10:34", "11:34", "14:04", "15:04", "16:04", "17:04", "18:04",
					"19:04", "20:04"}),
			new Schedule(ls15r7, ls, new String[]{"09:38", "10:38", "11:38", "14:08", "15:08", "16:08", "17:08", "18:08",
					"19:08", "20:08"}),
			new Schedule(ls15r8, ls, new String[]{"09:48", "10:48", "11:48", "14:18", "15:18", "16:18", "17:18", "18:18",
					"19:18", "20:18"}),
			new Schedule(ls15r9, ls, new String[]{"09:48", "10:48", "11:48", "14:18", "15:18", "16:18", "17:18", "18:18",
					"19:18", "20:18"}),
			new Schedule(ls15r10, ls, new String[]{"09:49", "10:49", "11:49", "14:19", "15:19", "16:19", "17:19", "18:19",
					"19:19", "20:19"}),
			new Schedule(ls15r11, ls, new String[]{"09:50", "10:50", "11:50", "14:20", "15:20", "16:20", "17:20", "18:20",
					"19:20","20:20"}),
			new Schedule(ls15r12, ls, new String[]{"09:52", "10:52", "11:52", "14:22", "15:22", "16:22", "17:22", "18:22",
					"19:22", "20:22"}),
					
			new Schedule(ls15a1, d, new String[]{"09:00", "10:00", "11:00", "13:30", "14:30", "15:30", "16:30", "17:30"}),
			new Schedule(ls15a2, d, new String[]{"09:00", "10:00", "11:00", "13:30", "14:30", "15:30", "16:30", "17:30"}),
			new Schedule(ls15a3, d, new String[]{"09:01", "10:01", "11:01", "13:31", "14:31", "15:31", "16:31", "17:31"}),
			new Schedule(ls15a4, d, new String[]{"09:02", "10:02", "11:02", "13:32", "14:32", "15:32", "16:32", "17:32"}),
			new Schedule(ls15a5, d, new String[]{"09:03", "10:03", "11:03", "13:33", "14:33", "15:33", "16:33", "17:33"}),
			new Schedule(ls15a6, d, new String[]{"09:10", "10:10", "11:10", "13:40", "14:40", "15:40", "16:40", "17:40"}),
			new Schedule(ls15a7, d, new String[]{"09:12", "10:12", "11:12", "13:42", "14:42", "15:42", "16:42", "17:42"}),
			new Schedule(ls15a8, d, new String[]{"09:14", "10:14", "11:14", "13:44", "14:44", "15:44", "16:44", "17:44"}),
			new Schedule(ls15a9, d, new String[]{"09:15", "10:15", "11:15", "13:45", "14:45", "15:45", "16:45", "17:45"}),
			new Schedule(ls15a10, d, new String[]{"09:18", "10:18", "11:18", "13:48", "14:48", "15:48", "16:48", "17:48"}),
			new Schedule(ls15a11, d, new String[]{"09:22", "10:22", "11:22", "13:52", "14:52", "15:52", "16:52", "17:52"}),
			new Schedule(ls15a12, d, new String[]{"09:24", "10:24", "11:24", "13:54", "14:54", "15:54", "16:54", "17:54"}),
			
			new Schedule(ls15r1, d, new String[]{"09:24", "10:24", "11:24", "13:54", "14:54", "15:54", "16:54", "17:54"}),
			new Schedule(ls15r2, d, new String[]{"09:25", "10:25", "11:25", "13:55", "14:55", "15:55", "16:55", "17:55"}),
			new Schedule(ls15r3, d, new String[]{"09:29", "10:29", "11:29", "13:59", "14:59", "15:59", "16:59", "17:59"}),
			new Schedule(ls15r4, d, new String[]{"09:31", "10:31", "11:31", "14:01", "15:01", "16:01", "17:01", "18:01"}),
			new Schedule(ls15r5, d, new String[]{"09:33", "10:33", "11:33", "14:03", "15:03", "16:03", "17:03", "18:03"}),
			new Schedule(ls15r6, d, new String[]{"09:34", "10:34", "11:34", "14:04", "15:04", "16:04", "17:04", "18:04"}),
			new Schedule(ls15r7, d, new String[]{"09:38", "10:38", "11:38", "14:08", "15:08", "16:08", "17:08", "18:08"}),
			new Schedule(ls15r8, d, new String[]{"09:48", "10:48", "11:48", "14:18", "15:18", "16:18", "17:18", "18:18"}),
			new Schedule(ls15r9, d, new String[]{"09:48", "10:48", "11:48", "14:18", "15:18", "16:18", "17:18", "18:18"}),
			new Schedule(ls15r10, d, new String[]{"09:49", "10:49", "11:49", "14:19", "15:19", "16:19", "17:19", "18:19"}),
			new Schedule(ls15r11, d, new String[]{"09:50", "10:50", "11:50", "14:20", "15:20", "16:20", "17:20", "18:20"}),
			new Schedule(ls15r12, d, new String[]{"09:52", "10:52", "11:52", "14:22", "15:22", "16:22", "17:22", "18:22"}),
			
			
				
		};
		
		b.schedulesArray.addAll(Arrays.asList(array));
		GsonBuilder builder = new GsonBuilder()/*.setPrettyPrinting()*/;
		Gson gson = builder.create();
		String json = gson.toJson(b);  
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json);
		
    }

}
