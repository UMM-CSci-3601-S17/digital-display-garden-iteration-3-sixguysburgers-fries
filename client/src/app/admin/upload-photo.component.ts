import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AdminService } from './admin.service';
import {ImageUploadComponent} from "./image-upload.component";


@Component({
    selector: 'upload-photo-component',
    templateUrl: 'upload-photo.component.html',
})

export class UploadPhotoComponent implements OnInit {

    @ViewChild('iu') iu;
    @ViewChild('nameInput') nameInput: ElementRef;

    newFileName: string;
    flowerName: string;
    filename:string;
    uploadAttempted:boolean = false;


    handleUpload() {
        console.log("inputName = " + this.newFileName);
        this.iu.upload(this.newFileName, this.flowerName).subscribe(
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
