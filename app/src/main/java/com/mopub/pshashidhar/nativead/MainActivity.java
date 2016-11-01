package com.mopub.pshashidhar.nativead;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mopub.nativeads.MoPubAdAdapter;
import com.mopub.nativeads.MoPubNativeAdPositioning;
import com.mopub.nativeads.MoPubStaticNativeAdRenderer;
import com.mopub.nativeads.RequestParameters;
import com.mopub.nativeads.ViewBinder;

import java.util.Arrays;
import java.util.EnumSet;

public class MainActivity extends AppCompatActivity {

    private ListView myListView;
    private MoPubAdAdapter mAdAdapter;
    private static final String MY_AD_UNIT_ID = "a5bc909bb64f4232b5fd86cfc624c130";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Adapter myAdapter;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewBinder viewBinder = new ViewBinder.Builder(R.layout.activity_main)
                .mainImageId(R.id.native_ad_main_image)
                .iconImageId(R.id.native_ad_icon_image)
                .titleId(R.id.native_ad_title)
                .textId(R.id.native_ad_text)
                .privacyInformationIconImageId(R.id.native_ad_privacy_information_icon_image)
                .build();


        MoPubStaticNativeAdRenderer adRenderer = new MoPubStaticNativeAdRenderer(viewBinder);

        MoPubNativeAdPositioning.MoPubServerPositioning adPositioning =
                MoPubNativeAdPositioning.serverPositioning();

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Arrays.asList("cell1", "cell2", "cell3", "cell4", "cell5", "cell6", "cell7", "cell8"));

         mAdAdapter = new MoPubAdAdapter(this, itemsAdapter, adPositioning);

        mAdAdapter.registerAdRenderer(adRenderer);


        myListView = (ListView) findViewById(R.id.list);
        myListView.setAdapter(mAdAdapter);


    }

    @Override
    public void onResume() {

        final EnumSet<RequestParameters.NativeAdAsset> desiredAssets = EnumSet.of(
                RequestParameters.NativeAdAsset.TITLE,
                RequestParameters.NativeAdAsset.TEXT,
                // Don't pull the ICON_IMAGE
                // NativeAdAsset.ICON_IMAGE,
                RequestParameters.NativeAdAsset.MAIN_IMAGE,
                RequestParameters.NativeAdAsset.CALL_TO_ACTION_TEXT);

        RequestParameters myRequestParameters = new RequestParameters.Builder()
                .desiredAssets(desiredAssets)
                .build();


        // Request ads when the user returns to this activity.
        mAdAdapter.loadAds(MY_AD_UNIT_ID, myRequestParameters);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAdAdapter.destroy();
        super.onDestroy();
    }


}
