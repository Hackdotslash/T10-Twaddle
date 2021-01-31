import pandas as pd
from sklearn import linear_model
import matplotlib.pyplot as plt
import pickle
import numpy
import math


train_df = pd.read_csv('../data/train/train.csv')
#print(train_df)
test_df = pd.read_csv('../data/test/test.csv')

x_train = train_df[["ACCELERATION"]]
#x_train[["ENGINE_LOAD"]] **= 2
x_train = x_train.to_numpy()
#print(type(x_train))
y_train = train_df[["FUEL_CONSUMED"]]
y_train = y_train.to_numpy()
#print(x_train, y_train)
x_test = test_df[["ACCELERATION"]]
#x_test[["ACCELERATION"]] /= 3
x_test = x_test.to_numpy()
y_test = test_df[["FUEL_CONSUMED"]]
y_test = y_test.to_numpy()
#print(x_test, y_test)

model = linear_model.LinearRegression()
model.fit(x_train, y_train)

xx_test = []
accuracy = 0
count = 0
y_pred = []
for i, j in zip(range(len(x_test)), range(len(y_test))):
    #print(x_test[i])
    predicted = model.predict(x_test[i].reshape(-1, 1))
    y_pred.append(predicted[0][0])
    xx_test.append(x_test[i][0])
    if y_test[j] != 0:
        print(round(predicted[0][0]), y_test[j][0], abs(predicted[0][0] - y_test[j][0]) / y_test[j][0])
        # if abs(predicted[0][0] - y_test[j][0]) / y_test[j][0] < 1:
        accuracy += 1 - abs(predicted[0][0] - y_test[j][0]) / y_test[j][0]
        count += 1

print("Accuracy = {}%".format(accuracy * 100 / count))


#plt.scatter(x_train, y_train,  color='black')

plt.scatter(xx_test, [y for y in y_test], color='black')
plt.scatter(xx_test, [y for y in y_pred], color='blue')

plt.show()


saved_model = 'model.sav'
pickle.dump(model, open(saved_model, 'wb'))
