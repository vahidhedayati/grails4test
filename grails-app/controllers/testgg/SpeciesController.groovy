package testgg

class SpeciesController {

    def index() { }

    def createSpecies(Animal a) {
        [animal: a]
    }
}

class Animal {
    String name
    int height

    static constraints = {
        height min: 1
    }
}
