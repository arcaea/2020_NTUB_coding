|top|[Classification](#Classification)|[Clustering](#Clustering)|
|:----:|:----:|:----:|

scikit learn網站:https://scikit-learn.org/stable/

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
[top](#top)

Clustering
----
```python
#Clustering
import pandas as pd
rating=[['n1',1,1,1,1],
        ['n2',2,2,2,2],
        ['n3',3,3,3,3],
        ['n4',4,4,4,4],
        ['n5',5,5,5,5],]
movies =pd.DataFrame(rating,columns=['Name','A','B','C','D'])
movies
```
```python
from sklearn.cluster import KMeans
data =movies.drop('Name',axis=1)
model3=KMeans(n_clusters=2)
model3.fit(data)
print(model3.labels_)
```
```python
model3.cluster_centers_
```
```python
from sklearn import datasets
import matplotlib.pyplot as plt
%matplotlib inline
data,y=datasets.make_moons(n_samples=500,noise=1)
data=pd.DataFrame(data,columns=['x','y'])
data.plot.scatter(x='x',y='y')
```
```python
from sklearn.cluster import KMeans
model3=KMeans(n_clusters=2)
model3.fit(data)
data.plot.scatter(x='x',y='y',c=model3.labels_,cmap='jet')
```
```python
from sklearn.cluster import DBSCAN
model4=DBSCAN(eps=0.2,min_samples=10)
model4.fit(data)
data.plot.scatter(x='x',y='y',c=model4.labels_,cmap='jet')
```
[top](#top)
