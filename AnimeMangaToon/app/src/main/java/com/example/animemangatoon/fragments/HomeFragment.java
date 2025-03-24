package com.example.animemangatoon.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animemangatoon.R;
import com.example.animemangatoon.adapter.FeaturedAdapter;
import com.example.animemangatoon.model.FeaturedItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private FeaturedAdapter adapter;
    private List<FeaturedItem> featuredItemList = new ArrayList<>();
    private List<FeaturedItem> featuredFilteredItemList;

    private FloatingActionButton fab;

    private AdView mAdView;


    public HomeFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);  // Make sure this is called
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

      //  fab = view.findViewById(R.id.fab);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Call method to display featured items
        featuredList();

        // Initialize Mobile Ads SDK
        MobileAds.initialize(getContext(), initializationStatus -> {
            // You can add custom code here if needed when initialization is complete
        });

        // Find AdView and load the banner ad
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


       // fab.setOnClickListener(v -> showPopupMenu(v));


        return view;
    }

    private void featuredList() {


        featuredItemList = new ArrayList<>();
        featuredItemList.add(new FeaturedItem(R.drawable.image1, "FEATURED", "The Remarried Empress Chapter 25-32", "I Love Yoo Chapter 9-16 deals with Shin-ae meeting Yeong-gi..."));
        featuredItemList.add(new FeaturedItem(R.drawable.image2, "FEATURED", "Remarried Empress Chapter 17-24: Heinrey’s Affection for Navier", "Remarried Empress Chapter 17-24 deals with Heinrey's growing affection for Navier and Rashta's anxiety about the presence of Lord Lotteshu."));
        featuredItemList.add(new FeaturedItem(R.drawable.image3, "FEATURED", "The 70 Best Romance Webtoon And Manhwa of All Time", "We offer you 70 best romance webtoon, from popular to underrated and iskeai to YA, literally anything you can think of, so keep scrolling!"));
        featuredItemList.add(new FeaturedItem(R.drawable.image4, "FEATURED", "Gwichon Village Chapter 1-5: Mumyeong And Dark Spirit", "Gwichon Village Chapter 1-5 will immerse you in a tapestry of horrifying truths lurking within what appears to be a tranquil village."));
        featuredItemList.add(new FeaturedItem(R.drawable.image5, "FEATURED", "Top 10 Popular Webtoons with Over 50 million Views", "In this article, we explain why you should read these top 10 popular webtoons with more than 50 million views."));

        featuredItemList.add(new FeaturedItem(R.drawable.image1, "FEATURED", "The Remarried Empress Chapter 25-32", "I Love Yoo Chapter 9-16 deals with Shin-ae meeting Yeong-gi..."));
        featuredItemList.add(new FeaturedItem(R.drawable.image2, "FEATURED", "Remarried Empress Chapter 17-24: Heinrey’s Affection for Navier", "Remarried Empress Chapter 17-24 deals with Heinrey's growing affection for Navier and Rashta's anxiety about the presence of Lord Lotteshu."));
        featuredItemList.add(new FeaturedItem(R.drawable.image3, "FEATURED", "The 70 Best Romance Webtoon And Manhwa of All Time", "We offer you 70 best romance webtoon, from popular to underrated and iskeai to YA, literally anything you can think of, so keep scrolling!"));
        featuredItemList.add(new FeaturedItem(R.drawable.image4, "FEATURED", "Gwichon Village Chapter 1-5: Mumyeong And Dark Spirit", "Gwichon Village Chapter 1-5 will immerse you in a tapestry of horrifying truths lurking within what appears to be a tranquil village."));
        featuredItemList.add(new FeaturedItem(R.drawable.image5, "FEATURED", "Top 10 Popular Webtoons with Over 50 million Views", "In this article, we explain why you should read these top 10 popular webtoons with more than 50 million views."));

        featuredItemList.add(new FeaturedItem(R.drawable.image1, "FEATURED", "The Remarried Empress Chapter 25-32", "I Love Yoo Chapter 9-16 deals with Shin-ae meeting Yeong-gi..."));
        featuredItemList.add(new FeaturedItem(R.drawable.image2, "FEATURED", "Remarried Empress Chapter 17-24: Heinrey’s Affection for Navier", "Remarried Empress Chapter 17-24 deals with Heinrey's growing affection for Navier and Rashta's anxiety about the presence of Lord Lotteshu."));
        featuredItemList.add(new FeaturedItem(R.drawable.image3, "FEATURED", "The 70 Best Romance Webtoon And Manhwa of All Time", "We offer you 70 best romance webtoon, from popular to underrated and iskeai to YA, literally anything you can think of, so keep scrolling!"));
        featuredItemList.add(new FeaturedItem(R.drawable.image4, "FEATURED", "Gwichon Village Chapter 1-5: Mumyeong And Dark Spirit", "Gwichon Village Chapter 1-5 will immerse you in a tapestry of horrifying truths lurking within what appears to be a tranquil village."));
        featuredItemList.add(new FeaturedItem(R.drawable.image5, "FEATURED", "Top 10 Popular Webtoons with Over 50 million Views", "In this article, we explain why you should read these top 10 popular webtoons with more than 50 million views."));

        featuredItemList.add(new FeaturedItem(R.drawable.image1, "FEATURED", "The Remarried Empress Chapter 25-32", "I Love Yoo Chapter 9-16 deals with Shin-ae meeting Yeong-gi..."));
        featuredItemList.add(new FeaturedItem(R.drawable.image2, "FEATURED", "Remarried Empress Chapter 17-24: Heinrey’s Affection for Navier", "Remarried Empress Chapter 17-24 deals with Heinrey's growing affection for Navier and Rashta's anxiety about the presence of Lord Lotteshu."));
        featuredItemList.add(new FeaturedItem(R.drawable.image3, "FEATURED", "The 70 Best Romance Webtoon And Manhwa of All Time", "We offer you 70 best romance webtoon, from popular to underrated and iskeai to YA, literally anything you can think of, so keep scrolling!"));
        featuredItemList.add(new FeaturedItem(R.drawable.image4, "FEATURED", "Gwichon Village Chapter 1-5: Mumyeong And Dark Spirit", "Gwichon Village Chapter 1-5 will immerse you in a tapestry of horrifying truths lurking within what appears to be a tranquil village."));
        featuredItemList.add(new FeaturedItem(R.drawable.image5, "FEATURED", "Top 10 Popular Webtoons with Over 50 million Views", "In this article, we explain why you should read these top 10 popular webtoons with more than 50 million views."));

        featuredItemList.add(new FeaturedItem(R.drawable.image1, "FEATURED", "The Remarried Empress Chapter 25-32", "I Love Yoo Chapter 9-16 deals with Shin-ae meeting Yeong-gi..."));
        featuredItemList.add(new FeaturedItem(R.drawable.image2, "FEATURED", "Remarried Empress Chapter 17-24: Heinrey’s Affection for Navier", "Remarried Empress Chapter 17-24 deals with Heinrey's growing affection for Navier and Rashta's anxiety about the presence of Lord Lotteshu."));
        featuredItemList.add(new FeaturedItem(R.drawable.image3, "FEATURED", "The 70 Best Romance Webtoon And Manhwa of All Time", "We offer you 70 best romance webtoon, from popular to underrated and iskeai to YA, literally anything you can think of, so keep scrolling!"));
        featuredItemList.add(new FeaturedItem(R.drawable.image4, "FEATURED", "Gwichon Village Chapter 1-5: Mumyeong And Dark Spirit", "Gwichon Village Chapter 1-5 will immerse you in a tapestry of horrifying truths lurking within what appears to be a tranquil village."));
        featuredItemList.add(new FeaturedItem(R.drawable.image5, "FEATURED", "Top 10 Popular Webtoons with Over 50 million Views", "In this article, we explain why you should read these top 10 popular webtoons with more than 50 million views."));

        featuredItemList.add(new FeaturedItem(R.drawable.image1, "FEATURED", "The Remarried Empress Chapter 25-32", "I Love Yoo Chapter 9-16 deals with Shin-ae meeting Yeong-gi..."));
        featuredItemList.add(new FeaturedItem(R.drawable.image2, "FEATURED", "Remarried Empress Chapter 17-24: Heinrey’s Affection for Navier", "Remarried Empress Chapter 17-24 deals with Heinrey's growing affection for Navier and Rashta's anxiety about the presence of Lord Lotteshu."));
        featuredItemList.add(new FeaturedItem(R.drawable.image3, "FEATURED", "The 70 Best Romance Webtoon And Manhwa of All Time", "We offer you 70 best romance webtoon, from popular to underrated and iskeai to YA, literally anything you can think of, so keep scrolling!"));
        featuredItemList.add(new FeaturedItem(R.drawable.image4, "FEATURED", "Gwichon Village Chapter 1-5: Mumyeong And Dark Spirit", "Gwichon Village Chapter 1-5 will immerse you in a tapestry of horrifying truths lurking within what appears to be a tranquil village."));
        featuredItemList.add(new FeaturedItem(R.drawable.image5, "FEATURED", "Top 10 Popular Webtoons with Over 50 million Views", "In this article, we explain why you should read these top 10 popular webtoons with more than 50 million views."));


        adapter = new FeaturedAdapter(getContext(), featuredItemList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();

        searchView.setPadding(0, 0, 10, 0);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(@NonNull MenuItem item) {
                // Customize search view icons and text colors here

                // Set custom search icon
                ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
                if (searchIcon != null) {
                    searchIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.search_icon));
                    searchIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.primaryColor), PorterDuff.Mode.SRC_IN);
                }

                // Set custom close icon
                ImageView closeIcon = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
                if (closeIcon != null) {
                    closeIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.close_icon));
                    closeIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.primaryColor), PorterDuff.Mode.SRC_IN);
                }

                // Customize search text
                TextView searchTextView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
                if (searchTextView != null) {
                    searchTextView.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.primaryColor));
                    searchTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.primaryColor));
                }

                searchView.setSubmitButtonEnabled(true);
                searchView.setQueryHint("Search");

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(@NonNull MenuItem item) {
                // When search view collapses, show the original list
                featuredList();
                return true;
            }
        });

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d("TAG", "method called for searching");
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.isEmpty()) {
                    featuredList();
                } else {
                    filterItems(newText);
                    Log.d("TAG", "method called for searching");
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.actionSearch) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void performSearch(String query) {
        if (query != null && !query.isEmpty()) {
            filterItems(query);
        }
    }

    private void filterItems(String query) {
        // Reset the filtered item list
        featuredFilteredItemList = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            // If the query is empty, show all original featured items
            featuredFilteredItemList.addAll(featuredItemList);
        } else {
            // Normalize the query
            String normalizedQuery = query.toLowerCase().trim();

            // Iterate through the original featured item list to find matches
            for (FeaturedItem featuredItem : featuredItemList) {
                boolean matches = false;

                // Check if the featured text, description, or title contains the query substring
                if (featuredItem.getFeaturedText() != null && featuredItem.getFeaturedText().toLowerCase().contains(normalizedQuery)) {
                    matches = true;
                } else if (featuredItem.getDescription() != null && featuredItem.getDescription().toLowerCase().contains(normalizedQuery)) {
                    matches = true;
                } else if (featuredItem.getTitle() != null && featuredItem.getTitle().toLowerCase().contains(normalizedQuery)) {
                    matches = true;
                }

                // If a match is found, add the item to the filtered list
                if (matches) {
                    featuredFilteredItemList.add(featuredItem);
                }
            }
        }

        // Update the adapter with the filtered list
        adapter.updateItems(featuredFilteredItemList);

        // Show a toast if no results are found for non-empty queries
        if (featuredFilteredItemList.isEmpty() && !query.trim().isEmpty()) {
            Toast.makeText(getContext(), "No Result", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPopupMenu(View view) {
        // Create the PopupMenu instance
        androidx.appcompat.widget.PopupMenu popupMenu = new androidx.appcompat.widget.PopupMenu(getContext(), view);

        // Inflate the menu resource (fab_menu.xml)
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.fab_menu, popupMenu.getMenu());

        // Set a click listener for menu item clicks
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.action_watch) {
                Toast.makeText(getContext(), "Watch clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.action_featured) {
                Toast.makeText(getContext(), "Featured clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.action_news) {
                Toast.makeText(getContext(), "News clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;  // Return false if no valid item was clicked
        });

        // Show the popup menu
        popupMenu.show();
    }


}