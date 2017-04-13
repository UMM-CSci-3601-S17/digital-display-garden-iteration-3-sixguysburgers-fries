import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from "rxjs";
import {Plant} from "../plants/plant";

@Injectable()
export class AdminService {
    private url: string = API_URL;
    constructor(private http:Http) { }

    getUploadIds(): Observable<string[]> {
        return this.http.request(this.url + "uploadIds").map(res => res.json());
    }

    getLiveUploadId(): Observable<string> {
        return this.http.request(this.url + "liveUploadId").map(res => res.json());
    }

    getGraphData(): Observable<any[][]> {
        return this.http.request(this.url + "getData").map(res => res.json());
    }

    postGraphData(): Observable<any> {
        return this.http.request(this.url + "postData").map(res => res.json());
    }

    getGardenLocations(): Observable<Plant[]> {
        return this.http.request(API_URL + "/gardenLocations").map(res => res.json());
    }

    getInfoForOneBed(bed: string): Observable<any[][]> {
        return this.http.request(API_URL + "/getBedData/" + bed).map(res => res.json());
    }

}