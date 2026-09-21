package com.jacob.smartpantrymanager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutors {

    // Stores one shared copy of AppExecutors.
    private static volatile AppExecutors instance;

    // Used to run database and other file-related tasks in the background.
    private final ExecutorService diskIO;

    // Private constructor so other classes cannot create this class directly.
    private AppExecutors() {

        // Create one background thread for disk tasks.
        // Tasks will run one at a time in the order they are added.
        diskIO = Executors.newSingleThreadExecutor();
    }

    // Gets the shared AppExecutors instance.
    // If it does not exist, it creates it.
    public static AppExecutors getInstance() {

        // Check if an instance already exists.
        if (instance == null) {

            // Prevent multiple threads from creating the instance
            // at the same time.
            synchronized (AppExecutors.class) {

                // Check again after getting the lock.
                if (instance == null) {

                    // Create the AppExecutors instance.
                    instance = new AppExecutors();
                }
            }
        }

        // Return the shared instance.
        return instance;
    }

    // Gives access to the background thread used for disk tasks.
    public ExecutorService diskIO() {
        return diskIO;
    }
}