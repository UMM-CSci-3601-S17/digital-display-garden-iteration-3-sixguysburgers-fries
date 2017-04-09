/**
 * Created by holma198 on 4/9/17.
 */

import {Component, OnInit} from '@angular/core';
import { Ng2GoogleChartsModule } from 'ng2-google-charts';
import {AdminService} from "./admin.service";
import {Observable} from "rxjs";

@Component({
    templateUrl: 'google-charts.component.html'
})

// Component class
export class GraphComponent {

    public text: string;
    private uploadIds: string[];
    private stuff: Observable<string[]>;
    constructor(private adminService: AdminService) {
        this.text = "Hello world!";
    }

    /*
     ngOnInit(): void {
     this.adminService.getUploadIds()
     .subscribe(result => this.uploadIds = result , err => console.log(err));
     }*/

    public line_ChartData = {
        chartType: `LineChart`,
        dataTable: [
            ['Year', 'Sales', 'Expenses'],
            ['2004', 1000, 400],
            ['2005', 1170, 460],
            ['2006', 660, 1120],
            ['2007', 1030, 540]],
        options: {'title': 'dataAndStuff'},
    };

}