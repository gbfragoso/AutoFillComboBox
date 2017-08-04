# AutoFillCombobox
An simple autofill approach to extend the default JavaFX's combobox behavior.

# Motivation
The default combobox component of JavaFX dont offer a option to query his list of items. This code is a powerful solution to this problem.

# Assumptions
* You has an ordered list of strings

# Performance tips
* For type safe reasons we use .toLowerCase() function at input string and on search. If your list contains only upper/lower case strings you can remove the .toLowerCase() from search.

  items.stream().filter(p -> p.**toLowerCase()**.startsWith(lower)).findFirst();

# Code Example
With a predefined list:

```java
  ObservableList<String> items = FXCollections.observableArrayList();
  // Add items to list
  AutoFillComboBox autoFill = new AutoFillComboBox(items);

```

Without a predefined list of items:
```java
  AutoFillComboBox autoFill = new AutoFillComboBox();
  // some code
  ObservableList<String> items = FXCollections.observableArrayList();
  autoFill.setItems(items);
  autoFill.setAutoFill(true);
```  
