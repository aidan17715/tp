# SudoCook Architecture

This diagram reflects the implemented architecture in `src/main/java/seedu/sudocook`, not features described only in documentation.

```mermaid
flowchart LR
    user["CLI User"]

    subgraph runtime["Application Runtime"]
        ui["Ui: input and output"]
        app["SudoCook: startup loop and routing"]
        parser["Parser: input to command"]
        commands["Command hierarchy"]
    end

    subgraph domain["Domain State"]
        inventory["Inventory"]
        recipeBook["RecipeBook"]
        recipe["Recipe"]
        ingredient["Ingredient"]
        fuzzy["FuzzySearch"]
    end

    subgraph persistence["Persistence"]
        storage["Storage: JSON serialization"]
        inventoryJson[("data/inventory.json")]
        recipesJson[("data/recipes.json")]
    end

    user -->|types commands| ui
    ui -->|reads input| app
    app -->|parses text| parser
    parser -->|creates| commands
    app -->|dispatches| commands

    commands -->|reads and writes| inventory
    commands -->|reads and writes| recipeBook
    commands -->|shows messages| ui

    inventory -->|stores| ingredient
    recipeBook -->|stores| recipe
    recipe -->|depends on| ingredient

    inventory -->|searches with| fuzzy
    recipeBook -->|searches with| fuzzy

    app -->|loads and saves| storage
    storage -->|hydrates| inventory
    storage -->|hydrates| recipeBook
    storage --> inventoryJson
    storage --> recipesJson

    inventory -->|prints via| ui
    recipeBook -->|prints via| ui
```

## Notes

- `SudoCook` owns application startup, loads persisted state, runs the input loop, and routes commands using `instanceof`.
- `Parser` converts raw CLI text into a concrete `Command` subclass.
- The command layer is broad but shallow: most command classes delegate directly to `Inventory`, `RecipeBook`, or both.
- `Storage` is a simple file-based persistence layer that loads at startup and saves on shutdown using JSON files under `data/`.
- Output is not isolated to a single presentation layer. Commands, `Inventory`, and `RecipeBook` all call `Ui` directly.
