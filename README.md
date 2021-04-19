Mini splitwise implementation as takehome
-----

Two classes (BasicImplementation.java && MultiGroup.java) have driver main functions.

Instead of iterating over all transactions, have added a HashMap in each group to maintain balance between users. This
adds some complexity to maintain the map, but reduces time complexity of printing the summaries to O(k) where k is the 
number of users in the group.


Question
------

Design miniSplitwise which works just like Splitwise but with limited features. 
Features:
1. User should be able to create group, by default admin 
    1. Admin should be able to add members in a group 
    2. Admin should be able to remove members in a group // cannot remove self 
        1. Restriction to not allow remove member unless the person owes some money with in a group 
2. User shares can be divided equally, based on fixed-values or by percentage 
3. User can see owe amount group-wise and overall. 
4. For simplicity, user cannot owe anyone without a group, example: if Ram owes Rs. 20 to Sham, he has to create a group to record the amount. 
5. User can be part of multiple groups 

Assumptions:
1. Users cannot be invited 
2. User can not be deleted 
4. Admin privileges cannot be shifted 


Initialize your objects with follow values:
User:

ID	Name
1	Michael
2	Jane
3	Shawn

Group:
		
User_id	Group_name	role
1	Group1	admin
2	Group1	member
3	Group1	member



Consider the following input for bills:
Bill1, Rs. 600, shared between 1 and 2 equally, paid by 1
Bill2, Rs. 750, shared between 1,2 and 3 by fixed-value(1: Rs.200, 2: Rs. 500 3: 
Rs. 50), paid by 2

The user can view the summary of owed/ lent amounts when he visits the group balance summary page ; Each user sees information relevant to him with respect to how much he owes someone else in the group and/or how much he is owed
Eg:
Group1 shown to 1:
Jane Owe Rs 100 to you
Group1 shown to 2:
	You Owe Rs. 100 to Michael
	Shawn Owe Rs. 50 to you
Group1 shown to 3:
	You owe Rs. 50 to Jane




