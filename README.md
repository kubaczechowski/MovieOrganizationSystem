# Movie Organization System
Program was developed within two weeks in collaboration with @Laszlo199 and @kamila0801. Program is the final assignment for the 1st semester in Business Academy Southwest.

![image](https://user-images.githubusercontent.com/67064580/104900972-100cf200-597d-11eb-935f-cb486047655e.png)
 
 
## Which functionalities are included?

- [adding / removing movies](###adding-and-removing-movies)
- adding / removing categories
- setting multiple categories per movie
- filtering movies by title, rating and category
- changing rating for a movie
- playing movie in built in media player
- searching up a movie title in built-in browser
- updating lastview each time user plays a movie

### Adding and removing movies
![image](https://user-images.githubusercontent.com/67064580/104903405-3c763d80-5980-11eb-9dd7-aea372c4ca06.png)

In order to add a movie user has to invoke "plus" button in the bottom left corner. Then they will see a window where they are supposed to enter the title of the movie, rating and by using the choose button the link to the movie file on the disc. 

Here are three scenarios :
- entered title exists in the database
- entered title is similar to the title/s existing in the database
- entered title is not similar to the existing tiltes and doesn't exist in database

In the fist case user will be notified that title in question already exists and won't be able to add this same title. Warning alert will pop up

![image](https://user-images.githubusercontent.com/67064580/104903569-7c3d2500-5980-11eb-95eb-a5efc282640b.png)

In the second case informatory alert will pop up. User will see similar titles and will be able to decide if they want to add the record. 

![image](https://user-images.githubusercontent.com/67064580/104904362-5cf2c780-5981-11eb-902a-dce2e41c8cbc.png)

In the last case movie will be saved without any further actions.

To remove a movie user has to select a movie and invoke the "dust bin" button in the bottom left conrner and then confirm choise.

### Adding removing categories 
Adding and removing categories is very straighforward. User decides to do so using two buttons at the left in the bottom right corner. Two this same categories cannot be entered and if there is a similar category user will be notified.

### Setting categories
User has to select both category and movie on which they want to make changes and then regarding wether they want to set/unset a category they invoke corresponding button (they are located at the bottom right corner)

### Changing rating
User has to select the movie on which will be made changes and then press the "edit" icon in the bottom left corner. Alert will pop up and user will be able to make changes.

### playing movie in built-in media player
User has to double click the movie and then movie player will appear

![image](https://user-images.githubusercontent.com/67064580/104906909-9aa51f80-5984-11eb-8117-52cdf699d197.png)

