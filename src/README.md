# Self Project One - Task Management System

## Description

A `Tasks Management` console application to be used by a small team of developers, who need to keep track of all the tasks, 
surrounding a software product they are building

## Models

> Note: All validation intervals are inclusive (closed).

### **Bug**

- `title` - a string between `10` and `100` symbols.
- `description` - a string between `10` and `500` symbols.
- `steps` - a collection of strings showing how to reproduce the bug.
- `priority` - an enum of type `[High, Medium, Low]`.
- `severity` - an enum of type `[High, Medium, Low]`.
- `status` are always `4`.
- Should be convertible to **String** in the format:

```none
#. Car:
Make: {make}
Model: {model}
Wheels: {wheels}
Price: ${price}
Seats: {seats}
```

### **Motorcycle**

- `make` - a string with а length between `2` and `15` symbols.
- `model` - a string with а length between `1` and `15` symbols.
- `price` - a number between `0.0` and `1000000.0`.
- `category` - a string with а length between `3` and `10` symbols.
- `wheels count` are always `2`.
- Should be convertible to **String** in the format:

```none
#. Motorcycle:
Make: {make}
Model: {model}
Wheels: {wheels}
Price: ${price}
Category: {category}
```

### **Truck**

- `make` - a string with а length between `2` and `15` symbols.
- `model` - a string with а length between `1` and `15` symbols.
- `price` - a number between `0.0` and `1000000.0`.
- `weight capacity` - a number between `1` and `100`.
- `wheels count` are always `8`.
- Should be convertible to **String** in the format:

```none
#. Truck:
Make: {make}
Model: {model}
Wheels: {wheels}
Price: ${price}
Weight Capacity: {weightCapacity}t
```

### **User**

- `username` - a string with а length between `2` and `20` symbols and a certain pattern to be followed.
- `first name` - a string with а length between `2` and `20` symbols.
- `last name` - a string with а length between `2` and `20` symbols.
- `password` - a string with а length between `5` and `30` symbols a certain pattern to be followed.
- a collection of vehicles.
- `User role` - an enum of type `[Normal, VIP, or Admin]`:
   - If the user is an admin, they cannot add a vehicle
   - If the user is not VIP, they cannot add more than `5` vehicles

- The users can **AddVehicle**, **RemoveVehicle**, **AddComment** and **RemoveComment**
- Every user has a collection of `Vehicles` and every `Vehicle` in this collection has a collection of `Comments`.
- Users should **register** and **login** before doing anything in the Car Dealership. If a user is not logged-in or another one is logged-in, the former cannot do anything in the system.

- Printing
	- For the `User` class:

`Username: {Username}, FullName: {FirstName} {LastName}, Role: {Role}`

	- For all vehicles of the user:

```none
--USER {Username}--
1. {Vehicle type}:
Make: {make}
Model: {model}
Wheels: {wheels}
Price: ${price}
Category/Weight capacity/Seats: {category/weight capacity/seats}
--COMMENTS--
----------
{Content}
User: {Comment Username}
----------
----------
{Content}
User: {Comment username}
----------
--COMMENTS--
2. {Vehicle type}:
Make: {make}
Model: {model}
Wheels: {wheels}
Price: ${price}
Category/Weight capacity/Seats: {category/weight capacity/seats}
--NO COMMENTS--
```

### **Comment**

- `author`
- `content` - a string with а length between `3` and `200` symbols.
- Should be convertible to **String** in the format:

```none
----------
{Content}
User: {Comment username}
----------
```

### **Additional Validations**

**The error messages and all the constraints for each field that must be validated can be *found in the example output*. If you are unsure about some constraints, run the tests. You already have the error messages in the respective classes.**

- **Vehicle validation**
  - Make and model length.
  - Price range.
  - Wheels count.
  - Seats count (for car).
  - Category length (for motorcycle).
  - Weight capacity (for truck).

- **Comment validation**
  - Content.

