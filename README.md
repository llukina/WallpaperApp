# WallpaperApp
Android REST API Client App for getting wallpapers from Unsplash.com

Aplication consists of three screens:
- Photos
- Collections
- Favorites

Each of the screens has only **RecyclerView** and a **Navigation Drawer**. Navigation Drawer is used to switch between screens.
Photos screen has the newest photos from Unsplash, Collections has top collections on site and Favorites has photos that user saved to favorites. The photos  are added to Recyclerview with **Glide**. When user clicks on any image, app opens the image in full screen mode. In full screen mode, the app has **[FloatingActionMenu](https://github.com/Clans/FloatingActionButton)**, which has 2 buttons: Add to favorites (Remove from favorites) and Set as wallpaper. Adding to favorites is handled with **[Realm](https://realm.io/products/realm-database/)**. **Gson** and **Retrofit** are used to get photos and data from Unsplashed, and **ButterKnife** is used for binding views and removing boilerplate code.



## Requirements:
- Android Studio 3.2 or later
- Java 7+
- Android 5.1 or later
- Internet connection



## How to install:
1. Import project to Android studio:
    - Click on **Checkout from Version Control** (on Welcome screen, or in Toolbar - **VCS**) - **Git** - in URL field set **https://github.com/isink17/WallpaperApp** or
    - Download this project as .zip - unpack it, and select **Open an existing Android Studio project** from Welcome Screen _or_ in Toolbar - **File** - **Open...** and select the folder with the extracted project.
2. Click on **Run 'app** or **Shift + F10**
3. Select deployment target:
   - To deploy the application to virtual device go [here](https://developer.android.com/studio/run/emulator) .
   - To deploy the application to real device go [here](https://developer.android.com/studio/run/device) .




![Alt text](screenshot1.png?raw=true)
![Alt text](screenshot2.png?raw=true "Collections screen")
![Alt text](screenshot3.png?raw=true "Selected Collection")
![Alt text](screenshot4.png?raw=true "Selected image")
