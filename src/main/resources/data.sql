insert into location values (1, "Bengaluru", "India", "Karnataka");
insert into location values (2, "Kochi", "India", "Kerala");
insert into location values (3, "Kolkata", "India", "West Bengal");
insert into location values (4, "Chennai", "India", "Tamil Nadu");
insert into location values (5, "Mumbai", "India", "Maharashtra");

insert into main_category values (1, "Education");
insert into main_category values (2, "Science");
insert into main_category values (3, "Politics");
insert into main_category values (4, "General");
insert into main_category values (5, "Sports");

insert into sub_category values (1, "Computer Science", 1);
insert into sub_category values (2, "Physics", 2);
insert into sub_category values (3, "Elections", 3);
insert into sub_category values (4, "Miscellaneous", 4);
insert into sub_category values (5, "Football", 5);

INSERT INTO roles VALUES(1,'ROLE_USER');

ALTER TABLE questions ADD FULLTEXT(title);
ALTER TABLE questions ADD FULLTEXT(body);
ALTER TABLE answers ADD FULLTEXT(answer_body);

