# COMP5216: Assignment 1 -> Android Reminder App

## Student Details
- **Name:** Aryan Bhatia 
- **SID:** 490352056
- **Unikey:** abha6244

## Features
### Main View:
- A ListView which displays all the saved tasks. Each task consists of a title and the remaining 
  time (in days, hours and mins e.g. 1 day 7 hours 15 mins) to complete the task. Clicking a task 
  will switch to the “Edit/Add Task” view.
- An “ADD NEW” button. Once this button is clicked, the app will switch to the “Edit/Add Task” view.

### Edit/Add Task View:
- A “Text field” which allows user to type or edit the title of a task to add or update
the ListView
- A “Date/Time Picker” that allows user to set or update the due date/time of a task
- A “Save” button used for adding new, or updating the title and due date/time of a task in 
  the ListView
- If adding a new task item, capture both the title and due date/time of the task. 
  To calculate the remaining time to complete a task, take the time difference between the 
  due date/time and the current system date/time. 
- A “Cancel” button next to the “Save” button, used to close the Activity without updating the task.
  When this button is clicked, the app will pop up a dialog that asks user: 
  ”Are you sure to cancel this edit? Your unsaved edit will be discarded if you click YES”.

### SQLite Database ([**Room**](https://developer.android.com/training/data-storage/room)):
- Every time user launches this app, the app loads the list of tasks from the local Database.
- The list of tasks should be displayed and **sorted based on the remaining time** to complete a task
- If a task is **overdue**, it should display a text “OVERDUE” next to the task title instead of 
  showing the remaining time.
- When clicking the “Save” button in the “Edit/Add Task” view, the app should add or update the task 
  in both the ListView and local Database.
- Add a long click event to delete a task from the ListView. When user tries to delete the selected 
  task, the app will pop up a message that asks user: ”Do you want to delete this task?” If the user
  clicks “YES”, this task will be deleted from both the ListView and local Database.
  
## References 
- [Custom Android Layouts with Your Own ArrayAdapter](https://www.sitepoint.com/custom-data-layouts-with-your-own-android-arrayadapter/)
- [Getting Current Time in Java](https://www.javatpoint.com/java-get-current-date)
- [Comparing Dates in Java](https://www.tutorialspoint.com/how-to-compare-two-dates-in-java#:~:text=In%20Java%2C%20two%20dates%20can,if%20date1%20is%20before%20date2.)
- [Sorting Lists with Custom Comparator](https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property)
