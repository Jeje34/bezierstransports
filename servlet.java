package com.bezierstransports.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

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

	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		
		BeziersTransports b = new BeziersTransports();

		/* CITIES */
		City beziers = new City(1, "Béziers");
		City vlb = new City(2, "Villeneuve-lès-Béziers");
		City cers = new City(3, "Cers");
		
		/* PERIODS */
		Period ls = new Period(1, "LS", "Hiver");
		Period d = new Period(2, "D", "Hiver");
		Period lv = new Period(3, "LV", "Hiver");
		Period s = new Period(4, "S", "Hiver");
		
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
		Station mai45 = new Station(48, "8 mai 45", 0.0f, 0.0f, beziers);
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
		Station aout22 = new Station(70, "22 août", 0.0f, 0.0f, beziers);
		Station claparede = new Station(71, "E. Claparède", 0.0f, 0.0f, beziers);		
		Station arenesPiscine = new Station(72, "Arènes - Piscine", 0.0f, 0.0f, beziers);
		Station laDullague = new Station(73, "La Dullague", 0.0f, 0.0f, beziers);
		Station docteurMourrut = new Station(74, "Docteur Mourrut", 0.0f, 0.0f, beziers);
		Station mermoz = new Station(75, "L.E.P J. Mermoz", 0.0f, 0.0f, beziers);
		Station jaures = new Station(76, "Jean Jaurès", 0.0f, 0.0f, beziers);
		Station messager = new Station(77, "Messager", 0.0f, 0.0f, beziers);
		Station leProgres = new Station(78, "Le Progrès", 0.0f, 0.0f, beziers);
		Station mousquetaires = new Station(79, "Mousquetaires", 0.0f, 0.0f, beziers);
		Station centreSocial = new Station(80, "Centre Social", 0.0f, 0.0f, beziers);
		Station saintSaens = new Station(81, "Saint Saëns", 0.0f, 0.0f, beziers);
		Station palaisDesCongres = new Station(82, "Palais des Congrès", 0.0f, 0.0f, beziers);
		Station cc2 = new Station(83, "CC Béziers II", 0.0f, 0.0f, beziers);
		Station peyrePauliniere = new Station(84, "Peyre Paulinière", 0.0f, 0.0f, beziers);		
		Station voieDomitienne = new Station(85, "Voie Domitienne", 0.0f, 0.0f, beziers);
		Station nougaro = new Station(86, "Nougaro", 0.0f, 0.0f, beziers);
		Station noyon = new Station(87, "Noyon", 0.0f, 0.0f, beziers);
		Station malafosse = new Station(88, "Malafosse", 0.0f, 0.0f, beziers);
		Station amboisePare = new Station(89, "Amboise Paré", 0.0f, 0.0f, beziers);
		Station hopital1 = new Station(90, "Hôpital 1", 0.0f, 0.0f, beziers);
		Station hopital2 = new Station(91, "Hôpital 2", 0.0f, 0.0f, beziers);
		Station capendeguy = new Station(92, "Capendeguy", 0.0f, 0.0f, beziers);
		Station laGayonne = new Station(93, "La Gayonne", 0.0f, 0.0f, beziers);
		Station monteCassino = new Station(94, "Monté Cassino", 0.0f, 0.0f, beziers);
		Station birHakeim = new Station(95, "Bir Hakeim", 0.0f, 0.0f, beziers);
		Station montimaran1 = new Station(96, "Montimaran 1", 0.0f, 0.0f, beziers);
		Station ccMontimaran = new Station(97, "CC Montimaran", 0.0f, 0.0f, beziers);
				
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
		
		String[] h1_a_ls = new String[]{"06:55", "07:30", "08:10", "08:50", "09:30", "10:10", "10:50", "11:30",
				"12:10", "12:50", "13:30", "14:10",  "14:50", "15:30", "16:10", "16:50", "17:30", "18:10", "18:50",
				"19:30"};
		
		String[] h1_a_d = new String[]{"09:25", "10:50", "13:30", "14:55", "16:20", "17:45"};
		
		
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
		
		String[] h1_r_ls = new String[]{"07:11", "07:46", "08:26", "09:06", "09:46", "10:26",
				"11:06", "11:46", "12:26", "13:06", "13:46", "14:26", "15:06", "15:46", "16:26", "17:06",
				"17:46", "18:26", "19:06"};
		
		String[] h1_r_d = new String[]{"09:37", "11:02", "13:42", "15:07", "16:32", "17:57"};
		
		
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
		
		String[] h2_a_ls = new String[]{"07:10", "07:50", "08:30", "09:10", "09:50", "10:30", "11:10", "11:50",
				"12:30", "13:10", "13:50", "14:30", "15:10", "15:50", "16:30", "17:10", "17:50", "18:30", "19:10", "19:45"};
		
		
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
		
		String[] h2_r_ls = new String[]{"07:27", "08:07", "08:47", "09:27", "10:07", "10:47", "11:27", "12:07",
				"12:47", "13:27", "14:07", "14:47", "15:27", "16:07", "16:47", "17:27", "18:07", "18:47", "19:27", "20:02"};
		
		
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
		
		String[] h3_a_ls = new String[]{"06:30", "06:45", "07:00", "07:15", "07:30", "07:45", "08:00", "08:15",
				"08:30", "08:45", "09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", "11:00",
				"11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45",
				 "14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15",
				 "16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00", "18:15", "18:30", "18:45",
				 "19:00", "19:15", "19:30", "19:45", "20:00"};
		
		String[] h3_a_d = new String[]{"08:00", "09:15", "10:30", "11:45", "13:00", "14:15", "15:30", "16:45",
				"18:00", "19:15"};
		
		
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
		
		String[] h3_r_ls = new String[]{"06:13", "06:40", "06:55", "07:10", "07:25", "07:40", "07:55", "08:10",
				"08:25", "08:40", "08:55", "09:10", "09:25", "09:40", "09:55", "10:10", "10:25", "10:40", "10:55",
				"11:10", "11:25", "11:40", "11:55", "12:10", "12:25", "12:40", "12:55", "13:10", "13:25", "13:40",
				 "13:55", "14:10", "14:25", "14:40", "14:55", "15:10", "15:25", "15:40", "15:55", "16:10",
				 "16:25", "16:40", "16:55", "17:10", "17:25", "17:40", "17:55", "18:10", "18:25", "18:40",
				 "18:55", "19:10", "19:25", "19:40", "19:55", "20:10"};
		
		String[] h3_r_d = new String[]{"08:40", "09:55", "11:10", "12:25", "13:40", "14:55", "16:10", "17:25",
				"18:40", "19:55"};
		
		// LIGNE 4 - ALLER		
		LineStation ls4a1 = new LineStation(l4, deGaulle, "A", 1);
		LineStation ls4a2 = new LineStation(l4, aout22, "A", 2);
		LineStation ls4a3 = new LineStation(l4, claparede, "A", 3);
		LineStation ls4a4 = new LineStation(l4, arenesPiscine, "A", 4);
		LineStation ls4a5 = new LineStation(l4, laDullague, "A", 5);
		LineStation ls4a6 = new LineStation(l4, docteurMourrut, "A", 6);
		LineStation ls4a7 = new LineStation(l4, mermoz, "A", 7);
		LineStation ls4a8 = new LineStation(l4, jaures, "A", 8);
		LineStation ls4a9 = new LineStation(l4, messager, "A", 9);
		LineStation ls4a10 = new LineStation(l4, leProgres, "A", 10);
		LineStation ls4a11 = new LineStation(l4, mousquetaires, "A", 11);
		
		String[] h4_a_ls = new String[]{"06:20", "06:45", "07:15", "07:45", "08:15", "08:45", "09:15",
			"09:50", "10:25", "11:00", "11:35", "12:10", "12:40", "13:15", "13:50", "14:25", "15:00",
			"15:35", "16:10", "16:45", "17:15", "17:50", "18:20", "19:00", "19:40"};
					
		
		// LIGNE 4 - RETOUR		
		LineStation ls4r1 = new LineStation(l4, mousquetaires, "R", 1);
		LineStation ls4r2 = new LineStation(l4, centreSocial, "R", 2);
		LineStation ls4r3 = new LineStation(l4, laDullague, "R", 3);
		LineStation ls4r4 = new LineStation(l4, arenesPiscine, "R", 4);
		LineStation ls4r5 = new LineStation(l4, claparede, "R", 5);
		LineStation ls4r6 = new LineStation(l4, saintSaens, "R", 6);
		LineStation ls4r7 = new LineStation(l4, palaisDesCongres, "R", 7);
		LineStation ls4r8 = new LineStation(l4, alleesPaulRiquet, "R", 8);
		LineStation ls4r9 = new LineStation(l4, deGaulle, "R", 9);
				
		String[] h4_r_ls = new String[]{"06:30", "06:55", "07:25", "07:55", "08:25", "08:55", "09:27",
				"10:02", "10:37", "11:12", "11:47", "12:22", "12:52", "13:27", "14:02", "14:37", "15:12",
				"15:47", "16:22", "16:57", "17:27", "18:02", "18:32", "19:12", "19:52"};
		
		
		// LIGNE 5 - ALLER		
		LineStation ls5a1 = new LineStation(l5, deGaulle, "A", 1);
		LineStation ls5a2 = new LineStation(l5, aout22, "A", 2);
		LineStation ls5a3 = new LineStation(l5, claparede, "A", 3);
		LineStation ls5a4 = new LineStation(l5, arenesPiscine, "A", 4);
		LineStation ls5a5 = new LineStation(l5, laDullague, "A", 5);
		LineStation ls5a6 = new LineStation(l5, centreSocial, "A", 6);
		LineStation ls5a7 = new LineStation(l5, cc2, "A", 7);
		LineStation ls5a8 = new LineStation(l5, mousquetaires, "A", 8);
		LineStation ls5a9 = new LineStation(l5, peyrePauliniere, "A", 9);
		LineStation ls5a10 = new LineStation(l5, voieDomitienne, "A", 10);
		LineStation ls5a11 = new LineStation(l5, nougaro, "A", 11);
		LineStation ls5a12 = new LineStation(l5, noyon, "A", 12);
		LineStation ls5a13 = new LineStation(l5, malafosse, "A", 13);
		LineStation ls5a14 = new LineStation(l5, amboisePare, "A", 14);
		LineStation ls5a15 = new LineStation(l5, hopital1, "A", 15);
		LineStation ls5a16 = new LineStation(l5, hopital2, "A", 16);
		LineStation ls5a17 = new LineStation(l5, capendeguy, "A", 17);
		LineStation ls5a18 = new LineStation(l5, laGayonne, "A", 18);
		LineStation ls5a19 = new LineStation(l5, monteCassino, "A", 19);
		LineStation ls5a20 = new LineStation(l5, birHakeim, "A", 20);
		LineStation ls5a21 = new LineStation(l5, montimaran1, "A", 21);
		LineStation ls5a22 = new LineStation(l5, ccMontimaran, "A", 22);
		
		String[] h5_a_lv = new String[]{"06:00", "06:55", "07:20", "07:35", "07:50", "08:05", "08:20",
				"08:35", "08:50", "09:05", "09:20", "09:35", "09:50", "10:05", "10:20", "10:35", "10:50",
				"11:05", "11:20", "11:35", "11:50", "12:05", "12:20", "12:35", "12:50", "13:05", "13:14",
				 "13:42", "14:12", "14:27", "14:50", "15:05", "15:20", "15:35", "15:58", "16:13", "16:28",
				 "16:43", "17:06", "17:21", "17:36", "17:51", "18:14", "18:30", "18:44", "19:00","19:20",
				 "19:36", "20:00"};
		
		String[] h5_r_s = new String[]{"06:00", "06:55", "07:20", "07:35", "07:50", "08:05", "08:20",
				"08:35", "08:50", "09:05", "09:20", "09:35", "09:50", "10:05", "10:20", "10:35", "10:50",
				"11:05", "11:20", "11:35", "11:50", "12:05", "12:20", "12:35", "12:50", "13:05", "13:14",
				 "13:42", "14:12", "14:27", "14:50", "15:05", "15:20", "15:35", "15:58", "16:13", "16:28",
				 "16:43", "17:06", "17:21", "17:36", "17:51", "18:14", "18:30", "18:44", "19:00","19:20",
				 "19:36", "20:00"};
		
		
		// LIGNE 5 - RETOUR		
		LineStation ls5r1 = new LineStation(l5, ccMontimaran , "R", 1);
		LineStation ls5r2 = new LineStation(l5, montimaran1, "R", 2);
		LineStation ls5r3 = new LineStation(l5, birHakeim, "R", 3);
		LineStation ls5r4 = new LineStation(l5, monteCassino, "R", 4);
		LineStation ls5r5 = new LineStation(l5, laGayonne, "R", 5);
		LineStation ls5r6 = new LineStation(l5, capendeguy, "R", 6);
		LineStation ls5r7 = new LineStation(l5, hopital2, "R", 7);
		LineStation ls5r8 = new LineStation(l5, hopital1, "R", 8);
		LineStation ls5r9 = new LineStation(l5, amboisePare, "R", 9);
		LineStation ls5r10 = new LineStation(l5, malafosse, "R", 10);
		LineStation ls5r11 = new LineStation(l5, noyon, "R", 11);
		LineStation ls5r12 = new LineStation(l5, nougaro, "R", 12);
		LineStation ls5r13 = new LineStation(l5, peyrePauliniere, "R", 13);
		LineStation ls5r14 = new LineStation(l5, mousquetaires, "R", 14);
		LineStation ls5r15 = new LineStation(l5, centreSocial, "R", 15);
		LineStation ls5r16 = new LineStation(l5, laDullague, "R", 16);
		LineStation ls5r17 = new LineStation(l5, arenesPiscine, "R", 17);
		LineStation ls5r18 = new LineStation(l5, claparede, "R", 18);
		LineStation ls5r19 = new LineStation(l5, aout22, "R", 19);
		LineStation ls5r20 = new LineStation(l5, deGaulle, "R", 20);
				
		
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
		
		String[] h15_a_ls = new String[]{"09:00", "10:00", "11:00", "13:30", "14:30", "15:30", "16:30", "17:30",
				"18:30", "19:30"};
		
		String[] h15_a_d = new String[]{"09:00", "10:00", "11:00", "13:30", "14:30", "15:30", "16:30", "17:30"};
		
		
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
		
		String[] h15_r_ls = new String[]{"09:24", "10:24", "11:24", "13:54", "14:54", "15:54", "16:54", "17:54",
				"18:54", "19:54"};
		
		String[] h15_r_d = new String[]{"09:24", "10:24", "11:24", "13:54", "14:54", "15:54", "16:54", "17:54"};


		Schedule[] array = null;
		try {
			array = new Schedule[] {
					
				/*************************************************************/
				/*************************LIGNE 1*****************************/
				/*************************************************************/
					
				// LIGNE 1 - ALLER - LS	
				new Schedule(ls1a1, ls, h1_a_ls),
				new Schedule(ls1a2, ls, add(h1_a_ls, 1)),
				new Schedule(ls1a3, ls, add(h1_a_ls, 2)),
				new Schedule(ls1a4, ls, add(h1_a_ls, 3)),
				new Schedule(ls1a5, ls, add(h1_a_ls, 3)),
				new Schedule(ls1a6, ls, add(h1_a_ls, 4)),
				new Schedule(ls1a7, ls, add(h1_a_ls, 5)),
				new Schedule(ls1a8, ls, add(h1_a_ls, 6)),
				new Schedule(ls1a9, ls, add(h1_a_ls, 6)),
				new Schedule(ls1a10, ls, add(h1_a_ls, 7)),
				new Schedule(ls1a11, ls, add(h1_a_ls, 8)),
				new Schedule(ls1a12, ls, add(h1_a_ls, 8)),
				new Schedule(ls1a13, ls, add(h1_a_ls, 9)),
				new Schedule(ls1a14, ls, add(h1_a_ls, 10)),
				new Schedule(ls1a15, ls, add(h1_a_ls, 11)),
				new Schedule(ls1a16, ls, add(h1_a_ls, 11)),
				new Schedule(ls1a17, ls, add(h1_a_ls, 12)),
				new Schedule(ls1a18, ls, add(h1_a_ls, 13)),
				new Schedule(ls1a19, ls, add(h1_a_ls, 14)),
				new Schedule(ls1a20, ls, add(h1_a_ls, 16)),
				
				// LIGNE 1 - ALLER - D
				new Schedule(ls1a1, d, h1_a_d),
				new Schedule(ls1a2, d, h1_a_d),
				new Schedule(ls1a3, d, add(h1_a_d, 1)),
				new Schedule(ls1a4, d, add(h1_a_d, 2)),
				new Schedule(ls1a5, d, add(h1_a_d, 2)),
				new Schedule(ls1a6, d, add(h1_a_d, 3)),
				new Schedule(ls1a7, d, add(h1_a_d, 4)),
				new Schedule(ls1a8, d, add(h1_a_d, 5)),
				new Schedule(ls1a9, d, add(h1_a_d, 5)),
				new Schedule(ls1a10, d, add(h1_a_d, 5)),
				new Schedule(ls1a11, d, add(h1_a_d, 6)),
				new Schedule(ls1a12, d, add(h1_a_d, 6)),
				new Schedule(ls1a13, d, add(h1_a_d, 7)),
				new Schedule(ls1a14, d, add(h1_a_d, 7)),
				new Schedule(ls1a15, d, add(h1_a_d, 8)),
				new Schedule(ls1a16, d, add(h1_a_d, 8)),
				new Schedule(ls1a17, d, add(h1_a_d, 9)),
				new Schedule(ls1a18, d, add(h1_a_d, 10)),
				new Schedule(ls1a19, d, add(h1_a_d, 10)),
				new Schedule(ls1a20, d, add(h1_a_d, 12)),					
				
				// LIGNE 1 - RETOUR - LS
				new Schedule(ls1r1, ls, h1_r_ls),
				new Schedule(ls1r2, ls, add(h1_r_ls, 1)),
				new Schedule(ls1r3, ls, add(h1_r_ls, 1)),
				new Schedule(ls1r4, ls, add(h1_r_ls, 2)),
				new Schedule(ls1r5, ls, add(h1_r_ls, 3)),
				new Schedule(ls1r6, ls, add(h1_r_ls, 4)),
				new Schedule(ls1r7, ls, add(h1_r_ls, 4)),
				new Schedule(ls1r8, ls, add(h1_r_ls, 5)),
				new Schedule(ls1r9, ls, add(h1_r_ls, 6)),
				new Schedule(ls1r10, ls, add(h1_r_ls, 6)),
				new Schedule(ls1r11, ls, add(h1_r_ls, 7)),
				new Schedule(ls1r12, ls, add(h1_r_ls, 8)),
				new Schedule(ls1r13, ls, add(h1_r_ls, 9)),
				new Schedule(ls1r14, ls, add(h1_r_ls, 11)),
				new Schedule(ls1r15, ls, add(h1_r_ls, 12)),
				new Schedule(ls1r16, ls, add(h1_r_ls, 12)),
				new Schedule(ls1r17, ls, add(h1_r_ls, 14)),
				new Schedule(ls1r18, ls, add(h1_r_ls, 16)),
				
				// LIGNE 1 - RETOUR - D
				new Schedule(ls1r1, d, h1_r_d),
				new Schedule(ls1r2, d, h1_r_d),
				new Schedule(ls1r3, d, add(h1_r_d, 1)),
				new Schedule(ls1r4, d, add(h1_r_d, 1)),
				new Schedule(ls1r5, d, add(h1_r_d, 1)),
				new Schedule(ls1r6, d, add(h1_r_d, 2)),
				new Schedule(ls1r7, d, add(h1_r_d, 2)),
				new Schedule(ls1r8, d, add(h1_r_d, 2)),
				new Schedule(ls1r9, d, add(h1_r_d, 3)),
				new Schedule(ls1r10, d, add(h1_r_d, 4)),
				new Schedule(ls1r11, d, add(h1_r_d, 4)),
				new Schedule(ls1r12, d, add(h1_r_d, 5)),
				new Schedule(ls1r13, d, add(h1_r_d, 6)),
				new Schedule(ls1r14, d, add(h1_r_d, 7)),
				new Schedule(ls1r15, d, add(h1_r_d, 8)),
				new Schedule(ls1r16, d, add(h1_r_d, 8)),
				new Schedule(ls1r17, d, add(h1_r_d, 10)),
				new Schedule(ls1r18, d, add(h1_r_d, 11)),
				
				/*************************************************************/
				/*************************LIGNE 2*****************************/
				/*************************************************************/
				
				// LIGNE 2 - ALLER - LS
				new Schedule(ls2a1, ls, h2_a_ls),
				new Schedule(ls2a2, ls, add(h2_a_ls, 1)),
				new Schedule(ls2a3, ls, add(h2_a_ls, 2)),
				new Schedule(ls2a4, ls, add(h2_a_ls, 3)),
				new Schedule(ls2a5, ls, add(h2_a_ls, 5)),
				new Schedule(ls2a6, ls, add(h2_a_ls, 6)),
				new Schedule(ls2a7, ls, add(h2_a_ls, 7)),
				new Schedule(ls2a8, ls, add(h2_a_ls, 9)),
				new Schedule(ls2a9, ls, add(h2_a_ls, 10)),
				new Schedule(ls2a10, ls, add(h2_a_ls, 10)),
				new Schedule(ls2a11, ls, add(h2_a_ls, 11)),
				new Schedule(ls2a12, ls, add(h2_a_ls, 13)),
				new Schedule(ls2a13, ls, add(h2_a_ls, 16)),
				
				// LIGNE 2 - RETOUR - LS
				new Schedule(ls2r1, ls, h2_r_ls),
				new Schedule(ls2r2, ls, add(h2_r_ls, 2)),
				new Schedule(ls2r3, ls, add(h2_r_ls, 3)),
				new Schedule(ls2r4, ls, add(h2_r_ls, 4)),
				new Schedule(ls2r5, ls, add(h2_r_ls, 5)),
				new Schedule(ls2r6, ls, add(h2_r_ls, 6)),
				new Schedule(ls2r7, ls, add(h2_r_ls, 7)),
				new Schedule(ls2r8, ls, add(h2_r_ls, 7)),
				new Schedule(ls2r9, ls, add(h2_r_ls, 9)),
				new Schedule(ls2r10, ls, add(h2_r_ls, 9)),
				new Schedule(ls2r11, ls, add(h2_r_ls, 10)),
				new Schedule(ls2r12, ls, add(h2_r_ls, 12)),
				new Schedule(ls2r13, ls, add(h2_r_ls, 13)),
				new Schedule(ls2r14, ls, add(h2_r_ls, 15)),
				
				/*************************************************************/
				/*************************LIGNE 3*****************************/
				/*************************************************************/
				
				// LIGNE 3 - ALLER - LS
				new Schedule(ls3a1, ls, h3_a_ls),
				new Schedule(ls3a2, ls, add(h3_a_ls, 1)),
				new Schedule(ls3a3, ls, add(h3_a_ls, 2)),
				new Schedule(ls3a4, ls, add(h3_a_ls, 4)),
				new Schedule(ls3a5, ls, add(h3_a_ls, 6)),
				new Schedule(ls3a6, ls, add(h3_a_ls, 7)),
				new Schedule(ls3a7, ls, add(h3_a_ls, 8)),
				new Schedule(ls3a8, ls, add(h3_a_ls, 10)),
				new Schedule(ls3a9, ls, add(h3_a_ls, 11)),
				new Schedule(ls3a10, ls, add(h3_a_ls, 12)),
				new Schedule(ls3a11, ls, add(h3_a_ls, 14)),
				new Schedule(ls3a12, ls, add(h3_a_ls, 14)),
				new Schedule(ls3a13, ls, add(h3_a_ls, 15)),
				new Schedule(ls3a14, ls, add(h3_a_ls, 16)),
				new Schedule(ls3a15, ls, add(h3_a_ls, 17)),
				new Schedule(ls3a16, ls, add(h3_a_ls, 18)),
				new Schedule(ls3a17, ls, add(h3_a_ls, 19)),
				new Schedule(ls3a18, ls, add(h3_a_ls, 20)),
				new Schedule(ls3a19, ls, add(h3_a_ls, 21)),
				new Schedule(ls3a20, ls, add(h3_a_ls, 22)),
				new Schedule(ls3a21, ls, add(h3_a_ls, 23)),
				new Schedule(ls3a22, ls, add(h3_a_ls, 23)),
				new Schedule(ls3a23, ls, add(h3_a_ls, 24)),
				new Schedule(ls3a24, ls, add(h3_a_ls, 25)),
				new Schedule(ls3a25, ls, add(h3_a_ls, 25)),
				new Schedule(ls3a26, ls, add(h3_a_ls, 26)),
				new Schedule(ls3a27, ls, add(h3_a_ls, 28)),
				new Schedule(ls3a28, ls, add(h3_a_ls, 28)),
				new Schedule(ls3a29, ls, add(h3_a_ls, 30)),
				new Schedule(ls3a30, ls, add(h3_a_ls, 31)),
				new Schedule(ls3a31, ls, add(h3_a_ls, 31)),
				new Schedule(ls3a32, ls, add(h3_a_ls, 33)),
				
				// LIGNE 3 - ALLER - D
				new Schedule(ls3a1, d, h3_a_d),
				new Schedule(ls3a2, d, add(h3_a_d, 1)),
				new Schedule(ls3a3, d, add(h3_a_d, 2)),
				new Schedule(ls3a4, d, add(h3_a_d, 4)),
				new Schedule(ls3a5, d, add(h3_a_d, 6)),
				new Schedule(ls3a6, d, add(h3_a_d, 7)),
				new Schedule(ls3a7, d, add(h3_a_d, 8)),
				new Schedule(ls3a8, d, add(h3_a_d, 10)),
				new Schedule(ls3a9, d, add(h3_a_d, 11)),
				new Schedule(ls3a10, d, add(h3_a_d, 12)),
				new Schedule(ls3a11, d, add(h3_a_d, 14)),
				new Schedule(ls3a12, d, add(h3_a_d, 14)),
				new Schedule(ls3a13, d, add(h3_a_d, 15)),
				new Schedule(ls3a14, d, add(h3_a_d, 16)),
				new Schedule(ls3a15, d, add(h3_a_d, 17)),
				new Schedule(ls3a16, d, add(h3_a_d, 18)),
				new Schedule(ls3a17, d, add(h3_a_d, 19)),
				new Schedule(ls3a18, d, add(h3_a_d, 20)),
				new Schedule(ls3a19, d, add(h3_a_d, 21)),
				new Schedule(ls3a20, d, add(h3_a_d, 22)),
				new Schedule(ls3a21, d, add(h3_a_d, 23)),
				new Schedule(ls3a22, d, add(h3_a_d, 23)),
				new Schedule(ls3a23, d, add(h3_a_d, 24)),
				new Schedule(ls3a24, d, add(h3_a_d, 25)),
				new Schedule(ls3a25, d, add(h3_a_d, 25)),
				new Schedule(ls3a26, d, add(h3_a_d, 26)),
				new Schedule(ls3a27, d, add(h3_a_d, 28)),
				new Schedule(ls3a28, d, add(h3_a_d, 28)),
				new Schedule(ls3a29, d, add(h3_a_d, 30)),
				new Schedule(ls3a30, d, add(h3_a_d, 31)),
				new Schedule(ls3a31, d, add(h3_a_d, 31)),
				new Schedule(ls3a32, d, add(h3_a_d, 33)),
				
				// LIGNE 3 - RETOUR - LS
				new Schedule(ls3r1, ls, h3_r_ls),
				new Schedule(ls3r2, ls, add(h3_r_ls, 1)),
				new Schedule(ls3r3, ls, add(h3_r_ls, 2)),
				new Schedule(ls3r4, ls, add(h3_r_ls, 3)),
				new Schedule(ls3r5, ls, add(h3_r_ls, 4)),
				new Schedule(ls3r6, ls, add(h3_r_ls, 6)),
				new Schedule(ls3r7, ls, add(h3_r_ls, 7)),
				new Schedule(ls3r8, ls, add(h3_r_ls, 8)),
				new Schedule(ls3r9, ls, add(h3_r_ls, 9)),
				new Schedule(ls3r10, ls, add(h3_r_ls, 10)),
				new Schedule(ls3r11, ls, add(h3_r_ls, 11)),
				new Schedule(ls3r12, ls, add(h3_r_ls, 12)),
				new Schedule(ls3r13, ls, add(h3_r_ls, 13)),
				new Schedule(ls3r14, ls, add(h3_r_ls, 14)),
				new Schedule(ls3r15, ls, add(h3_r_ls, 16)),
				new Schedule(ls3r16, ls, add(h3_r_ls, 17)),
				new Schedule(ls3r17, ls, add(h3_r_ls, 18)),
				new Schedule(ls3r18, ls, add(h3_r_ls, 19)),
				new Schedule(ls3r19, ls, add(h3_r_ls, 20)),
				new Schedule(ls3r20, ls, add(h3_r_ls, 22)),
				new Schedule(ls3r21, ls, add(h3_r_ls, 24)),
				new Schedule(ls3r22, ls, add(h3_r_ls, 25)),
				new Schedule(ls3r23, ls, add(h3_r_ls, 26)),
				new Schedule(ls3r24, ls, add(h3_r_ls, 28)),
				new Schedule(ls3r25, ls, add(h3_r_ls, 31)),
				new Schedule(ls3r26, ls, add(h3_r_ls, 35)),
				
				// LIGNE 3 - RETOUR - D
				new Schedule(ls3r1, d, h3_r_d),
				new Schedule(ls3r2, d, add(h3_r_d, 1)),
				new Schedule(ls3r3, d, add(h3_r_d, 2)),
				new Schedule(ls3r4, d, add(h3_r_d, 3)),
				new Schedule(ls3r5, d, add(h3_r_d, 4)),
				new Schedule(ls3r6, d, add(h3_r_d, 6)),
				new Schedule(ls3r7, d, add(h3_r_d, 7)),
				new Schedule(ls3r8, d, add(h3_r_d, 8)),
				new Schedule(ls3r9, d, add(h3_r_d, 9)),
				new Schedule(ls3r10, d, add(h3_r_d, 10)),
				new Schedule(ls3r11, d, add(h3_r_d, 11)),
				new Schedule(ls3r12, d, add(h3_r_d, 12)),
				new Schedule(ls3r13, d, add(h3_r_d, 13)),
				new Schedule(ls3r14, d, add(h3_r_d, 14)),
				new Schedule(ls3r15, d, add(h3_r_d, 16)),
				new Schedule(ls3r16, d, add(h3_r_d, 17)),
				new Schedule(ls3r17, d, add(h3_r_d, 18)),
				new Schedule(ls3r18, d, add(h3_r_d, 19)),
				new Schedule(ls3r19, d, add(h3_r_d, 20)),
				new Schedule(ls3r20, d, add(h3_r_d, 22)),
				new Schedule(ls3r21, d, add(h3_r_d, 24)),
				new Schedule(ls3r22, d, add(h3_r_d, 25)),
				new Schedule(ls3r23, d, add(h3_r_d, 26)),
				new Schedule(ls3r24, d, add(h3_r_d, 28)),
				new Schedule(ls3r25, d, add(h3_r_d, 31)),
				new Schedule(ls3r26, d, add(h3_r_d, 35)),
				
				/*************************************************************/
				/*************************LIGNE 4*****************************/
				/*************************************************************/
				
				// A VERIFIER
				// LIGNE 4 - ALLER - LS
				new Schedule(ls4a1, ls, h4_a_ls),
				new Schedule(ls4a2, ls, add(h4_a_ls, 1)),
				new Schedule(ls4a3, ls, add(h4_a_ls, 2)),
				new Schedule(ls4a4, ls, add(h4_a_ls, 3)),
				new Schedule(ls4a5, ls, add(h4_a_ls, 4)),
				new Schedule(ls4a6, ls, add(h4_a_ls, 4)),
				new Schedule(ls4a7, ls, add(h4_a_ls, 6)),
				new Schedule(ls4a8, ls, add(h4_a_ls, 7)),
				new Schedule(ls4a9, ls, add(h4_a_ls, 8, 7, 9)),
				new Schedule(ls4a10, ls, add(h4_a_ls, 8, 7, 10)),
				new Schedule(ls4a11, ls, add(h4_a_ls, 10, 7, 12)),
				
				// LIGNE 4 - RETOUR - LS
				new Schedule(ls4r1, ls, h4_r_ls),
				new Schedule(ls4r2, ls, add(h4_r_ls, 2)),
				new Schedule(ls4r3, ls, add(h4_r_ls, 4, 7, 5)),
				new Schedule(ls4r4, ls, add(h4_r_ls, 6, 7, 7)),
				new Schedule(ls4r5, ls, add(h4_r_ls, 8, 7, 9)),
				new Schedule(ls4r6, ls, add(h4_r_ls, 9, 7, 11)),
				new Schedule(ls4r7, ls, add(h4_r_ls, 10, 7, 12)),
				new Schedule(ls4r8, ls, add(h4_r_ls, 11, 7, 14)),
				new Schedule(ls4r9, ls, add(h4_r_ls, 15, 7, 18)),
				
				/*************************************************************/
				/*************************LIGNE 5*****************************/
				/*************************************************************/
				
				/*************************************************************/
				/*************************LIGNE 15*****************************/
				/*************************************************************/
				
				// LIGNE 15 - ALLER - LS
				new Schedule(ls15a1, ls, h15_a_ls),
				new Schedule(ls15a2, ls, h15_a_ls),
				new Schedule(ls15a3, ls, add(h15_a_ls, 1)),
				new Schedule(ls15a4, ls, add(h15_a_ls,  2)),
				new Schedule(ls15a5, ls, add(h15_a_ls,  3)),
				new Schedule(ls15a6, ls, add(h15_a_ls,  10)),
				new Schedule(ls15a7, ls, add(h15_a_ls,  12)),
				new Schedule(ls15a8, ls, add(h15_a_ls,  14)),
				new Schedule(ls15a9, ls, add(h15_a_ls,  15)),
				new Schedule(ls15a10, ls, add(h15_a_ls,  18)),
				new Schedule(ls15a11, ls, add(h15_a_ls,  22)),
				new Schedule(ls15a12, ls, add(h15_a_ls,  24)),
				
				// LIGNE 15 - ALLER - D
				new Schedule(ls15a1, d, h15_a_d),
				new Schedule(ls15a2, d, h15_a_d),
				new Schedule(ls15a3, d, add(h15_a_d, 1)),
				new Schedule(ls15a4, d, add(h15_a_d,  2)),
				new Schedule(ls15a5, d, add(h15_a_d,  3)),
				new Schedule(ls15a6, d, add(h15_a_d,  10)),
				new Schedule(ls15a7, d, add(h15_a_d,  12)),
				new Schedule(ls15a8, d, add(h15_a_d,  14)),
				new Schedule(ls15a9, d, add(h15_a_d,  15)),
				new Schedule(ls15a10, d, add(h15_a_d,  18)),
				new Schedule(ls15a11, d, add(h15_a_d,  22)),
				new Schedule(ls15a12, d, add(h15_a_d,  24)),
				
				// LIGNE 15 - RETOUR - LS
				new Schedule(ls15r1, ls, h15_r_ls),
				new Schedule(ls15r2, ls, add(h15_r_ls, 1)),
				new Schedule(ls15r3, ls, add(h15_r_ls, 5)),
				new Schedule(ls15r4, ls, add(h15_r_ls, 7)),
				new Schedule(ls15r5, ls, add(h15_r_ls, 9)),
				new Schedule(ls15r6, ls, add(h15_r_ls, 10)),
				new Schedule(ls15r7, ls, add(h15_r_ls, 14)),
				new Schedule(ls15r8, ls, add(h15_r_ls, 24)),
				new Schedule(ls15r9, ls, add(h15_r_ls, 24)),
				new Schedule(ls15r10, ls, add(h15_r_ls, 25)),
				new Schedule(ls15r11, ls, add(h15_r_ls, 26)),
				new Schedule(ls15r12, ls, add(h15_r_ls, 28)),
				
				// LIGNE 15 - RETOUR - D
				new Schedule(ls15r1, d, h15_r_d),
				new Schedule(ls15r2, d, add(h15_r_d, 1)),
				new Schedule(ls15r3, d, add(h15_r_d, 5)),
				new Schedule(ls15r4, d, add(h15_r_d, 7)),
				new Schedule(ls15r5, d, add(h15_r_d, 9)),
				new Schedule(ls15r6, d, add(h15_r_d, 10)),
				new Schedule(ls15r7, d, add(h15_r_d, 14)),
				new Schedule(ls15r8, d, add(h15_r_d, 24)),
				new Schedule(ls15r9, d, add(h15_r_d, 24)),
				new Schedule(ls15r10, d, add(h15_r_d, 25)),
				new Schedule(ls15r11, d, add(h15_r_d, 26)),
				new Schedule(ls15r12, d, add(h15_r_d, 28)),
					
				
					
				
				
				
				
				
				
					
				
				
				
					
					
					
				
				
				
				
						
				
				
						
					

			};
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		b.schedulesArray.addAll(Arrays.asList(array));
		GsonBuilder builder = new GsonBuilder()/*.setPrettyPrinting()*/;
		Gson gson = builder.create();
		String json = gson.toJson(b);  
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json);
		
    }
	
	private String[] add(String[] tab, int minutes) throws ParseException {
		String[] resultat = new String[tab.length];
		for (int i=0 ; i < tab.length ; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(tab[i]));
			c.add(Calendar.MINUTE, minutes);
			resultat[i] = df.format(c.getTime());
		}
		return resultat;
	}
	
	private String[] add(String[] tab, int minutesAvant, int index, int minutesApres) throws ParseException {
		String[] resultat = new String[tab.length];
		for (int i=0 ; i < index ; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(tab[i]));
			c.add(Calendar.MINUTE, minutesAvant);
			resultat[i] = df.format(c.getTime());
		}
		for (int i=index ; i < tab.length ; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(tab[i]));
			c.add(Calendar.MINUTE, minutesApres);
			resultat[i] = df.format(c.getTime());
		} 
		return resultat;
	}
	
	private String[] add(String[] tab, int minutesAvant, int i1, int minutesEntre, int i2, int minutesApres) throws ParseException {
		String[] resultat = new String[tab.length];
		for (int i=0 ; i < i1 ; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(tab[i]));
			c.add(Calendar.MINUTE, minutesAvant);
			resultat[i] = df.format(c.getTime());
		}
		for (int i=i1 ; i < i2 ; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(tab[i]));
			c.add(Calendar.MINUTE, minutesEntre);
			resultat[i] = df.format(c.getTime());
		}
		for (int i=i2 ; i < tab.length ; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(tab[i]));
			c.add(Calendar.MINUTE, minutesApres);
			resultat[i] = df.format(c.getTime());
		} 
		return resultat;
	}

}
