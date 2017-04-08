import {Component, OnInit, ViewChild} from '@angular/core';
import { AdminService } from './admin.service';


@Component({
    selector: 'take-photo-component',
    templateUrl: 'take-photo.component.html',
    styles: ['video.preview-window { display: block;margin: 0 auto; }',
     'button.capture { display: block;margin: 0 auto; }'],
})

export class TakePhotoComponent implements OnInit {

    ngOnInit(): void {

    }
}