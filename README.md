
### IMPORTS

`import androidx.appcompat.app.AppCompatActivity`

AppCompatActivity is a support-library base class for Android activities that adds 
features like the Action Bar and built-in light/dark theme switching using 
Theme.AppCompat.DayNight. Activities that extend it should be themed with 
Theme.AppCompat (or a theme that extends it).

`import android.os.Bundle`

The Bundle is used in Android development to include the Bundle class from the Android 
operating system, which allows you to pass data between activities and save state 
information. The Bundle class is a mapping from String keys to various Parcelable values.




### POSITIONING

In Android, use `android:layout_gravity="center"` to center a view within its parent, 
or `android:gravity="center"` to center the view’s contents. For example, layout_gravity 
works for a child in a FrameLayout; in a LinearLayout, the direction and available space 
affect how the child can be positioned.