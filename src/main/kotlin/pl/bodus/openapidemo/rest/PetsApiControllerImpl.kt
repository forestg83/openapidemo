package pl.bodus.openapidemo.rest

import org.springframework.http.ResponseEntity
import pl.bodus.openapidemo.server.petstore.api.Pet
import pl.bodus.openapidemo.server.petstore.api.PetApiController

class PetsApiControllerImpl() : PetApiController() {

    override fun findPetsByStatus(
        status: List<String>
    ): ResponseEntity<List<Pet>> {
        return ResponseEntity.ok(
            listOf(
                Pet(
                    name = "Bulky",
                    photoUrls = emptyList(),
                    id = 123L
                )
            )
        )
    }

}