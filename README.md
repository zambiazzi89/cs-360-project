# cs-360-project
CS-360 Mobile Architect & Programming

Q. Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?

A. The app should allow users to keep track of their inventory. It should contain a login screen with the option for registration. It should also contain a screen to display the user's inventory and perform CRUD operations. Lastly, the app should have a mechanism to request the system for permission to send SMS.


Q. What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?

A. Four screens were necessary to create the app. I created a login screen and a registration screen for users to log in or register an account. The main screen of the app contained a RecyclerView to display the user's inventory and a button to add new items to the list. Each item was designed as a card that had buttons to update or delete the selected item. I used dialogs to prompt the user to enter new item information or to update existing items. I also created a screen for the user to select if they want to enable the app to send SMS, which then asks permission to the user's system. I believe that the design I chose for my app was successful because I kept users in mind while creating it. I focused on designing a simple UI, which was intuitive and efficient.


Q. How did you approach the process of coding your app? What techniques or strategies did you use? How could those be applied in the future?

A. I approach developing the application as connecting the requirements to the user interface I created. I first define the main classes and objects needed to fulfill the app's functionalities. For this, I identify the best data structures to use. Furthermore, other OOP analysis and design tools, such as class diagrams, are helpful in defining the properties and behaviors of the classes. When developing applications is also crucial to take into account the behavior of the user. For example, when requesting for permission to send SMS, we must consider the scenario where the user changed their mind after denying the permission the first time. As I developed the functionlity of the app, I also refined the design, improving the layout and components according to the functionalities developed.


Q. How did you test to ensure your code was functional? Why is this process important and what did it reveal?

A. For this application, since it was not very complex, I used manual testing to ensure it was functional. Each time I created a new component or a new functionality, I would run the app on the emulator and test if it was working. I also used breakpoints to better analyze the behavior of specific parts of the code with Android Studio's debugger. Another useful feature was Android Studio's Logcat, which logs error messages and makes it easier to identify problems with the code.


Q. Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge?

A. One point of the development process that I had to research a solition for was handling the click event on the Update button of each item. I wanted to open a dialog With the item's current information, however it would not be a best practice to have this logic in the Item Adapter, where the OnClickListener was set. A solution was fairly simple, I defined the function to open the dialog in MainActivity, and then I used ((MainActivity)context).onUpdateItemClick(Args...) to reference that function. That allowed me to respect the separation of concerns between the Activity and the Adapter.


Q. In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?

A. One of the most complex components of my app was UpdateItemDialog. In this class, I show how to retrieve arguments from a bundle and to create a dialog with positive and negative buttons. Furthermore, I created a listener interface and an OnAttach method that defines that the listener must be implemented by the activity. The activity uses this listener to update the database and the adapter in the main activity.
