MINIMUM REQUIREMENTS IMPLEMENTED:

1. Bugs from previous phase that our peer reviewers found are corrected and implemented.
    - Bottom Navigation Bar now works properly

2. 100 % of User stories are implemented -
   DONE · As a non-registered user, I want to be able to register with Paid Aid, so that I can access
     all that the app has to offer.
   DONE · As a user, I want to be able to log into the app, so I can access all of the app's features.
   DONE · As a user, I want to create a request, so that I can get my request fulfilled.
   DONE · As a user, I want to be able to swipe through the requests, so that I can view all of the requests.
   · As a user, I want to be able to select a request, so that I can get see the details of the request.
   · As a user, I want to be able to message another user, so that I can confirm the request with them.
   · As a user, I want to be able to see the rating of other users, so that I can see their reputation.
   · As a user, I want to submit a rating, so that I submit my experience with the user.

3. We implemented SharedPreferences for storing Users usernames, passwords, and emails.

   HELP - Your app must save data to the device’s storage using SQLite and one other storage mechanism
   (SharedPreferences or File Storage). Explain in your README.txt file as to where and why you
    used the data storage.

4. We use web services for all of our requests. We grab data from our requests stored in the Database
   and populate the request when a user clicks on one to see the details in case they are interested.

   HELP - Your app must use web services to make the app functional. Using dummy data is not allowed
   for this phase. Explain in your README.txt file as to where and why you used web services.

5. We implemented Content Sharing via email messaging. To send an email, you must interested in a
   request. When a user clicks on a request, and then clicks "Pick Up", they are able to send an email
   to the user who posted the request. The email feature is solely for figuring our details
   (where, when, etc.) when users are interested in helping out someone.

6. We implemented custom account Sign-In. A new account can be registered, using the normal account
   registration validation scheme: must type in a username not registered yet / must not be blank,
   a password that is at least 7+ characters, and an email with an '@' sign.

7. We use graphics for our icon that appears in the emulator and also our logo shown plenty of times
   on many different screens throughout our app.

8. You must write at least one JUnit class for a model class and at least one Instrumentation test
   to test a fragment/activity in your project. The tests need to be thorough not trivial. Your app
   must not crash when any of the options are chosen. Every crash on the app is a deduction on the
   overall project grade. I suggest installing the apk file on a different tester’s device to test.
   They should use the account information provided to login. This will ensure that we can test your
   app and run it successfully. Your source code must have javadoc for all the methods and comments
   for any obscure code. Java coding conventions must be followed that you learned in the past
   courses. Remove all TODOs. Make sure that each java file has a header with the information for
   each class.