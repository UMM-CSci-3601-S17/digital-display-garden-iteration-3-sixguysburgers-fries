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


    inputName: string;
    filename:string;
    uploadAttempted:boolean = false;


    handleUpload() {
        this.inputName = this.nameInput.nativeElement.value;
        console.log("inputName = " + this.inputName);
        this.iu.upload(this.nameInput).subscribe(
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
