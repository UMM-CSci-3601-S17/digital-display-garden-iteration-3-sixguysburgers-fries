// Imports
import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {PlantListComponent} from "./plants/plant-list.component";
import {PlantComponent} from "./plants/plant.component";
import {AdminComponent} from "./admin/admin.component";
import {ExportComponent} from "./admin/export.component";
import {ImportComponent} from "./admin/import.component";
import {UpdateComponent} from "./admin/update.component";
import {BedComponent} from "./plants/bed.component";
import {GraphComponent} from "./admin/google-charts.component";
import {UploadPhotoComponent} from "./admin/upload-photo.component";


// Route Configuration
export const routes: Routes = [
    { path: '', component: BedComponent },
    { path: 'plants/:plantID', component: PlantComponent },
    { path: 'admin', component: AdminComponent},
    { path: 'admin/exportData', component: ExportComponent},
    { path: 'admin/importData', component: ImportComponent},
    { path: 'bed/:gardenLocation', component: BedComponent },
    { path: 'admin/charts', component: GraphComponent},
    { path: 'admin/updateData' , component: UpdateComponent},
    { path: 'bed/:gardenLocation', component: BedComponent },
    { path: 'bed/:gardenLocation', component: BedComponent },
    { path: 'admin/uploadPhoto', component: UploadPhotoComponent },

];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);