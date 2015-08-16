# GameManager
Sample App to fetch the Data of Popular Games and processing that data to show in native application in Android.
1) I have assumed that Network will be available ( there was no offline support added ) , a loading screen will come if no network is present

2) Loading screen will come as soon as you launch the app and I triggered a AsyncTask in the background to fetch the details such as App name, rating , description price etc , as soon as fetching and parsing competed loading screen will be removed with list view

3) API HIT COUNT and NO OF GAMES are added as footer, you need to scroll whole list to see this part , didn’t have time to make it stick able view

4) for fetching API HIT count another async is triggered

5) on click on the row item , Another activity will open which will have all details about app

6) as soon as user click on list , another async task will be triggered to fetch the image

7) BACK , APP STORE , SMS and SHARE buttons are working well

8) showing the demographic data in the form of list view as don’t have time to convert into pie chart


