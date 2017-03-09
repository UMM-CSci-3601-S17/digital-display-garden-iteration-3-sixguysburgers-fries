import { Component, OnInit } from '@angular/core';
import { PlantListService } from "./plant-list.service";
import { Plant } from "./plant";
import { FilterBy } from "./filter.pipe";

@Component({
    selector: 'plant-list-component',
    templateUrl: 'plant-list.component.html',
    providers: [ FilterBy ]
})

export class PlantListComponent implements OnInit {
    public plants: Plant[];
    public locations: Plant[];

    constructor(private plantListService: PlantListService) {
        // this.plants = this.plantListService.getPlants();
    }

    ngOnInit(): void {
        this.plantListService.getPlants().subscribe(
            plants => this.plants = plants,
            err => {
                console.log(err);
            }
        );

        this.plantListService.getGardenLocations().subscribe(
            locations => this.locations = locations,
            err => {
                console.log(err);
            }
        );
    }
}