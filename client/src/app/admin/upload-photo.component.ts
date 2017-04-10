import {Component, OnInit, ViewChild} from '@angular/core';
import { AdminService } from './admin.service';
import {ImageUploadComponent} from "./image-upload.component";


@Component({
    selector: 'upload-photo-component',
    templateUrl: 'upload-photo.component.html',
})

export class UploadPhotoComponent implements OnInit {

    @ViewChild('iu') iu;

    filename:string;
    uploadAttempted:boolean = false;

    handleUpload(){
        console.log("this.iu.upload() = " + this.iu);
        this.iu.upload().subscribe(
            response => {
                this.filename = response.json();
                this.uploadAttempted = true;
            },
            err => {
                this.uploadAttempted = true;
            }

        );
    }

    ngOnInit(): void {

    }
}
