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
        NOW(),
        NOW()
    ),
    (
        'Pannenkoeken',
        'Luchtige pannenkoeken voor ontbijt of lunch.',
        '1. Meng bloem, melk en eieren tot een glad beslag. 2. Verhit boter in een koekenpan. 3. Giet een dunne laag beslag in de pan. 4. Bak beide kanten goudbruin.',
        20,
        4,
        NOW(),
        NOW()
    ),
    (
        'Caesarsalade',
        'Een frisse salade met kip, sla, croutons en dressing.',
        '1. Was de sla en snijd deze in stukken. 2. Bak de kip gaar en snijd in reepjes. 3. Meng de sla met kip en croutons. 4. Voeg de dressing en Parmezaanse kaas toe.',
        25,
        2,
        NOW(),
        NOW()
    ),
    (
        'Tomatensoep',
        'Een eenvoudige soep met rijpe tomaten en kruiden.',
        '1. Fruit ui en knoflook in een pan. 2. Voeg tomaten en groentebouillon toe. 3. Laat 20 minuten koken. 4. Pureer de soep en breng op smaak met peper en zout.',
        30,
        4,
        NOW(),
        NOW()
    ),
    (
        'Gebakken zalm met groenten',
        'Zalmfilet met verse groenten als complete maaltijd.',
        '1. Verwarm de oven voor op 180 graden. 2. Kruid de zalm met peper, zout en citroen. 3. Snijd de groenten en leg alles in een ovenschaal. 4. Bak ongeveer 15 tot 20 minuten tot de zalm gaar is.',
        30,
        2,
        NOW(),
        NOW()
    );