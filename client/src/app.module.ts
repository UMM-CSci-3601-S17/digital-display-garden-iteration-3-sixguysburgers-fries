import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { HttpModule, JsonpModule } from '@angular/http';

import { AppComponent }         from './app/app.component';
import { NavbarComponent } from './app/navbar/navbar.component';
import { BedComponent } from './app/plants/bed.component';
import { PlantListComponent } from './app/plants/plant-list.component';
import { PlantComponent } from './app/plants/plant.component';
import { PlantListService } from './app/plants/plant-list.service';
import { routing } from './app/app.routes';
import { FormsModule } from '@angular/forms';

import { PipeModule } from './pipe.module';
import {AdminComponent} from "./app/admin/admin.component";
import {ExportComponent} from "./app/admin/export.component";
import {AdminService} from "./app/admin/admin.service";
import {ImportComponent} from "./app/admin/import.component";
import {FileUploadComponent} from "./app/admin/file-upload.component";
import {UpdateComponent} from "./app/admin/update.component";
import { NguiPopupModule } from '@ngui/popup';
import {GraphComponent} from "./app/admin/google-charts.component";
import { Ng2GoogleChartsModule } from 'ng2-google-charts';
import {ImageUploadComponent} from "./app/admin/image-upload.component";
import {UploadPhotoComponent} from "./app/admin/upload-photo.component";




@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        JsonpModule,
        routing,
        FormsModule,
        PipeModule,
        NguiPopupModule,
        Ng2GoogleChartsModule,
    ],
    declarations: [
        AppComponent,
        NavbarComponent,
        PlantListComponent,
        PlantComponent,
        AdminComponent,
        ExportComponent,
        ImportComponent,
        FileUploadComponent,
        BedComponent,
        UpdateComponent,
        GraphComponent,
        ImageUploadComponent,
        BedComponent,
        UploadPhotoComponent,

    ],
    providers: [ PlantListService, AdminService ],
    bootstrap: [ AppComponent ]
})

export class AppModule {}