use drugs;

insert into ingredients values
(1, "acetylsalicylic", DEFAULT),
(2, "phenacetin", DEFAULT),
(3, "caffeine", DEFAULT),
(4, "cocoa", DEFAULT),
(5, "citric acid", DEFAULT),
(6, "paracetamol", DEFAULT),
(7, "diclofenac sodium", DEFAULT),
(8, "benzylpenicillin", DEFAULT),
(9, "aminopenicillins", DEFAULT),
(10, "mezlocillin", DEFAULT);

insert into patients values
(1, DEFAULT, DEFAULT),
(2, DEFAULT, DEFAULT),
(3, DEFAULT, DEFAULT);

insert into patients_ingredients values
(1, 3),
(1, 4),
(2, 6);

insert into drugs values
(1, "Citramon",
"Alvogen IPKo S.r.l, 5, Rue Heinhaff, L-1736, Senningerberg, Luxembourg", "S. A. FARMATEN (first and second pakuvannya, control of the series, approval for the release of the series), 6, Dervenakion, Pallini Attiki 15351, Grecia",
"UA/15524/02/02", "N02B A51",
"https://compendium.com.ua/dec/261896/", "",
"Voriconazole", DEFAULT, DEFAULT),
(2, "Phenergan",
"Kusum Helthker Pvt Ltd, India", "KUSUM HELTHKER PVT LTD, India",
"UA/15524/02/02", "M01A B55",
"https://compendium.com.ua/info/6905/fanigan/", "unlimited from 15.12.2016",
"", DEFAULT, DEFAULT);

insert into drugs_ingredients values
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(2, 6),
(2, 7);

insert into ingredient_groups values(1, "Penicillins", DEFAULT, DEFAULT);
insert into ingredients_group_belonging values
(1,8),
(1,9),
(1,10);


