# Smart Pantry Manager

## Description
An Android app called Smart Pantry Manager tracks the ingredients a user 
actually has at home and makes meal suggestions based only on those ingredients, 
saving them from having to go shopping. Only when all necessary ingredients are 
available in the pantry in the correct quantities is a recipe recommended; 
partial matches are never included in the main recommendations.

## Author
Jacob

## Database Choice: SQLite via Room
For local, on-device storage, this application makes use of **Room** (Android's persistence library based on SQLite). 
This was selected instead of PostgreSQL or Firebase because:
- There is no requirement for cloud sync or multi-device access because the app's data (a personal pantry and a fixed recipe library) is natural local and single-user.
- Compared to raw SQLiteOpenHelper, Room reduces runtime mistakes by offering type-safe DAOs and compile-time verification of SQL queries.
- The app's complete offline functionality due to its lack of network requirement makes it suitable for use cases in kitchens and pantries where dependable connectivity isn't always guaranteed.
- It directly aligns with the assignment requirement of the persistent-data method.

## The schema consists of three tables:
- `pantry_items`: Ingredients currently used by the user (name, quantity, unit)
- `recipes`: Name of recipe and steps for preparation
- `recipe_ingredients`: The components that are required for each recipe, connected to `recipes` using a foreign key with cascade delete

## Core Feature: Strict Ingredient Matching
When comparing pantry quantities to recipe needs, the `IngredientMatcher` class normalizes 
ingredient names (lowercase, basic plural-stripping) and, if possible, converts between compatible 
units (weight: g/kg, volume: ml/l). Only when every necessary component is met no partial matches 
does a recipe show up in Suggested Recipes.

## Setup & Run Instructions
1. Clone this repository: https://github.com/jaymfune/Smart-Pantry-Manager.git
2. Open the project in Android Studio
3. Let Gradle sync automatically (this downloads the Room dependencies)
4. Run the app on an emulator or physical device with **API 24 (Android 7.0) or higher**.
5. There is no need for manual setup because the app automatically loads 16 sample recipes into its database upon initial launch. To begin getting recipe options, add pantry ingredients using the **+** icon in the bottom menu.
6. Some Ingredients that can be added to start getting recipe suggestions
   - Cheese (3, slices)
   - Eggs (4, units)
   - Flour (500, g)
   - Salt (2, pinch)
   - Banana (3, unit)

## Tech Stack
- Java, Android SDK
- Room (SQLite) for persistence
- RecyclerView with custom Adapters
- Material Components (BottomNavigationView, TextInputLayout)
- SharedPreferences for app settings

## References
- Android Developers. (n.d.). *Save data in a local database using Room*. https://developer.android.com/training/data-storage/room
- Android Developers. (n.d.). *Create dynamic lists with RecyclerView*. https://developer.android.com/training/material/lists-cards
- Android Developers. (n.d.). *Save simple data with SharedPreferences*. https://developer.android.com/training/data-storage/shared-preferences
- Android Developers. (n.d.). *Intents and intent filters*. https://developer.android.com/guide/components/intents-filters
- Android Developers. (n.d.). *BottomNavigationView*. https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView
