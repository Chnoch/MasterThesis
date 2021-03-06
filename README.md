## MasterThesis - Simon Baumann
# Predicting User's Interest Profile for Public Transportation

## Abstract

In this thesis we analyze different ways to reliable predict the interest profile of users of public transportation. Having a method where we can successfully predict future behavior enables us to create useful assistances for the user. We can support the way people access information about public transport and proactively suggest trips they are most likely to take in the near future. We also avoid wasting gathered data on the user profiles.

To achieve our goal we applied different machine learning techniques on a real life dataset, gathered anonymously from users of a public transport related Android application. The application gives users the possibility to display the timetable of public transportation at stations close to them. In addition the app collects data about the stations the user has passed in his daily routine. We want to be able to predict the next station the user is going to as accurately as possible, given information that is available to the system in the moment.

As raw data we have gathered data points that include the user id, the current timestamp and the station id of the closest station. We have analyzed our data set from different perspectives such as the temporal distribution of our data or geographical usage distribution. This analyzes has given us insight into how our data is structured and what limitations are present. We have also created a baseline approach to get a first test of the accuracy, where we achieved 45% correctly predicted entries.

We then applied statistical analysis to further refine and reduce our data set. This was necessary to avoid a distortion of our predictions from users where we don’t have enough data or the data doesn’t properly represent real-life use cases. We have removed low-profile users and have reduced the amount of stations that we want to be able to predict. We compared multiple machine learning algorithms and analyzed the advantages and disadvantages of each of the algorithms. We executed different experiments with a combination of feature sets with all algorithms and evaluated the results and predictions to draw conclusions to the reliability and the meaningfulness of our approach. We achieved an average accuracy on our predictions of 89%. We further discuss what future work can be done to take advantage of the techniques that we explored in our thesis.

## Thesis
You can find the whole thesis [here](https://github.com/Chnoch/MasterThesis/blob/master/Thesis/Master%20Thesis%20-%20Simon%20Baumann.pdf)

All code that we used for the thesis is available in this repository.
