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

INSERT INTO categories (name, description, created_at, updated_at)
VALUES
    ('Ontbijt', 'Recepten voor het ontbijt', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Lunch', 'Recepten voor de lunch', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Diner', 'Recepten voor het avondeten', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Vegetarisch', 'Recepten zonder vlees of vis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Dessert', 'Zoete gerechten en nagerechten', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user_profiles
(username, email, password, password_reset_token,
 profile_photo_url, bio, created_at, updated_at)
VALUES
    ('jan_kok', 'jan@receta.com', '',
     NULL, 'https://receta.com/photos/jan.jpg',
     'Ik hou van koken en nieuwe recepten ontdekken.',
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('sara_food', 'sara@receta.com', '',
     NULL, 'https://receta.com/photos/sara.jpg',
     'Liefhebber van gezonde en vegetarische gerechten.',
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('peter_chef', 'peter@receta.com', '',
     NULL, NULL,
     'Ik experimenteer graag met nieuwe smaken.',
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('lisa_bakt', 'lisa@receta.com', '',
     NULL, 'https://receta.com/photos/lisa.jpg',
     'Mijn passie is bakken en desserts maken.',
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('tom_foodie', 'tom@receta.com', '',
     NULL, NULL,
     'Ik deel graag mijn favoriete recepten.',
     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO review_ratings
    (comment, ratingscore, user_id, recipe_id, created_at, updated_at)
VALUES
    ('Heerlijk recept, makkelijk te bereiden!', 5, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Lekker van smaak, ik maak dit zeker opnieuw.', 4, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Goed recept, maar de bereiding duurde wat langer.', 3, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Heel lekker en duidelijke instructies.', 5, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Prima recept, maar er mocht wat meer kruiden in.', 4, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO recipe_images
(recipe_id, image_url, created_at, updated_at)
VALUES
    (
        (SELECT recipe_id
         FROM recipes
         WHERE title = 'Spaghetti Bolognese'),
        'https://receta.com/photos/spaghetti-bolognese.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT recipe_id
         FROM recipes
         WHERE title = 'Pannenkoeken'),
        'https://receta.com/photos/pancakes.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT recipe_id
         FROM recipes
         WHERE title = 'Caesarsalade'),
        'https://receta.com/photos/caesar-salad.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT recipe_id
         FROM recipes
         WHERE title = 'Tomatensoep'),
        'https://receta.com/photos/tomato-soup.jpg',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        (SELECT recipe_id
         FROM recipes
         WHERE title = 'Gebakken zalm met groenten'),
        'https://images.unsplash.com/photo-1791398667285-345d7cc05a04?q=80&w=763&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=NHwxMjA3fDA%3D',
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