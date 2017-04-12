package com.udacity.firebase.shoppinglistplusplus;



/**
 * Includes one-time initialization of Firebase related code
 */
public class ShoppingListApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /* Initialize Firebase
        In the new SDK, it's no longer necessary to call Firebase.setAndroidContext() so you can remove it from your code.
        Firebase.setAndroidContext(this);*/
    }

}
