# android-assignment
  This is my Android assignment which I have to done it before getting interview, but the outcome was not as I expected. Anyway this would be my best practice, so in this project I will show you what I have done in this project. Any advice would be grateful.

## Introduction
  In this assignment needs the following skills Android development skills, API request and handling, and quality of product. They already have design and API prepared for me. [Coin Ranking API](https://docs.coinranking.com/) will be used to display data as shown in the screenshots below.

## Screenshots
  <img src="https://github.com/brown-kaew/android-assignment/blob/develop/screenshots/Screenshot_20200729-205451_CoinRanking.jpg?raw=true" alt="coinRanking" width="240" /> <img src="https://github.com/brown-kaew/android-assignment/blob/develop/screenshots/Screenshot_20200729-205503_CoinRanking.jpg?raw=true" alt="coinRanking search" width="240" />
  
## Libraries used
  - Foundation - Components for core system capabilities
    - AppCompat - Degrade gracefully on older versions of Android.
    - Test - An Android testing framework for unit and runtime UI tests. (not yet implemented)
  - Architecture - A collection of libraries that help you design robust, testable, and maintainable apps.
    - Viewmodel - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.
    - Lifecycles - Create a UI that automatically responds to lifecycle events.
    - LiveData - Build data objects that notify views when the data changes.
  - UI - Details on why and how to use UI Components in your apps - together or separate
    - Fragment - A basic unit of composable UI.
    - Layout - Lay out widgets using different algorithms.
  - Third party
    - [Glide](https://github.com/bumptech/glide) for image loading
    - [Retrofit](https://square.github.io/retrofit/) for API request and handling
    - [AndroidSVG](https://bigbadaboom.github.io/androidsvg/) for SVG image parser and renderer

## MVVM design pattern
<img src="https://uploads-ssl.webflow.com/5d3a2bf76e364b62a89a075a/5d3ec41a73102c436ec70f0f_MVM_one-1.png" alt="coinRanking" width="600"/>

Read more [mvvm-android-architecture-data-binding](https://www.wearemobilefirst.com/blog/mvvm-android-architecture-data-binding)

  - Model - Repository store data that recieved from API calls.
  - View - Activity/Fragment handle UI events and observe data in ViewModel.
  - ViewModel - Handle called events from View and Hold LiveData which getting from model in this case repository.
