import {Component, OnInit, ViewChild} from '@angular/core';
import { AdminService } from './admin.service';


@Component({
    selector: 'take-photo-component',
    templateUrl: 'take-photo.component.html',
})

export class TakePhotoComponent implements OnInit {

    @ViewChild('fu') fu;

    filename:string;
    uploadAttempted:boolean = false;

    handleUpload() {
        this.fu.upload().subscribe(
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