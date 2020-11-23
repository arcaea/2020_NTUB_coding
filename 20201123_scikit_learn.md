|[#Classification]|[#Clustering]|
|:----:|:----:|

Classification
----
```python
#Step 1. 取得資料
from sklearn import datasets
iris=datasets.load_iris()
#print(iris.data)
#print(iris.target)
#print(iris.deature_names)
print(iris.target_names)
```
```python
#Step 2. 分割資料
from sklearn.model_selection import train_test_split
X=iris.data
y=iris.target
X_train,X_test,y_train,y_test=train_test_split(X,y,train_size=0.7)
```
```python
#Step3. 建構模型
from sklearn.tree import DecisionTreeClassifier
model1=DecisionTreeClassifier()#建盒子
model1.fit(X_train,y_train)
y_pred=model1.predict(X_test)
```
```python
from sklearn.neighbors import KNeighborsClassifier
model2=KNeighborsClassifier(n_neighbors=7)
model2.fit(X_train,y_train)
y_pred2=model2.predict(X_test)
```
```python
#Step4. 模型評估
from sklearn import metrics
#metrics.confusion_matrix(y_test,y_pred)
#print(metrics.confusion_matrix(y_test,y_pred))
#print(metrics.confusion_matrix(y_test,y_pred2))
print(metrics.classification_report(y_test,y_pred))
print(metrics.classification_report(y_test,y_pred2))
```

Clustering
----
