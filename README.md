MasterThesis
============

The main repository for my Master Thesis.

# Table of Content
1. Abstract
2. Introduction
3. Theoretical Part
  1. Machine Learning Basics
  2. Prior Art
  3. Detailed Description of different Classifiers used
4. Practical Part
  1. Introduction / Procedure
  2. Data Set / Data Analysis
  3. Naive Approach
  4. Machine Learning Results
    1. Execution Plan
    2. Data Preparation / Statistical Pre-Analysis
    3. Decision Trees
    4. Random Forest
    5. Naive Bayes
    6. HMM / Neural Networks
  5. Comparison of Results
5. Further Work
6. Conclusion

# Execution Plan

In order to create reproducable results and to have a good understanding of what we want to test and how we actually test it we have created an execution plan. The plan defines in advance the features and the data set that we validate our ideas against and that we use in our evaluation. 

We are using different classifiers each with all the different feature sets. We are using the same pre processed data set for every execution. 

## Classifiers

- Decision Trees (Based on J48, unpruned)
- Naive Bayes
- Random Forest

- Classifier with Back Propagation (e.g. [MultilayerPerceptron](http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/MultilayerPerceptron.html) or [Hidden Markov Models](http://www.doc.gold.ac.uk/~mas02mg/software/hmmweka/index.html))

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
