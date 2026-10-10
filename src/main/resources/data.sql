INSERT INTO recipes (
    title,
    description,
    instructions,
    preparation_time,
    servings,
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
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Pannenkoeken',
        'Luchtige pannenkoeken voor ontbijt of lunch.',
        '1. Meng bloem, melk en eieren tot een glad beslag. 2. Verhit boter in een koekenpan. 3. Giet een dunne laag beslag in de pan. 4. Bak beide kanten goudbruin.',
        20,
        4,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Caesarsalade',
        'Een frisse salade met kip, sla, croutons en dressing.',
        '1. Was de sla en snijd deze in stukken. 2. Bak de kip gaar en snijd in reepjes. 3. Meng de sla met kip en croutons. 4. Voeg de dressing en Parmezaanse kaas toe.',
        25,
        2,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Tomatensoep',
        'Een eenvoudige soep met rijpe tomaten en kruiden.',
        '1. Fruit ui en knoflook in een pan. 2. Voeg tomaten en groentebouillon toe. 3. Laat 20 minuten koken. 4. Pureer de soep en breng op smaak met peper en zout.',
        30,
        4,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'Gebakken zalm met groenten',
        'Zalmfilet met verse groenten als complete maaltijd.',
        '1. Verwarm de oven voor op 180 graden. 2. Kruid de zalm met peper, zout en citroen. 3. Snijd de groenten en leg alles in een ovenschaal. 4. Bak ongeveer 15 tot 20 minuten tot de zalm gaar is.',
        30,
        2,
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

INSERT INTO categories (name, description, created_at, updated_at)
VALUES
    ('Ontbijt', 'Recepten voor het ontbijt', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Lunch', 'Recepten voor de lunch', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Diner', 'Recepten voor het avondeten', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Vegetarisch', 'Recepten zonder vlees of vis', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Dessert', 'Zoete gerechten en nagerechten', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO review_ratings
    (comment, ratingscore, user_id, recipe_id, created_at, updated_at)
VALUES
    ('Heerlijk recept, makkelijk te bereiden!', 5, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Lekker van smaak, ik maak dit zeker opnieuw.', 4, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Goed recept, maar de bereiding duurde wat langer.', 3, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Heel lekker en duidelijke instructies.', 5, 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Prima recept, maar er mocht wat meer kruiden in.', 4, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);