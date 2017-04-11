import {Component, OnInit, ViewChild} from '@angular/core';
import { AdminService } from './admin.service';


@Component({
    selector: 'update-component',
    templateUrl: 'update.component.html',
})

export class UpdateComponent implements OnInit {

    @ViewChild('fu') fu;

    filename:string;
    uploadAttempted:boolean = false;

    handleUpload(){
        this.fu.update().subscribe(
            response => {
                this.filename = response.json();
                this.uploadAttempted = true;
            },
            err => {
                this.uploadAttempted = true;
            }
        );
    }

    fName:string;
    clearAttempted:boolean = false;

    clearDb(){
        this.fu.clear().subscribe(
            response => {
                this.fName = response.json();
                this.clearAttempted = true;
            },
            err => {
                this.clearAttempted = true;
            }
        );
    }

    ngOnInit(): void {

    }
}
