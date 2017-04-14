// import { ComponentFixture, TestBed, async } from "@angular/core/testing";
// import { Plant } from "./plant";
// import { Observable } from "rxjs";
// import { PipeModule } from "../../pipe.module";
// import {PlantListComponent} from "./plant-list.component";
// import {PlantListService} from "./plant-list.service";
//
// describe("Plant list", () => {
//
//     let plantList: PlantListComponent;
//     let fixture: ComponentFixture<PlantListComponent>;
//
//     let plantListServiceStub: {
//         getPlants: () => Observable<Plant[]>
//     };
//
//     beforeEach(() => {
//         // stub UserService for test purposes
//         plantListServiceStub = {
//             getPlants: () => Observable.of([
//                     {
//                         id: "chris_id",
//                         plantID: "483352",
//                         plantType: "plant",
//                         commonName: "UMM",
//                         cultivar: "bob@this.that",
//                         source: "tt",
//                         gardenLocation: "Lab",
//                         year: 0,
//                         pageURL: "",
//                         plantImageURLs: [""],
//                         recognitions: [""],
//
//
//         },
//                 {
//                     id: "chris_id",
//                     plantID: "483352",
//                     plantType: "plant",
//                     commonName: "UMM",
//                     cultivar: "bob@this.that",
//                     source: "tt",
//                     gardenLocation: "Lab",
//                     year: 0,
//                     pageURL: "",
//                     plantImageURLs: [""],
//                     recognitions: [""],
//                 },
//                 {
//                     id: "chris_id",
//                     plantID: "3368798",
//                     plantType: "plant",
//                     commonName: "UMM",
//                     cultivar: "pizza@this.that",
//                     source: "ss",
//                     gardenLocation: "Library",
//                     year: 0,
//                     pageURL: "",
//                     plantImageURLs: [""],
//                     recognitions: [""],
//                 }
//                 ])
//         };
//
//         TestBed.configureTestingModule({
//             imports: [PipeModule],
//             declarations: [ PlantListComponent ],
//             // providers:    [ UserListService ]  // NO! Don't provide the real service!
//             // Provide a test-double instead
//             providers:    [ { provide: PlantListService, useValue: plantListServiceStub } ]
//         })
//     });
//
//     beforeEach(async(() => {
//         TestBed.compileComponents().then(() => {
//             fixture = TestBed.createComponent(PlantListComponent);
//             plantList = fixture.componentInstance;
//             fixture.detectChanges();
//         });
//     }));
//
//     it("contains all the plants", () => {
//         expect(plantList.plants.length).toBe(3);
//     });
//
//     // it("contains a plant commonName 'To'", () => {
//     //     expect(plantList.plants.some((plant: Plant) => plant.commonName === "To" )).toBe(true);
//     // });
//     //
//     // it("contain a plant named 'Hi'", () => {
//     //     expect(plantList.plants.some((plant: Plant) => plant.commonName === "Hi" )).toBe(true);
//     // });
//     //
//     // it("doesn't contain a plant with cultivar named 'pizza@this.that'", () => {
//     //     expect(plantList.plants.some((plant: Plant) => plant.cultivar === "pizza@this.that" )).toBe(false);
//     // });
//     //
//     // it("has two plants that have source 'ss'", () => {
//     //     expect(plantList.plants.filter((plant: Plant) => plant.source === "ss").length).toBe(2);
//     // });
//
// });