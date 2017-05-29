MINIMUM REQUIREMENTS IMPLEMENTED:

1. Bugs from previous phase that our peer reviewers found are corrected and implemented.
    - Bottom Navigation Bar now works properly
    - Readme.txt is fixed. (Password must be 5+ characters long)

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

5. We implemented Content Sharing via email messaging. To send an email, you must be interested in a
   request. When a user clicks on a request, and then clicks "Pick Up", they are able to send an email
   to the user who posted the request. The email feature is solely for figuring our details
   (where, when, etc.) when users are interested in helping out someone.
    - To use email messaging, you must go to 'emails' on the emulators 'apps' screen and have your
    email account logged in on the emulator. This allows for you to send an email to a recipient via
    PaidAid. This allows users to have an email option when the prompt "Send Email Via" pops up.

6. We implemented custom account Sign-In. A new account can be registered, using the normal account
   registration validation scheme:
    - Username must not be registered yet / must not be blank
    - Password must be 5+ characters
    - Email must have '@' sign and "---".com  |   Ex: (test@gmail.com)

7. We implemented graphics for both our icon and our logo.
    - Icon appears on emulator apps screen
    - Logo appears on multiple screens throughout our app

8. We implemented both a JUnit class test and an Instrumentation test.
    - JUnit (RequestTest)
    - Instrumentation (RegisterFragmentTest)