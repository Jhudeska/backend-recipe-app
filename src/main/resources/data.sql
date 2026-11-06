INSERT INTO categories (name, description, created_at, updated_at)
VALUES
    ('Ontbijt', 'Recepten voor het ontbijt', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Lunch', 'Recepten voor de lunch', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Diner', 'Recepten voor het avondeten', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Vegetarisch', 'Recepten zonder vlees of vis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Dessert', 'Zoete gerechten en nagerechten', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO user_profiles
(keycloak_id, username, email, profile_photo_url, bio, created_at, updated_at)
VALUES
    ('1f77a917-e396-4ac0-8ce6-a3354ce8c70b','jan_kok', 'jan@receta.com',  'https://plus.unsplash.com/premium_photo-1679076061298-8e08f84c9f95?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=NHwxMjA3fDA%3D',
     'Koken is mijn hobby',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('558943c2-0879-4a39-8563-59a097d7dd96','sara_food', 'sara@receta.com', 'https://plus.unsplash.com/premium_photo-1770447616600-139515d59a39?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=NHwxMjA3fDA%3D',
     'Liefhebber van gezonde en vegetarische gerechten.',
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('9ff19590-29ef-4a27-bb1f-a8d1dd90e2e8','peter_chef', 'peter@receta.com', 'https://images.unsplash.com/photo-1627729205753-52d2ddeefce1?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=NHwxMjA3fDA%3D',
     'Ik experimenteer graag met nieuwe smaken.',
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('608a5ff3-1715-485a-8170-c9f20b4ce3ca', 'lisa_bakt', 'lisa_bakt@receta.com', 'https://plus.unsplash.com/premium_photo-1792955403624-365ca397c8f1?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=NHwxMjA3fDA%3D',
     'Mijn passie is bakken en desserts maken.',
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('052a934e-99fc-4214-864f-aa8674648902','tom_foodie', 'tom@receta.com', 'https://media.istockphoto.com/id/2234215664/photo/a-company-manager-at-his-working-desk-showing-worried-face-expression.webp?a=1&b=1&s=612x612&w=0&k=20&c=yCBkOfhAwK6XjR4e64R2LPH4HZjQ180UiYj5_lFxY9A=',
     'Ik deel graag mijn favoriete recepten.',
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO recipes (
    title,
    description,
    instructions,
    preparation_time,
    servings,
    category_id,
    created_at,
    updated_at
)
VALUES
    (
        'Spaghetti Bolognese',
        'Een klassieke Italiaanse pasta met gehaktsaus en tomaat.',
        '1. Kook de spaghetti volgens de verpakking. 2. Bak het gehakt met ui en knoflook. 3. Voeg tomatensaus toe en laat 20 minuten sudderen. 4. Serveer de saus met de spaghetti.',
        35,
        4,
        (SELECT category_id FROM categories WHERE name = 'Diner'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Pannenkoeken',
        'Luchtige pannenkoeken voor ontbijt of lunch.',
        '1. Meng bloem, melk en eieren tot een glad beslag. 2. Verhit boter in een koekenpan. 3. Giet een dunne laag beslag in de pan. 4. Bak beide kanten goudbruin.',
        20,
        4,
        (SELECT category_id FROM categories WHERE name = 'Ontbijt'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Caesarsalade',
        'Een frisse salade met kip, sla, croutons en dressing.',
        '1. Was de sla en snijd deze in stukken. 2. Bak de kip gaar en snijd in reepjes. 3. Meng de sla met kip en croutons. 4. Voeg de dressing en Parmezaanse kaas toe.',
        25,
        2,
        (SELECT category_id FROM categories WHERE name = 'Lunch'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Tomatensoep',
        'Een eenvoudige soep met rijpe tomaten en kruiden.',
        '1. Fruit ui en knoflook in een pan. 2. Voeg tomaten en groentebouillon toe. 3. Laat 20 minuten koken. 4. Pureer de soep en breng op smaak met peper en zout.',
        30,
        4,
        (SELECT category_id FROM categories WHERE name = 'Lunch'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Gebakken zalm met groenten',
        'Zalmfilet met verse groenten als complete maaltijd.',
        '1. Verwarm de oven voor op 180 graden. 2. Kruid de zalm met peper, zout en citroen. 3. Snijd de groenten en leg alles in een ovenschaal. 4. Bak ongeveer 15 tot 20 minuten tot de zalm gaar is.',
        30,
        2,
        (SELECT category_id FROM categories WHERE name = 'Diner'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

INSERT INTO ingredients (name, created_at, updated_at)
VALUES
    ('Tomaat', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Ui', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Knoflook', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Paprika', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Courgette', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO recipe_ingredients (
    recipe_id,
    ingredient_id,
    quantity,
    unit,
    created_at,
    updated_at
)
VALUES
    (
        (SELECT recipe_id FROM recipes
         WHERE title = 'Spaghetti Bolognese'),
        (SELECT ingredient_id FROM ingredients
         WHERE name = 'Tomaat'),
        400,
        'gram',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT recipe_id FROM recipes
         WHERE title = 'Spaghetti Bolognese'),
        (SELECT ingredient_id FROM ingredients
         WHERE name = 'Ui'),
        1,
        'stuk',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT recipe_id FROM recipes
         WHERE title = 'Spaghetti Bolognese'),
        (SELECT ingredient_id FROM ingredients
         WHERE name = 'Knoflook'),
        2,
        'tenen',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT recipe_id FROM recipes
         WHERE title = 'Tomatensoep'),
        (SELECT ingredient_id FROM ingredients
         WHERE name = 'Tomaat'),
        500,
        'gram',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT recipe_id FROM recipes
         WHERE title = 'Tomatensoep'),
        (SELECT ingredient_id FROM ingredients
         WHERE name = 'Ui'),
        1,
        'stuk',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

INSERT INTO recipe_images
(recipe_id, image_url, created_at, updated_at)
VALUES
    (
        (SELECT recipe_id
         FROM recipes
         WHERE title = 'Spaghetti Bolognese'),
        'https://www.leukerecepten.nl/app/uploads/2025/07/spaghetti-bolognese_2.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT recipe_id
         FROM recipes
         WHERE title = 'Pannenkoeken'),
        'https://uitdekeukenvanfatima.nl/wp-content/uploads/2018/01/IMG_2824-2-1.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT recipe_id
         FROM recipes
         WHERE title = 'Caesarsalade'),
        'https://www.leukerecepten.nl/app/uploads/2025/06/caesar-salade_.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT recipe_id
         FROM recipes
         WHERE title = 'Tomatensoep'),
        'https://media.istockphoto.com/id/2083032901/photo/fresh-tomato-soup-in-a-white-bowl-on-a-white-backgroundtomato-soup-with-basil-and-olive-oil.webp?a=1&b=1&s=612x612&w=0&k=20&c=nMhlSU6s5NoagY8kGxAm5bDFpSBQvxeqZU8jEUxl3jU=',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT recipe_id
         FROM recipes
         WHERE title = 'Gebakken zalm met groenten'),
        'https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?q=80&w=2340&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=NHwxMjA3fDA%3D',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );


INSERT INTO user_profile_roles
(user_id, role, created_at, updated_at)
VALUES
    (
        (SELECT user_id FROM user_profiles WHERE username = 'jan_kok'),
        'USER',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT user_id FROM user_profiles WHERE username = 'sara_food'),
        'USER',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT user_id FROM user_profiles WHERE username = 'peter_chef'),
        'CHEF',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT user_id FROM user_profiles WHERE username = 'lisa_bakt'),
        'ADMIN',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT user_id FROM user_profiles WHERE username = 'tom_foodie'),
        'USER',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );




INSERT INTO review_ratings
(comment, ratingscore, user_id, recipe_id, created_at, updated_at)
VALUES
    ('Heerlijk recept, makkelijk te bereiden!', 5, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Lekker van smaak, ik maak dit zeker opnieuw.', 4, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Goed recept, maar de bereiding duurde wat langer.', 3, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Heel lekker en duidelijke instructies.', 5, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Prima recept, maar er mocht wat meer kruiden in.', 4, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO favorites (
    user_id,
    recipe_id,
    created_at,
    updated_at
)
VALUES
    (
        (SELECT user_id FROM user_profiles
         WHERE username = 'jan_kok'),
        (SELECT recipe_id FROM recipes
         WHERE title = 'Spaghetti Bolognese'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT user_id FROM user_profiles
         WHERE username = 'jan_kok'),
        (SELECT recipe_id FROM recipes
         WHERE title = 'Pannenkoeken'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT user_id FROM user_profiles
         WHERE username = 'jan_kok'),
        (SELECT recipe_id FROM recipes
         WHERE title = 'Caesarsalade'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT user_id FROM user_profiles
         WHERE username = 'jan_kok'),
        (SELECT recipe_id FROM recipes
         WHERE title = 'Tomatensoep'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT user_id FROM user_profiles
         WHERE username = 'jan_kok'),
        (SELECT recipe_id FROM recipes
         WHERE title = 'Gebakken zalm met groenten'),
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );

