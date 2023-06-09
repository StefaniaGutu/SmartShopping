# Smart Shopping
### Aplicatie mobila Android pentru gestionarea listelor de cumparaturi

Aplicatia permite utilizatorilor sa creeze liste de cumparaturi, sa le editeze si sa le stearga. Pentru fiecare lista se pot adauga un numar nelimitat de iteme (produse), care ulterior pot fi bifate ca fiind cumparate sau sterse.
Listele create pot fi trimise printr-un mesaj text prin functia de Share.

### :white_check_mark: Implementarea unui RecyclerView cu functie de cautare
In aplicatie sunt folosite 2 RecyclerView-uri:
- un RecyclerView cu functie de cautare este folosit pentru afisarea unei liste cu listele de cumparaturi existente

<p float="left">
<img src="https://user-images.githubusercontent.com/79320751/236265580-ce83cb2a-14ab-4b53-b74f-c24a5ab569c9.png" width="250">
<img src="https://user-images.githubusercontent.com/79320751/236265028-b4256b4f-5966-49c0-a885-abed21b290d5.png" width="250">
</p>

- un RecyclerView simplu este folosit pentru listarea produselor din listele de cumparaturi
<img src="https://user-images.githubusercontent.com/79320751/236272197-71b17028-b71d-41a3-92c0-3ef89c7a1819.png" width="250">

### :white_check_mark: Utilizarea unei metode de navigatie: Bottom Navigation

![bottom_nav](https://user-images.githubusercontent.com/79320751/236263600-05376083-a6d7-42e4-bb30-0978fed7f9f2.png)

### :white_check_mark: Implementarea unei metode de Share (Android Sharesheet)

<p float="left">
<img src="https://user-images.githubusercontent.com/79320751/236288189-9084b5a9-74af-4cfe-accb-3dcf11d07bc3.png" width="250">
<img src="https://user-images.githubusercontent.com/79320751/236288476-c4f4a5f7-83d0-4459-8819-b372427a5b9f.png" width="250">
</p>

### :white_check_mark: Local Notifications using Firebase

Am folosit Firebase Messaging pentru a crea si trimite notificari.

<img src="https://user-images.githubusercontent.com/79320751/236277440-690a4d0a-e0b2-441c-92be-149d12b9fce3.png" width="270">

### :white_check_mark: Implementarea unei animatii folosind ObjectAnimator si a uneia folosind MotionLayout
- animatia folosind ObjectAnimator poate fi gasita pe Profilul utilizatorului, iar animatia folosind MotionLayout se afla pe Splash Screen

<p float="left">
<img src="https://user-images.githubusercontent.com/79320751/236267807-ef4114d4-6cac-45f3-acf1-abcdf882514c.png" width="250">
<img src="https://user-images.githubusercontent.com/79320751/236270554-3bcf3560-0af4-4e25-ba3b-b21370233f10.png" width="250">
</p>

### :white_check_mark: Login with Firebase
- Login, Register si buton pentru Log out pe Profil

<p float="left">
<img src="https://user-images.githubusercontent.com/79320751/236268451-011e220b-828d-441c-a024-a297aa4c1911.png" width="250">
<img src="https://user-images.githubusercontent.com/79320751/236268461-ee0fafc1-848b-407d-ab3e-3a8817c58cb2.png" width="250">
<img src="https://user-images.githubusercontent.com/79320751/236267807-ef4114d4-6cac-45f3-acf1-abcdf882514c.png" width="250">
</p>

### :white_check_mark: UI adaptat pentru landscape mode
<img src="https://user-images.githubusercontent.com/79320751/236276901-82c1f745-0385-4046-8a33-063a4b823a72.png" height="250">
<img src="https://user-images.githubusercontent.com/79320751/236276915-7b32a837-b9bc-4533-8a4a-6de5b1524c78.png" height="250">
<img src="https://user-images.githubusercontent.com/79320751/236276929-33395760-7354-424e-9dfc-0b869d3d3bdc.png" height="250">

### :white_check_mark: Persistenta datelor folosind baze de date (remote: Firebase)

Am folosit Firebase Realtime Database pentru a stoca listele de cumparaturi si informatii despre utilizatorii inregistrati.

### :white_check_mark: Web services (Firebase)

Am folosit serviciile de la Firebase pentru a trimite notificari (Firebase Messaging) si pentru a stoca datele din baza de date (Firebase Realtime Database).