- **User Validation**
  - Username length.
  - FirstName length.
  - LastName length.
  - Password length.

**All properties in the above classes are mandatory (cannot be empty).**

- **User actions**
	- Adding a vehicle
		- If the user is admin, they cannot add a vehicle
		- If the user is not VIP, they cannot add more than `5` vehicles

	- Adding a comment
		- Just add the comment to the list

	- Removing a vehicle
		- Just remove the vehicle from the list

	- Removing a comment
		- If the author of the comment is not the current user, they cannot remove it

- **The dashes separating the comments are exactly 10.**
- **Price has `$` in front of the value and has no trailing zeros** *(e.g. `Price: $10000`)*

*Hint: You can use this method found in the `FormattingHelpers` class for removing trailing zeros:*

```java
public static String removeTrailingZerosFromDouble(double number) {
    BigDecimal num = BigDecimal.valueOf(number).stripTrailingZeros();
    return num.toPlainString();
}
```

- **The weight capacity has `t` after the value** *(e.g. `Weight capacity: 40t`)*
- **Look into the example below to get better understanding of the printing format.**

## Commands

Currently, the engine supports the following commands:

- **RegisterUser** `[username] [firstName] [lastName] [password] [role]` - registers a user if there is no such user already.
- **Login** `[username] [password]` - logs in a user if they are not already logged-in and there is such a registered user.
- **Logout** - logs out the currently logged-in user.
- **AddVehicle** `[type] [make] [model] [price] [category/seats/weightCapacity]` - adds a vehicle to the current user. The fourth parameter depends on the type of the vehicle.
- **RemoveVehicle** `[vehicleIndex]` - removes the vehicle on that index if there is such.
- **AddComment** `[content] [author] [vehicleIndex]` - adds a comment with the content provided to the vehicle with that index and sets the author.
- **RemoveComment** `[vehicleIndex] [commentIndex] [username]` - removes the comment from the vehicle.
- **ShowVehicles** `[username]` - shows all the vehicles of a user.

Commands that you should implement yourself:
- **ShowUsers** - shows all registered users.

>Note: All commands return **appropriate success messages**. The `Engine` returns **appropriate error messages** in case of an invalid operation or error.

## Constraints

You should implement the empty classes. You can add new classes where needed and modify any of the existing code under the **models** package if necessary.

## Unit Tests

- You have been given unit tests to keep track of your progress. Run them by right-clicking the **tests** package and selecting **Run 'All Tests'**.
- If you get stuck, check out the tests' names to guide you on what to do.
 
> Note: Be careful not to change anything in the **tests** folder.

## Step by step guide

Before starting your work, build the project and see what `TODO`s it has. 

1. Implement all interfaces.

- Look at the **contracts** folder and decide how to use each of the interfaces there.
- **HINT:** some interfaces are segregated (separated into several interfaces) like the `Priceable` interface.

2.Implement the classes in which there is a `TODO`.
**Hint:** Implement `Vehicles` classes first, then `print()` methods and finally `ShowUsersCommand`.
**Hint:** You can check all `TODO`s in a project from the TODO tab on bottom left (may vary for different IDE or different versions of the same IDE)

3. Validate all properties according to the guidelines set above.

- **HINT FOR USERIMPL:** Use UsernamePattern = "^[A-Za-z0-9]+$"; PasswordPattern = "^[A-Za-z0-9@*_-]+$"; You already have it in the class. 
  See `ValidationHelpers` class for the method needed.

4. Try running the tests. You can run them by class in order to orient yourself more easily.

5. Implement printing.

- Instead of a `print()` method, you need to override `toString()` in order to output the classes in the console.

6. Implement the methods inside the `UserImpl` class.

### Sample Input

