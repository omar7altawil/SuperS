#import numpy(NumPy is the fundamental package for scientific computing with Python)
import numpy as np
#import sklearn(scikit-learn is a Python module for machine learning built on top of SciPy)
from sklearn import preprocessing, cross_validation, neighbors
#import panda(easy-to-use data structures and data analysis tools for the Python)
import pandas as pd
#store the data set in df
df = pd.read_csv('data.csv')
#x will contain the features(1-255 column)
X = np.array(df.drop(['label'],1))
#y will contain the label(256 column)
y = np.array(df['label'])

#split the data to training data(80%) and testing data(20%)
X_train, X_test, y_train, y_test = cross_validation.train_test_split(X, y, test_size=0.2)
#we use KNeighborsClassifier to train the data(not a good idea)
clf = neighbors.KNeighborsClassifier()
#the result of the training
clf.fit(X_train, y_train)
#find the accuracy based on the tested data
accuracy= clf.score(X_test, y_test)
#print out the accuracy
print(accuracy)
