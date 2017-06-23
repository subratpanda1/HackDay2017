package ekart.com.hackapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.Map;

import ekart.com.hackapp.AppInfoProvider;
import ekart.com.hackapp.R;
import ekart.com.hackapp.adapters.SectionsPagerAdapter;
import ekart.com.hackapp.fragments.SlotDialogFragment;
import ekart.com.hackapp.models.SlotDiscount;
import ekart.com.hackapp.networkHandler.NetworkHandler;
import ekart.com.hackapp.sample.TTS;
import ekart.com.hackapp.utils.FragmentUtils;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static final int REQUEST_AUDIO_PERMISSIONS_ID = 33;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onStart() {
        super.onStart();
        checkAudioRecordPermission();
        TTS.init(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        makeReady();

    }

    void makeReady() {
//        io.reactivex.Observable.create(new ObservableOnSubscribe<List<String>>() {
//            @Override
//            public void subscribe(ObservableEmitter<List<String>> e) throws Exception {
//                e.onNext(NetworkHandler.getInstance().getLocalities());
//            }
//        }).subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<String>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<String> localitiesList) {
//                        AppInfoProvider.INSTANCE.setLocalitiesList(localitiesList);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        io.reactivex.Observable.create(new ObservableOnSubscribe<Map<String, List<SlotDiscount>>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, List<SlotDiscount>>> e) throws Exception {
                e.onNext(NetworkHandler.getInstance().getSlots());
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<String, List<SlotDiscount>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Map<String, List<SlotDiscount>> discountMap) {
                        AppInfoProvider.INSTANCE.setDiscountMap(discountMap);
                        SlotDialogFragment slotDialogFragment = new SlotDialogFragment();
                        FragmentUtils.showCustomDialog(slotDialogFragment, getSupportFragmentManager());
                    }

                    @Override
                    public void onError(Throwable e) {
                        int a =1;
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SlotDialogFragment slotDialogFragment = new SlotDialogFragment();
            FragmentUtils.showCustomDialog(slotDialogFragment, getSupportFragmentManager());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    private void startActivity(Class<?> cls) {
        final Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void checkAudioRecordPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_AUDIO_PERMISSIONS_ID);

            }
        }
    }
}