```none
RegisterUser p Petar Petrov 123456
RegisterUser pesh0= Petar Petrov 123456
RegisterUser pesh0 Petar Petrov 1234
RegisterUser pesh0 Petar P 123456
RegisterUser pesh0 P Petrov 123456
RegisterUser pesho Petar Petrov 123456
AddVehicle Motorcycle K Z1000 9999 Race
AddVehicle Motorcycle Kawasaki Z1000 -1000 Race
AddVehicle Motorcycle Kawasaki Z1000 9999 N
AddVehicle Car Opel Vectra 5000 -1
AddVehicle Truck Volvo FH4 11800 200
AddVehicle Motorcycle Kawasaki Z 9999 Race
AddVehicle Car Opel Vectra 5000 5
AddVehicle Car Mazda 6 10000 5
AddVehicle Motorcycle Suzuki V-Strom 7500 CityEnduro
AddVehicle Car BMW Z3 11200 2
AddVehicle Car BMW Z3 11200 2
AddVehicle Car BMW Z3 11200 2
AddComment {{U}} pesho 1
AddComment {{Amazing speed and handling!}} pesho 1
ShowUsers
RegisterUser pesho Petar Petrov 123457
Logout
RegisterUser pesho Petar Petrov 123457
RegisterUser gosho Georgi Georgiev 123457 VIP
Logout
Login pesho 123456
Login gosho 123457
Logout
Login gosho 123457
AddComment {{I like this one! It is faster than all the rest!}} pesho 1
RemoveComment 1 1 pesho
RemoveComment 2 5 pesho
AddVehicle Motorcycle Suzuki GSXR1000 8000 Racing
AddVehicle Car Skoda Fabia 2000 5
AddVehicle Car BMW 535i 7200 5
AddVehicle Motorcycle Honda Hornet600 4150 Race
AddVehicle Car Mercedes S500L 15000 5
AddVehicle Car Opel Zafira 8000 5
AddVehicle Car Opel Zafira 7450 5
AddVehicle Truck Volvo FH4 11800 40
ShowUsers
Logout
RegisterUser ivancho Ivan Ivanov admin Admin
AddVehicle Car Skoda Fabia 2000 5
ShowUsers
ShowVehicles gosho
ShowVehicles ivanch0
AddComment {{Empty comment}} pencho 1
AddComment {{Empty comment}} pesho 20
RemoveComment 1 1 pesho
ShowVehicles pesho
Logout
Login pesho 123456
AddComment {{I dream of having this one one day.}} pesho 1
Logout
Login ivancho admin
AddComment {{What is the mileage on it?}} pesho 3
Logout
Login pesho 123456
AddComment {{This one passed me by on the highway today. So pretty!}} pesho 3
ShowVehicles pesho
ShowVehicles gosho
ShowVehicles ivancho
Logout
Login gosho 123457
RemoveComment 1 2 pesho
ShowVehicles pesho
Logout
Login pesho 123456
RemoveVehicle 1
ShowVehicles pesho
Exit

```

### Sample Output

