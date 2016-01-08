MasterThesis
============

The main repository for my Master Thesis.

# Table of Contents
1. Abstract (motivations of the work, a problem that is addressed, solutions proposed and evaluation, ~1/2 page)
2. Introduction (the same as abstract but in more detail)
3. Related work (prior works go here)
4. Sequence prediction
  1. Task Description (we model our problem as a sequence prediction problem)
  2. Proposed Solutions (Detailed Description of Different Classifiers used, and how they are going to be used)
5. Experiments and Evaluation
  1. Introduction / Procedure
  2. Data Set / Data Analysis
  3. Naive Approach
  4. Machine Learning Results
    1. Execution Plan
    2. Data Preparation / Statistical Analysis
    3. Decision Trees (Random Forest is included)
    4. Naive Bayes
    5. HMM / Neural Networks
  5. Comparison of Results
6. Conclusion
7. Future Work

# Execution Plan

In order to create reproducable results and to have a good understanding of what we want to test and how we actually test it we have created an execution plan. The plan defines in advance the features and the data set that we validate our ideas against and that we use in our evaluation. 

We are using different classifiers each with all the different feature sets. We are using the same pre processed data set for every execution. 

## Classifiers

- Decision Trees (Based on J48, unpruned)
- Naive Bayes
- Random Forest

- Classifier with Back Propagation (e.g. [MultilayerPerceptron](http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/MultilayerPerceptron.html) or [Hidden Markov Models](http://www.doc.gold.ac.uk/~mas02mg/software/hmmweka/index.html))

## Machine Learning Experiments
- Precision/Recall/F1 vs Number of most frequent stations
- Precision/Recall/F1 for different users (frequent/non-frequent)

## Feature Sets

We have gathered data for the following features. We will use different combinations of feature sets in our evaluation in order to make the most precise predictions.
- User
- Current Station
- Hour of Day
- Minute of Hour
- Day of Week
- Weekday/Weekendday
- Previous Station
- Next Station

Stations are categorical and are encoded as one-hot-vectors. Stations that are not in the most frequent set should be encoded as an additional dimention of a vector.

The ground truth, i.e. what's to be predicted is the next Station. We only use data for each user separately, as the classifiers are being too overloaded when using the full data set. So the user is automatically respected for every feature set.

The following are the different feature sets that we're using:

Current Station

---

Current Station, Previous Station

---

Current Station, Hour of Day

---

Current Station, Hour of Day, Day of Week

---

Current Station, Hour of Day, Weekday/Weekendday

---

Current Station, Hour of Day, Minute of Hour, Day of Week

---

Current Station, Previous Station, Hour of Day, Day of Week

---

Current Station, Previous Station, Hour of Day, Weekday / Weekendday

---

Current Station, Previous Station, Hour of Day, Minute of Hour, Day of Week, Weekday / Weekendday

---

Hour of Day, Day of Week, Minute of Hour
