import { Component, OnInit } from '@angular/core';


@Component({
    selector: 'admin-component',
    templateUrl: 'admin-component.html',
})

export class AdminComponent implements OnInit {
    url : string = API_URL;
    constructor() {

    }

    ngOnInit(): void {

    }
}