```none
Username must be between 2 and 20 characters long!
####################
Username contains invalid symbols!
####################
Password must be between 5 and 30 characters long!
####################
Lastname must be between 2 and 20 characters long!
####################
Firstname must be between 2 and 20 characters long!
####################
User pesho registered successfully!
####################
Make must be between 2 and 15 characters long!
####################
Price must be between 0.0 and 1000000.0!
####################
Category must be between 3 and 10 characters long!
####################
Seats must be between 1 and 10!
####################
Weight capacity must be between 1 and 100!
####################
pesho added vehicle successfully!
####################
pesho added vehicle successfully!
####################
pesho added vehicle successfully!
####################
pesho added vehicle successfully!
####################
pesho added vehicle successfully!
####################
You are not VIP and cannot add more than 5 vehicles!
####################
You are not VIP and cannot add more than 5 vehicles!
####################
Content must be between 3 and 200 characters long!
####################
pesho added comment successfully!
####################
You are not an admin!
####################
User pesho is logged in! Please log out first!
####################
You logged out!
####################
User pesho already exist. Choose a different username!
####################
User gosho registered successfully!
####################
You logged out!
####################
User pesho successfully logged in!
####################
User pesho is logged in! Please log out first!
####################
You logged out!
####################
User gosho successfully logged in!
####################
gosho added comment successfully!
####################
You are not the author of the comment you are trying to remove!
####################
There is no comment on this index.
####################
gosho added vehicle successfully!
####################
gosho added vehicle successfully!
####################
gosho added vehicle successfully!
####################
gosho added vehicle successfully!
####################
gosho added vehicle successfully!
####################
gosho added vehicle successfully!
####################
gosho added vehicle successfully!
####################
gosho added vehicle successfully!
####################
You are not an admin!
####################
You logged out!
####################
User ivancho registered successfully!
####################
You are an admin and therefore cannot add vehicles!
####################
--USERS--
1. Username: pesho, FullName: Petar Petrov, Role: Normal
2. Username: gosho, FullName: Georgi Georgiev, Role: VIP
3. Username: ivancho, FullName: Ivan Ivanov, Role: Admin
####################
--USER gosho--
1. Motorcycle:
Make: Suzuki
Model: GSXR1000
Wheels: 2
Price: $8000
Category: Racing
--NO COMMENTS--
2. Car:
Make: Skoda
Model: Fabia
Wheels: 4
Price: $2000
Seats: 5
--NO COMMENTS--
3. Car:
Make: BMW
Model: 535i
Wheels: 4
Price: $7200
Seats: 5
--NO COMMENTS--
4. Motorcycle:
Make: Honda
Model: Hornet600
Wheels: 2
Price: $4150
Category: Race
--NO COMMENTS--
5. Car:
Make: Mercedes
Model: S500L
Wheels: 4
Price: $15000
Seats: 5
--NO COMMENTS--
6. Car:
Make: Opel
Model: Zafira
Wheels: 4
Price: $8000
Seats: 5
--NO COMMENTS--
7. Car:
Make: Opel
Model: Zafira
Wheels: 4
Price: $7450
Seats: 5
--NO COMMENTS--
8. Truck:
Make: Volvo
Model: FH4
Wheels: 8
Price: $11800
Weight Capacity: 40t
--NO COMMENTS--
####################
There is no user with username ivanch0!
####################
There is no user with username pencho!
####################
The vehicle does not exist!
####################
You are not the author of the comment you are trying to remove!
####################
--USER pesho--
1. Motorcycle:
Make: Kawasaki
Model: Z
Wheels: 2
Price: $9999
Category: Race
--COMMENTS--
----------
Amazing speed and handling!
User: pesho
----------
----------
I like this one! It is faster than all the rest!
User: gosho
----------
--COMMENTS--
2. Car:
Make: Opel
Model: Vectra
Wheels: 4
Price: $5000
Seats: 5
--NO COMMENTS--
3. Car:
Make: Mazda
Model: 6
Wheels: 4
Price: $10000
Seats: 5
--NO COMMENTS--
4. Motorcycle:
Make: Suzuki
Model: V-Strom
Wheels: 2
Price: $7500
Category: CityEnduro
--NO COMMENTS--
5. Car:
Make: BMW
Model: Z3
Wheels: 4
Price: $11200
Seats: 2
--NO COMMENTS--
####################
You logged out!
####################
User pesho successfully logged in!
####################
pesho added comment successfully!
####################
You logged out!
####################
User ivancho successfully logged in!
####################
ivancho added comment successfully!
####################
You logged out!
####################
User pesho successfully logged in!
####################
pesho added comment successfully!
####################
--USER pesho--
1. Motorcycle:
Make: Kawasaki
Model: Z
Wheels: 2
Price: $9999
Category: Race
--COMMENTS--
----------
Amazing speed and handling!
User: pesho
----------
----------
I like this one! It is faster than all the rest!
User: gosho
----------
----------
I dream of having this one one day.
User: pesho
----------
--COMMENTS--
2. Car:
Make: Opel
Model: Vectra
Wheels: 4
Price: $5000
Seats: 5
--NO COMMENTS--
3. Car:
Make: Mazda
Model: 6
Wheels: 4
Price: $10000
Seats: 5
--COMMENTS--
----------
What is the mileage on it?
User: ivancho
----------
----------
This one passed me by on the highway today. So pretty!
User: pesho
----------
--COMMENTS--
4. Motorcycle:
Make: Suzuki
Model: V-Strom
Wheels: 2
Price: $7500
Category: CityEnduro
--NO COMMENTS--
5. Car:
Make: BMW
Model: Z3
Wheels: 4
Price: $11200
Seats: 2
--NO COMMENTS--
####################
--USER gosho--
1. Motorcycle:
Make: Suzuki
Model: GSXR1000
Wheels: 2
Price: $8000
Category: Racing
--NO COMMENTS--
2. Car:
Make: Skoda
Model: Fabia
Wheels: 4
Price: $2000
Seats: 5
--NO COMMENTS--
3. Car:
Make: BMW
Model: 535i
Wheels: 4
Price: $7200
Seats: 5
--NO COMMENTS--
4. Motorcycle:
Make: Honda
Model: Hornet600
Wheels: 2
Price: $4150
Category: Race
--NO COMMENTS--
5. Car:
Make: Mercedes
Model: S500L
Wheels: 4
Price: $15000
Seats: 5
--NO COMMENTS--
6. Car:
Make: Opel
Model: Zafira
Wheels: 4
Price: $8000
Seats: 5
--NO COMMENTS--
7. Car:
Make: Opel
Model: Zafira
Wheels: 4
Price: $7450
Seats: 5
--NO COMMENTS--
8. Truck:
Make: Volvo
Model: FH4
Wheels: 8
Price: $11800
Weight Capacity: 40t
--NO COMMENTS--
####################
--USER ivancho--
--NO VEHICLES--
####################
You logged out!
####################
User gosho successfully logged in!
####################
gosho removed comment successfully!
####################
--USER pesho--
1. Motorcycle:
Make: Kawasaki
Model: Z
Wheels: 2
Price: $9999
Category: Race
--COMMENTS--
----------
Amazing speed and handling!
User: pesho
----------
----------
I dream of having this one one day.
User: pesho
----------
--COMMENTS--
2. Car:
Make: Opel
Model: Vectra
Wheels: 4
Price: $5000
Seats: 5
--NO COMMENTS--
3. Car:
Make: Mazda
Model: 6
Wheels: 4
Price: $10000
Seats: 5
--COMMENTS--
----------
What is the mileage on it?
User: ivancho
----------
----------
This one passed me by on the highway today. So pretty!
User: pesho
----------
--COMMENTS--
4. Motorcycle:
Make: Suzuki
Model: V-Strom
Wheels: 2
Price: $7500
Category: CityEnduro
--NO COMMENTS--
5. Car:
Make: BMW
Model: Z3
Wheels: 4
Price: $11200
Seats: 2
--NO COMMENTS--
####################
You logged out!
####################
User pesho successfully logged in!
####################
pesho removed vehicle successfully!
####################
--USER pesho--
1. Car:
Make: Opel
Model: Vectra
Wheels: 4
Price: $5000
Seats: 5
--NO COMMENTS--
2. Car:
Make: Mazda
Model: 6
Wheels: 4
Price: $10000
Seats: 5
--COMMENTS--
----------
What is the mileage on it?
User: ivancho
----------
----------
This one passed me by on the highway today. So pretty!
User: pesho
----------
--COMMENTS--
3. Motorcycle:
Make: Suzuki
Model: V-Strom
Wheels: 2
Price: $7500
Category: CityEnduro
--NO COMMENTS--
4. Car:
Make: BMW
Model: Z3
Wheels: 4
Price: $11200
Seats: 2
--NO COMMENTS--
####################
```
