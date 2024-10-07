package pl.bodus.openapidemo.repository

import org.springframework.stereotype.Repository
import pl.bodus.petstore.api.PetApi as PetsClientApi
import pl.bodus.petstore.model.Pet as PetDto

@Repository
class PetsRepository(val petsApi: PetsClientApi) {
    fun listPets(): List<PetDto> {
        return petsApi.findPetsByStatus(listOf(PetsClientApi.StatusFindPetsByStatus.pending))
    }
}