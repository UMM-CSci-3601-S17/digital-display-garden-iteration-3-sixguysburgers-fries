import { Component, OnInit } from '@angular/core';


@Component({
    selector: 'admin-component',
    templateUrl: 'admin-component.html',
    styles: ['input.camera-launch {opacity: 0;}',],
})

export class AdminComponent implements OnInit {
    url : String = API_URL;
    constructor() {

    }

    ngOnInit(): void {

    }
}