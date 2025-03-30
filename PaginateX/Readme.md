
# **Infinite Scrolling with API Pagination in Android (Java)**  

This project demonstrates **infinite scrolling with API pagination** in an Android app using Java. It fetches user data from a paginated API and displays it in a `RecyclerView`, loading more data as the user scrolls down.  

## **📌 Features**  
✅ Fetches **20 users per page** from a paginated API.  
✅ Implements **infinite scrolling** – loads more users automatically when reaching the bottom.  
✅ Displays a **loading indicator** when fetching data.  
✅ Uses `Retrofit` for API calls and `RecyclerView` for listing users.  
✅ Follows **MVVM architecture** with `LiveData` and `ViewModel`.  
✅ **Error Handling** – Shows a retry option if the API request fails.  
✅ **Pull to Refresh** – Allows users to manually refresh the list.  

---

## **🛠 Tech Stack**  
- **Language:** Java  
- **Networking:** Retrofit + Gson  
- **Architecture:** MVVM (ViewModel + LiveData)  
- **UI Components:** RecyclerView, SwipeRefreshLayout, ProgressBar  


